package com.kotlin.study.activity

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.alibaba.android.vlayout.layout.*
import com.kotlin.study.BaseActivity
import com.kotlin.study.R
import com.kotlin.study.R.id.recyclerView
import kotlinx.android.synthetic.main.activity_xlayout.*
import java.util.*
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.alibaba.android.vlayout.VirtualLayoutAdapter


class XLayoutActivity : BaseActivity() {
    private val mSubAdapters = LinkedList<DelegateAdapter.Adapter<*>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xlayout)
//        val mLayoutManager = VirtualLayoutManager(this)
//        mLayoutManager.setRecycleOffset(300)
//        recycler.layoutManager = (mLayoutManager)
//
//        val mViewPool = RecyclerView.RecycledViewPool()
//        recycler.recycledViewPool = (mViewPool)
//        mViewPool.setMaxRecycledViews(0, 20)
//
//        val mDelegateAdapter = DelegateAdapter(mLayoutManager, true)
//        mDelegateAdapter.setAdapters(mSubAdapters)
//        recycler.adapter = (mDelegateAdapter)

        val virtualLayoutManager =  VirtualLayoutManager(this)
        val recycledViewPool =  RecyclerView.RecycledViewPool()
        recycledViewPool.setMaxRecycledViews(0, 10)
        recycler.recycledViewPool = recycledViewPool
        val layoutHelpers =  arrayListOf<BaseLayoutHelper>()
        val linearLayoutHelper =  LinearLayoutHelper(10, 2)
        val gridLayoutHelper =  GridLayoutHelper(10, 3)
        val fixLayoutHelper= FixLayoutHelper(200,200)
        val scrollFixLayoutHelper= ScrollFixLayoutHelper(400,400)
        val floatLayoutHelper= FloatLayoutHelper()
        floatLayoutHelper.setDragEnable(true)
        val columnLayoutHelper = ColumnLayoutHelper()
        columnLayoutHelper.setWeights(  floatArrayOf(1f,2f,1.2f,5f))
        val stickyLayoutHelper = StickyLayoutHelper(true)
        layoutHelpers.add(linearLayoutHelper)
        layoutHelpers.add(gridLayoutHelper)
        layoutHelpers.add(fixLayoutHelper)
        layoutHelpers.add(scrollFixLayoutHelper)
        layoutHelpers.add(floatLayoutHelper)
        layoutHelpers.add(columnLayoutHelper)
        layoutHelpers.add(stickyLayoutHelper)
        virtualLayoutManager.setLayoutHelpers(layoutHelpers as List<LayoutHelper>?)
        recycler.layoutManager = virtualLayoutManager
        val list = (0..40).map { "第" + it + "个" }
        recycler.adapter = MyShiPei(virtualLayoutManager, list)

    }

    override fun finish() {
        super.finish()
    }

    internal inner class MyShiPei( layoutManager: VirtualLayoutManager, private val list: List<String>) : VirtualLayoutAdapter<MyViewHolder>(layoutManager) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(LayoutInflater.from(applicationContext).inflate(R.layout.item, parent, false))
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.textView.text = list[position]
            holder.imageView.setImageResource(R.mipmap.ic_launcher)
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var textView: TextView = itemView!!.findViewById(R.id.textView)
        internal var imageView: ImageView = itemView!!.findViewById(R.id.imageView)

    }
}