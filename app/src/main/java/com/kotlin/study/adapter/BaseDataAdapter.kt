package com.kotlin.study.adapter

import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.facebook.drawee.view.SimpleDraweeView
import com.kotlin.study.R
import com.kotlin.study.entity.Content
import com.kotlin.study.utils.TimeUtils
import com.kotlin.study.video.VideoDetailActivity
import com.kotlin.study.widget.font.CustomFontTextView
import java.util.*


/**
 * Description: 基础信息适配器
 */

open class BaseDataAdapter(data: MutableList<Content>) : BaseQuickAdapter<Content, BaseViewHolder>(data) {


    /**
     * 卡片类型
     */
    companion object {
        const val BANNER_TYPE = 0
        const val FOLLOW_CARD_TYPE = 1
        const val HORIZONTAL_SCROLL_CARD_TYPE = 2
        const val VIDEO_COLLECTION_WITH_COVER_TYPE = 3
        const val SQUARE_CARD_COLLECTION_TYPE = 4
        const val VIDEO_COLLECTION_OF_HORIZONTAL_SCROLL_CARD_TYPE = 5
        const val VIDEO_COLLECTION_WITH_BRIEF_TYPE = 6
        const val TEXT_CARD_TYPE = 7
        const val BRIEF_CARD_TYPE = 8
        const val BLANK_CARD_TYPE = 9
        const val SQUARE_CARD_TYPE = 10
        const val RECTANGLE_CARD_TYPE = 11
        const val VIDEO_TYPE = 12
        const val VIDEO_BANNER_THREE_TYPE = 13

        const val BANNER = "banner"
        const val FOLLOW_CARD = "followCard"
        const val HORIZONTAL_CARD = "horizontalScrollCard"
        const val VIDEO_COLLECTION_WITH_COVER = "videoCollectionWithCover"
        const val SQUARE_CARD_COLLECTION = "squareCardCollection"
        const val VIDEO_COLLECTION_OF_HORIZONTAL_SCROLL_CARD = "videoCollectionOfHorizontalScrollCard"
        const val VIDEO_COLLECTION_WITH_BRIEF = "videoCollectionWithBrief"
        const val TEXT_CARD = "textCard"
        const val BRIEF_CARD = "briefCard"
        const val BLANK_CARD = "blankCard"
        const val SQUARE_CARD = "squareCard"
        const val RECTANGLE_CARD = "rectangleCard"
        const val VIDEO = "video"
        const val VIDEO_BANNER_THREE = "banner3"
    }


    init {
        multiTypeDelegate = object : MultiTypeDelegate<Content>() {
            override fun getItemType(andyInfoItem: Content?): Int {
                when (andyInfoItem?.type) {
                    BANNER -> return BANNER_TYPE
                    FOLLOW_CARD -> return FOLLOW_CARD_TYPE
                    HORIZONTAL_CARD -> return HORIZONTAL_SCROLL_CARD_TYPE
                    VIDEO_COLLECTION_WITH_COVER -> return VIDEO_COLLECTION_WITH_COVER_TYPE
                    SQUARE_CARD_COLLECTION -> return SQUARE_CARD_COLLECTION_TYPE
                    VIDEO_COLLECTION_OF_HORIZONTAL_SCROLL_CARD -> return VIDEO_COLLECTION_OF_HORIZONTAL_SCROLL_CARD_TYPE
                    VIDEO_COLLECTION_WITH_BRIEF -> return VIDEO_COLLECTION_WITH_BRIEF_TYPE
                    TEXT_CARD -> return TEXT_CARD_TYPE
                    BRIEF_CARD -> return BRIEF_CARD_TYPE
                    BLANK_CARD -> return BLANK_CARD_TYPE
                    SQUARE_CARD -> return SQUARE_CARD_TYPE
                    RECTANGLE_CARD -> return RECTANGLE_CARD_TYPE
                    VIDEO -> return VIDEO_TYPE
                    VIDEO_BANNER_THREE -> return VIDEO_BANNER_THREE_TYPE
                }
                return VIDEO_TYPE
            }
        }
        with(multiTypeDelegate) {
//            registerItemType(VIDEO_TYPE, R.layout.layout_single_video)
            registerItemType(BANNER_TYPE, R.layout.layout_single_video)
            registerItemType(FOLLOW_CARD_TYPE, R.layout.layout_follow_card)
            registerItemType(HORIZONTAL_SCROLL_CARD_TYPE, R.layout.layout_single_video)
            registerItemType(VIDEO_COLLECTION_WITH_COVER_TYPE, R.layout.layout_single_video)
            registerItemType(SQUARE_CARD_COLLECTION_TYPE, R.layout.layout_single_video)
            registerItemType(VIDEO_COLLECTION_OF_HORIZONTAL_SCROLL_CARD_TYPE, R.layout.layout_single_video)
            registerItemType(VIDEO_COLLECTION_WITH_BRIEF_TYPE, R.layout.layout_single_video)
            registerItemType(TEXT_CARD_TYPE, R.layout.layout_single_video)
            registerItemType(BRIEF_CARD_TYPE, R.layout.layout_single_video)
            registerItemType(BLANK_CARD_TYPE, R.layout.layout_single_video)
            registerItemType(SQUARE_CARD_TYPE, R.layout.layout_single_video)
            registerItemType(RECTANGLE_CARD_TYPE, R.layout.layout_single_video)
            registerItemType(VIDEO_TYPE, R.layout.layout_single_video)
            registerItemType(VIDEO_BANNER_THREE_TYPE, R.layout.layout_single_video)
        }

    }

