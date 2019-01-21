package com.kotlin.study

import android.os.Bundle
import android.view.View
import com.kotlin.study.utils.SystemUtils


/**
 * Description: 基础类activity
 */

abstract class BaseVideoActivity<V, T : BasePresenter<V>> : BaseAppCompatActivity(), BaseView {


    protected lateinit var mPresenter: T


    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter = SystemUtils.getGenericInstance(this, 1)
        mPresenter.attachView(this as V)
        super.onCreate(savedInstanceState)

    }


    override fun showLoading() {
        mMultipleStateView.showLoading()
    }

    override fun showNetError(onClickListener: View.OnClickListener) {
        mMultipleStateView.showNetError(onClickListener)
    }

    override fun showEmpty(onClickListener: View.OnClickListener) {
        mMultipleStateView.showEmpty(onClickListener)
    }

    override fun showContent() {
        mMultipleStateView.showContent()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detach()

    }
}