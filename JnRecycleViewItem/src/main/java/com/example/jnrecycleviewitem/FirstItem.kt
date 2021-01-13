package com.example.jnrecycleviewitem

import android.graphics.Color
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * author  : Liushuai
 * time    : 2021/1/13 20:50
 * desc    :
 */
class FirstItem(val dataItem: ItemData) :JnDataItem<ItemData,RecyclerView.ViewHolder>(dataItem){
    override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
          holder.itemView.findViewById<TextView>(R.id.tv_first).setTextColor(Color.BLUE)
        holder.itemView.findViewById<TextView>(R.id.tv_first).setText(dataItem.name)
    }

    override fun getItemLayoutRes(): Int {
        return R.layout.first_item_layout
    }
}