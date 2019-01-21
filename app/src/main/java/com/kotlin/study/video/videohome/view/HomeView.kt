package com.kotlin.study.video.videohome.view

import com.kotlin.study.BaseView
import com.kotlin.study.entity.AndyInfo


/**
 * Description:
 */

interface HomeView : BaseView {

    /**
     * 加载信息成功
     */
    fun loadDataSuccess(andyInfo: AndyInfo)

    /**
     * 加载信息成功
     */
    fun refreshDataSuccess(andyInfo: AndyInfo)

    /**
     * 加载更多成功
     */
    fun loadMoreSuccess(andyInfo: AndyInfo)

    /**
     * 没有更多
     */
    fun showNoMore()

}