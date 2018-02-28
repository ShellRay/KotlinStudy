package com.kotlin.study.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.kotlin.study.activity.MainActivity

/**
 * Created by GG on 2018/2/27.
 */
class ConvenientUtil {

    companion object {


        fun startActivity(context: Context):Unit{
//            Toast.makeText(context,"跳转", Toast.LENGTH_SHORT).show()
            var intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}