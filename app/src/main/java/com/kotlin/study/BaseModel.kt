package com.kotlin.study

import com.kotlin.study.entity.AndyInfo
import com.kotlin.study.net.Api
import com.kotlin.study.rx.RxHelper
import io.reactivex.Observable


/**
 * Description:
 */

interface BaseModel {

    /**
     * 加载更多信息，针对于AndyInfo数据类型
     */
    fun loadMoreAndyInfo(nextPageUrl: String?) = Api.getDefault().getMoreAndyInfo(nextPageUrl)?.compose(RxHelper.handleResult())

    /**
     * 加载更多信息，针对于JenniferInfo数据类型
     */
    fun loadMoreJenniferInfo(nextPageUrl: String?) = Api.getDefault().getMoreJenniferInfo(nextPageUrl)?.compose(RxHelper.handleResult())

    /**
     * 根据tab的url,获取tab信息
     */
    fun getTabInfo(url: String): Observable<AndyInfo> = Api.getDefault().getTabInfo(url).compose(RxHelper.handleResult())
}