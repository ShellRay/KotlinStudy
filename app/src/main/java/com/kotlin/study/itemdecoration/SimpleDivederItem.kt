package com.kotlin.study.itemdecoration

import android.graphics.Color

/**
 * Created by GG on 2018/12/14.
 */
class SimpleDivederItem(orientation: Int) : AbsDividerItemDecoration(orientation) {
    override fun getItemDecoration(): Decoration {
         return Decoration(0, 20, Color.TRANSPARENT)
    }
}