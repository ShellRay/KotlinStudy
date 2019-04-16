package com.kotlin.study.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.kotlin.study.BaseActivity
import com.kotlin.study.R
import com.kotlin.study.adapter.SimpleDataAdapter
import com.kotlin.study.greendaogenlib.Account
import com.kotlin.study.greendaogenlib.Moment
import com.kotlin.study.greendaogenlib.utils.DBHelper
import com.kotlin.study.utils.ToastUtils


/**
 * @author ShellRay
 * Created  on 2019/3/28.
 * @description
 */
class GreenDaoActivity : BaseActivity(), View.OnClickListener {

    val accountDao = DBHelper.getInstance(this)!!.daoSession!!.getAccountDao()
    val momentDao = DBHelper.getInstance(this)!!.daoSession!!.getMomentDao()
    val accountDaoList = mutableListOf<Account>()
    val momentDaoList = mutableListOf<Moment>()

    var flag = 0

    private var adapter: SimpleDataAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tangram)
        val add = findViewById<TextView>(R.id.add)
        val delete = findViewById<TextView>(R.id.delete)
        val deleteAll = findViewById<TextView>(R.id.deleteAll)
        val change = findViewById<TextView>(R.id.change)
        val read = findViewById<TextView>(R.id.read)
        val recyclerView = findViewById<RecyclerView>(R.id.recycle)

        add.setOnClickListener(this)
        delete.setOnClickListener(this)
        change.setOnClickListener(this)
        read.setOnClickListener(this)
        deleteAll.setOnClickListener(this)

        recyclerView!!.layoutManager = LinearLayoutManager(this)
        adapter = SimpleDataAdapter(this, accountDaoList)
        recyclerView!!.adapter = adapter

        adapter!!.setOnItemClickListener(object : SimpleDataAdapter.OnItemClickListener {
            override fun onItemLongClickListener(position: Int): Boolean {
                return changeData(position)
            }

            override fun onItemClickListener(position: Int) {
                deleteData(position)
            }
        })

        if (accountDao.loadAll() != null && accountDao.loadAll().size > 1) {
            if (accountDao.loadAll().size > 1) {
                flag = accountDao.loadAll().size
            } else if (accountDao.loadAll().size == 1) {
                flag = 1
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.add -> {
                addData()
            }
            R.id.delete -> {
                deleteData(0)
            }
            R.id.deleteAll -> {
                deleteAllData()
            }
            R.id.change -> {
                changeData(0)
            }
            R.id.read -> {
                readData()
            }

        }
    }

    fun addData() {
        val account = Account()
        account.avatarlink = "everyThing come be " + flag
        account.id = "" + flag
        account.name = "where is where" + flag
        val add = accountDao.add(this, account)
        ToastUtils.showToast(this, "增加数据： " + add + "--id " + flag + "--account.name:" + account.name + "--account.avatarlink: " + account.avatarlink)
        ++flag
    }

    fun deleteData(position: Int) {
        if (accountDaoList.size > 0 && accountDaoList[position] != null) {
            val delete = accountDao.deleteData(this, accountDaoList.get(position).id, accountDaoList.get(position).name)
            ToastUtils.showToast(this, "删除数据： " + delete)
            readData()
        }

    }

    fun deleteAllData() {
        val accountDaoData = accountDao.loadAll()
        if (accountDaoData.size > 0) {
            val delete = accountDao.deleteAllData(this)
            ToastUtils.showToast(this, "删除All数据： " + delete)
            readData()
        }
    }

    fun changeData(position: Int): Boolean {
        if (accountDaoList.size > 0 && accountDaoList[position] != null) {
            val staur = accountDao.changeData(this, accountDaoList[position].id, accountDaoList[position].name, "this is update data")
            ToastUtils.showToast(this, "更新数据： " + staur)
            readData()
            return staur
        }
        return false
    }

    fun readData() {
        val accountDaoData = accountDao.loadAll()
        if (accountDaoData.size > 0) {
            accountDaoList.clear()
            //在这里循环7次为0至6
            //kotlin真的是为开发者着想的
            //多出这一个是为数组历遍而设计的
            for (i in 0 until accountDaoData.size) {
                val account = Account()
                account.id = accountDaoData[i].id
                account.avatarlink = accountDaoData[i].avatarlink
                account.name = accountDaoData[i].name
                accountDaoList.add(account)
            }
            adapter?.setDatas(accountDaoList)
        } else {
            accountDaoList.clear()
            adapter?.setDatas(accountDaoList)
        }

    }


}




