package com.kotlin.study.player.controllerview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;

import com.kotlin.study.R;
import com.kotlin.study.entity.ContentBean;
import com.kotlin.study.player.IjkMediaController;


/**
 * Description:
 */


public class ErrorControllerView extends ControllerView implements View.OnClickListener {

    private ImageView mIvReload;

    public ErrorControllerView(MediaController.MediaPlayerControl player, IjkMediaController controller, ContentBean currentVideoInfo, Context context) {
        super(player, controller, currentVideoInfo, context);
    }

    @Override
    public void initView(View rootView) {
        mIvReload = rootView.findViewById(R.id.iv_reload);
    }

    @Override
    public void initControllerListener() {
        getRootView().setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(mIvReload, "rotation", 0f, 360f);
        rotation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //执行选中动画，然后开始刷新操作
                mController.hide();
                mController.getControllerListener().onErrorViewClick();
            }
        });
        rotation.setDuration(500);
        rotation.start();
    }

    @Override
    public int setControllerLayoutId() {
        return R.layout.layout_video_error;
    }

}
