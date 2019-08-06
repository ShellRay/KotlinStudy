package com.kotlin.study.activity

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.*
import android.support.annotation.RequiresApi
import android.support.v4.view.ViewPager
import android.transition.Visibility
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.animation.doOnEnd
import com.kotlin.study.KotlinApplication
import com.kotlin.study.R
import com.kotlin.study.UserPreferences
import com.kotlin.study.adapter.SplashVideoFragmentAdapter
import com.kotlin.study.fragment.SloganFragment
import com.rd.utils.DensityUtils
import kotlinx.android.synthetic.main.activity_welcome.*
import me.yokeyword.fragmentation.SupportActivity
import java.util.*

/**
 * Created by GG on 2018/2/27.
 */
class WelcomeActivity : SupportActivity(){

    private lateinit var mFragmentList: MutableList<SloganFragment>
    private lateinit var mSplashVideoFragmentAdapter: SplashVideoFragmentAdapter


    private var timerTast:TimerTask? =null
    private var timer1:Timer? =null
    private var handler:Handler? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        handler = Handler(object :Handler.Callback{
            override fun handleMessage(msg: Message?): Boolean {
                if(msg!!.what == 100){
                    doUpAnimator()
                }
                return true
            }
        })
        timerTast = object:TimerTask(){
            override fun run() {

                 handler!!.sendEmptyMessage(100)
            }
        }

        timer1 = Timer()

        timer1!!.schedule(timerTast,3000)

        initSloganText()
        tvSkip.setOnClickListener {
            goMainActivity()
        }

    }

    class TimerTasks(private val context: WelcomeActivity) : java.util.TimerTask(){
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun run() {
            Log.e("kotlin","jump"+ Thread.currentThread().name)
            Looper.prepare()
//            Toast.makeText(baseContext, "inside thread : Jump it", Toast.LENGTH_SHORT).show()
            /*var intent = Intent(baseContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            baseContext.startActivity(intent)
            baseContext.finish()*/

            context.runOnUiThread {
                context.doUpAnimator()
            }
            Looper.loop()


        }

    }

    /**
     * 这里我采用的比较暴力的方法，主要是不想写事件拦截了，想写的小伙伴，可以自己去写
     */
    private fun initSloganText() {
        //设置初始标语
        tv_slogan_en.printText(application.resources.getStringArray(R.array.slogan_array_en)[0])
        tv_slogan_zh.printText(application.resources.getStringArray(R.array.slogan_array_zh)[0])

        pageIndicatorView.count = 4
        pageIndicatorView.visibility = View.GONE
        pageIndicatorView.setSelected(0)

        mFragmentList = mutableListOf()
        for (position in 0..4) {//这里创建5个是因为要滑动退出
            mFragmentList.add(SloganFragment.newInstance())
        }

        mSplashVideoFragmentAdapter = SplashVideoFragmentAdapter(mFragmentList, supportFragmentManager)
        view_pager.verticalListener = { goMainActivity() }

        view_pager.offscreenPageLimit = mFragmentList.size
        view_pager.adapter = mSplashVideoFragmentAdapter
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position == mFragmentList.size - 2 && positionOffset >= 0.5) {
                    goMainActivity()
                }

            }

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageSelected(position: Int) {
                Log.e("shell","onPageSelected " + position)
                if (position in 0..3) {
                    tv_slogan_en.printText(KotlinApplication.getResource().getStringArray(R.array.slogan_array_en)[position])
                    tv_slogan_zh.printText(KotlinApplication.getResource().getStringArray(R.array.slogan_array_zh)[position])
                    pageIndicatorView.setSelected(position)
                }
            }
        })
    }
    private fun goMainActivity() {
        UserPreferences.saveUserIsFirstLogin(false)
        readyGoThenKillSelf(MainActivity::class.java)
    }

    private fun readyGoThenKillSelf(clazz: Class<out Any>, bundle: Bundle? = null) {
        val intent = Intent(this, clazz)
        bundle?.let {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        this?.finish()
    }

    /**
     * 执行上升动画
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun doUpAnimator() {
        val moveY = DensityUtils.dpToPx(100)
        upAnimator = ObjectAnimator.ofFloat(ll_move_container, "translationY", 0f, -moveY.toFloat())
        upAnimator!!.addUpdateListener {
            if (it.currentPlayTime in 600..1500) {
                iv_head_outer.setImageResource(R.drawable.ic_eye_white_outer)
                iv_head_inner.setImageResource(R.drawable.ic_eye_white_inner)
                tv_name.setTextColor(resources.getColor(R.color.gray_B7B9B8))

            } else if (it.currentPlayTime in 1500..2000) {
                iv_head_outer.setImageResource(R.drawable.ic_eye_black_outer)
                iv_head_inner.setImageResource(R.drawable.ic_eye_black_inner)

                tv_name.setTextColor(resources.getColor(R.color.black_444444))
            }

        }
        upAnimator!!.duration = 2000
        upAnimator!!.start()
        upAnimator!!.doOnEnd {
            doBackgroundAnimator()
        }
    }
    private var backgroundAnimator: ValueAnimator?=null
    private var upAnimator: ValueAnimator?=null
    /**
     * 执行背景动画
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun doBackgroundAnimator() {
        backgroundAnimator= ValueAnimator.ofArgb(0xff444444.toInt(), 0xff26A599.toInt())
        iv_head_inner.visibility = View.VISIBLE
        val rotateAnimator = ObjectAnimator.ofFloat(iv_head_inner, "rotation", 0f, 720f)
        rotateAnimator.repeatCount = ObjectAnimator.INFINITE

        backgroundAnimator!!.addUpdateListener {
            rootMain.setBackgroundColor(it.animatedValue as Int)
        }
        backgroundAnimator!!.doOnEnd {
            doTextAnimator()
        }
        rotateAnimator.duration = 2000
        backgroundAnimator!!.duration = 2000
        backgroundAnimator!!.start()
        rotateAnimator.start()
    }

    private fun doTextAnimator() {
        tv_slogan_zh.visibility = View.VISIBLE
        tv_slogan_en.visibility = View.VISIBLE
        pageIndicatorView.visibility = View.VISIBLE
        tvSkip.visibility = View.VISIBLE

    }

    override fun finish() {
        super.finish()
        tvSkip.setOnClickListener(null)
        timer1!!.cancel()
        timerTast!!.cancel()
        timer1 = null
        timerTast = null
        handler!!.removeCallbacksAndMessages(null)
        iv_head_outer.setImageResource(0)
        iv_head_inner.setImageResource(0)
        view_pager.adapter = null
        view_pager.addOnPageChangeListener(null)
        upAnimator!!.addUpdateListener(null)
        backgroundAnimator!!.addUpdateListener(null)

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}