package com.example.jnrecycleviewitem

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.ParameterizedType
import java.util.*

/**
 * author  : Liushuai
 * time    : 2021/1/12 21:20
 * desc    :
 */
class JnAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var inflater: LayoutInflater? = null
    private var dataSets = ArrayList<JnDataItem<*, RecyclerView.ViewHolder>>()
    private var typeArrays = SparseArray<JnDataItem<*, RecyclerView.ViewHolder>>()

    init {
        inflater = LayoutInflater.from(context)
    }

    fun addItem(index: Int, item: JnDataItem<*, RecyclerView.ViewHolder>, notify: Boolean) {
        if (index > 0) {
            dataSets.add(index, item)

        } else {
            dataSets.add(item)
        }
        //插入位置 没指定插入最后
        val notifyPos = if (index > 0) index else dataSets.size - 1
        if (notify) {
            notifyItemInserted(notifyPos)
        }
    }

    //添加一个集合
    fun addItems(items: List<JnDataItem<*, RecyclerView.ViewHolder>>, notify: Boolean) {
        val start = dataSets.size
        for (item in items) {
            dataSets.add(item)
        }
        if (notify) {
            notifyItemRangeChanged(start, items.size)
        }
    }

    //删除指定位置的item
    fun removeItem(index: Int): JnDataItem<*, *>? {
        if (index > 0 && index < dataSets.size) {
            val remove = dataSets.removeAt(index)
            notifyItemRemoved(index)
            return remove
        } else {
            return null
        }
    }

    fun removeItem(item: JnDataItem<*, *>?) {
        if (item != null) {
            val index = dataSets.indexOf(item)
            removeItem(index)
        }
    }

    fun refreshItem(jnDataItem: JnDataItem<*, *>) {
        val index = dataSets.indexOf(jnDataItem)
        notifyItemChanged(index)
    }

    override fun getItemViewType(position: Int): Int {
        val dataItem = dataSets[position]
        val type = dataItem.javaClass.hashCode()
        if (typeArrays.indexOfKey(type) < 0) {
            typeArrays.put(type, dataItem)
        }
        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val dataItem = typeArrays[viewType]
        var view = dataItem.getItemView(parent)
        if (view == null) {
            val layout = dataItem.getItemLayoutRes()
            if (layout < 0) {
                RuntimeException("must override getItemView or getItemLayoutRes")

            }
            view = inflater!!.inflate(layout, parent, false)
        }
        return createViewHolderInternal(dataItem.javaClass, view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dataItem = dataSets[position]
        dataItem.onBindData(holder, position)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            val spanCount = layoutManager.spanCount
//            spanCount/sapanSize=每行item数
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (position < dataSets.size) {
                        val jnDataItem = dataSets.get(position)
                        val spanSize = jnDataItem.getSpanSize()
                        return if (spanSize <= 0) spanCount else spanSize
                    }
                    return spanCount
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return dataSets.size
    }

    private fun createViewHolderInternal(
            javaClass: Class<JnDataItem<*, RecyclerView.ViewHolder>>,
            view: View?): RecyclerView.ViewHolder {
        val superClass = javaClass.genericSuperclass
        if (superClass is ParameterizedType) {
            val arguments = superClass.actualTypeArguments
            for (argument in arguments) {
                //获取viewHolder类型  isAssignableFrom判断是否为某个类的父类
                if (argument is Class<*> && RecyclerView.ViewHolder::class.java.isAssignableFrom(argument)) {
                    return argument.getConstructor(View::class.java).newInstance(view) as RecyclerView.ViewHolder
                }
            }
        }
        return object : RecyclerView.ViewHolder(view!!) {}
    }
}