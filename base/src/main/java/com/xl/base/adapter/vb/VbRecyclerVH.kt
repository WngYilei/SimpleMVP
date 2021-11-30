package com.xl.base.adapter.vb

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.xl.base.adapter.recycler.RecyclerSupport

open class VbRecyclerVH( vb: ViewBinding, val support: RecyclerSupport) :
    RecyclerView.ViewHolder(vb.root) {

    open fun bind(itemCell: VbItemCell, payloads: MutableList<Any> = mutableListOf()) {
        //empty
    }
}