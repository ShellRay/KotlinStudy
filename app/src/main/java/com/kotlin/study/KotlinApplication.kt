package com.kotlin.study

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.net.http.HttpResponseCache
import android.support.multidex.MultiDexApplication
import android.widget.ImageView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.kotlin.study.greendaogenlib.utils.DBHelper
import com.kotlin.study.utils.GlideLoadUtils
import com.tmall.wireless.tangram.util.IInnerImageSetter
import com.tmall.wireless.tangram.TangramBuilder
import io.reactivex.annotations.NonNull
import io.reactivex.annotations.Nullable
import kotlinx.android.synthetic.main.fragment_main.view.*
import java.io.File

val mAppContext: Context
get() = KotlinApplication.INSTANCE
/**
 * @author ShellRay
 * Created  on 2019/1/4.
 * @description
 */
class KotlinApplication: MultiDexApplication() {

    companion object {

        lateinit var INSTANCE: Application

        /**
         * 获取资源文件访问对象
         */
        @JvmStatic
        fun getResource(): Resources {
            return INSTANCE.resources
        }

    }

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this
//        GlobalConfig.setApplicationContext(this)
//        GlobalConfig.setAppDebug(false)
//        GlobalConfig.setApplicationRootDir("simpleeyes")
        initARoute()
        DBHelper.getInstance(this)
        initFresco()
        initTangram()
        //todo 这里还要做崩溃检查 腾讯的bugly 热更新等操作

        val cacheDir = File(applicationContext.cacheDir, "http")
        HttpResponseCache.install(cacheDir, 1024 * 1024 * 128)

    }

    /**
     * 初始化 Tangram 环境
     * */
    private fun initTangram() {
        TangramBuilder.init(this, object : IInnerImageSetter {
            override fun <IMAGE : ImageView> doLoadImageUrl(view: IMAGE, url: String?) {
                //假设你使用 Picasso 加载图片                		Picasso.with(context).load(url).into(view);
                GlideLoadUtils.getInstance().loadImageAsGif(applicationContext,url,view)
            }
        }, ImageView::class.java)
    }

    /**
     * 初始化Fresco,打开压缩
     */
    private fun initFresco() {
        val config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .build()
        Fresco.initialize(this, config)
    }

    /**
     * 初始化路由操作
     */
    private fun initARoute() {
        /*if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)*/
    }

   /* override fun initUpdateParams(): LocalUpdateService.UpdateParams {
        val localCheckUpdateProtocol = CheckUpdateProtocol()
        localCheckUpdateProtocol.packageName = applicationContext.packageName
        localCheckUpdateProtocol.versionName = AppUtils.getAppVersionName(applicationContext)
        localCheckUpdateProtocol.versionCode = AppUtils.getAppVersionCode(applicationContext)
        localCheckUpdateProtocol.udid = UDIDUtils.getRandomUUID()
        val localDefault = Locale.getDefault()
        localCheckUpdateProtocol.language = localDefault.displayCountry + "-" + localDefault.displayLanguage
        localCheckUpdateProtocol.rom = Build.MODEL//版本信息
        localCheckUpdateProtocol.romVersion = Build.VERSION.RELEASE
        localCheckUpdateProtocol.appName = "simpleeyes"
        localCheckUpdateProtocol.isOem = false
        val updateParams = LocalUpdateService.UpdateParams()
        updateParams.checkUpdateProtocol = localCheckUpdateProtocol
        return updateParams
    }*/
}