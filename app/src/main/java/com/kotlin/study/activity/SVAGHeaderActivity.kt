package com.kotlin.study.activity

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.handmark.pulltorefresh.library.PullToRefreshBase
import com.kotlin.study.BaseActivity
import com.kotlin.study.R
import com.kotlin.study.adapter.MainBannerAdapter
import com.kotlin.study.adapter.RecommendTagAdapter
import com.kotlin.study.inter.IRefreshLayout
import com.kotlin.study.inter.IRefreshLayout.REFRESH_FAILURE
import com.kotlin.study.inter.IRefreshLayout.REFRESH_SUCCESS
import com.kotlin.study.widget.pullrefreshview.HomeFooterLayout
import com.kotlin.study.widget.pullrefreshview.HomeHeaderLayout
import kotlinx.android.synthetic.main.activity_svga_header.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author ShellRay
 * Created  on 2019/9/11.
 * @description
 */
class SVAGHeaderActivity : BaseActivity(){

    private val VIEW_TYPE_TAG = 12
    private val VIEW_TYPE_SONGS = 13
    private val VIEW_TYPE_HOT_KTV = 14
    private val VIEW_TYPE_BANNER = 15
    private val VIEW_TYPE_WITH_ROOM = 16
    private val mSubAdapters = LinkedList<DelegateAdapter.Adapter<*>>()

    var mainBannerAdapter:MainBannerAdapter? = null
    var mRankTagAdapter:RecommendTagAdapter<SVAGHeaderActivity>? = null
    var mDelegateAdapter:DelegateAdapter? = null

    val list:ArrayList<String> = arrayListOf("http://img1.imgtn.bdimg.com/it/u=4015794218,4048140087&fm=26&gp=0.jpg",
            "http://img4q.duitang.com/uploads/item/201505/06/20150506202234_thzKj.jpeg",
            "http://b-ssl.duitang.com/uploads/item/201505/16/20150516185030_v8LBu.jpeg",
            "http://i4.hoopchina.com.cn/hupuapp/bbs/796/25836796/thread_25836796_20190729011002_s_340309_o_w_1920_h_1080_1472.jpg?x-oss-process=image/resize,w_800/format,jpg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_svga_header)

        var recyclerView = pullRecycle.refreshableView
        var headerLayout = HomeHeaderLayout(this)
        var footerLayout = HomeFooterLayout(this)
        pullRecycle.setHeaderLayout(headerLayout)
        pullRecycle.setFooterLayout(footerLayout)
        pullRecycle.mode = PullToRefreshBase.Mode.BOTH
        pullRecycle.setOnRefreshListener(object : PullToRefreshBase.OnRefreshListener2<RecyclerView> {
            override fun onPullDownToRefresh(refreshView: PullToRefreshBase<RecyclerView>) {
                    Log.e("shell","onPullDownToRefresh")
                pullRecycle.postDelayed(Runnable {
                    if (null != pullRecycle) {
                        refreshComplete(REFRESH_SUCCESS,headerLayout)
                    }
                }, 5000)

            }

            override fun onPullUpToRefresh(refreshView: PullToRefreshBase<RecyclerView>) {
                Log.e("shell","onPullUpToRefresh")
                pullRecycle.postDelayed(Runnable {
                    if (null != pullRecycle) {
                        refreshComplete(REFRESH_FAILURE,footerLayout)
                    }
                }, 5000)

            }
        })
        var mLayoutManager = VirtualLayoutManager(this)
        mLayoutManager.setRecycleOffset(300)
        recyclerView.layoutManager = mLayoutManager

        var mViewPool = RecyclerView.RecycledViewPool()
        recyclerView.recycledViewPool = mViewPool
        mViewPool.setMaxRecycledViews(0, 20)

        mDelegateAdapter = DelegateAdapter(mLayoutManager, true)
        mDelegateAdapter!!.setAdapters(mSubAdapters)
        recyclerView.adapter = mDelegateAdapter

        initSubAdapters()
        updateSubAdapterList()

        mainBannerAdapter!!.setBannerData(list)
    }

    private fun updateSubAdapterList() {
        mSubAdapters.clear()

        //添加首页的banner
        mSubAdapters.add(this.mainBannerAdapter!!)
        mSubAdapters.add(this.mRankTagAdapter!!)
        //添加快速跟房栏

        mDelegateAdapter!!.setAdapters(mSubAdapters)
        mDelegateAdapter!!.notifyDataSetChanged()
    }
    private fun initSubAdapters() {
        initBannerAdapter()
        initRoomAdapter()
    }

    private fun initBannerAdapter() {
         mainBannerAdapter = MainBannerAdapter(this, LinearLayoutHelper(), VIEW_TYPE_BANNER)
    }

    private fun initRoomAdapter() {
        mRankTagAdapter = RecommendTagAdapter(this, LinearLayoutHelper(), "tag", VIEW_TYPE_TAG, null, false)
    }

    private fun refreshComplete(state: Int, refreshLayout: IRefreshLayout?) {
        if (pullRecycle.isRefreshing) {
                refreshLayout!!.setRefreshState(state)
            pullRecycle.postDelayed({
                    pullRecycle!!.onRefreshComplete()
            }, 2000)
        }
    }
}