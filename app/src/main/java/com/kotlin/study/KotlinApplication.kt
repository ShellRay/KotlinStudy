package com.kotlin.study

import android.app.Application
import android.content.res.Resources
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig

/**
 * @author ShellRay
 * Created  on 2019/1/4.
 * @description
 */
class KotlinApplication: Application() {

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
        initFresco()
        //todo 这里还要做崩溃检查 腾讯的bugly 热更新等操作


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