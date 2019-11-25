package com.kotlin.study.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.kotlin.study.BaseActivity
import com.kotlin.study.R
import com.kotlin.study.itemdecoration.AbsDividerItemDecoration
import com.kotlin.study.itemdecoration.SimpleDivederItem
import com.kotlin.study.utils.ConvenientUtil
import com.kotlin.study.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_start.*
import android.preference.PreferenceManager
import android.text.TextUtils
import com.kotlin.study.utils.ResourceUtils

/**
 * Created by GG on 2018/2/27.
 */
class StartBeginActivity : BaseActivity(), View.OnClickListener {

    private val titleItem = listOf(
            "svgaPlayer",
            "simpleview",
            "NestedScrollView",
            "FundamentalType",
            "SingleInstance",
            "Function",
            "SimpleEyes",
            "PreferenceActivity",
            "Tangram",
            "GreenDao",
            "Interpolator",
            "floatTable",
            "XLayout",
            "BannerAndDrag",
            "SVGAHeader",
            "redPackage"

    )

    private val items = listOf(
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

    class GridMainAdapter(val items: List<String>, val context: Context) : RecyclerView.Adapter<GridMainAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(Button(parent.context))
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.buttonView.setTextColor(Color.BLACK)
            holder.buttonView.text = items[position]
            holder.buttonView.textSize = 18f
            holder.buttonView.typeface = Typeface.defaultFromStyle(Typeface.BOLD_ITALIC)
//            holder.buttonView.width = ConvenientUtil.convertDpToPixel(context, 130)
            holder.buttonView.height = ConvenientUtil.convertDpToPixel(context, 70)
            holder.buttonView.setOnClickListener {
                if(listener == null) return@setOnClickListener
                listener!!.onClick(position)
            }

        }

        private  var listener: ClickListener? = null

        fun  setOnClickListener(listener: ClickListener) {
            this.listener = listener
        }

        class ViewHolder(val buttonView: Button) : RecyclerView.ViewHolder(buttonView)

        interface ClickListener{
            fun onClick(position: Int)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        setSupportActionBar(toolbar)
        var gManager = LinearLayoutManager(this)
        recycle.addItemDecoration(SimpleDivederItem(AbsDividerItemDecoration.VERTICAL))
        recycle.layoutManager = gManager
        recycle.adapter = MainAdapter(items)

        var gridManager = GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)

        //以下方法解决不了recycle显示不全 到时候看别的地方吧
        //以下方法解决不了recycle显示不全 到时候看别的地方吧

        recycleTitle.layoutManager = object :GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false){
            override fun canScrollVertically() = false
        }
        //解决数据加载不完的问题
        recycleTitle.isNestedScrollingEnabled = false
        recycleTitle.setHasFixedSize(true)
        //解决数据加载完成后, 没有停留在顶部的问题
        recycleTitle.isFocusable = false

        recycleTitle.adapter = GridMainAdapter(titleItem,this)

        svgaPlayer.setOnClickListener(this)
        simple.setOnClickListener(this)
        (recycleTitle.adapter as GridMainAdapter).setOnClickListener(listener = object : GridMainAdapter.ClickListener{
            override fun onClick(position: Int) {
                when(position){
                    0 ->  startActivity(Intent(baseContext,SVGAProjectActivity::class.java))
                    1 ->  startActivity(Intent(baseContext,SimpleActivity::class.java))
                    2 ->  startActivity(Intent(baseContext,NestedScrollViewActivity::class.java))
                    3 ->  startActivity(Intent(baseContext,FundamentalTypeActivity::class.java))
                    4 ->  startActivity(Intent(baseContext,SingleInstanceActivity::class.java))
                    5 ->  startActivity(Intent(baseContext,FunctionActivity::class.java))
                    6 ->  startActivity(Intent(baseContext,SimpleEyesActivity::class.java))
                    7 ->  startActivity(Intent(baseContext, SettingActivity::class.java))
                    8 ->  startActivity(Intent(baseContext, TangramActivity::class.java))
                    9 ->  startActivity(Intent(baseContext, GreenDaoActivity::class.java))
                    10 ->  startActivity(Intent(baseContext, InterplatorActivity::class.java))
                    11 ->  startActivity(Intent(baseContext, FloatTableActivity::class.java))
                    12 ->  startActivity(Intent(baseContext, XLayoutActivity::class.java))
                    13 ->  startActivity(Intent(baseContext, BannerAndDragActivity::class.java))
                    14 ->  startActivity(Intent(baseContext, SVAGHeaderActivity::class.java))
                    15 ->  startActivity(Intent(baseContext, RedPackageRainActivity::class.java))

                    else -> toast("no view setClick event")
                }
            }
        })

        ResourceUtils.getInstance().intialize()

    }

    override fun finish() {
        super.finish()
        recycle.layoutManager = null
        recycle.adapter = null

        recycleTitle.layoutManager =null
        recycleTitle.adapter = null

        svgaPlayer.setOnClickListener(null)
        simple.setOnClickListener(null)


    }

    override fun onResume() {
        super.onResume()
        val pre = PreferenceManager.getDefaultSharedPreferences(this)
        val dir = pre.getString("key_edtkpreference", "")//两个参数,一个是key，就是在PreferenceActivity的xml中设置的,另一个是取不到值时的默认值
        if(!TextUtils.isEmpty(dir)){
            ToastUtils.showToast(this,"SettingActivity 中设置的值为："+dir)
        }

    }

    fun ksClick(view: View) {
        toast("GO GO KOTLIN")
    }

    override fun onClick(view: View){

        when(view.id){
            svgaPlayer.id  -> startActivity(Intent(this,SVGAProjectActivity::class.java))
            simple.id  -> startActivity(Intent(this,SVGAProjectActivity::class.java))
            else -> ToastUtils.showCustomToast(this,"no view setClick event")
        }

    }

    private fun toast(msg : String) {
        ToastUtils.showCustomToast(baseContext,msg)
    }

    private fun sum(a:Int,b:Int):Int{
        return a + b
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

    override fun toggleOverridePendingTransition() = true

    override fun getOverridePendingTransition() = TransitionMode.TOP
}