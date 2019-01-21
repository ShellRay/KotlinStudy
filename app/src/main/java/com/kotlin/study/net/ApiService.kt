package com.kotlin.study.net

import com.jennifer.andy.simpleeyes.entity.Tab
import com.kotlin.study.entity.AndyInfo
import com.kotlin.study.entity.Category
import com.kotlin.study.entity.ContentBean
import com.kotlin.study.entity.JenniferInfo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url


/**
 * Description:
 */

interface ApiService {

    ///////////////////////////////////////////////////////////////////////////
    // 首页相关
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 首页
     */
    @GET("api/v4/tabs/selected")
    fun getHomeInfo(): Observable<AndyInfo>

    /**
     * 获取热门关键词
     */
    @GET("api/v3/queries/hot")
    fun getHotWord(): Observable<MutableList<String>>

    /**
     * 关键词搜索
     */
    @GET("api/v1/search")
    fun searchVideoByWord(@Query("query") word: String): Observable<AndyInfo>

    /**
     * 每日精选旁的日历显示
     */
    @GET("api/v3/issueNavigationList")
    fun getIssueNaviGationList(): Observable<JenniferInfo>

    ///////////////////////////////////////////////////////////////////////////
    // 发现相关
    ///////////////////////////////////////////////////////////////////////////
    /**
     * 发现
     */
    @GET("api/v4/discovery")
    fun getDiscoveryTab(): Observable<Tab>

    /**
     * 获取全部分类信息
     */
    @GET("api/v4/categories/all")
    fun getAllCategoriesInfo(): Observable<AndyInfo>

    /**
     * 获取排行榜tab信息
     */
    @GET("api/v4/rankList")
    fun getRankListTab(): Observable<Tab>

    /**
     * 获取专题信息
     */
    @GET("api/v3/specialTopics")
    fun getTopicInfo(): Observable<AndyInfo>

    /**
     * 获取tag信息
     * @param tagId tagId
     * @param strategy tag模式
     */
    @GET("api/v3/tag/videos")
    fun getTagInfo(@Query("tagId") tagId: String, @Query("strategy") strategy: String): Observable<AndyInfo>

    /**
     * 获取种类下tag详细信息
     */
    @GET("api/v4/categories/detail/tab")
    fun getCategoryTagInfo(@Query("id") id: String): Observable<Category>

    /**
     * 获取种类下tag下列表集合
     */
    @GET("api/v4/categories/detail/index")
    fun getCategroyTagListItemInfo(@Query("id") id: String): Observable<AndyInfo>

    ///////////////////////////////////////////////////////////////////////////
    // 关注相关
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 关注
     */
    @GET("api/v4/tabs/follow")
    fun getFollowTabs(): Observable<AndyInfo>

    ///////////////////////////////////////////////////////////////////////////
    // 公共接口
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 获取发现Tab详细数据
     * @url tab请求地址
     */
    @GET
    fun getTabInfo(@Url url: String?): Observable<AndyInfo>

    /**
     * 获取更多信息
     * @param url 下一页请求地址
     */
    @GET
    fun getMoreAndyInfo(@Url url: String?): Observable<AndyInfo>?


    /**
     * 获取更多信息
     * @param url 下一页请求地址
     */
    @GET
    fun getMoreJenniferInfo(@Url url: String?): Observable<JenniferInfo>?


    ///////////////////////////////////////////////////////////////////////////
    // 视频相关
    ///////////////////////////////////////////////////////////////////////////
    /**
     * 根据视频id,获取相关信息
     */
    @GET("api/v2/video/{id}")
    fun getVideoInfoById(@Path("id") id: String): Observable<ContentBean>

    /**
     * 获取相关视频信息
     * @id 视频id
     */
    @GET("api/v4/video/related")
    fun getRelatedVideo(@Query("id") id: String): Observable<AndyInfo>

    /**
     * 每日编辑精选
     */
    @GET("api/v2/feed?num=3")
    fun getDailyElite(): Observable<JenniferInfo>


}