package com.kotlin.study.activity

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.kotlin.study.BaseActivity
import com.kotlin.study.R
import com.kotlin.study.manager.ActivityStack
import com.kotlin.study.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_simple_view.*


class SimpleActivity : BaseActivity() {

    private val titleItem = listOf(
            "svgaPlayer",
            "simpleview",
            "NestedScrollView",
            "JetBrains",
            "JetBrains",
            "JetBrains",
            "JetBrains",
            "JetBrains"

    )

    class MainAdapter(val items : List<String>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(Button(parent.context))
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.setTextColor(Color.BLACK)
            holder.textView.text = items[position]
            holder.textView.textSize = 18f
            holder.textView.typeface = Typeface.defaultFromStyle(Typeface.BOLD_ITALIC)

            val parea = RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
//            parea.gravity = Gravity.CENTER
            parea.addRule(RelativeLayout.CENTER_IN_PARENT)
            holder.textView.layoutParams = parea
        }

        class ViewHolder(val textView: Button) : RecyclerView.ViewHolder(textView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_view)
        ToastUtils.showToast(this, ActivityStack.mActivityList!!.size.toString())

        recyclerView.layoutManager = object : LinearLayoutManager(this) {
            //解决RecyclerView嵌套RecyclerView滑动卡顿的问题
            //如果你的RecyclerView是水平滑动的话可以重写canScrollHorizontally方法
            override fun canScrollVertically() = false
        }

        //解决数据加载不完的问题
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.setHasFixedSize(true)
//        //解决数据加载完成后, 没有停留在顶部的问题
        recyclerView.isFocusable = false

        recyclerView.adapter = MainAdapter(titleItem)

        recyclerView1.layoutManager = object : LinearLayoutManager(this) {
            //解决RecyclerView嵌套RecyclerView滑动卡顿的问题
            //如果你的RecyclerView是水平滑动的话可以重写canScrollHorizontally方法
            override fun canScrollVertically() = false
        }

        //解决数据加载不完的问题
        recyclerView1.isNestedScrollingEnabled = false
        recyclerView1.setHasFixedSize(true)
        //解决数据加载完成后, 没有停留在顶部的问题
        recyclerView1.isFocusable = false

        recyclerView1.adapter = MainAdapter(titleItem)

        //

    }


}
