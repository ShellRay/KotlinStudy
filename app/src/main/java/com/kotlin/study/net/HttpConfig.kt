package com.kotlin.study.net

import java.util.HashMap

/**
 * Created by GG on 2018/12/18.
 */
interface HttpConfig {
     fun getHeader(): HashMap<String, String>
}