package com.xl.base.adapter.item

import android.view.View
import com.xl.base.R
import com.xl.base.adapter.recycler.RecyclerSupport
import com.xl.base.adapter.recycler.RecyclerVH
import kotlinx.android.synthetic.main.item_default_loading.view.*

/**
 * 默認Loading
 */
class DefaultLoadingCell : SortedItemCell {

    override fun order(): Long = 0

    override fun layoutResId() = R.layout.item_default_loading

    override fun itemId() = "DefaultLoadingCell"

    override fun itemContent() = "DefaultLoadingCell"

    override fun onCreateViewHolder(itemView: View, support: RecyclerSupport) =
        DefaultLoadingVH(itemView, support)
}

class DefaultLoadingVH(itemView: View, support: RecyclerSupport) : RecyclerVH(itemView, support) {
    init {
        itemView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(v: View?) {
                itemView.lottie_loading.cancelAnimation()
            }

            override fun onViewAttachedToWindow(v: View?) {
                itemView.lottie_loading.playAnimation()
            }
        })
    }
}