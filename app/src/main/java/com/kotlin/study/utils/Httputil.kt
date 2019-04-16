package com.kotlin.study.utils

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.kotlin.study.bean.PreviewData

import java.io.IOException

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

/**
 * Created by dongdongzheng on 2018/11/16.
 */

object Httputil {


    fun getdata(url: String?, vout: VVout?) {

        Thread(Runnable {
            kotlin.run {
                val client = OkHttpClient()
                val request = Request.Builder()
                        .url(url)
                        .build()

                try {
                    val response = client.newCall(request).execute()
                    if (response != null && response.isSuccessful) {
                        if (response.body() != null) {
                            val string = response.body()!!.string()
                            val previewData = Gson().fromJson(string, PreviewData::class.java)
                            if (previewData != null) {
                                vout!!.out(previewData)
                            }
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: JsonSyntaxException) {
                    e.printStackTrace()
                }
            }

        }).start()
    }

    interface VVout {
        fun out(data: PreviewData?)
    }

}
