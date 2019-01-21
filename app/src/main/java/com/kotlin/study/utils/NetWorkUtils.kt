package com.kotlin.study.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager


/**
 * Description:
 */

object NetWorkUtils {

    /**
     * 判断当前网络时候连接
     */
    @SuppressLint("MissingPermission")
    @JvmStatic
    fun isNetWorkConnected(context: Context?): Boolean {
        if (context != null) {
            val mgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = mgr.activeNetworkInfo//返回当前网络的详细信息
            if (networkInfo != null) {
                return networkInfo.isAvailable
            }
        }
        return false
    }


}