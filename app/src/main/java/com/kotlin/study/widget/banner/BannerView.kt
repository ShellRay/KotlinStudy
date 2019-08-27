package com.kotlin.study.widget.banner

import android.content.Context
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout

import com.kotlin.study.R
import com.kotlin.study.utils.ConvenientUtil

import java.util.ArrayList


/**
 * Created by
 *
 * 自定义Banner无限轮播控件
 */
class BannerView @JvmOverloads constructor(private val m_context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(m_context, attrs, defStyleAttr) {

    internal var viewPager: AutoScrollViewPager

    internal var points: LinearLayout

    //private CompositeSubscription compositeSubscription;

    //默认轮播时间，10s
    private var delayTime = 5000

    private val imageViewList: MutableList<ImageView>

    private var bannerList: MutableList<String>? = null

    //选中显示Indicator
    private var selectRes = R.drawable.widget_banner_point_s

    //非选中显示Indicator
    private var unSelcetRes = R.drawable.widget_banner_point_n

    //当前页的下标
    private var currrentPos: Int = 0


    private var bannerClickListener: BannerClickListener? = null


    init {
        val view = LayoutInflater.from(context).inflate(R.layout.widget_custom_banner, this, true)
        viewPager = view.findViewById(R.id.layout_banner_viewpager) as AutoScrollViewPager
        points = view.findViewById(R.id.layout_banner_points_group)
        viewPager.offscreenPageLimit = 2//预加载2个
        viewPager.pageMargin = ConvenientUtil.convertDpToPixel(context, 8)//设置viewpage之间的间距
        viewPager.clipChildren = false
        viewPager.setPageTransformer(true, CardTransformer())
        imageViewList = ArrayList()
    }


    /**
     * 设置轮播间隔时间
     *
     * @param time 轮播间隔时间，单位秒
     */
    fun delayTime(time: Int): BannerView {

        this.delayTime = time
        return this
    }


    /**
     * 设置Points资源 Res
     *
     * @param selectRes   选中状态
     * @param unselcetRes 非选中状态
     */
    fun setPointsRes(selectRes: Int, unselcetRes: Int) {

        this.selectRes = selectRes
        this.unSelcetRes = unselcetRes
    }


    /**
     * 图片轮播需要传入参数
     */
    fun build(list: ArrayList<String>) {

        //        if (list.size() == 0) {
        //            this.setVisibility(GONE);
        //            return;
        //        }else{
        //            this.setVisibility(View.VISIBLE);
        //        }

        bannerList = ArrayList<String>()

        (bannerList as ArrayList<String>).addAll(list)
        val pointSize: Int
        pointSize = bannerList!!.size
        //判断是否清空 指示器点
        if (points.childCount != 0) {
            points.removeAllViewsInLayout()
        }
        //初始化与个数相同的指示器点
        for (i in 0 until pointSize) {
            val dot = View(context)
            dot.setBackgroundResource(unSelcetRes)
            val params = LinearLayout.LayoutParams(
                    ConvenientUtil.convertDpToPixel(context, 6),
                    ConvenientUtil.convertDpToPixel(context, 6))
            params.leftMargin = ConvenientUtil.convertDpToPixel(context, 4)
            dot.layoutParams = params
            dot.isEnabled = false
            points.addView(dot)
        }

        points.getChildAt(0).setBackgroundResource(selectRes)
        val params = LinearLayout.LayoutParams(
                ConvenientUtil.convertDpToPixel(context, 12),
                ConvenientUtil.convertDpToPixel(context, 6))
        params.leftMargin = ConvenientUtil.convertDpToPixel(context, 4)
        points.getChildAt(0).layoutParams = params

        imageViewList.clear()
        for (i in bannerList!!.indices) {
            val imageView = ImageView(context)
            imageViewList.add(imageView)
        }
        val imagePagerAdapter: ImagePagerAdapter
        if (imageViewList.size == 1) {
            imagePagerAdapter = ImagePagerAdapter(m_context, bannerList as ArrayList<String>)
        } else {
            imagePagerAdapter = ImagePagerAdapter(m_context, bannerList as ArrayList<String>).setInfiniteLoop(true)
        }
        imagePagerAdapter.setOnItemListening(object : ImagePagerAdapter.OnItemListening {
            override fun ItemListening(url: String) {
//                if (bannerClickListener != null && !TextUtils.isEmpty(bannerList!![currrentPos].getSkipUrl())) {
//                    bannerClickListener!!.bannerClick(bannerList!![currrentPos].getSkipUrl())
//                }

            }
        })
        viewPager.setAdapter(imagePagerAdapter)

        viewPager.setOffscreenPageLimit(2)//预加载2个
        viewPager.setPageMargin(ConvenientUtil.convertDpToPixel(context, 6))//设置viewpage之间的间距
        viewPager.setClipChildren(false)
        //监听图片轮播，改变指示器状态
        // viewPager.clearOnPageChangeListeners();
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}


            override fun onPageSelected(pos: Int) {
                var pos = pos
                pos = pos % pointSize
                currrentPos = pos
                for (i in 0 until points.childCount) {
                    points.getChildAt(i).setBackgroundResource(unSelcetRes)
                    val params = LinearLayout.LayoutParams(
                            ConvenientUtil.convertDpToPixel(context, 6),
                            ConvenientUtil.convertDpToPixel(context, 6))
                    params.leftMargin = ConvenientUtil.convertDpToPixel(context, 4)
                    points.getChildAt(i).layoutParams = params
                }

                points.getChildAt(pos).setBackgroundResource(selectRes)

                val params = LinearLayout.LayoutParams(
                        ConvenientUtil.convertDpToPixel(context, 12),
                        ConvenientUtil.convertDpToPixel(context, 6))
                params.leftMargin = ConvenientUtil.convertDpToPixel(context, 4)
                points.getChildAt(pos).layoutParams = params
            }


            override fun onPageScrollStateChanged(state: Int) {}
        })

        viewPager.setInterval(5000)

        viewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_NONE)

        viewPager.setCycle(true)
        viewPager.setBorderAnimation(true)
        viewPager.setCurrentItem(10000 / 2 - 10000 / 2 % imageViewList.size)
        if (imageViewList.size > 1) {
            points.visibility = View.VISIBLE
            viewPager.startAutoScroll()
        } else {
            points.visibility = View.INVISIBLE
            viewPager.stopAutoScroll()
        }
    }

    interface BannerClickListener {
        fun bannerClick(url: String)
    }

    fun setBannerClickListener(bannerClickListener: BannerClickListener) {
        this.bannerClickListener = bannerClickListener
    }
}
