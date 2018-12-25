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
import kotlinx.android.synthetic.main.activity_fundamental_type.*

class SingleInstanceActivity :AppCompatActivity(){

    //kotlin取消了activity.this的用法
    val instance = lazy { this }

    val catalogs = listOf<String>("kotlin的单例模式\n",
            "饿汉式\n" +
                    "懒汉式\n" +
                    "线程安全的懒汉式\n" +
                    "双重校验锁式\n" +
                    "静态内部类式" ,
            "一、饿汉式实现\n" +
                    "//Java实现\n" +
                    "public class SingletonDemo {\n" +
                    "    private static SingletonDemo instance=new SingletonDemo();\n" +
                    "    private SingletonDemo(){\n" +
                    "\n" +
                    "    }\n" +
                    "    public static SingletonDemo getInstance(){\n" +
                    "        return instance;\n" +
                    "    }\n" +
                    "}\n" +
                    "//Kotlin实现\n" +
                    "object SingletonDemo\n" +
                    "选择\"Kotlin\",选择“Show Kotlin Bytecode\" 发现字节码与java代码同" +
                    "\n",
            "二、懒汉式\n" +
                    "//Java实现\n" +
                    "public class SingletonDemo {\n" +
                    "    private static SingletonDemo instance;\n" +
                    "    private SingletonDemo(){}\n" +
                    "    public static SingletonDemo getInstance(){\n" +
                    "        if(instance==null){\n" +
                    "            instance=new SingletonDemo();\n" +
                    "        }\n" +
                    "        return instance;\n" +
                    "    }\n" +
                    "}\n" +
                    "//Kotlin实现\n" +
                    "class SingletonDemo private constructor() {\n" +
                    "    companion object {\n" +
                    "        private var instance: SingletonDemo? = null\n" +
                    "            get() {\n" +
                    "                if (field == null) {\n" +
                    "                    field = SingletonDemo()\n" +
                    "                }\n" +
                    "                return field\n" +
                    "            }\n" +
                    "        fun get(): SingletonDemo{\n" +
                    "        //细心的小伙伴肯定发现了，这里不用getInstance作为为方法名，是因为在伴生对象声明时，内部已有getInstance方法，所以只能取其他名字\n" +
                    "         return instance!!\n" +
                    "        }\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" ,
            "三、线程安全的懒汉式\n" +
                    "//Java实现\n" +
                    "public class SingletonDemo {\n" +
                    "    private static SingletonDemo instance;\n" +
                    "    private SingletonDemo(){}\n" +
                    "    public static synchronized SingletonDemo getInstance(){//使用同步锁\n" +
                    "        if(instance==null){\n" +
                    "            instance=new SingletonDemo();\n" +
                    "        }\n" +
                    "        return instance;\n" +
                    "    }\n" +
                    "}\n" +
                    "//Kotlin实现\n" +
                    "class SingletonDemo private constructor() {\n" +
                    "    companion object {\n" +
                    "        private var instance: SingletonDemo? = null\n" +
                    "            get() {\n" +
                    "                if (field == null) {\n" +
                    "                    field = SingletonDemo()\n" +
                    "                }\n" +
                    "                return field\n" +
                    "            }\n" +
                    "        @Synchronized\n" +
                    "        fun get(): SingletonDemo{\n" +
                    "            return instance!!\n" +
                    "        }\n" +
                    "    }\n" +
                    "\n" +
                    "}\n" +
                    "\n" ,
            "四、双重校验锁式（Double Check)\n" +
                    "//Java实现\n" +
                    "public class SingletonDemo {\n" +
                    "    private volatile static SingletonDemo instance;\n" +
                    "    private SingletonDemo(){} \n" +
                    "    public static SingletonDemo getInstance(){\n" +
                    "        if(instance==null){\n" +
                    "            synchronized (SingletonDemo.class){\n" +
                    "                if(instance==null){\n" +
                    "                    instance=new SingletonDemo();\n" +
                    "                }\n" +
                    "            }\n" +
                    "        }\n" +
                    "        return instance;\n" +
                    "    }\n" +
                    "}\n" +
                    "//kotlin实现\n" +
                    "class SingletonDemo private constructor() {\n" +
                    "    companion object {\n" +
                    "        val instance: SingletonDemo by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {\n" +
                    "        SingletonDemo() }\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "哇！小伙伴们惊喜不，感不感动啊。我们居然几行代码就实现了多行的Java代码。其中我们运用到了Kotlin的延迟属性 Lazy。\n" +
                    "Lazy是接受一个 lambda 并返回一个 Lazy 实例的函数，返回的实例可以作为实现延迟属性的委托： 第一次调用 get() 会执行已传递给 lazy() 的 lambda 表达式并记录结果， 后续调用 get() 只是返回记录的结果。\n" +
                    "\n" ,
            "五、静态内部类式\n" +
                    "//Java实现\n" +
                    "public class SingletonDemo {\n" +
                    "    private static class SingletonHolder{\n" +
                    "        private static SingletonDemo instance=new SingletonDemo();\n" +
                    "    }\n" +
                    "    private SingletonDemo(){\n" +
                    "        System.out.println(\"Singleton has loaded\");\n" +
                    "    }\n" +
                    "    public static SingletonDemo getInstance(){\n" +
                    "        return SingletonHolder.instance;\n" +
                    "    }\n" +
                    "}\n" +
                    "//kotlin实现\n" +
                    "class SingletonDemo private constructor() {\n" +
                    "    companion object {\n" +
                    "        val instance = SingletonHolder.holder\n" +
                    "    }\n" +
                    "\n" +
                    "    private object SingletonHolder {\n" +
                    "        val holder= SingletonDemo()\n" +
                    "    }\n" +
                    "\n" +
                    "}\n" +
                    "\n",
            "补充\n" +
                    "在该篇文章结束后，有很多小伙伴咨询，如何在Kotlin版的Double Check，给单例添加一个属性，这里我给大家提供了一个实现的方式。（不好意思，最近才抽出时间来解决这个问题）\n" +
                    "class SingletonDemo private constructor(private val property: Int) {//这里可以根据实际需求发生改变\n" +
                    "  \n" +
                    "    companion object {\n" +
                    "        @Volatile private var instance: SingletonDemo? = null\n" +
                    "        fun getInstance(property: Int) =\n" +
                    "                instance ?: synchronized(this) {\n" +
                    "                    instance ?: SingletonDemo(property).also { instance = it }\n" +
                    "                }\n" +
                    "    }\n" +
                    "}\n" +
                    "\n"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fundamental_type)
        rcyCatalog.layoutManager = LinearLayoutManager(this)
        rcyCatalog.adapter = MainAdapter(catalogs,this,ClickCallback(this))

    }

    inner class ClickCallback(private val kotlinCatalog: SingleInstanceActivity) : MainAdapter.OnClickCallback {
        override fun onClick(view: View, position: Int) {
            var intent = Intent()
            when(position){
                0 -> intent.setClass(kotlinCatalog, SingleInstanceActivity::class.java)

            }
            startActivity(intent)
        }
    }


    class MainAdapter(val items: List<String>, val instances: SingleInstanceActivity, clickCallback: OnClickCallback?) : RecyclerView.Adapter<MainAdapter.ViewHolder>()
    {

        private var instance = instances
        private var mOnClickCallback = clickCallback

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(TextView(parent.context))
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = items[position]
            holder.textView.textSize = 17f
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