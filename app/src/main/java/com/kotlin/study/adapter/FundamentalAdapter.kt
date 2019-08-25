package com.kotlin.study.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kotlin.study.R
import com.kotlin.study.mAppContext


class FundamentalAdapter(val items: List<String>,  clickCallback: OnClickCallback?) : RecyclerView.Adapter<FundamentalAdapter.ViewHolder>() {

    //        private var instance = instances
    private var mOnClickCallback = clickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TextView(parent.context))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position]
        holder.textView.textSize = 17f
        holder.textView.setTextColor(ContextCompat.getColor(mAppContext, R.color.colorPrimaryDark))
        holder.textView.setOnClickListener {
            mOnClickCallback!!.onClick(holder.textView, position)
        }
    }

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    interface OnClickCallback {
        fun onClick(view: View, position: Int)
    }
}
