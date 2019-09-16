package com.kotlin.study.fragment

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
class FourthFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = layoutInflater.inflate(R.layout.fragment_fourth, null)
        return inflate
    }
}