package com.boscatov.navigationdrawer.scan

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.boscatov.navigationdrawer.R
import com.boscatov.navigationdrawer.history.CheckFragment
import kotlinx.android.synthetic.main.fragment_scan.view.*

class ScanFragment : Fragment() {
    var item = CheckFragment()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_scan, null)
        v.request_message.text = "Обработка запроса"
        return v
    }
}