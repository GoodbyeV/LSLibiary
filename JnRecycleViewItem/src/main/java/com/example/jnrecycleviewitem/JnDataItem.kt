package com.example.jnrecycleviewitem

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * author  : Liushuai
 * time    : 2021/1/12 21:16
 * desc    :
 */
abstract class JnDataItem<DATA ,VH:RecyclerView.ViewHolder>(val data:DATA) {
    private lateinit var adapter: JnAdapter
    abstract fun onBindData(holder: RecyclerView.ViewHolder,position:Int)

    fun setAdapter(adapter: JnAdapter) {
        this.adapter=adapter
    }

    open fun getItemLayoutRes():Int{
        return  -1;
    }

    open fun getItemView(parent:ViewGroup): View?{
        return null
    }

    //刷新列表
    fun refreshItem(){
       adapter.refreshItem(this)
    }
    //移除
    fun removeItem(){
        adapter.removeItem(this)
    }

     fun getSpanSize():Int{
         return 0
     }
}