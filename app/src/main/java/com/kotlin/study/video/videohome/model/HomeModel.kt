package com.kotlin.study.video.videohome.model

import com.kotlin.study.BaseModel
import com.kotlin.study.entity.AndyInfo
import com.kotlin.study.net.Api
import com.kotlin.study.rx.RxHelper
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


/**
 * Description:
 */

class HomeModel : BaseModel {

    /**
     * 加载首页信息
     */
    fun loadCategoryInfo(): Observable<AndyInfo> = Api.getDefault().getHomeInfo().compose(RxHelper.handleResult())

    /**
     * 刷新主页信息，延迟1秒执行
     */
    fun refreshCategoryInfo(): Observable<AndyInfo> = Api.getDefault().getHomeInfo().delay(1000, TimeUnit.MILLISECONDS).compose(RxHelper.handleResult())


    /**
     * 热门关键词获取
     */
    fun getHotWord(): Observable<MutableList<String>> = Api.getDefault().getHotWord().compose(RxHelper.handleResult())

    /**
     * 根据关键字搜索视频
     */
    fun searchVideoByWord(word: String): Observable<AndyInfo> = Api.getDefault().searchVideoByWord(word).compose(RxHelper.handleResult())

    /**
     * 获取每日编辑精选
     */
    fun getDailyElite() = Api.getDefault().getDailyElite().compose(RxHelper.handleResult())
}