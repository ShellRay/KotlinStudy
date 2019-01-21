package com.kotlin.study.video.videohome.view

import com.kotlin.study.entity.Content
import com.kotlin.study.video.videohome.LoadMoreView


/**
 * Description:
 */

interface DailyEliteView : LoadMoreView<MutableList<Content>> {

    fun showGetDailySuccess(content: MutableList<Content>)

    fun showRefreshSuccess(content: MutableList<Content>)

}