package com.kotlin.study.activity

import android.graphics.Color
import android.os.Bundle
import com.kotlin.study.BaseActivity
import com.kotlin.study.R
import android.support.design.widget.AppBarLayout
import android.view.View
import com.kotlin.study.adapter.ViewPagerAdapter
import com.kotlin.study.fragment.DemoFragment
import com.kotlin.study.widget.tableLayout.AppBarStateChangeListener
import kotlinx.android.synthetic.main.activity_float_table.*


class FloatTableActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_float_table)
        var viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        tablayout.setxTabDisplayNum(3)
        viewPagerAdapter.addItem(DemoFragment(), "收益总榜")
        viewPagerAdapter.addItem(DemoFragment(), "消费总榜")
        viewPagerAdapter.addItem(DemoFragment(), "我邀请的会员")
        viewpager.adapter = viewPagerAdapter
        tablayout.setupWithViewPager(viewpager)
        initAppBar()
        initListener()
    }

    private fun initListener() {
        iv_back_topic.setOnClickListener {
            finish()
        }

    }

    private fun initAppBar() {
        app_bar_topic.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                if (state === State.EXPANDED) {
                    iv_back_topic.setImageResource(R.mipmap.back_white)
                    toolbar_topic.setBackgroundColor(Color.argb(0, 0, 0, 0))
                    titleCenter.visibility = View.VISIBLE
                    titleCenter.setTextColor(resources.getColor(R.color.white))
                    //展开状态
                } else if (state === State.COLLAPSED) {
                    iv_back_topic.setImageResource(R.mipmap.search_back)
                    toolbar_topic.setBackgroundColor(Color.argb(255, 255, 255, 255))
                    titleCenter.visibility = View.VISIBLE
                    titleCenter.setTextColor(resources.getColor(R.color.black_alpha_4D))
                    //折叠状态
                } else {
                    iv_back_topic.setImageResource(R.mipmap.back_white)
                    toolbar_topic.setBackgroundColor(Color.argb(0, 0, 0, 0))
                    titleCenter.visibility = View.GONE
                    //中间状态
                }
            }
        })
    }
}