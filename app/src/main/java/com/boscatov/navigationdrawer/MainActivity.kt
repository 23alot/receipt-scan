package com.boscatov.navigationdrawer

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.boscatov.navigationdrawer.scan.QRRequests.Check
import com.boscatov.navigationdrawer.scan.QRRequests.CheckStore
import com.boscatov.navigationdrawer.scan.QRRequests.PriorityCheckQueue
import com.boscatov.navigationdrawer.scan.QRRequests.QRApi
import com.boscatov.navigationdrawer.history.HistoryFragment
import com.google.gson.GsonBuilder
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import okhttp3.HttpUrl
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.boscatov.navigationdrawer.scan.ScanFragment
import kotlinx.android.synthetic.main.fragment_scan.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val qrApi: QRApi = QRApi.create()
    lateinit var curCheck: Check
    var isFragment: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        checkPermission()

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {

        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else if(isFragment) {
            removeFragment((supportFragmentManager.findFragmentById(R.id.container) as HistoryFragment).item)
        }
        else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment?
        var fragmentClass: Class<*>? = null
        when (item.itemId) {
            R.id.scan -> {
                fragmentClass = ScanFragment::class.java
                val scanIntegrator = IntentIntegrator(this)
                scanIntegrator.initiateScan()
            }
            R.id.history -> {
                fragmentClass = HistoryFragment::class.java
            }
        }
        if (fragmentClass != null) {
            try {
                fragment = (fragmentClass.newInstance() as Fragment)
                val fragmentManager = supportFragmentManager
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        title = item.title
        item.isChecked = true
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val scanningResult: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if(scanningResult != null && scanningResult.contents != null) {
            val scanInfo: String = scanningResult.contents
            addCheck(scanInfo)
        }
        else if((supportFragmentManager.findFragmentById(R.id.container) as ScanFragment) != null) {
            userMessage("")
        }
    }
    private fun addCheck(scanInfo: String) {
        val th = Thread(Runnable {
            val url = HttpUrl.parse("https://qrtests.herokuapp.com/receipts/get?$scanInfo")
            val tryCheck = HttpUrl.parse("https://qrtests.herokuapp.com/receipts/check?$scanInfo")
            try {

                val checkTest = qrApi.check(tryCheck.toString()).execute()
                if(checkTest.body() != null) {
                    if(checkTest.body()!!.isLegal) {
                        val test = qrApi.get(url.toString()).execute()
                        if (test.body() != null) {
                            val check: Check = test.body()!!
                            addToCache(check)
                            userMessage("Чек успешно сохранен")
                        }
                        else {
                            userMessage("Не удалось получить ответ от сервера")
                        }
                    }
                    else {
                        userMessage("Чек не валиден")
                    }
                }

            }
            catch (e: Exception) {
                userMessage("Не удалось подключиться к серверу")
            }
        })
        th.start()
    }
    private fun userMessage(msg: String?) {
        runOnUiThread {
            (supportFragmentManager.findFragmentById(R.id.container) as ScanFragment).request_message.text = msg
        }
    }
    private fun addToCache(check: Check) {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("Checks", Context.MODE_PRIVATE)
        val data: String? = sharedPreferences.getString("Array", null)
        val gson = GsonBuilder().create()
        val store: CheckStore = if(data != null) gson.fromJson(data, CheckStore::class.java) else CheckStore(PriorityCheckQueue())
        store.checkStore.add(check)
        sharedPreferences.edit().putString("Array", gson.toJson(store)).apply()
    }
    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CAMERA),
                    123)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.INTERNET),
                    123)
        }
    }
    fun removeFragment(item: Fragment) {
        supportFragmentManager.beginTransaction().remove(item).commit()
        isFragment = false
    }
}
