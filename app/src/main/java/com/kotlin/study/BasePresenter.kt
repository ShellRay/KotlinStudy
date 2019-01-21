package com.kotlin.study

import com.kotlin.study.rx.RxManager


/**
 * Description:
 */

open class BasePresenter<V> {

    protected var mView: V? = null
    protected val mRxManager: RxManager = RxManager()

    /**
     * 与view进行关联
     */
    fun attachView(view: V) {
        this.mView = view

    }

    /**
     * 与view解除关联，并取消订阅
     */
    fun detach() {
        mView = null
        mRxManager.clear()
    }


    /**
     * 判断当前View是否存活
     */
    fun isViewActive() = mView != null


}