package com.kotlin.study.video

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.facebook.drawee.view.SimpleDraweeView
import com.kotlin.study.R
import com.kotlin.study.entity.AuthorBean
import com.kotlin.study.utils.bindView
import com.kotlin.study.widget.font.CustomFontTextView


/**
 * Description:
 */

class VideoDetailAuthorView : FrameLayout {

    private val mImage by bindView<SimpleDraweeView>(R.id.iv_image)
    private val mTitle by bindView<CustomFontTextView>(R.id.tv_title)
    private val mDescription by bindView<CustomFontTextView>(R.id.tv_desc)
    private val mAddFollow by bindView<CustomFontTextView>(R.id.tv_follow)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.layout_video_author_head, this, true)
    }

    /**
     * 设置视频作者信息
     */
    fun setVideoAuthorInfo(author: AuthorBean) {
        mImage.setImageURI(author.icon)
        mTitle.text = author.name
        mDescription.text = author.description
    }
}