package com.kotlin.study.activity

import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.RelativeLayout
import android.widget.TextView
import com.kotlin.study.BaseActivity
import com.kotlin.study.R
import com.kotlin.study.widget.CenterAlignImageSpan
import com.kotlin.study.widget.MultipleStateView
import com.kotlin.study.widget.SearchHotRemindView


/**
 * Date:    2018/4/3 10:54
 * Description:搜索界面
 */

class SearchHotActivity : BaseActivity(){

    private lateinit var mSearchRemind: SearchHotRemindView
    private lateinit var mSearchView: SearchView
    private lateinit var mTvCancel: TextView
    private lateinit var multipleStateView: MultipleStateView
    private lateinit var mRecycler: RecyclerView

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_search_hot)
        initSearchView()
        mTvCancel.setOnClickListener { finish() }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initSearchView() {

        mSearchRemind = findViewById(R.id.rl_search_remind)
        mSearchView = findViewById(R.id.searchView)
        mTvCancel = findViewById(R.id.tv_cancel)
        multipleStateView = findViewById(R.id.multiple_state_view)
        mRecycler = findViewById(R.id.rv_search_recycler)

        mSearchView.isIconified = false
        mSearchView.setIconifiedByDefault(false)

        //设置输入框提示文字样式
        val searchComplete = mSearchView.findViewById<SearchView.SearchAutoComplete>(R.id.search_src_text)
        searchComplete.gravity = Gravity.CENTER
        searchComplete.setHintTextColor(resources.getColor(R.color.gray_B7B9B8))
        searchComplete.textSize = 13f
        searchComplete.hint = getDecoratedHint(searchComplete.hint, getDrawable(R.mipmap.ic_action_search_no_padding), 50)
        //添加搜索监听
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showKeyboard(false)
//                mPresenter.searchVideoByWord(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    /**
     * 设置输入框提示文字
     */
    private fun getDecoratedHint(hintText: CharSequence, searchHintIcon: Drawable, drawableSize: Int): CharSequence {
        searchHintIcon.setBounds(0, 0, drawableSize, drawableSize)
        val ssb = SpannableStringBuilder("   ")
        ssb.setSpan(CenterAlignImageSpan(searchHintIcon), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        ssb.append(hintText)
        return ssb
    }

    /**
     * 进入内容动画
     */
    private fun startContentAnimation() {
        val valueAnimator = ValueAnimator.ofInt(multipleStateView.measuredHeight, 0)
        valueAnimator.duration = 500
        valueAnimator.interpolator = AccelerateInterpolator()
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Int
            multipleStateView.scrollTo(0, value)
        }
        valueAnimator.start()
    }


    override fun toggleOverridePendingTransition() = true

    override fun getOverridePendingTransition() = TransitionMode.TOP




}