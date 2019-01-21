package com.kotlin.study.video

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import com.chad.library.adapter.base.BaseQuickAdapter
import com.facebook.drawee.view.SimpleDraweeView
import com.kotlin.study.entity.Content
import com.kotlin.study.entity.ContentBean
import com.jennifer.andy.simpleeyes.net.Extras
import com.kotlin.study.rx.RxBus
import com.kotlin.study.video.adapter.VideoDetailAdapter
import com.kotlin.study.video.presenter.VideoDetailPresenter
import com.kotlin.study.video.view.VideoDetailView
import com.kotlin.study.BaseVideoActivity
import com.kotlin.study.R
import com.kotlin.study.player.IjkMediaController
import com.kotlin.study.player.IjkVideoView
import com.kotlin.study.player.event.VideoProgressEvent
import com.kotlin.study.player.render.IRenderView
import com.kotlin.study.utils.bindView
import io.reactivex.functions.Consumer
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import java.util.*


/**
 * Description: 视频详细信息界面
 */

class VideoDetailActivity : BaseVideoActivity<VideoDetailView, VideoDetailPresenter>(), VideoDetailView {

    private val mPlaceImage by bindView<SimpleDraweeView>(R.id.iv_place_image)
    private val mBlurredImage by bindView<SimpleDraweeView>(R.id.iv_blurred)
    private val mVideoView by bindView<IjkVideoView>(R.id.video_view)
    private val mProgress by bindView<ProgressBar>(R.id.progress)
    private val mHorizontalProgress by bindView<ProgressBar>(R.id.sb_progress)
    private val mRecycler by bindView<RecyclerView>(R.id.rv_video_recycler)

    private lateinit var mCurrentVideoInfo: ContentBean
    private var mCurrentIndex = 0
    private lateinit var mVideoListInfo: MutableList<Content>
    private var mBackPressed = false

    private lateinit var mVideoDetailAdapter: VideoDetailAdapter
    private lateinit var ijkMediaController: IjkMediaController


    companion object {

        /**
         * 跳转到视频详细界面
         */
        @JvmStatic
        fun start(context: Context, content: ContentBean, videoListInfo: ArrayList<Content>, defaultIndex: Int = 0) {
            val bundle = Bundle()
            bundle.putSerializable(Extras.VIDEO_INFO, content)
            bundle.putSerializable(Extras.VIDEO_LIST_INFO, videoListInfo)
            bundle.putInt(Extras.VIDEO_INFO_INDEX, defaultIndex)
            val starter = Intent(context, VideoDetailActivity::class.java)
            starter.putExtras(bundle)
            context.startActivity(starter)
        }
    }

    override fun getBundleExtras(extras: Bundle) {
        mCurrentVideoInfo = extras.getSerializable(Extras.VIDEO_INFO) as ContentBean
        mVideoListInfo = extras.getSerializable(Extras.VIDEO_LIST_INFO) as ArrayList<Content>
        mCurrentIndex = extras.getInt(Extras.VIDEO_INFO_INDEX, 0)
    }

    override fun initView(savedInstanceState: Bundle?) {
        initPlaceHolder()
        initMediaController()
        setProgressListener()
        playVideo()
    }


    private fun initPlaceHolder() {
        mPlaceImage.setImageURI(mCurrentVideoInfo.cover.detail)
        mBlurredImage.setImageURI(mCurrentVideoInfo.cover.blurred)
        mHorizontalProgress.max = 1000
    }


    private fun initMediaController() {
        ijkMediaController = IjkMediaController(mCurrentIndex, mVideoListInfo.size, mCurrentVideoInfo, mContext)
        ijkMediaController.controllerListener = object : IjkMediaController.ControllerListener {
            override fun onBackClick() {
                finish()
            }

            override fun onNextClick() {
                mCurrentIndex = ++mCurrentIndex
                ijkMediaController.currentIndex = mCurrentIndex
                ijkMediaController.hide()
                refreshVideo(getFutureVideo())
            }

            override fun onFullScreenClick() {
                mVideoView.enterFullScreen()
            }

            override fun onTinyScreenClick() {
                mVideoView.exitFullScreen()
            }

            override fun onPreClick() {
                mCurrentIndex = --mCurrentIndex
                ijkMediaController.currentIndex = mCurrentIndex
                ijkMediaController.hide()
                refreshVideo(getFutureVideo())
            }

            override fun onErrorViewClick() {
                ijkMediaController.hide()
                refreshVideo(getFutureVideo())
            }
        }
    }

