package com.boscatov.navigationdrawer.history

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import com.boscatov.navigationdrawer.scan.QRRequests.Item
import com.boscatov.navigationdrawer.R
import kotlinx.android.synthetic.main.item.view.*

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private val checkList: ArrayList<Item> = ArrayList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(checkList.elementAt(position))
    }

    override fun getItemCount(): Int {
        return checkList.size
    }

    fun setItems(item: Collection<Item>) {
        checkList.addAll(item)
        notifyDataSetChanged()
    }

    fun clearItems() {
        checkList.clear()
        notifyDataSetChanged()
    }
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Item) {
            itemView.name.text = item.name
            itemView.barcode.text = item.barcode
            itemView.cost.text = "${item.quantity}.000 * ${toRub(item.price)} =${toRub(item.sum)}"
            itemView.nds10.text = toRub(item.ndsCalculated10)
            itemView.nds18.text = toRub(item.ndsCalculated18)
        }
        private fun toRub(value: Int): String {
            return "${value/100}."+ if (value % 100 < 10) "0${value%100}" else "${value%100}"
        }
    }
}