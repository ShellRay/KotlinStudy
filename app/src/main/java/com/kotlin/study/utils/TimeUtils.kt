package com.kotlin.study.utils

import java.text.SimpleDateFormat
import java.util.*


/**
 * Description:
 */

object TimeUtils {

    /**
     * 将秒数转换为 00 00' 00''格式
     * @seconds 秒
     */
    @JvmStatic
    fun getElapseTimeForShow(seconds: Int): String {
        val sb = StringBuilder()
        val hour = seconds / (60 * 60)
        if (hour != 0) {
            sb.append(hour).append("")
        }
        val minute = (seconds - 60 * 60 * hour) / 60
        if (minute != 0 && minute > 9) {
            sb.append(minute).append("' ")
        } else {
            sb.append("0").append(minute).append("' ")
        }
        val second = seconds - 60 * 60 * hour - 60 * minute
        if (second != 0 && second > 9) {
            sb.append(second).append("\"")
        } else {
            sb.append("0").append(second).append("\"")
        }
        return sb.toString()
    }


    /**
     * 将日期转换为指定格式的日期字符串
     *
     * @param date   日期
     * @param format 格式化字符串
     */
    @JvmStatic
    fun getDateString(date: Date, format: String): String {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        return formatter.format(date)
    }

    /**
     * 判断是否是今天
     *
     */
    @JvmStatic
    fun isCurrentDay(time: Long): Boolean {
        return isSameDay(Date(), Date(time))
    }


    /**
     * 是否是同一天
     */
    @JvmStatic
    fun isSameDay(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.time = date1
        cal2.time = date2
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }


    /**
     * 格式化时间
     * 如果是当天就格式化时间为 HH:mm
     * 如果是一周前发布的就显示几天前
     * 如果超过5天显示一周前
     * @currentDate 当前日期
     */
    @JvmStatic
    fun getTimeStr(currentDate: Date): String {
        val todayStart = Calendar.getInstance()
        todayStart.set(Calendar.HOUR_OF_DAY, 0)
        todayStart.set(Calendar.MINUTE, 0)
        todayStart.set(Calendar.SECOND, 0)
        todayStart.set(Calendar.MILLISECOND, 0)
        val numberBeforeCurrentDay = getNumberBeforeCurrentDay(todayStart.time, currentDate)
        return when {
            numberBeforeCurrentDay == 0 -> {//当天发布
                val timeFormatter24 = SimpleDateFormat("HH:mm", Locale.getDefault())
                timeFormatter24.format(currentDate)
            }
            numberBeforeCurrentDay <= 5 -> {//小于一周前
                "$numberBeforeCurrentDay 天前"
            }
            else -> {//一周前
                "1 周前"
            }
        }
    }

    private fun getNumberBeforeCurrentDay(todayBegin: Date, currentDate: Date): Int {
        var index = 7
        while (index >= 1) {
            val date = Date(todayBegin.time - 3600 * 24 * 1000 * index)
            if (currentDate.before(date)) break else index--
        }
        return index

    }
}