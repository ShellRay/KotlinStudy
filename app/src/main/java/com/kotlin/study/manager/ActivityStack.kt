package com.kotlin.study.manager

import android.app.Activity
import java.lang.Exception

import java.util.ArrayList

/**
 *
 *
 */
object ActivityStack {
    var mActivityList: MutableList<Activity>? = ArrayList()

    /**
     * 获取最顶端Activity
     */
    val topActivity: Activity?
        get() = if (mActivityList != null && mActivityList!!.size > 0) {
            mActivityList!![mActivityList!!.size - 1]
        } else null

    /**
     * 获取最顶的上一个Activity
     */
    val topPreviousActivity: Activity?
        get() = if (mActivityList != null && mActivityList!!.size > 1) {
            mActivityList!![mActivityList!!.size - 2]
        } else null

    /**
     * 判断Activity栈是否为空
     */
    val isActivityStackEmpty: Boolean
        get() = if (mActivityList == null) {
            true
        } else mActivityList!!.isEmpty()

    fun remove(activity: Activity) {
        if (mActivityList == null) {
            return
        }

        mActivityList!!.remove(activity)
    }

    fun add(activity: Activity) {
        if (mActivityList == null) {
            return
        }

        mActivityList!!.add(activity)
    }

    fun finishProgram() {
        if (mActivityList == null) {
            return
        }

        for (activity in mActivityList!!) {
            activity.finish()
        }
    }

    fun isActivityRunning(className: String?): Boolean {
        if (className == null || mActivityList == null) {
            return false
        }

        for (activity in mActivityList!!) {
            if (activity.javaClass.name == className) {
                return true
            }
        }

        return false
    }

    /**
     * 根据类名获取Activity
     */
    fun getActivity(className: String?): Activity? {
        if (className != null && !isActivityStackEmpty) {
            var activity: Activity
            for (i in mActivityList!!.indices.reversed()) {
                activity = mActivityList!![i]
                if (activity.javaClass.name == className) {
                    return activity
                }
            }
        }

        return null
    }

    /**
     * 判断Activity是否运行在栈顶
     */
    fun isActivityOnTop(className: String): Boolean {
        val activity = topActivity
        return if (activity != null) {
            activity.javaClass.name == className
        } else false
    }

    fun recreateAllOtherActivity(activity: Activity) {
        var i = 0
        val size: Int? = mActivityList?.size
        while (i < size!!) {
            if (null != mActivityList?.get(i) && mActivityList!!.get(i) !== activity) {
                mActivityList!!.get(i).recreate()
            }
            i++
        }
    }

    /**
     * 退出应用程序
     */
    fun AppExit() {
        try {
            finishProgram()
            System.exit(0)
        } catch (e: Exception) {
        }
    }
}
