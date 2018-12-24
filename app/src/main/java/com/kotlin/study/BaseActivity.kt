package com.kotlin.study

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.kotlin.study.manager.ActivityStack

/**
 * @author ShellRay
 * Created  on 2018/12/24.
 * @description
 */

@SuppressLint("Registered")
open class BaseActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityStack.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityStack.remove(this)
    }
}