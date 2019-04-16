package com.kotlin.study.activity

import android.annotation.SuppressLint
import android.content.Context
import android.net.sip.SipAudioCall
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.kotlin.study.BaseActivity
import com.kotlin.study.GlideApp
import com.kotlin.study.R
import com.kotlin.study.bean.PreviewData
import com.kotlin.study.utils.Httputil
import com.kotlin.study.utils.JSONutils
import com.kotlin.study.widget.GlideVVSimpleTarget
import com.tmall.wireless.tangram.TangramBuilder
import com.tmall.wireless.tangram.TangramEngine
import com.tmall.wireless.tangram.dataparser.concrete.Card
import com.tmall.wireless.tangram.dataparser.concrete.Cell
import com.tmall.wireless.tangram.structure.BaseCell
import com.tmall.wireless.tangram.support.ExposureSupport
import com.tmall.wireless.tangram.support.SimpleClickSupport
import com.tmall.wireless.tangram.support.async.CardLoadSupport
import com.tmall.wireless.vaf.framework.VafContext
import com.tmall.wireless.vaf.virtualview.Helper.ImageLoader
import com.tmall.wireless.vaf.virtualview.event.EventData
import com.tmall.wireless.vaf.virtualview.event.EventManager
import com.tmall.wireless.vaf.virtualview.event.IEventProcessor
import com.tmall.wireless.vaf.virtualview.view.image.ImageBase
import io.reactivex.annotations.NonNull
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


/**
 * @author ShellRay
 * Created  on 2019/3/28.
 * @description
 */
class TangramActivity : BaseActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.add -> {
                getData("KouBeiHome")}
        }

    }


    val builder: TangramBuilder.InnerBuilder = TangramBuilder.newInnerBuilder(this@TangramActivity)
    private var engine :TangramEngine? = null
    private var mainHandler: Handler? = null
    private val HANDLER_UPUI = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tangram)

        engine = builder.build()
        findViewById<TextView>(R.id.add).setOnClickListener(this)
        initTangram()
        initVirtual()
        mainHandler = Handler(Handler.Callback { msg ->
            when (msg.what) {
                HANDLER_UPUI -> {
                    val name = msg.data.getString("name")
                    val data = msg.data.get("data") as PreviewData
                    for (i in 0 until data!!.templates.size) {
                        val vvname = name!! + i
                        builder.registerVirtualView<View>(vvname)
                        engine!!.setVirtualViewTemplate(JSONutils.getBase64(data!!.templates.get(i)))
                    }
                    val jsonArray = data!!.data.getAsJsonArray("jsondata")
                    val json = jsonArray.toString()
                    var jsdata: JSONArray? = null
                    try {
                        jsdata = JSONArray(json)
                        engine!!.setData(jsdata)
                        engine!!.refresh()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                }
                else -> {
                }
            }


            false
        })

    }

    private fun initTangram() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycle)
        engine!!.register(SimpleClickSupport::class.java, object : SimpleClickSupport() {

            override fun setOptimizedMode(optimizedMode: Boolean) {
                super.setOptimizedMode(optimizedMode)
            }

            override fun onClick(targetView: View?, cell: BaseCell<*>?, eventType: Int) {
                super.onClick(targetView, cell, eventType)
            }

            override fun onClick(targetView: View?, cell: Cell<*>?, eventType: Int) {
                super.onClick(targetView, cell, eventType)
            }

            override fun onClick(targetView: View?, cell: BaseCell<*>?, eventType: Int, params: MutableMap<String, Any>?) {
                super.onClick(targetView, cell, eventType, params)
            }

            override fun defaultClick(targetView: View?, cell: BaseCell<*>?, eventType: Int) {
                super.defaultClick(targetView, cell, eventType)
            }
        })


        engine!!.register(CardLoadSupport::class.java, CardLoadSupport())


        engine!!.register(ExposureSupport::class.java, object : ExposureSupport() {
            override fun onExposure(card: Card, offset: Int, position: Int) {

            }

        });


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                engine!!.onScrolled()
            }
        })

        engine!!.bindView(recyclerView)

    }

    private fun initVirtual() {
        val vafContext = engine!!.getService<VafContext>(VafContext::class.java!!)
        vafContext.setImageLoaderAdapter(object : ImageLoader.IImageLoaderAdapter {
            override fun bindImage(uri: String?, imageBase: ImageBase, reqWidth: Int, reqHeight: Int) {
                val target = GlideVVSimpleTarget(imageBase)
                target.setSize(reqWidth, reqHeight)
                val bitmapTypeRequest = GlideApp.with(applicationContext).asBitmap()
                        .load(uri)
                if (imageBase.getTag() != null) {
                    bitmapTypeRequest.circleCrop().into(target)
                } else {
                    bitmapTypeRequest.into(target)
                }
            }

            override fun getBitmap(uri: String?, reqWidth: Int, reqHeight: Int, lis: ImageLoader.Listener) {
                val target = GlideVVSimpleTarget(lis)
                target.setSize(reqWidth, reqHeight)
                GlideApp.with(applicationContext).asBitmap()
                        .load(uri).into(target)
            }
        })

        vafContext.getEventManager().register(EventManager.TYPE_Click, object : IEventProcessor {

            override fun process(data: EventData): Boolean {
                val action = data.mVB.getAction()


                Toast.makeText(this@TangramActivity,
                        "TYPE_Click view name:" + data.mVB.getTag("name")
                                + "\n action:" + action, Toast
                        .LENGTH_LONG).show()
                return true
            }
        })
        vafContext.getEventManager().register(EventManager.TYPE_Exposure, object : IEventProcessor {

            override fun process(data: EventData): Boolean {
                //handle here
                return true
            }
        })
        vafContext.getEventManager().register(EventManager.TYPE_LongCLick, object : IEventProcessor {
            override fun process(data: EventData): Boolean {

                return true
            }
        })

    }

    private fun getData(name: String?) {

        //        String url = "http://192.168.11.131:7788/" + name + "/data.json";
        val url = "http://192.168.25.164:8000/" + name// + "/data.json";

        Httputil.getdata(url, object : Httputil.VVout {
            override fun out(data: PreviewData?) {
                if (data != null) {
                    val msg = mainHandler?.obtainMessage()
                    msg!!.what = HANDLER_UPUI
                    val b = Bundle()
                    b.putString("name", name)
                    b.putSerializable("data", data)
                    msg.data = b
                    mainHandler!!.sendMessage(msg)
                }
            }
        })

        //解析本地数据
        val string = getJson("KouBeiHome/data.json", this)
        val data = Gson().fromJson(string, PreviewData::class.java)

        for (i in 0 until data.templates.size) {
            val vvname = name + i
            builder.registerVirtualView<View>(vvname)
            engine!!.setVirtualViewTemplate(JSONutils.getBase64(data.templates[i]))
        }
        val jsonArray = data.data.getAsJsonArray("jsondata")
        val json = jsonArray.toString()
        var jsdata: JSONArray? = null
        try {
            jsdata = JSONArray(json)
            engine!!.setData(jsdata)
            engine!!.refresh()
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    fun getJson(fileName: String?, context: Context?): String {
        //将json数据变成字符串
        val stringBuilder = StringBuilder()
        try {
            //获取assets资源管理器
            val assetManager = context!!.assets
            //通过管理器打开文件并读取
            val bf = BufferedReader(InputStreamReader(
                    assetManager.open(fileName)))
            var line: String
            while (true) {
                line = bf.readLine()?: break
                stringBuilder.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return stringBuilder.toString()
    }
    override fun onDestroy() {
        super.onDestroy()
        engine!!.destroy()
    }


}