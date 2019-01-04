package com.kotlin.study.adapter

import android.support.v4.app.FragmentPagerAdapter
import com.kotlin.study.fragment.SloganFragment
import android.support.v4.app.FragmentManager

/**
 * Date:    2018/5/15 10:59
 * Description: 闪屏视频适配器
 */

class SplashVideoFragmentAdapter(var mFragmentList: MutableList<SloganFragment>, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {


    override fun getItem(position: Int) = mFragmentList[position]


    override fun getCount() = mFragmentList.size


}