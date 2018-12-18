package com.kotlin.study.net

import com.kotlin.study.net.NetManager.NetManager.instance

/**
 * Created by GG on 2018/12/18.
 */
class  NetManager {
    private var mHttpConfig: HttpConfig? = null

   object NetManager {
        val instance = NetManager()
    }

    companion object {
        fun getInstance(): com.kotlin.study.net.NetManager {
            return instance
        }
    }


    fun getHttpConfig(): HttpConfig? {
        return mHttpConfig
    }

    fun setHttpConfig(httpConfig: HttpConfig) {
        this.mHttpConfig = httpConfig
    }
}