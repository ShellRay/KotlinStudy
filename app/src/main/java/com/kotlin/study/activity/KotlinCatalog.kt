package com.kotlin.study.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kotlin.study.R
import kotlinx.android.synthetic.main.activity_catalog.*

/**
 * kotlin 目录表
 * */

class KotlinCatalog: AppCompatActivity() {

    //kotlin取消了activity.this的用法
    val instance = lazy { this }

    val catalogs = listOf<String>("基本语法","基本类型")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)

        rcyCatalog.layoutManager = LinearLayoutManager(this)
        rcyCatalog.adapter = MainAdapter(catalogs,this,ClickCallback(this))

    }

    inner class ClickCallback(private val kotlinCatalog: KotlinCatalog) : MainAdapter.OnClickCallback {
        override fun onClick(view: View, position: Int) {
        var intent = Intent()
            when(position){
                0 -> intent.setClass(this@KotlinCatalog, StartBeginActivity::class.java)
                1 -> intent.setClass(kotlinCatalog, FundamentalTypeActivity::class.java)

            }
//            if(position == 0){
//                intent.setClass(this@KotlinCatalog, StartBeginActivity::class.java)
//            }else if(position == 1){
//                intent.setClass(kotlinCatalog, FundamentalTypeActivity::class.java)
//            }

            startActivity(intent)
        }
    }


    class MainAdapter(val items: List<String>, val instances: KotlinCatalog, clickCallback: OnClickCallback?) : RecyclerView.Adapter<MainAdapter.ViewHolder>()
    {

        private var instance = instances
        private var mOnClickCallback = clickCallback

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(TextView(parent.context))
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = items[position]
            holder.textView.setTextColor(ContextCompat.getColor(instance,R.color.colorPrimaryDark))
            holder.textView.setOnClickListener{
                mOnClickCallback!!.onClick(holder.textView,position)
            }
        }

        class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

        interface OnClickCallback {
            fun onClick(view: View, position: Int)
        }
    }
}