    /**
     * 重置视频信息
     */
    private fun refreshVideo(videoInfo: Content) {
        mCurrentVideoInfo = videoInfo.data

        mRecycler.visibility = View.INVISIBLE
        mPlaceImage.visibility = View.VISIBLE
        mProgress.visibility = View.VISIBLE

        mHorizontalProgress.secondaryProgress = 0
        mHorizontalProgress.progress = 0

        initPlaceHolder()
        mVideoView.setVideoPath(mCurrentVideoInfo.playUrl)
        mVideoView.start()
    }

    /**
     * 获取即将展示的视频
     */
    private fun getFutureVideo(): Content {
        return if (mVideoListInfo[mCurrentIndex].data.content != null) {
            mVideoListInfo[mCurrentIndex].data.content!!
        } else {
            mVideoListInfo[mCurrentIndex]
        }

    }

    private fun setProgressListener() {
        //注册进度条监听
        RxBus.register(this, VideoProgressEvent::class.java, Consumer {
            mHorizontalProgress.progress = it.progress
            mHorizontalProgress.secondaryProgress = it.secondaryProgress
        })

    }


    /**
     * 播放视频
     */
    private fun playVideo() {

        mVideoView.setVideoPath(mCurrentVideoInfo.playUrl)
//        mVideoView.setVideoPath("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=61071&resourceType=video&editionType=normal&source=qcloud")

        mVideoView.setMediaController(ijkMediaController)
        mVideoView.start()
        //设置准备完成监听
        mVideoView.setOnPreparedListener {
            //隐藏进度条
            mPlaceImage.handler.postDelayed({
                mPlaceImage.visibility = View.GONE
                mProgress.visibility = View.GONE
            }, 500)
            //获取相关视屏信息
            mPresenter.getRelatedVideoInfo(mCurrentVideoInfo.id)
            ijkMediaController.resetType()

        }
        mVideoView.toggleAspectRatio(IRenderView.AR_MATCH_PARENT)

        mVideoView.setOnErrorListener { p0, p1, p2 ->
            mProgress.handler.postDelayed({
                mProgress.visibility = View.GONE
                ijkMediaController.showErrorView()
            }, 1000)

            true
        }
        //设置完成监听
        mVideoView.setOnCompletionListener {

        }

    }

    /**
     * 添加标题
     */
    private fun getVideoDetailView(): View {
        val view = VideoDetailHeadView(mContext)
        with(view) {
            setTitle(mCurrentVideoInfo.title)
            setCategoryAndTime(mCurrentVideoInfo.category, mCurrentVideoInfo.duration)
            setFavoriteCount(mCurrentVideoInfo.consumption.collectionCount)
            setShareCount(mCurrentVideoInfo.consumption.replyCount)
            setReplayCount(mCurrentVideoInfo.consumption.replyCount)
            setDescription(mCurrentVideoInfo.description)
        }
        view.startScrollAnimation()
        return view
    }

    /**
     * 设置相关视频信息
     */
    private fun getRelationVideoInfo(): View {
        val view = VideoDetailAuthorView(mContext)
        view.setVideoAuthorInfo(mCurrentVideoInfo.author)
        return view
    }

    override fun getRelatedVideoInfoSuccess(itemList: MutableList<Content>) {
        mRecycler.visibility = View.VISIBLE
        mVideoDetailAdapter = VideoDetailAdapter(itemList)
        mVideoDetailAdapter.addHeaderView(getVideoDetailView())
        mVideoDetailAdapter.addHeaderView(getRelationVideoInfo())
        mVideoDetailAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        mVideoDetailAdapter.setFooterView(LayoutInflater.from(mContext).inflate(R.layout.item_the_end, null))
        mVideoDetailAdapter.setOnItemClickListener { _, _, position ->
            if (mVideoDetailAdapter.getItemViewType(position) != BaseQuickAdapter.HEADER_VIEW) {
                val item = mVideoDetailAdapter.getItem(position)
                refreshVideo(item!!)
            }
        }
        mRecycler.layoutManager = LinearLayoutManager(mContext)
        mRecycler.adapter = mVideoDetailAdapter
    }

    override fun getRelatedVideoFail() {
    }


    override fun onBackPressedSupport() {
        super.onBackPressedSupport()
        mBackPressed = true
    }


    override fun onPause() {
        super.onPause()
        if (mVideoView.isPlaying) {
            mVideoView.pause()
        }
    }

    override fun onStop() {
        super.onStop()
        if (mBackPressed) {
            mVideoView.stopPlayback()
            mVideoView.release(true)
        }
        IjkMediaPlayer.native_profileEnd()
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.unRegister(this)
    }

    override fun toggleOverridePendingTransition() = true

    override fun getOverridePendingTransition(): TransitionMode = TransitionMode.BOTTOM

    override fun getContentViewLayoutId() = R.layout.activity_video_detail

}