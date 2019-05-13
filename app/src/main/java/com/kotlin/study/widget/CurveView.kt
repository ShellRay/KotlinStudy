package com.kotlin.study.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathEffect
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 *
 * @ClassName: CurveView
 * @Description: This custom View is to show the x-y relation curve
 */
class CurveView : View {

    private var xValues: FloatArray? = null
    private var yValues: FloatArray? = null
    private var mPath: Path? = null
    private var mPaint: Paint? = null
    private var mGridPaint: Paint? = null

    private var mWidth = 0
    private var mHeight = 0

    private var mUnitHeight = 0
    private var mHeightMargin = 0

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    private fun init() {
        mPath = Path()
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mPaint!!.isDither = true
        mPaint!!.color = Color.RED
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.strokeCap = Paint.Cap.ROUND
        mPaint!!.strokeWidth = 2f

        // 平滑曲线
        val pathEffect = CornerPathEffect(30f)
        mPaint!!.pathEffect = pathEffect

        mGridPaint = Paint()
        mGridPaint!!.isAntiAlias = true
        mGridPaint!!.isDither = true
        mGridPaint!!.color = Color.BLACK
        mGridPaint!!.style = Paint.Style.STROKE
        mGridPaint!!.strokeWidth = 1f

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h

        mUnitHeight = Math.round(0.5f * mHeight)

        mHeightMargin = (mHeight - mUnitHeight) / 2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawVerticalGrids(canvas)
        drawHorizontalGrids(canvas)
        if (null != xValues && null != yValues
                && xValues!!.size == yValues!!.size) {

            mPath!!.reset()
            // move to start point
            mPath!!.moveTo(xValues!![0] * mWidth, mHeightMargin + (1 - yValues!![0]) * mUnitHeight)
            // line to other points
            for (i in 1 until xValues!!.size) {
                mPath!!.lineTo(xValues!![i] * mWidth, mHeightMargin + (1 - yValues!![i]) * mUnitHeight)
            }

        }

        canvas.drawPath(mPath!!, mPaint!!)

    }

    private fun drawHorizontalGrids(canvas: Canvas) {
        for (i in xValues!!.indices) {
            canvas.drawLine(0f, mHeightMargin + xValues!![i] * mUnitHeight,
                    mWidth.toFloat(), mHeightMargin + xValues!![i] * mUnitHeight,
                    mGridPaint!!)
        }
    }

    private fun drawVerticalGrids(canvas: Canvas) {

        for (i in xValues!!.indices) {
            canvas.drawLine(xValues!![i] * mWidth, 0f, xValues!![i] * mWidth,
                    mHeight.toFloat(), mGridPaint!!)
        }
    }

    fun getxValues(): FloatArray? {
        return xValues
    }

    fun setxValues(xValues: FloatArray) {
        this.xValues = xValues
    }

    fun getyValues(): FloatArray? {
        return yValues
    }

    fun setyValues(yValues: FloatArray) {
        this.yValues = yValues
    }

}
