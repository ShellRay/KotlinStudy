package com.kotlin.study.activity

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.Snackbar
import android.support.v4.app.NavUtils
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.kotlin.study.BaseActivity
import com.kotlin.study.R
import kotlinx.android.synthetic.main.activity_nested_scroll_view.*


class NestedScrollViewActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_scroll_view)

        //给页面设置工具栏
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        //启动返回键箭头展示
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//      方法一 ：清单文件中加想要返回的activity  android:parentActivityName=".MainActivity"
//      onOptionsItemSelected 此中加代码 NavUtils.navigateUpFromSameTask(this)

        //设置工具栏标题
        val collapsingToolbar = findViewById<View>(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        collapsingToolbar.title = "nestedscrollview"

        mojieicon.setOnClickListener { view ->
            val snackBar = Snackbar.make(view, "world", Snackbar.LENGTH_LONG)
                    .setAction("PUT YOUR HANDS UP", View.OnClickListener { view ->

                    })
            snackBar.view.setBackgroundColor(Color.parseColor("#FFBFBFBF"))
            snackBar.setActionTextColor(Color.parseColor("#FFF9F6F9"))
            snackBar.show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            if (item.itemId == android.R.id.home){
                NavUtils.navigateUpFromSameTask(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
