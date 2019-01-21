package com.kotlin.study.video.videohome.presenter

import android.view.View
import com.kotlin.study.video.videohome.model.HomeModel
import com.kotlin.study.video.videohome.view.DailyEliteView
import com.kotlin.study.BasePresenter
import com.kotlin.study.entity.Content
import com.kotlin.study.entity.JenniferInfo


/**
 * Description:
 */

class DailyElitePresenter : BasePresenter<DailyEliteView>() {

    private var mHomeModel: HomeModel = HomeModel()
    private var mNextPageUrl: String? = null

    fun getDailyElite() {
        mRxManager.add(mHomeModel.getDailyElite().subscribe({
            mView?.showContent()
            mNextPageUrl = it.nextPageUrl
            mView?.showGetDailySuccess(combineContentInfo(it))
        }, {
            mView?.showNetError(View.OnClickListener {
                getDailyElite()
            })
        }))
    }

    /**
     * 刷新
     */
    fun refresh() {
        mRxManager.add(mHomeModel.getDailyElite().subscribe({
            mView?.showContent()
            mNextPageUrl = it.nextPageUrl
            mView?.showRefreshSuccess(combineContentInfo(it))
        }, {
            mView?.showNetError(View.OnClickListener {
                refresh()
            })
        }))
    }

    /**
     * 加載更多
     */
    fun loadMoreResult() {
        mRxManager.add(mHomeModel.loadMoreJenniferInfo(mNextPageUrl)!!.subscribe({
            mNextPageUrl = it.nextPageUrl
            mView?.loadMoreSuccess(combineContentInfo(it))
        }, {
            mView?.showNetError(View.OnClickListener {
                getDailyElite()
            })
        }))
    }

    private fun combineContentInfo(jenniferInfo: JenniferInfo): MutableList<Content> {
        val list = mutableListOf<Content>()
        val issueList = jenniferInfo.issueList
        for (contentBean in issueList) {
            val itemList = contentBean.itemList
            for (content in itemList) {
                list.add(content)
            }
        }
        return list
    }
}