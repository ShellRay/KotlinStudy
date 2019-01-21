package com.kotlin.study.net


/**
 * Description:
 */

object Api {


    /**
     * 主域名
     */
    val BASE_URL: String get() = "http://baobab.kaiyanapp.com/"

    /**
     * 获取默认Service
     */
    fun getDefault() = RetrofitConfig.getDefaultService()


}