package com.kotlin.study.widget.pullrefreshview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.handmark.pulltorefresh.library.LoadingLayoutBase
import com.kotlin.study.R
import com.kotlin.study.inter.IRefreshLayout
import com.opensource.svgaplayer.SVGADrawable
import com.opensource.svgaplayer.SVGAImageView
import com.opensource.svgaplayer.SVGAParser
import com.opensource.svgaplayer.SVGAVideoEntity

/**
 *
 * @version 1.6.1
 * @description 大厅上拉加载效果
 */
class HomeFooterLayout(context: Context) : LoadingLayoutBase(context), IRefreshLayout {

    private val mContainerLayout: ViewGroup
    private val mDownImage: SVGAImageView
    private val mTvContent: TextView?

    private val mRefreshingLabel: CharSequence
    private val mCompleteLabel: CharSequence

    private val downPath = "svga/refresh_down.svga"
    private var downDrawable: SVGADrawable? = null
    var failureHint: String
    var notNetWorkHint: String
    private var isComplete: Boolean = false

    init {
        failureHint = context.getString(R.string.li_refresh_header_failure)
        notNetWorkHint = context.getString(R.string.li_refresh_header_not_work)

        LayoutInflater.from(context).inflate(R.layout.li_home_footer_loadinglayout, this)
        mContainerLayout = findViewById<View>(R.id.fl_inner) as ViewGroup
        mDownImage = mContainerLayout.findViewById<View>(R.id.img_down) as SVGAImageView
        mTvContent = mContainerLayout.findViewById<View>(R.id.tv_content) as TextView?
        //创建指针和旋转动画
        createAnimation()
        // Load in labels
        mRefreshingLabel = context.getString(R.string.li_refresh_footer_loading)
        mCompleteLabel = context.getString(R.string.li_refresh_footer_success)
        reset()
    }

    private fun createAnimation() {
        val parser = SVGAParser(context)
        try {
            parser.parse(downPath, object : SVGAParser.ParseCompletion {
                override fun onComplete(videoItem: SVGAVideoEntity) {
                    downDrawable = SVGADrawable(videoItem)
                }

                override fun onError() {}
            })
        } catch (e: Exception) {
        }

    }

    // 获取"加载头部"高度
    override fun getContentSize(): Int {
        return mContainerLayout.height
    }

    // 开始下拉时的回调
    override fun pullToRefresh() {
        if (isComplete) {
            mDownImage.visibility = View.GONE
            mTvContent!!.text = mCompleteLabel
        } else {
            mDownImage.visibility = View.VISIBLE
            mTvContent!!.text = mRefreshingLabel
            mDownImage.setImageDrawable(downDrawable)
            mDownImage.startAnimation()
        }
    }

    // "加载头部"完全显示时的回调
    override fun releaseToRefresh() {

    }

    // 下拉拖动时的回调
    override fun onPull(scaleOfLayout: Float) {}

    // 释放后刷新时的回调
    override fun refreshing() {

    }

    override fun reset() {
        mDownImage.stopAnimation()
    }

    override fun setPullLabel(pullLabel: CharSequence) {

    }

    override fun setRefreshingLabel(refreshingLabel: CharSequence) {}

    override fun setReleaseLabel(releaseLabel: CharSequence) {

    }

    /**
     * 刷新结束后调用
     *
     * @param state
     */
    override fun setRefreshState(state: Int) {
        if (null == mTvContent) {
            return
        }
        if (isComplete) {
            return
        }
        when (state) {
            IRefreshLayout.REFRESH_FAILURE -> {
                mDownImage.visibility = View.GONE
                mTvContent.text = failureHint
            }
            IRefreshLayout.REFRESH_NOT_NETWORK -> {
                mDownImage.visibility = View.GONE
                mTvContent.text = notNetWorkHint
            }
            else -> {
                mTvContent.text = mRefreshingLabel
                mDownImage.visibility = View.VISIBLE
            }
        }
    }

    fun setComplete(complete: Boolean) {
        isComplete = complete
    }
}