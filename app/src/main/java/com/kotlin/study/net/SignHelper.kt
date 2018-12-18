package com.kotlin.study.net

import android.text.TextUtils
import com.kotlin.study.utils.MD5
import java.security.MessageDigest
import java.util.*
import kotlin.experimental.and

/**
 * Created by GG on 2018/12/18.
 */
object SignHelper {
    private val KEY = "LIVEMobile(2016)#$%&*"
    private val KEY_BILLING = "key=ux7YIWRVw0"

    fun addSign(sArray: MutableMap<String, String>) {
        val map = paraFilter(sArray)
        val str = createLinkString(map)
        val _sign = SHA1(str + "&" + KEY)
        sArray["livesign"] = _sign
    }

    fun addBillingSign(params: MutableMap<String, String>) {
        val map = paraFilter(params)
        val str = createLinkString(map)
        val md5 = MD5.generate(str + "&" + KEY_BILLING)
        params["sign"] = md5.toUpperCase()
    }

    /**
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    private fun paraFilter(sArray: Map<String, String>?): Map<String, String> {
        val result = HashMap<String, String>()

        if (sArray == null || sArray.size <= 0)
            return result

        for (key in sArray.keys) {
            val value = sArray[key]
            if (value == null || value == "" || key.equals("sign", ignoreCase = true)
                    || key.equals("sign_type", ignoreCase = true)) {
                continue
            }
            result[key] = value
        }

        return result
    }


    /**
     * 使用SAH1进行加密
     * @param str 需要加密的字符串
     * @return String 加密后的值
     */
    private fun SHA1(str: String): String {
        if (TextUtils.isEmpty(str))
            return ""

        var value = ""
        try {
            val md = MessageDigest.getInstance("SHA-1")
            md.update(str.toByteArray(charset("UTF-8")))
            val result = md.digest()
            val sb = StringBuffer()
            for (b in result) {
                val i = b and 0xff.toByte()
                if (i < 0xf) {
                    sb.append(0)
                }
                sb.append(Integer.toHexString(i.toInt()))
            }
            value = sb.toString().toUpperCase()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return value
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    private fun createLinkString(params: Map<String, String>?): String? {
        if (params == null)
            return null

        val keys = ArrayList(params.keys)
        Collections.sort(keys)

        var prestr = ""

        for (i in keys.indices) {
            val key = keys[i]
            val value = params[key]

            if (i == keys.size - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value
            } else {
                prestr = "$prestr$key=$value&"
            }
        }

        return prestr
    }
}