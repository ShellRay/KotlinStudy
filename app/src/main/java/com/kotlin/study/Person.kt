package com.kotlin.study

/**
 * @author ShellRay
 * Created  on 2019/9/18.
 * @description
 */
data class Person(val name:String, private val age:Int?= null) {

    fun main(args:Array<String>){

        val listOf = listOf(Person("fd"), Person("er", 25))

        val oldest = listOf.maxBy { it.age ?:0 }

        println("this is ildest is: $oldest")
    }

}