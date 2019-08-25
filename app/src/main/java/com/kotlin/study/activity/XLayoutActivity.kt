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

/**
LinearLayoutHelper: 线性布局
GridLayoutHelper: Grid布局， 支持横向的colspan
FixLayoutHelper: 固定布局，始终在屏幕固定位置显示
ScrollFixLayoutHelper: 固定布局，但之后当页面滑动到该图片区域才显示, 可以用来做返回顶部或其他书签等
FloatLayoutHelper: 浮动布局，可以固定显示在屏幕上，但用户可以拖拽其位置
ColumnLayoutHelper: 栏格布局，都布局在一排，可以配置不同列之间的宽度比值
SingleLayoutHelper: 通栏布局，只会显示一个组件View
OnePlusNLayoutHelper: 一拖N布局，可以配置1-5个子元素
StickyLayoutHelper: stikcy布局， 可以配置吸顶或者吸底
StaggeredGridLayoutHelper: 瀑布流布局，可配置间隔高度/宽度
*/


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
        recycledViewPool.setMaxRecycledViews(0, 20)
        recycler.recycledViewPool = recycledViewPool
        val layoutHelpers =  arrayListOf<BaseLayoutHelper>()

        val linearLayoutHelper =  LinearLayoutHelper(50, 2)
        val gridLayoutHelper =  GridLayoutHelper(3, 3,1)
        val fixLayoutHelper= FixLayoutHelper(400,400)
        val scrollFixLayoutHelper= ScrollFixLayoutHelper(600,600)
        val floatLayoutHelper= FloatLayoutHelper()
        floatLayoutHelper.setDragEnable(true)
        floatLayoutHelper.itemCount = 1


        val columnLayoutHelper = ColumnLayoutHelper()
        columnLayoutHelper.itemCount = 4
//        columnLayoutHelper.setWeights(  floatArrayOf(1f,2f,1.2f,5f))
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