package com.kotlin.study.utils

import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and

/**
 * Created by GG on 2018/12/18.
 */
class  MD5 {

    companion object {
        val TAG = "MD5"

        private val HASH_ALGORITHM = "MD5"
        private val RADIX = 16 // 16进制

        fun generate(`val`: String): String {
            val md5 = getMD5(`val`.toByteArray())
            val bi = BigInteger(1, md5).abs()
            return bi.toString(RADIX)
        }

        private fun getMD5(data: ByteArray): ByteArray? {
            var hash: ByteArray? = null
            try {
                val digest = MessageDigest.getInstance(HASH_ALGORITHM)
                digest.update(data)
                hash = digest.digest()
            } catch (e: NoSuchAlgorithmException) {
                //			LogUtils.printStackTrace(e);
            }

            return hash
        }


        fun main(args: Array<String>) {
            val test = "uid=89187079&key=ux7YIWRVw0"
            val md5 = generate(test)
            println(md5)
        }

        fun getMessageDigest(buffer: ByteArray): String? {
            val hexDigits = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')
            try {
                val mdTemp = MessageDigest.getInstance("MD5")
                mdTemp.update(buffer)
                val md = mdTemp.digest()
                val j = md.size
                val str = CharArray(j * 2)
                var k = 0
                for (i in 0 until j) {
                    val byte0 = md[i]
                    str[k++] = hexDigits[(byte0.toInt() ushr 4) and 0xf]
                    str[k++] = hexDigits[(byte0 and 0xf).toInt()]
                }
                return String(str)
            } catch (e: Exception) {
                return null
            }

        }
    }
}