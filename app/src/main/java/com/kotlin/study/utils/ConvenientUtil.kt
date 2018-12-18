package com.kotlin.study.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.kotlin.study.activity.MainActivity

/**
 * Created by GG on 2018/2/27.
 */
class ConvenientUtil {

    companion object {

        fun convertDpToPixel(context: Context, dp: Int): Int {
            val scale = context.resources.displayMetrics.density
            return (dp * scale + 0.5f).toInt()
        }
    }
}