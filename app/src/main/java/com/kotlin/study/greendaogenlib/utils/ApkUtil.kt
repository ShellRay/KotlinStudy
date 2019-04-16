package com.kotlin.study.greendaogenlib.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.text.TextUtils


import java.util.UUID

/**
 * @Description: app信息处理类
 */
object ApkUtil {
    /**
     * @throws
     * @Description: 获得独一无二的Psuedo ID
     * @param:
     * @return:
     */
    fun getUniquePsuedoID(context: Context): String {
        //        String deviceId = PreferencesUtil.getString(context, Constants.DEVICE_ID, "");
        var deviceId = "123456"
        if (!TextUtils.isEmpty(deviceId)) {
            return deviceId
        }
        val m_szDevIDShort = "35" +
                Build.BOARD.length % 10 + Build.BRAND.length % 10 +
                Build.CPU_ABI.length % 10 + Build.DEVICE.length % 10 +
                Build.DISPLAY.length % 10 + Build.HOST.length % 10 +
                Build.ID.length % 10 + Build.MANUFACTURER.length % 10 +
                Build.MODEL.length % 10 + Build.PRODUCT.length % 10 +
                Build.TAGS.length % 10 + Build.TYPE.length % 10 +
                Build.USER.length % 10 //13 位

        var serial = ""
        try {
            //API>=9 使用serial号
            serial = Build::class.java.getField("SERIAL").get(null).toString()
        } catch (e: Exception) {
            //serial需要一个初始化
            serial = UUID.randomUUID().toString()
        }

        //使用硬件信息拼凑出来的15位号码
        deviceId = UUID(m_szDevIDShort.hashCode().toLong(), serial.hashCode().toLong()).toString()
        //        PreferencesUtil.putString(context, Constants.DEVICE_ID, deviceId);
        return deviceId
    }

    // 版本名
    fun getVersionName(context: Context): String {
        return getPackageInfo(context)!!.versionName
    }

    // 版本号
    fun getVersionCode(context: Context): Int {
        return getPackageInfo(context)!!.versionCode
    }

    /**
     * 获取包名
     *
     * @param context
     * @return
     */
    private fun getPackageInfo(context: Context): PackageInfo? {
        var pi: PackageInfo? = null

        try {
            val pm = context.packageManager
            pi = pm.getPackageInfo(context.packageName,
                    PackageManager.GET_CONFIGURATIONS)

            return pi
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return pi
    }

    /**
     * 判断当前应用是否是debug状态
     *
     * @param context
     * @return
     */
    fun isApkInDebug(context: Context): Boolean {
        try {
            val info = context.applicationInfo
            return info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        } catch (e: Exception) {
            return false
        }

    }
}