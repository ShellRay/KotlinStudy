package com.kotlin.study.activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.*
import com.kotlin.study.BaseActivity
import com.kotlin.study.R
import com.kotlin.study.widget.CurveView
import com.kotlin.study.widget.TextSwitcherAnimation
import kotlinx.android.synthetic.main.activity_interplator.*
import java.util.*


class InterplatorActivity : BaseActivity() {

    private var mInterpolatorGroup: RadioGroup? = null
    private var mCurveView: CurveView? = null
    var index = 0
    private val mXValues = floatArrayOf(0.0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f)
    private val mYValues = FloatArray(11)
    val testList = listOf<String>("找房子", "租房子", "卖房子", "买房子", "装房子", "修房子")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interplator)
        mInterpolatorGroup = findViewById<RadioGroup>(R.id.interpolatorGroup)
        mCurveView = findViewById<CurveView>(R.id.curve)
        mCurveView!!.setxValues(mXValues)
        mInterpolatorGroup!!
                .setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {

                    override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
                        val interpolator = getInterpolator()
                        for (i in mXValues.indices) {
                            mYValues[i] = interpolator!!
                                    .getInterpolation(mXValues[i])
                        }
                        mCurveView!!.setyValues(mYValues)
                        mCurveView!!.invalidate()
                    }
                })

        mInterpolatorGroup!!.check(R.id.LinearInterpolator)

        textSwitcher.setFactory(object : ViewSwitcher.ViewFactory {
            override fun makeView(): View {
                val textView = TextView(baseContext)
                textView.setTextColor(baseContext.resources.getColor(R.color.colorPrimaryDark))
                textView.textSize = 25f
                return textView
            }
        }
        )

        TextSwitcherAnimation(textSwitcher, testList).create()

        val imgList = listOf<Drawable>(resources.getDrawable(R.mipmap.dead1), resources.getDrawable(R.mipmap.dead2), resources.getDrawable(R.mipmap.dead3),
                resources.getDrawable(R.mipmap.dead4), resources.getDrawable(R.mipmap.mojie), resources.getDrawable(R.mipmap.tiger))

        //通过代码设定从左缓进，从右换出的效果。

        imgSwitcher.setFactory(object : ViewSwitcher.ViewFactory {
            override fun makeView(): View {
                val imageView = ImageView(baseContext)
                imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                imageView.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
                return imageView
            }
        }
        )

        imgSwitcher.setImageDrawable(imgList.get(0))

        btnadd.setOnClickListener {
            imgSwitcher.inAnimation = AnimationUtils.loadAnimation(baseContext, R.anim.right_in)
            imgSwitcher.outAnimation = AnimationUtils.loadAnimation(baseContext, R.anim.left_out)
            index--
            if (index < 0) {
                //用于循环显示图片
                index = imgList.size - 1
            }
            //设定ImageSwitcher显示新图片
            imgSwitcher.setImageDrawable(imgList.get(index))
        }

        btnSub.setOnClickListener {
            imgSwitcher.inAnimation = AnimationUtils.loadAnimation(baseContext, android.R.anim.slide_in_left)
            imgSwitcher.outAnimation = AnimationUtils.loadAnimation(baseContext, android.R.anim.slide_out_right)
            index++
            if (index >= imgList.size) {
                //用于循环显示图片
                index = 0
            }
            imgSwitcher.setImageDrawable(imgList.get(index))
        }
    }

    private fun getInterpolator(): Interpolator? {

        val checkedId = mInterpolatorGroup!!.checkedRadioButtonId

        when (checkedId) {
            R.id.AccelerateDecelerateInterpolator -> return AccelerateDecelerateInterpolator()

            R.id.AccelerateInterpolator -> return AccelerateInterpolator()

            R.id.AnticipateInterpolator -> return AnticipateInterpolator()

            R.id.AnticipateOvershootInterpolator -> return AnticipateOvershootInterpolator()

            R.id.BounceInterpolator -> return BounceInterpolator()

            R.id.CycleInterpolator -> return CycleInterpolator(2f)

            R.id.DecelerateInterpolator -> return DecelerateInterpolator()

            R.id.LinearInterpolator -> return LinearInterpolator()

            R.id.OvershootInterpolator -> return OvershootInterpolator()

            else -> {
            }
        }

        return null

    }


}
