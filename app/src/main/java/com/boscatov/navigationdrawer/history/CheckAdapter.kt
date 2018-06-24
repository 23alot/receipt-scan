package com.boscatov.navigationdrawer.history

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import com.boscatov.navigationdrawer.scan.QRRequests.Check
import com.boscatov.navigationdrawer.scan.QRRequests.PriorityCheckQueue
import com.boscatov.navigationdrawer.R
import kotlinx.android.synthetic.main.check_item.view.*

class CheckAdapter(private val listener: ItemClickListener) : RecyclerView.Adapter<CheckAdapter.CheckViewHolder>() {

    private var checkList: ArrayList<Check> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.check_item, parent, false)
        return CheckViewHolder(view)
    }

    override fun onBindViewHolder(holder: CheckViewHolder, position: Int) {
        holder.bind(checkList.elementAt(position), listener)
    }

    override fun getItemCount(): Int {
        return checkList.size
    }

    fun setItems(checks: PriorityCheckQueue) {
        while (true) {
            val check: Check? = checks.poll() ?: break
            checkList.add(check!!)
        }
        notifyDataSetChanged()
    }

    class CheckViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(check: Check, listener: ItemClickListener) {
            itemView.shop_name.text = check.user
            itemView.total_cost.text = toRub(check.totalSum)
            itemView.time.text = check.dateTime
            itemView.setOnClickListener {
                listener.onItemClick(check)
            }
        }
        private fun toRub(value: Int): String {
            return "${value/100}."+ if (value % 100 < 10) "0${value%100}" else "${value%100}"
        }
    }

}