package com.kotlin.study.player.controllerview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kotlin.study.R;
import com.kotlin.study.entity.ContentBean;
import com.kotlin.study.player.IjkMediaController;
import com.kotlin.study.utils.NetWorkUtils;


/**
 * Description:竖直小界面视频控制器
 */


public class TinyControllerView extends ControllerView implements View.OnClickListener {

    private ImageView mPauseButton;
    private ImageView mPreButton;
    private ImageView mBackButton;
    private ImageView mNextButton;
    private ImageView mFullScreen;
    private SeekBar mProgress;

    private TextView mEndTime;
    private TextView mCurrentTime;


    private int mChangeProgress;

    public TinyControllerView(MediaController.MediaPlayerControl player, IjkMediaController controller, ContentBean currentVideoInfo, Context context) {
        super(player, controller, currentVideoInfo, context);
    }

    @Override
    public void initView(View rootView) {

        mPauseButton = rootView.findViewById(R.id.iv_pause);
        mPreButton = rootView.findViewById(R.id.iv_previous);
        mBackButton = rootView.findViewById(R.id.iv_back);
        mNextButton = rootView.findViewById(R.id.iv_next);
        mFullScreen = rootView.findViewById(R.id.iv_full_screen);
        mProgress = rootView.findViewById(R.id.sb_progress);
        mCurrentTime = rootView.findViewById(R.id.tv_currentTime);
        mEndTime = rootView.findViewById(R.id.tv_end_time);

        //初始化开始时间
        int position = mPlayer.getCurrentPosition();
        int duration = mPlayer.getDuration();
        mCurrentTime.setText(stringForTime(position));
        mEndTime.setText("/" + stringForTime(duration));

        //初始化进度条
        mProgress.setMax(1000);
        if (duration >= 0 && mPlayer.getBufferPercentage() > 0) {
            long progress = 1000L * position / duration;
            int secondProgress = mPlayer.getBufferPercentage() * 10;
            mProgress.setProgress((int) progress);
            mProgress.setSecondaryProgress(secondProgress);
        }
        updatePreNextButton();
    }

    //判断是否显示上一个按钮与下一个按钮
    private void updatePreNextButton() {
        mPreButton.setVisibility(mController.isHavePreVideo() ? View.VISIBLE : View.GONE);
        mNextButton.setVisibility(mController.isHaveNextVideo() ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_pause://暂停按钮
                togglePause();
                mController.show();
                break;
            case R.id.iv_previous://上一个
                mController.getControllerListener().onPreClick();
                updatePreNextButton();
                break;
            case R.id.iv_next://下一个按钮
                mController.getControllerListener().onNextClick();
                updatePreNextButton();
                break;
            case R.id.iv_back://回退键
                mController.hide();
                mController.getControllerListener().onBackClick();
                break;
            case R.id.iv_full_screen://全屏按钮
                mController.toggleControllerView(new FullScreenControllerView(mPlayer, mController, mCurrentVideoInfo, mContext));
                break;

        }

    }

    @Override
    public void initControllerListener() {
        mPreButton.setOnClickListener(this);
        mPauseButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        mFullScreen.setOnClickListener(this);

        mProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar bar) {
                //控制的时候停止更新进度条，同时禁止隐藏
                setDragging(true);
                mController.show(3600000);
                mController.cancelFadeOut();

            }

            @Override
            public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
                if (fromUser) {
                    //更新当前播放时间
                    long duration = mPlayer.getDuration();
                    long newPosition = (duration * progress) / 1000L;
                    mChangeProgress = progress;
                    mCurrentTime.setText(stringForTime((int) newPosition));
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar bar) {
                //定位都拖动位置
                if (NetWorkUtils.INSTANCE.isNetWorkConnected(mContext)) {
                    long newPosition = (mPlayer.getDuration() * mChangeProgress) / 1000L;
                    mPlayer.seekTo((int) newPosition);
                    mController.show();//开启延时隐藏
                    setDragging(false);
                } else {
                    mPlayer.pause();
                    mController.showErrorView();
                }


            }
        });
        mProgress.setPadding(0, 0, 0, 0);
        mProgress.setMax(1000);


    }

    @Override
    public void updateProgress(int progress, int secondaryProgress) {
        mProgress.setProgress(progress);
        mProgress.setSecondaryProgress(secondaryProgress);
    }

    @Override
    public void updateTime(String currentTime, String endTime) {
        mCurrentTime.setText(currentTime);
        mEndTime.setText("/" + endTime);
    }

    @Override
    public void updateTogglePauseUI(boolean isPlaying) {
        if (isPlaying) {
            mPauseButton.setImageResource(R.mipmap.ic_player_pause);
        } else {
            mPauseButton.setImageResource(R.mipmap.ic_player_play);
        }
    }


    @Override
    public int setControllerLayoutId() {
        return R.layout.layout_media_controller_tiny;
    }

}
