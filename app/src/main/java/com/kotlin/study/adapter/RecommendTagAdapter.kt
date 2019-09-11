package com.kotlin.study.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.alibaba.android.vlayout.LayoutHelper
import com.kotlin.study.R

/**
 * 推荐标签元素的适配器
 */
class RecommendTagAdapter<T : Activity>(context: Context, layoutHelper: LayoutHelper, private val mTag: String, private val mViewType: Int, private val mTargetClass: Class<Any>?, private val isShowMore: Boolean) : DelegateSubAdapter(context, layoutHelper, 1), View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DelegateSubAdapter.SubViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hall_recommend_tag_title, parent, false)
        view.findViewById<View>(R.id.tv_recommend_tag_more).visibility = if (isShowMore) View.VISIBLE else View.GONE
        view.findViewById<View>(R.id.tv_recommend_tag_more).setOnClickListener(this)
        return TagTextViewHolder(view)
    }

    override fun onBindViewHolder(holder: DelegateSubAdapter.SubViewHolder, position: Int) {
        val tagViewHolder = holder as RecommendTagAdapter<*>.TagTextViewHolder
        tagViewHolder.tvTagName.text = mTag
        tagViewHolder.tvMore.tag = mTargetClass
        tagViewHolder.tvMore.visibility = if (isShowMore) View.VISIBLE else View.GONE
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onClick(v: View) {
        val tag = v.tag
        if (tag is Class<*>) {
            val intent = Intent(mContext, tag)
            mContext.startActivity(intent)
        }
    }

    internal inner class TagTextViewHolder(itemView: View) : DelegateSubAdapter.SubViewHolder(itemView) {

        val tvTagName: TextView
        val tvMore: View

        init {
            tvTagName = itemView.findViewById(R.id.tv_recommend_tag_name)
            tvMore = itemView.findViewById(R.id.tv_recommend_tag_more)
        }
    }
}
