package com.kotlin.study.video.videohome

import com.kotlin.study.BaseView


/**
 * Description:
 */
interface LoadMoreView<T> : BaseView {

    /**
     * 加载更多信息成功
     */
    fun loadMoreSuccess(data: T)

    /**
     * 没有更多
     */
    fun showNoMore()
}
