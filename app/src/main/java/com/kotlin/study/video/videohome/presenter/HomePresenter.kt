package com.kotlin.study.video.videohome.presenter

import android.view.View
import com.kotlin.study.video.videohome.model.HomeModel
import com.kotlin.study.BasePresenter
import com.kotlin.study.video.videohome.view.HomeView


/**
 * Description:
 */

class HomePresenter : BasePresenter<HomeView>() {

    private var mHomeModel: HomeModel = HomeModel()
    private var mNextPageUrl: String? = null

    /**
     * 加载首页信息
     */
    fun loadCategoryData() {
        mView?.showLoading()
        mRxManager.add(mHomeModel.loadCategoryInfo().subscribe({
            mView?.showContent()
            mNextPageUrl = it.nextPageUrl
            mView?.loadDataSuccess(it)
        }, {
            mView?.showNetError(View.OnClickListener {
                loadCategoryData()
            })
        }))
    }

    /**
     * 加载更多首页信息
     */
    fun loadMoreCategoryData() {
        if (mNextPageUrl != null) {
            mRxManager.add(mHomeModel.loadMoreAndyInfo(mNextPageUrl)!!.subscribe({
                mView?.showContent()
                if (it.nextPageUrl == null) {
                    mView?.showNoMore()
                } else {
                    mNextPageUrl = it.nextPageUrl
                    mView?.loadMoreSuccess(it)
                }
            }, {
                mView?.showNetError(View.OnClickListener {
                    loadMoreCategoryData()
                })
            }))
        }
    }
}