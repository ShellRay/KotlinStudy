package com.kotlin.study.widget.pullrefreshview

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView

import com.handmark.pulltorefresh.library.LoadingLayoutBase
import com.kotlin.study.R
import com.kotlin.study.inter.IRefreshLayout
import com.opensource.svgaplayer.SVGADrawable
import com.opensource.svgaplayer.SVGAImageView
import com.opensource.svgaplayer.SVGAParser
import com.opensource.svgaplayer.SVGAVideoEntity

import com.kotlin.study.utils.dp2px


/**
 *
 * @version 1.5.1
 * @description 首页下拉刷新头
 */
class HomeHeaderLayout(context: Context) : LoadingLayoutBase(context), IRefreshLayout {

    private val tvHint: TextView?
    private val mInnerLayout: ConstraintLayout
    private val pullImg: SVGAImageView

    var successHint: String
    var failureHint: String
    var notNetWorkHint: String
    var outTimeHint: String
    private var loadingDrawable: SVGADrawable? = null
    private var pullDrawable: SVGADrawable? = null
    internal var dp16: Int = 0
    private var scaleOutPullImg: Float = 0.toFloat()

    init {
        successHint = context.getString(R.string.li_refresh_header_success)
        failureHint = context.getString(R.string.li_refresh_header_failure)
        notNetWorkHint = context.getString(R.string.li_refresh_header_not_work)
        outTimeHint = context.getString(R.string.li_refresh_header_failure)

        LayoutInflater.from(context).inflate(R.layout.li_home_pulltorefresh_header, this)
        mInnerLayout = findViewById(R.id.fl_inner)
        pullImg = mInnerLayout.findViewById<View>(R.id.iv_pull_icon) as SVGAImageView
        tvHint = mInnerLayout.findViewById(R.id.tv_hint)
        val lp = mInnerLayout.layoutParams as FrameLayout.LayoutParams
        lp.gravity = Gravity.BOTTOM
        reset()
        val parser = SVGAParser(getContext())
        initPullDrawable(parser)
        initLoadingDrawable(parser)
        dp16 = getContext().dp2px(16)
    }

    private fun initPullDrawable(parser: SVGAParser) {
        try {
            parser.parse(pullPath, object : SVGAParser.ParseCompletion {
                override fun onComplete(videoItem: SVGAVideoEntity) {
                    pullDrawable = SVGADrawable(videoItem)
                }

                override fun onError() {}
            })
        } catch (e: Exception) {
        }

    }

    private fun initLoadingDrawable(parser: SVGAParser) {
        try {
            parser.parse(loadingPath, object : SVGAParser.ParseCompletion {
                override fun onComplete(videoItem: SVGAVideoEntity) {
                    loadingDrawable = SVGADrawable(videoItem)
                }

                override fun onError() {}
            })
        } catch (e: Exception) {
        }

    }

    // 获取"加载头部"高度
    override fun getContentSize(): Int {
        return mInnerLayout.height
    }

    // 开始下拉时的回调
    override fun pullToRefresh() {
        pullImg.visibility = View.VISIBLE
        tvHint!!.visibility = View.GONE
        pullImg.setImageDrawable(pullDrawable)
    }

    // "加载头部"完全显示时的回调
    override fun releaseToRefresh() {

    }


    // 下拉拖动时的回调
    override fun onPull(scaleOfLayout: Float) {
        pullImg.visibility = View.VISIBLE
        tvHint!!.visibility = View.GONE
        //需要知道顶部下拉多少px，动画控件的左上角在什么坐标，
        val pullHeight = scaleOfLayout * contentSize//下拉距离
        val imgHeight = pullImg.height

        Log.e("shell", "pullHeight: $pullHeight--imgHeight:$imgHeight")
        if (pullHeight > imgHeight) {
            val diffHeight = pullHeight - imgHeight
            val layoutParams = pullImg.layoutParams
            val marginLayoutParams: ViewGroup.MarginLayoutParams
            marginLayoutParams = layoutParams as? ViewGroup.MarginLayoutParams ?: ViewGroup.MarginLayoutParams(layoutParams)
            var bottomMargin = diffHeight / 2
            bottomMargin = Math.min(bottomMargin, dp16.toFloat())
            marginLayoutParams.setMargins(0, dp16, 0, bottomMargin.toInt())
            pullImg.layoutParams = marginLayoutParams
        }
        if (scaleOutPullImg == 0f) {
            scaleOutPullImg = pullImg.height.toFloat() / contentSize.toFloat()
        }
        val realScale = (scaleOfLayout - scaleOutPullImg) / (1 - scaleOutPullImg)
        pullImg.stepToPercentage(realScale.toDouble(), false)//按进度运行动画
    }

    // 释放后刷新时的回调
    override fun refreshing() {
        pullImg.setImageDrawable(loadingDrawable)
        pullImg.startAnimation()
    }

    override fun reset() {
        pullImg.stopAnimation()
        tvHint!!.visibility = View.GONE
    }

    override fun setPullLabel(pullLabel: CharSequence) {

    }

    override fun setRefreshingLabel(refreshingLabel: CharSequence) {

    }

    override fun setReleaseLabel(releaseLabel: CharSequence) {

    }

    /**
     * 刷新结束后调用
     *
     * @param state
     */
    override fun setRefreshState(state: Int) {
        if (null == tvHint) {
            return
        }
        when (state) {
            IRefreshLayout.REFRESH_SUCCESS -> {
                hideLoading()
                showRefreshHint(successHint)
            }
            IRefreshLayout.REFRESH_FAILURE -> {
                hideLoading()
                showRefreshHint(failureHint)
            }
            IRefreshLayout.REFRESH_NOT_NETWORK -> {
                hideLoading()
                showRefreshHint(notNetWorkHint)
            }
            IRefreshLayout.REFRESH_TIMEOUT -> {
                hideLoading()
                showRefreshHint(outTimeHint)
            }
            else -> {
                pullImg.visibility = View.VISIBLE
                tvHint.visibility = View.GONE
            }
        }
    }

    private fun hideLoading() {
        pullImg.visibility = View.GONE
    }

    private fun showRefreshHint(hintText: String) {
        tvHint!!.text = hintText
        tvHint.visibility = View.VISIBLE
    }

    companion object {
        private val loadingPath = "svga/refresh_loading.svga"
        private val pullPath = "svga/refresh_pull.svga"
    }
}
