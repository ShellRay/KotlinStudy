package com.kotlin.study.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.study.R

/**
 * @author ShellRay
 * Created  on 2019/9/16.
 * @description
 */
class SecondFragment : Fragment() {

    var statusBar: View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = layoutInflater.inflate(R.layout.fragment_second, null)

        statusBar = inflate.findViewById<View>(R.id.status_bar)
        return inflate
    }

    fun changeStatusBackgroundAlphaValue(alphaValue: Int) {
        statusBar?.setBackgroundColor(Color.argb(alphaValue, 239, 239, 239))
    }
}