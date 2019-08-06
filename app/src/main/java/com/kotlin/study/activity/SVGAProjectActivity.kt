package com.kotlin.study.activity

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import com.kotlin.study.BaseActivity
import com.kotlin.study.R
import com.kotlin.study.utils.ResourceUtils
import com.opensource.svgaplayer.SVGADrawable
import com.opensource.svgaplayer.SVGADynamicEntity
import com.opensource.svgaplayer.SVGAParser
import com.opensource.svgaplayer.SVGAVideoEntity
import kotlinx.android.synthetic.main.activity_svga.*
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.io.ObjectInput
import java.net.URL

/**
 * Created by GG on 2018/12/14.
 */
class SVGAProjectActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_svga)

        svgaPlayer.setOnClickListener(this)
        svgaCacheButton.setOnClickListener(this)
        parser = SVGAParser(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            svgaPlayer -> loadAnimations()
            svgaCacheButton -> loadAnimationsFromCache()
        }
    }
    var parser:SVGAParser? = null
    private fun loadAnimationsFromCache() {
        toast("click")

        try {
            parser = SVGAParser(this)
            val filePath = ResourceUtils.getInstance().getFilePath("1")
            val file = File(filePath)
            if (!file.exists()) {
                Log.e("shell", "素材文件未下载")
                return
            }
            var `is`: InputStream? = FileInputStream(file)

            parser!!.parse( `is`!!, "banner",

                object : SVGAParser.ParseCompletion {

                    override fun onError() {
                    toast("网络加载资源失败")
                }

                override fun onComplete(videoItem: SVGAVideoEntity) {
                    var drawable = SVGADrawable(videoItem, requestDynamicItemWithSpannableText("Pony 送了一打风油精给主播"))
                    svgaCache.setImageDrawable(drawable)
                    svgaCache.startAnimation()
                    parser = null
                }

            },true)
        } catch (e: Exception) {
            e.stackTrace
            SystemClock.sleep(3000)
            loadAnimationsFromCache()

        }
    }

    private fun loadAnimations() {
        try {
            var requestDynamicItemWithSpannableText:SVGADynamicEntity?= null
            parser!!.parse(
                    URL("https://github.com/yyued/SVGA-Samples/blob/master/kingset.svga?raw=true"),
                    object : SVGAParser.ParseCompletion {
                        override fun onError() {
                            toast("网络加载资源失败")
                        }

                        override fun onComplete(videoItem: SVGAVideoEntity) {
                            requestDynamicItemWithSpannableText = requestDynamicItemWithSpannableText("Pony 送了一打风油精给主播")
                            var drawable = SVGADrawable(videoItem, requestDynamicItemWithSpannableText!!)
                            requestDynamicItemWithSpannableText = null
                            svgaInternet.setImageDrawable(drawable)
                            svgaInternet.startAnimation()
                        }

                    }
            )
        } catch (e: Exception) {
            e.stackTrace
            SystemClock.sleep(3000)
            loadAnimations()
        }

    }

    /**
     * 你可以设置富文本到 ImageKey 相关的元素上
     * 富文本是会自动换行的，不要设置过长的文本
     *
     * @return
     */
    private fun requestDynamicItemWithSpannableText(msg: String): SVGADynamicEntity {
        var textPaint:TextPaint? = null
        val dynamicEntity = SVGADynamicEntity()
//        val spannableStringBuilder = SpannableStringBuilder(msg)
//        spannableStringBuilder.setSpan(ForegroundColorSpan(Color.YELLOW), 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        textPaint = TextPaint()
        textPaint.color = Color.GREEN
        textPaint.textSize = 28f
        /*dynamicEntity.setDynamicText(new StaticLayout(
                spannableStringBuilder,
                0,
                spannableStringBuilder.length(),
                textPaint,
                0,
                Layout.Alignment.ALIGN_CENTER,
                1.0f,//间隔
                0.0f,
                false
        ), "banner");*/
        dynamicEntity.setDynamicImage("http://res.img002.com/pic//7202_9.gif", "99")
        dynamicEntity.setDynamicText(msg, textPaint, "banner")

        dynamicEntity.setDynamicDrawer({ canvas, frameIndex ->
            var aPaint:Paint?=null
             aPaint = Paint()
            aPaint.color = Color.RED
            aPaint.isAntiAlias = true
            canvas.drawCircle(55f, 55f, (frameIndex % 5).toFloat(), aPaint)//闪烁
            //                canvas.drawCircle(55, 55, 7, aPaint);

            //必须 释放资源
            aPaint = null
            textPaint = null
            false
        }, "banner")//banner 是素材中的一个属性，只有存在才会将自定义的数字展示到其中
        return dynamicEntity
    }

    override fun finish() {
        super.finish()
        //必须 释放资源
        svgaCache.setImageDrawable(null)
        svgaInternet.setImageDrawable(null)
        svgaPlayer.setOnClickListener(null)
        svgaCache.setOnClickListener(null)
        parser = null
    }
    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}