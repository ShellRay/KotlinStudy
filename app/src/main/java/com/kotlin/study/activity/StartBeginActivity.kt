package com.kotlin.study.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.kotlin.study.R
import com.kotlin.study.itemdecoration.AbsDividerItemDecoration
import com.kotlin.study.itemdecoration.SimpleDivederItem
import kotlinx.android.synthetic.main.activity_start.*

/**
 * Created by GG on 2018/2/27.
 */
class StartBeginActivity : AppCompatActivity(), View.OnClickListener {

    val items = listOf(
            "Kotlin 是一个用于现代多平台应用的静态编程语言由 JetBrains 开发",
            "Kotlin可以编译成Java字节码，也可以编译成JavaScript，方便在没有JVM的设备上运行",
            "Android之自定义View的死亡三部曲之Measure",
            "JetBrains，作为广受欢迎的 Java IDE IntelliJ 的提供商，在 Apache 许可下已经开源其Kotlin 编程语言。"

    )

    class MainAdapter(val items : List<String>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(TextView(parent.context))
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.setTextColor(Color.BLACK)
            holder.textView.text = items[position]
            holder.textView.textSize = 18f
            holder.textView.typeface = Typeface.defaultFromStyle(Typeface.BOLD_ITALIC)

        }

        class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        setSupportActionBar(toolbar)
        var gManager = LinearLayoutManager(this)
        recycle.addItemDecoration(SimpleDivederItem(AbsDividerItemDecoration.VERTICAL))
        recycle.layoutManager = gManager
        recycle.adapter = MainAdapter(items)
        svgaPlayer.setOnClickListener(this)
        simple.setOnClickListener(this)

    }

    fun ksClick(view: View) {
        toast("Go Go Kotlin")
    }

    override fun onClick(view: View){

        when(view.id){
            svgaPlayer.id  -> startActivity(Intent(this,SVGAProjectActivity::class.java))
//            simple.id  -> startActivity(Intent(this,SVGAProjectActivity::class.java))
            else -> toast("else click")
        }

    }

    private fun toast(msg : String) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }


    /**
     * @param a
     * @param b
     * */
    private fun sum(a:Int,b:Int):Int{
        return a + b
    }

    private fun print(str : String){
        println(str)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if (id == R.id.action_settings) {
            val sum = sum(4, 5)
            Toast.makeText(baseContext,"more " + sum,Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}