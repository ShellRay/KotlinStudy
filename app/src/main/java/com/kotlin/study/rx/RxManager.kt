package com.kotlin.study.rx

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * Description: 用于管理单个presenter生命周期中RxJava相关代码的生命周期处理
 */

class RxManager {

    private var mSerialDisposable: CompositeDisposable = CompositeDisposable()


    fun add(disposable: Disposable) {
        mSerialDisposable.add(disposable)
    }

    fun clear() {
        mSerialDisposable.dispose()
        mSerialDisposable = CompositeDisposable()//这里清除绑定的时候需要重新创建对象。
    }
}