    override fun convert(helper: BaseViewHolder?, item: Content) {
        when (helper?.itemViewType) {
//            VIDEO_TYPE -> setSingleVideoInfo(helper, item)
            BANNER_TYPE -> null
            FOLLOW_CARD_TYPE -> setFollowCardInfo(helper, item)
            HORIZONTAL_SCROLL_CARD_TYPE -> null
            VIDEO_COLLECTION_WITH_COVER_TYPE -> null
            SQUARE_CARD_COLLECTION_TYPE -> null
            VIDEO_COLLECTION_OF_HORIZONTAL_SCROLL_CARD_TYPE -> null
            VIDEO_COLLECTION_WITH_BRIEF_TYPE -> null
            TEXT_CARD_TYPE -> null
            BRIEF_CARD_TYPE -> null
            BLANK_CARD_TYPE -> null
            SQUARE_CARD_TYPE -> null
            RECTANGLE_CARD_TYPE -> null
            VIDEO_TYPE -> setSingleVideoInfo(helper, item)
            VIDEO_BANNER_THREE_TYPE -> null
        }

    }

    /**
     * 设置单视频卡片信息
     */
    private fun setFollowCardInfo(helper: BaseViewHolder, item: Content) {
        val info = item.data

        val eliteView = helper.getView<SimpleDraweeView>(R.id.iv_elite_image)
            eliteView.setImageURI(info.content!!.data.cover.feed)

        val tvTitle = helper.getView<CustomFontTextView>(R.id.tv_title)
        val tvDesc = helper.getView<CustomFontTextView>(R.id.tv_desc)
        val ivSource = helper.getView<SimpleDraweeView>(R.id.iv_source)
        val tvDate = helper.getView<CustomFontTextView>(R.id.tv_date)

        tvTitle.text = info.header.title
        tvDesc.text = info.header.description
        tvDate.text = TimeUtils.getTimeStr(Date(info.header.time))
        ivSource.setImageURI(info.header.icon)

        //跳转到视频详细界面
        helper.itemView.setOnClickListener {
            VideoDetailActivity.start(mContext!!, item.data.content?.data!!, mData as ArrayList, mData.indexOf(item))
        }
    }

    /**
     * 设置单视频信息
     */
    private fun setSingleVideoInfo(helper: BaseViewHolder, item: Content) {
            val imageView = helper.getView<SimpleDraweeView>(R.id.iv_image)
            if (item?.data != null && item.data.cover != null && !TextUtils.isEmpty(item.data.cover.feed)) {
                imageView.setImageURI(item.data.cover.feed)
            }

            helper.setText(R.id.tv_single_title, item.data.title)
            val description = "#${item.data.category}   /   ${TimeUtils.getElapseTimeForShow(item.data.duration)}"
            helper.setText(R.id.tv_single_desc, description)

            //设置label
            if (item.data.label != null) {
                helper.setGone(R.id.tv_label, true)
                helper.setText(R.id.tv_label, item.data.label?.text)
            } else {
                helper.setGone(R.id.tv_label, false)
            }
            //点击跳转到视频界面
            helper.itemView.setOnClickListener { VideoDetailActivity.start(mContext, item.data, data as ArrayList<Content>) }
    }
}