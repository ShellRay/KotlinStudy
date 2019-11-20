package com.kotlin.study.activity

import android.os.Bundle
import com.kotlin.study.BaseActivity
import android.widget.TextView
import com.kotlin.study.R
import com.kotlin.study.widget.banner.BannerView
import com.kotlin.study.widget.banner.ViewPagerIndicator
import kotlinx.android.synthetic.main.activity_banner_view.*
import kotlinx.android.synthetic.main.activity_red_package_rain.*


/**
 * @author ShellRay
 * Created  on 2019/8/26.
 * @description
 */
class RedPackageRainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_red_package_rain)

        btnStart.setOnClickListener {
            redPacketRain.playRainAnimation(30,7,30 * 400)
        }


    }



}