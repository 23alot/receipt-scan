package com.boscatov.navigationdrawer.history

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.boscatov.navigationdrawer.MainActivity
import com.boscatov.navigationdrawer.scan.QRRequests.Check
import com.boscatov.navigationdrawer.R
import kotlinx.android.synthetic.main.fragment_item.view.*

class CheckFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val check: Check = (activity as MainActivity).curCheck
        val v: View = inflater.inflate(R.layout.fragment_item, null)
        v.total_sum.text = toRub(check.totalSum)
        v.nds18_sum.text = toRub(check.nds18)
        v.ecash.text = toRub(check.ecashTotalSum)
        v.cash.text = toRub(check.cashTotalSum)
        v.user.text = check.user
        v.buyer_address.text = check.buyerAddress
        v.sender_address.text = check.senderAddress
        v.operator.text = check.operator
        v.kkt.text = check.kktRegId
        v.shift_number.text = "${check.shiftNumber}"
        v.date_time.text = check.dateTime
        v.fiscal_document.text = "${check.fiscalDocumentNumber}"
        v.fiscal_drive.text = check.fiscalDriveNumber
        v.request_number.text = "${check.requestNumber}"
        v.retail_place.text = check.retailPlaceAddress
        v.inn.text = check.userInn
        val adapter = ItemAdapter()
        adapter.setItems(check.items)
        v.items.adapter = adapter
        v.items.layoutManager = LinearLayoutManager(activity)
        v.layout_container.setOnClickListener {
            (activity as MainActivity).removeFragment(this)
        }
        var move = false
        v.items.setOnTouchListener({
            _, event -> when (event.action) {
                MotionEvent.ACTION_MOVE -> move = true
                MotionEvent.ACTION_UP -> if(!move) (activity as MainActivity).removeFragment(this)
                else -> return@setOnTouchListener true
            }

            return@setOnTouchListener true
        })
        v.items.setOnClickListener {
            (activity as MainActivity).removeFragment(this)
        }
        return v
    }
    private fun toRub(value: Int): String {
        return "${value/100}."+ if (value % 100 < 10) "0${value%100}" else "${value%100}"
    }
}