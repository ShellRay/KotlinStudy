package com.kotlin.study.activity

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.kotlin.study.BaseActivity
import com.kotlin.study.R
import com.kotlin.study.utils.ConvenientUtil
import java.util.*

/**
 * Created by GG on 2018/2/27.
 */
class WelcomeActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        var WelcomeActivity = this@WelcomeActivity
        var timer = Timer()
        var timerTast = TimerTasks(WelcomeActivity)
        timer.schedule(timerTast,3000)

    }

    class TimerTasks(private val baseContext: WelcomeActivity) : java.util.TimerTask(){
        override fun run() {
            Log.d("kotlin","jump")
            Looper.prepare()
            Toast.makeText(baseContext, "inside thread : Jump it", Toast.LENGTH_SHORT).show()
            var intent = Intent(baseContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            baseContext.startActivity(intent)
            baseContext.finish()
            Looper.loop()
        }

    }

}