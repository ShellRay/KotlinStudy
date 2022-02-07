package com.kotlin.study

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit


object UserPreferences {

    private const val NAME = "andy."
    private const val KEY_IS_USER_LOGIN = "is_user_login"
    private const val KEY_IS_FIRST_LOGIN = "is_first_login"
    private const val KEY_LANGUAGE_CURRENT = "language_current"

    /**
     * 保存用户是否登录配置
     */
    fun saveUserIsLogin(isUserLogin: Boolean) {
        getSharedPreferences().edit {
            putBoolean(KEY_IS_USER_LOGIN, isUserLogin)
        }
    }

    /**
     * 保存用户是否是第一次登录
     */
    fun saveUserIsFirstLogin(isFirstLogin: Boolean) {
        getSharedPreferences().edit {
            putBoolean(KEY_IS_FIRST_LOGIN, isFirstLogin)
        }
    }

    /**
     * 获取用户是否登录
     */
    fun getUserIsLogin() = getSharedPreferences().getBoolean(KEY_IS_USER_LOGIN, false)

    /**
     * 获取用户是否是第一次登录
     */
    fun getUserIsFirstLogin() = getSharedPreferences().getBoolean(KEY_IS_FIRST_LOGIN, true)

    fun getUserCurrentLanguage() = getSharedPreferences().getInt(KEY_LANGUAGE_CURRENT, 0)

    fun setUserCurrentLanguage(currentLanguage: Int) {
        getSharedPreferences().edit {
            putInt(KEY_LANGUAGE_CURRENT, currentLanguage)
        }
    }

    private fun getSharedPreferences(): SharedPreferences {
        return KotlinApplication.INSTANCE.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

}