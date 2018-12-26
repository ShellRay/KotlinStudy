package com.kotlin.study.bean

import java.io.Serializable

/**
 * @author ShellRay
 * Created  on 2018/12/26.
 * @description
 */
class UserBean(val userName : String, val userId : Long, val userSex:String, val userDesc:String) : Serializable{
}