package com.kotlin.study.greendaogenlib.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.kotlin.study.greendaogenlib.DaoMaster


import org.greenrobot.greendao.database.Database

/**
 * 更新
 */
class UpdateOpenHelper(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?) : DaoMaster.OpenHelper(context, name, factory) {

    /**
     * 数据库升级
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    override fun onUpgrade(db: Database?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            //操作数据库的更新 有几个表升级都可以传入到下面

        }
    }
}