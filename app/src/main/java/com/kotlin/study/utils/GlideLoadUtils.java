package com.kotlin.study.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.kotlin.study.GlideApp;
import com.kotlin.study.R;

/**
 * Created by GG on 2018/12/4.
 */

public class GlideLoadUtils<T> {

    private static GlideLoadUtils instance;
    private static RequestOptions options;

    public static GlideLoadUtils getInstance() {
        if (instance == null) {
            synchronized (GlideLoadUtils.class) {
                if (instance == null) {
                    instance = new GlideLoadUtils();
                    options = new RequestOptions();
                    options.fitCenter()
                            .placeholder(R.mipmap.mojieicon)
                            .error(R.mipmap.mojieicon)
                            .fallback(R.mipmap.mojieicon);
                }
            }
        }
        return instance;
    }

    public static void initGlide(Context ctx ) {
        RequestOptions options = new RequestOptions();
        options.centerCrop()
                .placeholder(R.mipmap.mojieicon)
                .error(R.mipmap.mojieicon)
                .fallback(R.mipmap.mojieicon);

        GlideApp.with(ctx).applyDefaultRequestOptions(options);
    }

    public void loadImageAsBitmap(Context ctx, T url, ImageView view) {
        GlideApp
                .with(ctx)
                .applyDefaultRequestOptions(options)
                .asBitmap()
                .load(url)
                .error(R.mipmap.mojieicon)
                .into(view);
    }

    public void loadImageAsGif(Context ctx, T url, ImageView view) {
        GlideApp
                .with(ctx)
                .applyDefaultRequestOptions(options)
                .asGif()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(R.mipmap.mojieicon)
                .into(view);
    }

}
