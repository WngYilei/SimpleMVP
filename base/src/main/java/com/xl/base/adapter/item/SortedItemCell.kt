package com.xl.base.adapter.item

import com.xl.xl_base.adapter.item.ItemCell

interface SortedItemCell : ItemCell {

    /**
     *  排序标志
     */
    fun order(): Long
}