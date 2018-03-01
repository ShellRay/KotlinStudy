package com.kotlin.study.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.kotlin.study.R
import kotlinx.android.synthetic.main.activity_start.*

/**
 * Created by GG on 2018/2/27.
 */
class StartBeginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        setSupportActionBar(toolbar)

        text.setOnClickListener {
            print("123")
        }


    }


    /**
     * @param a
     * @param b
     * */
    private fun sum(a:Int,b:Int):Int{
        return a + b
    }

    private fun print(str : String){
        println(str)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if (id == R.id.action_settings) {
            val sum = sum(4, 5)
            Toast.makeText(baseContext,"more " + sum,Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}