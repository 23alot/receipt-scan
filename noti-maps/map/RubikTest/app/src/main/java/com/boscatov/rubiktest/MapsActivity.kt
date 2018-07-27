package com.boscatov.rubiktest

import android.Manifest
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.Toast
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_maps.*
import java.util.Collections.list


class MapsActivity : AppCompatActivity(), GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {


    private lateinit var mMap: GoogleMap
    private var googleAPI = GoogleAPI.create()
    lateinit var googleApiClient: GoogleApiClient
    var currentLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                if(currentLocation == null) {
                    currentLocation = location
                    Log.d("currentLocation", currentLocation.toString())
                    val marker = LatLng(location.latitude, location.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
                }

            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

            override fun onProviderEnabled(provider: String) {}

            override fun onProviderDisabled(provider: String) {}
        }
        checkPermission()
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener)
        googleApiClient = GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build()
        search.setOnClickListener {
            getDirection("${currentLocation?.latitude},${currentLocation?.longitude}",
                    "${latitude.text},${longitude.text}", "walking")
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        checkPermission()
        mMap.isMyLocationEnabled = true
        mMap.setOnMyLocationButtonClickListener(this)
        mMap.setOnMyLocationClickListener(this)
    }
    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_LOCATION_REQUEST_CODE)
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.size == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermission()
            } else {
                // Permission was denied. Display an error message.
            }
        }
    }

    override fun onMyLocationButtonClick(): Boolean {

        return false
    }

    override fun onMyLocationClick(p0: Location) {

    }
   
    fun getDirection(origin: String, destination: String, mode: String) {
        val latlong = destination.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val latitude = latlong[0].toDouble()
        val longitude = latlong[1].toDouble()
        val location = LatLng(latitude, longitude)
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(location))
        val th = Thread(Runnable {
            try {

                val checkTest = googleAPI.getDirection(origin, destination, key, mode).execute()
                if(checkTest.body() != null) {
                    val data = checkTest.body()!!
                    runOnUiThread { displayDirection(getPolylines(data.routes[0].legs[0])) }
                }

            }
            catch (e: Exception) {
            }
        })
        th.start()
    }
    fun getPolylines(leg: Leg): ArrayList<String> {
        val result = ArrayList<String>()
        for(a in leg.steps) {
            result.add(a.polyline.points)
        }
        return result
    }
    fun displayDirection(dir: ArrayList<String>) {

        for (i in 0 until dir.size) {
            val settings = PolylineOptions()
            settings.color(Color.RED)
            settings.width(10f)
            settings.addAll(PolyUtil.decode(dir[i]))
            mMap.addPolyline(settings)
        }
    }

    companion object {
        val key = "AIzaSyAQFQjS4TPazqFXhH8pOUu4urwL96Nfunk"
        val LOCATION_UPDATE_MIN_DISTANCE = 10
        val LOCATION_UPDATE_MIN_TIME = 5000
        val MY_LOCATION_REQUEST_CODE = 123
    }
}
