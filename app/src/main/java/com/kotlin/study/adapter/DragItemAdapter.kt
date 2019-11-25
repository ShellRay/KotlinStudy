package com.kotlin.study.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.v4.view.MotionEventCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.kotlin.study.R
import com.kotlin.study.callback.ItemTouchHelperAdapter
import com.kotlin.study.inter.OnStartDragListener
import java.util.*

/**
 * 推荐标签元素的适配器
 */
class DragItemAdapter(context: Context, private val mTag: ArrayList<String>,private val mDragStartListener : OnStartDragListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperAdapter {
    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(mTag, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        mTag.removeAt(position)
        notifyItemRemoved(position)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.drag_view_item, parent, false)
        return TextViewHolder(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tagViewHolder = holder as DragItemAdapter.TextViewHolder
        tagViewHolder.tvView.text =  mTag[position]
        holder.rlRoot.setOnTouchListener(View.OnTouchListener { v, event ->
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                mDragStartListener.onStartDrag(holder)
            }
            false
        })

    }

    override fun getItemCount(): Int {
        return mTag.size
    }

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvView: TextView = itemView.findViewById(R.id.tvView)
        val rlRoot: RelativeLayout = itemView.findViewById(R.id.rlRoot)

        fun onItemSelected() {
            rlRoot.setBackgroundColor(Color.LTGRAY)
        }
        fun onItemFinish() {
            rlRoot.setBackgroundColor(0)
        }

    }


}
