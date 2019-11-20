package com.kotlin.study.activity

import android.os.Bundle
import com.kotlin.study.BaseActivity
import android.widget.TextView
import com.kotlin.study.R
import com.kotlin.study.widget.banner.BannerView
import com.kotlin.study.widget.banner.ViewPagerIndicator
import kotlinx.android.synthetic.main.activity_banner_view.*


/**
 * @author ShellRay
 * Created  on 2019/8/26.
 * @description
 */
class BannerActivity : BaseActivity() {

    private var mTv_hint: TextView? = null
    private var mBannerView: BannerView? = null
    private var mIndicatorDefault: ViewPagerIndicator? = null
    private var mIndicatorNotAnim: ViewPagerIndicator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner_view)
        val mUrlList = arrayListOf<String>()
        mUrlList.add("http://t2.hddhhn.com/uploads/tu/201301/149/2.jpg")
        mUrlList.add("http://t2.hddhhn.com/uploads/tu/201301/149/4.jpg")
        mUrlList.add("https://t2.hddhhn.com/uploads/tu/201409/036/5.jpg")
        mUrlList.add("https://t2.hddhhn.com/uploads/tu/201301/189/4.jpg")
        mUrlList.add("http://t2.hddhhn.com/uploads/tu/201409/036/1.jpg")

        mTv_hint = findViewById(R.id.tv_hint) as TextView
        mBannerView = findViewById(R.id.BannerView) as BannerView
        mBannerView!!.build(mUrlList)

        mIndicatorDefault = findViewById(R.id.indicator_default) as ViewPagerIndicator
        mIndicatorNotAnim = findViewById(R.id.indicator_not_anim) as ViewPagerIndicator
        // if mBannerView.setCircle(true);
        mIndicatorDefault!!.setViewPager(mBannerView!!.viewPager, mUrlList.size)
        mIndicatorNotAnim!!.setViewPager(mBannerView!!.viewPager, mUrlList.size)

        indicator_circle.setViewPager(mBannerView!!.viewPager,mUrlList.size)
        indicator_line.setViewPager(mBannerView!!.viewPager,mUrlList.size)
        indicator_circle_line.setViewPager(mBannerView!!.viewPager,mUrlList.size)
        indicator_bezier.setViewPager(mBannerView!!.viewPager,mUrlList.size)
        indicator_spring.setViewPager(mBannerView!!.viewPager,mUrlList.size)

        // if mBannerView.setCircle(false);
        //        mIndicatorDefault.setViewPager(mBannerView.getViewPager(),false);
        //        mIndicatorNotAnim.setViewPager(mBannerView.getViewPager(),false);


    }

    override fun onStart() {
        super.onStart()
//        mBannerView!!.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
//        mBannerView!!.stopAutoPlay()
    }



}