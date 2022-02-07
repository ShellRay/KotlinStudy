package com.kotlin.study.fragment

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.study.R
import com.kotlin.study.UserPreferences.getUserCurrentLanguage
import com.kotlin.study.UserPreferences.setUserCurrentLanguage
import com.kotlin.study.activity.MainActivity
import com.kotlin.study.manager.ActivityStack
import kotlinx.android.synthetic.main.fragment_fourth.*

/**
 * @author ShellRay
 * Created  on 2019/9/16.
 * @description
 */
class FourthFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = layoutInflater.inflate(R.layout.fragment_fourth, null)
        return inflate
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_change_language.setOnClickListener {
        // 更新选择状态
            var currentLanguage = getUserCurrentLanguage()
            if(currentLanguage == 0){
                setUserCurrentLanguage(2)
            }else if(currentLanguage == 2){
                setUserCurrentLanguage(3)
            }else{
                setUserCurrentLanguage(2)
            }
            ActivityStack.finishProgram()
//            ActivityStack.recreateAllOtherActivity(activity)
            reStartActivity()
        }
    }

    private fun reStartActivity() {
        val intent = Intent(activity, MainActivity::class.java)
        intent.flags = FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}