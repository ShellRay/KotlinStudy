package com.kotlin.study.widget


import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log

import com.bumptech.glide.request.Request
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.util.Util
import com.tmall.wireless.vaf.virtualview.Helper.ImageLoader
import com.tmall.wireless.vaf.virtualview.view.image.ImageBase


/**
 */

class GlideVVSimpleTarget : Target<Bitmap> {
    override fun onLoadFailed(errorDrawable: Drawable?) {
        if (mImageBase != null) {
            mImageBase!!.setImageDrawable(errorDrawable, true)
        }
        if (listener != null) {
            listener!!.onImageLoadSuccess(errorDrawable)
        }
    }

    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
        if (mImageBase != null) {
            mImageBase!!.setBitmap(resource)
        }
        if (listener != null) {
            listener!!.onImageLoadSuccess(resource)
        }
    }

    override fun removeCallback(cb: SizeReadyCallback) {

    }


    private var width = Target.SIZE_ORIGINAL
    private var height = Target.SIZE_ORIGINAL

    internal var mImageBase: ImageBase? = null

    internal var listener: ImageLoader.Listener? = null


    constructor(mImageBase: ImageBase) {
        this.mImageBase = mImageBase
    }

    constructor(listener: ImageLoader.Listener) {
        this.listener = listener
    }


    fun setSize(width: Int, height: Int) {
        if (!Util.isValidDimensions(width, height)) {
            Log.i("GlideVVSimpleTarget", "Width and height must be Target#SIZE_ORIGINAL or > 0")
        } else {
            this.width = width
            this.height = height
        }
    }


    override fun onLoadStarted(placeholder: Drawable?) {

    }

    override fun onLoadCleared(placeholder: Drawable?) {

    }

    override fun getSize(cb: SizeReadyCallback) {
        cb.onSizeReady(width, height)
    }

    override fun setRequest(request: Request?) {

    }

    override fun getRequest(): Request? {
        return null
    }

    override fun onStart() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {

    }


}
