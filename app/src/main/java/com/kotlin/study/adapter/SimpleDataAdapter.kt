package com.kotlin.study.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.kotlin.study.R
import com.kotlin.study.greendaogenlib.Account

/**
 * @author ShellRay
 * Created  on 2019/4/15.
 * @description
 */
class SimpleDataAdapter(var context: Context, private var items: MutableList<Account>) : RecyclerView.Adapter<SimpleDataAdapter.SimpleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_green_dao_item,parent,false)
        return SimpleViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {

        holder.id.text = items[position].id
        holder.name.text = items[position].name
        holder.avatar.text = items[position].avatarlink

        holder.layoutRoot.setOnLongClickListener (View.OnLongClickListener {
                listener.onItemLongClickListener(position)
        })

        holder.layoutRoot.setOnClickListener {
            listener.onItemClickListener(position)
        }

    }

    class SimpleViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val id = view.findViewById<TextView>(R.id.id)
        val name = view.findViewById<TextView>(R.id.name)
        val avatar = view.findViewById<TextView>(R.id.avatar)
        val layoutRoot = view.findViewById<LinearLayout>(R.id.layoutRoot)


    }

      fun setDatas(itemsData : MutableList<Account>) {
        items = itemsData
        notifyDataSetChanged()
    }

    interface OnItemClickListener{
        fun onItemClickListener(position: Int)
        fun  onItemLongClickListener(position: Int) : Boolean
    }

    private lateinit var listener: OnItemClickListener

    fun setOnItemClickListener(listener1:OnItemClickListener){
        this.listener = listener1
    }
}