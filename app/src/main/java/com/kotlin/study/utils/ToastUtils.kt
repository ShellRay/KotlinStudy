package com.kotlin.study.utils

import android.content.Context
import android.text.TextUtils
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.kotlin.study.R
import com.kotlin.study.manager.ActivityStack

/**
 *
 * Copyright: Copyright (c) 2016
 */
object ToastUtils {
    private var mToast: Toast? = null

    /**
     * 多次弹出信息只显示最新的一次
     * @param text
     * @param isCheckTopActivity 是否检测context是topActivity
     */
    fun showToast(context: Context?, text: CharSequence, isCheckTopActivity: Boolean) {
        var context: Context? = context ?: return

        if (TextUtils.isEmpty(text))
            return

        if (isCheckTopActivity) {
            val topActivity = ActivityStack.topActivity
            if (topActivity !== context && context is ContextThemeWrapper) {
                val baseContext = context.baseContext
                if (baseContext !== topActivity) {
                    return
                }
                context = baseContext
            }
        }

        if (mToast == null) {
            mToast = Toast.makeText(context!!.applicationContext, text, Toast.LENGTH_SHORT)
        } else {
            mToast!!.setText(text)
            mToast!!.duration = Toast.LENGTH_SHORT
        }

        mToast!!.setGravity(Gravity.CENTER_VERTICAL, 0, ConvenientUtil.convertDpToPixel(context!!, 40))
        mToast!!.show()
    }

    /**
     * 多次弹出信息只显示最新的一次
     */
    fun showToast(context: Context?, resId: Int, isCheckTopActivity: Boolean) {
        if (context == null)
            return

        showToast(context, context.getText(resId), isCheckTopActivity)
    }

    /**
     * 多次弹出信息只显示最新的一次
     */
    fun showToast(context: Context?, text: CharSequence) {
        if (context == null)
            return

        if (TextUtils.isEmpty(text))
            return

        //		Activity topActivity = ActivityStack.getTopActivity();
        //		if (topActivity != context && context instanceof ContextThemeWrapper) {
        //				Context baseContext = ((ContextThemeWrapper)context).getBaseContext();
        //				if (baseContext != topActivity) {
        //					return;
        //				}
        //				context = baseContext;
        //		}

        if (mToast == null) {
            mToast = Toast.makeText(context.applicationContext, text, Toast.LENGTH_SHORT)
        } else {
            mToast!!.setText(text)
            mToast!!.duration = Toast.LENGTH_SHORT
        }

        mToast!!.show()
    }

    /**
     * 自定义Toast
     * @param context
     * @param text
     */
    fun ShowCustomToast(context: Context, text: String) {
        val toast = Toast(context)
        val v = LayoutInflater.from(context).inflate(R.layout.customtoast_layout, null)
        val textView = v.findViewById<View>(R.id.tv_show_result) as TextView
        // toast.setMargin(0.5f, 0.5f);
        toast.view = v
        toast.setGravity(Gravity.BOTTOM, 0, ConvenientUtil.convertDpToPixel(context, 112))
        toast.duration = Toast.LENGTH_SHORT
        textView.text = text
        toast.show()
    }
    /**
     * 多次弹出信息只显示最新的一次
     */
    fun showToast(context: Context?, resId: Int) {
        if (context == null)
            return

        showToast(context, context.getText(resId))
    }

    /**
     * 去掉toast提示
     */
    fun cancelToast() {
        if (mToast != null)
            mToast!!.cancel()
    }

    fun noNetwork(context: Context) {
        showToast(context, "no network", true)
    }
}
