package com.kotlin.study.video.videohome

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.kotlin.study.BaseFragment
import com.kotlin.study.R
import com.kotlin.study.adapter.BaseDataAdapter
import com.kotlin.study.entity.AndyInfo
import com.kotlin.study.utils.bindView
import com.kotlin.study.video.videohome.presenter.HomePresenter
import com.kotlin.study.video.videohome.view.HomeView


/**
 * Description:首页
 */

class HomeFragment : BaseFragment<HomeView, HomePresenter>(), HomeView {

    private val mPullToZoomRecycler: RecyclerView by bindView(R.id.rv_category_recycler)
    private var mCateGoryAdapter: BaseDataAdapter? = null

    companion object {
        @JvmStatic
        fun newInstance(): HomeFragment = HomeFragment()
    }

    override fun getBundleExtras(extras: Bundle) {

    }

    override fun initView(savedInstanceState: Bundle?) {
      /*  mPullToZoomRecycler.setOnPullZoomListener(object : PullToZoomBase.onPullZoomListener {
            override fun onPullZooming(scrollValue: Int) {
                mHomePageHeaderView.showRefreshCover(scrollValue)
            }

            override fun onPullZoomEnd() {
                if (mHomePageHeaderView.judgeCanRefresh()) {//只有达到刷新的阀值，上升才刷新，其他情况不刷新
                    mPresenter.refreshCategoryData()
                } else {
                    mHomePageHeaderView.hideRefreshCover()
                }
            }
        })*/
        mPresenter.loadCategoryData()
    }

    override fun loadDataSuccess(andyInfo: AndyInfo) {
        if (mCateGoryAdapter == null) {
            setAdapterAndListener(andyInfo)
            Log.e("shell","mCateGoryAdapter init start")
        } else {
            mCateGoryAdapter?.setNewData(andyInfo.itemList)
        }
    }

    private fun setAdapterAndListener(andyInfo: AndyInfo) {
        mPullToZoomRecycler.setItemViewCacheSize(10)
        mCateGoryAdapter = BaseDataAdapter(andyInfo.itemList)
        mCateGoryAdapter?.setOnLoadMoreListener({ mPresenter.loadMoreCategoryData() }, mPullToZoomRecycler)
        mCateGoryAdapter?.setLoadMoreView(CustomLoadMoreView())
//        mPullToZoomRecycler.setAdapterAndLayoutManager(mCateGoryAdapter!!, LinearLayoutManager(_mActivity))
        mPullToZoomRecycler.adapter = mCateGoryAdapter
        mPullToZoomRecycler.layoutManager = LinearLayoutManager(_mActivity)
    }


    override fun refreshDataSuccess(andyInfo: AndyInfo) {
        mCateGoryAdapter?.removeAllFooterView()
        mCateGoryAdapter?.setNewData(andyInfo.itemList)
    }


    override fun loadMoreSuccess(andyInfo: AndyInfo) {
        mCateGoryAdapter?.addData(andyInfo.itemList)
        mCateGoryAdapter?.loadMoreComplete()
    }

    override fun showNoMore() {
        mCateGoryAdapter?.loadMoreEnd()
    }


    override fun getContentViewLayoutId() = R.layout.fragment_home



}