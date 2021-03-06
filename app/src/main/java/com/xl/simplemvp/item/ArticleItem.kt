package com.xl.simplemvp.item

import android.view.View
import com.xl.base.adapter.recycler.RecyclerSupport
import com.xl.base.adapter.recycler.RecyclerVH
import com.xl.simplemvp.R
import com.xl.simplemvp.dataclass.Article
import com.xl.xl_base.adapter.item.ItemCell
import kotlinx.android.synthetic.main.item_layout.view.*

class ArticleItem(val data: Article) : ItemCell {
    override fun layoutResId(): Int = R.layout.item_layout

    override fun itemId(): String = "TitleItem"
    override fun itemContent(): String = "TitleItem"
    override fun onCreateViewHolder(itemView: View, support: RecyclerSupport): RecyclerVH =
        TitleHoller(itemView, support)

    class TitleHoller(itemView: View, support: RecyclerSupport) : RecyclerVH(itemView, support) {
        override fun bind(itemCell: ItemCell, payloads: MutableList<Any>) {
            val cell = itemCell as ArticleItem
            itemView.tv_item.text = cell.data.title
            itemView.tv_item.setOnClickListener {
                support.simpleCallback?.let { it1 -> it1(layoutPosition) }
            }
        }
    }
}


