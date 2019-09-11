package com.kotlin.study.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup

import com.alibaba.android.vlayout.LayoutHelper
import com.kotlin.study.widget.banner.BannerView

import java.util.ArrayList

class MainBannerAdapter(context: Context, layoutHelper: LayoutHelper, private val mViewType: Int) : DelegateSubAdapter(context, layoutHelper, 1) {
    private var hallBannerView: BannerView? = null
    private var data: ArrayList<String>? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DelegateSubAdapter.SubViewHolder {
        hallBannerView = BannerView(mContext)
        if (null != data && !data!!.isEmpty())
            hallBannerView!!.build(data!!)
        hallBannerView!!.setBannerClickListener(object : BannerView.BannerClickListener {
            override fun bannerClick(url: String) {

            }
        })
        return BannerViewHolder(hallBannerView!!)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    internal inner class BannerViewHolder(itemView: View) : DelegateSubAdapter.SubViewHolder(itemView)

    fun setBannerData(data: ArrayList<String>) {
        this.data = data
        if (null != hallBannerView)
            hallBannerView!!.build(data)
    }
}
