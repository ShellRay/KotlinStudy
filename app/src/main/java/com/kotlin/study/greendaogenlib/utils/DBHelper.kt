package com.kotlin.study.greendaogenlib.utils

import android.content.Context
import com.kotlin.study.greendaogenlib.DaoMaster
import com.kotlin.study.greendaogenlib.DaoSession


import org.greenrobot.greendao.database.Database

/**
 * 数据库辅助类
 */

class DBHelper private constructor(context: Context) {
    private val DB_NAME = "kotlin.db"

    /**
     * 获取可写数据库
     *
     * @return
     */
    //        return mDevOpenHelper.getWritableDb();
    val writableDatabase: Database
        get() = mDevOpenHelper!!.getEncryptedWritableDb(mPassword)

    /**
     * 获取DaoMaster
     *
     * @return
     */
    private val daoMaster: DaoMaster?
        get() {
            if (null == mDaoMaster) {
                synchronized(lock = DBHelper::class.java) {
                    if (null == mDaoMaster) {
                        mDaoMaster = DaoMaster(writableDatabase)
                    }
                }
            }
            return mDaoMaster
        }

    /**
     * 获取DaoSession
     *
     * @return
     */
    val daoSession: DaoSession?
        get() {
            if (null == mDaoSession) {
                synchronized(DBHelper::class.java) {
                    mDaoSession = daoMaster!!.newSession()
                }
            }

            return mDaoSession
        }

    init {
        init(context)
    }

    private fun init(context: Context) {
        mPassword = ApkUtil.getUniquePsuedoID(context)
        // 初始化数据库信息
        mDevOpenHelper = UpdateOpenHelper(context, DB_NAME, null)
        daoMaster
        daoSession

    }

    companion object {
        private var _DBHelper: DBHelper? = null
        private var mDevOpenHelper: UpdateOpenHelper? = null
        private var mDaoMaster: DaoMaster? = null
        private var mDaoSession: DaoSession? = null
        private var mPassword: String? = null

        fun getInstance(context: Context): DBHelper? {
            if (null == _DBHelper) {
                synchronized(DBHelper::class.java) {
                    if (null == _DBHelper) {
                        _DBHelper = DBHelper(context)
                    }
                }
            }
            return _DBHelper
        }
    }
}
