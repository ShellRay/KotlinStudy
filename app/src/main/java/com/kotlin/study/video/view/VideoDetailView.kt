package com.kotlin.study.video.view

import com.kotlin.study.entity.Content
import com.kotlin.study.BaseView


/**
 * Description:
 */

interface VideoDetailView : BaseView {

    /**
     * 获取相关视频成功
     */
    fun getRelatedVideoInfoSuccess(itemList: MutableList<Content>)

    /**
     * 获取相关视屏失败
     */
    fun getRelatedVideoFail()

}