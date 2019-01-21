package com.kotlin.study.video.model

import com.kotlin.study.rx.RxHelper
import com.kotlin.study.net.Api
import com.kotlin.study.BaseModel


/**
 * Description:
 */

class VideoDetailModel : BaseModel {


    /**
     * 获取相关视频信息
     */
    fun getRelatedVideoInfo(id: String) = Api.getDefault().getRelatedVideo(id).compose(RxHelper.handleResult())

    /**
     * 根据视频id获取视频信息
     */
    fun getVideoInfoById(id: String) = Api.getDefault().getVideoInfoById(id).compose(RxHelper.handleResult())
}