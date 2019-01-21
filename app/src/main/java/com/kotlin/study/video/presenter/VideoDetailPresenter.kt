package com.kotlin.study.video.presenter

import com.kotlin.study.BasePresenter
import com.kotlin.study.video.model.VideoDetailModel
import com.kotlin.study.video.view.VideoDetailView


/**
 * Description:
 */
class VideoDetailPresenter : BasePresenter<VideoDetailView>() {

    private var mVideoModel: VideoDetailModel = VideoDetailModel()

    /**
     * 获取相关视频信息
     */
    fun getRelatedVideoInfo(id: String) {
        mRxManager.add(mVideoModel.getRelatedVideoInfo(id).subscribe({
            mView?.getRelatedVideoInfoSuccess(it.itemList)
        }, {
            mView?.getRelatedVideoFail()
        }))
    }


}
