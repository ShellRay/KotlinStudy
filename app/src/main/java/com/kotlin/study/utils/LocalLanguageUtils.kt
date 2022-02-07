package com.kotlin.study.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.LocaleList
import com.kotlin.study.UserPreferences
import java.util.*

object LocalLanguageUtils {

    /**
     * Activity 更新语言资源
     */
    fun getAttachBaseContext(context: Context): Context {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return setAppLanguageApi24(context)
        } else {
            setAppLanguage(context)
        }
        return context
    }

    /**
     * 设置应用语言
     */
    @Suppress("DEPRECATION")
    fun setAppLanguage(context: Context) {
        val resources = context.resources
        val displayMetrics = resources.displayMetrics
        val configuration = resources.configuration
        // 获取当前系统语言，默认设置跟随系统
        val locale = getAppLocale()
//        val locale = Locale.ENGLISH
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration, displayMetrics)
    }

    /**
     * 兼容 7.0 及以上
     */
    @TargetApi(Build.VERSION_CODES.N)
    private fun setAppLanguageApi24(context: Context): Context {
        val locale = getAppLocale()
//        val locale = Locale.ENGLISH
        val resource = context.resources
        val configuration = resource.configuration
        configuration.setLocale(locale)
        configuration.setLocales(LocaleList(locale))
        return context.createConfigurationContext(configuration)
    }

    /**
     * 获取 App 当前语言
     */
    private fun getAppLocale() = when (UserPreferences.getUserCurrentLanguage()) {
        0 -> { // 跟随系统
            getSystemLocale()
        }
        1 -> { // 中文
            Locale.CHINA
        }
        2 -> { // 英文
            Locale.ENGLISH
        }
        3 -> { //朝鲜文
            Locale.KOREA
        }
        else -> Locale.ENGLISH
    }

    /**
     * 获取当前系统语言，如未包含则默认英文
     */
    private fun getSystemLocale(): Locale {
        val systemLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList.getDefault()[0]
        } else {
            Locale.getDefault()
        }
        return when (systemLocale.language) {
            Locale.CHINA.language -> {
                Locale.CHINA
            }
            Locale.ENGLISH.language -> {
                Locale.ENGLISH
            }
            else -> {
                Locale.ENGLISH
            }
        }
    }
}