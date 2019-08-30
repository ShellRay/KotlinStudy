package com.kotlin.study.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.kotlin.study.activity.MainActivity

/**
 * Created by GG on 2018/2/27.
 */

fun Context.dp2px(dp: Int) :Int{

    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

class ConvenientUtil {//方法使用 ConvenientUtil.Companion.convertDpToPixel() 如果是object ConvenientUtil则直接用

    companion object {

        fun convertDpToPixel(context: Context, dp: Int): Int {
            val scale = context.resources.displayMetrics.density
            return (dp * scale + 0.5f).toInt()
        }
    }
}