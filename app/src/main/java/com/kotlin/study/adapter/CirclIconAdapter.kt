package com.kotlin.study.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.kotlin.study.R
import com.kotlin.study.utils.GlideLoadUtils

/**
 * @author ShellRay
 * Created  on 2019/4/15.
 * @description
 */
class CirclIconAdapter(var context: Context, private var items: ArrayList<String>,  clickCallback: OnClickCallback?) : RecyclerView.Adapter<CirclIconAdapter.SimpleViewHolder>() {

    private var mOnClickCallback = clickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.circl_icon_view_item,parent,false)
        return SimpleViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {

        GlideLoadUtils.getInstance().LoadImageCircle(context, items[position],holder.iconImg)
        holder.iconImg.setOnLongClickListener (View.OnLongClickListener {
            mOnClickCallback!!.onLongClick(position)
        })

    }

    class SimpleViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val iconImg = view.findViewById<ImageView>(R.id.iconImg)
    }

    interface OnClickCallback {
        fun onLongClick( position: Int):Boolean
    }

}