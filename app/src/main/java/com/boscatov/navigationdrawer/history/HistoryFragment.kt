package com.boscatov.navigationdrawer.history

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.boscatov.navigationdrawer.MainActivity
import com.boscatov.navigationdrawer.scan.QRRequests.Check
import com.boscatov.navigationdrawer.scan.QRRequests.CheckStore
import com.boscatov.navigationdrawer.scan.QRRequests.PriorityCheckQueue
import com.boscatov.navigationdrawer.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_history.view.*

class HistoryFragment : Fragment() {
    var item = CheckFragment()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_history, null)
        val adapter = CheckAdapter(object : ItemClickListener {
            override fun onItemClick(check: Check) {
                if ((activity as MainActivity).isFragment) {
                    (activity as MainActivity).removeFragment(item)
                }
                (activity as MainActivity).curCheck = check
                item = CheckFragment()
                val ft = fragmentManager!!.beginTransaction()
                (activity as MainActivity).isFragment = true
                ft.add(R.id.history_layout, item)
                ft.commit()
            }
        })
        val sharedPreferences: SharedPreferences = activity!!.getSharedPreferences("Checks", Context.MODE_PRIVATE)
        val data: String? = sharedPreferences.getString("Array", null)
        val gson = GsonBuilder().create()
        val store: CheckStore = if(data != null) gson.fromJson(data, CheckStore::class.java) else CheckStore(PriorityCheckQueue())
        adapter.setItems(store.checkStore)
        v.recycler_view.adapter = adapter
        v.recycler_view.layoutManager = LinearLayoutManager(activity)
        return v
    }
}