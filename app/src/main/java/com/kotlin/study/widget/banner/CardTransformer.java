package com.kotlin.study.widget.banner;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @author ShellRay
 *         Created  on 2019/8/26.
 * @description     实现viewpager缩放效果
 */

public class CardTransformer implements ViewPager.PageTransformer {
    private static final float MAX_SCALE = 1.0f;
    private static final float MIN_SCALE = 0.85f;//

    @Override
    public void transformPage(View page, float position) {
        if (position <= 1) {
            //   1.2f + (1-1)*(1.2-1.0)
            float scaleFactor = MIN_SCALE + (1 - Math.abs(position)) * (MAX_SCALE - MIN_SCALE);

            page.setScaleX(MAX_SCALE);  //缩放效果

            if (position > 0) {
                page.setTranslationY(-scaleFactor * 2);
            } else if (position < 0) {
                page.setTranslationY(scaleFactor * 2);
            }
            page.setScaleY(scaleFactor);
        } else {

            page.setScaleX(MAX_SCALE);
            page.setScaleY(MIN_SCALE);
        }
    }
}
