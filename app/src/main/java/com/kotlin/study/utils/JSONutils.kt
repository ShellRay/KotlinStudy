package com.kotlin.study.utils

import android.text.TextUtils
import android.util.Base64

/**
 * Created by dongdongzheng on 2018/11/14.
 */

object JSONutils {

    fun getBase64(card: String): ByteArray? {
        return if (TextUtils.isEmpty(card)) {
            null
        } else Base64.decode(card, Base64.DEFAULT)
    }

}
