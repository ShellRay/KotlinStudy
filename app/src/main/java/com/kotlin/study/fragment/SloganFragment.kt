package com.kotlin.study.fragment

import android.os.Bundle
import com.kotlin.study.R


/**
 * Description: 口号fragment 用于切换视频口号
 */

class SloganFragment : BaseAppCompatFragment() {


    companion object {
        @JvmStatic
        fun newInstance() = SloganFragment()
    }


    override fun initView(savedInstanceState: Bundle?) {

    }


    override fun getContentViewLayoutId() = R.layout.fragment_slogan
}