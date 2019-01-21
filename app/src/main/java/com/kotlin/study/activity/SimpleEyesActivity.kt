package com.kotlin.study.activity

import android.os.Bundle
import com.kotlin.study.video.videohome.HomeFragment
import com.kotlin.study.BaseAppCompatActivity
import com.kotlin.study.R
import me.yokeyword.fragmentation.SupportFragment

/**
 * @author ShellRay
 * Created  on 2019/1/18.
 * @description
 */
class SimpleEyesActivity : BaseAppCompatActivity(){

    private var mFragments = arrayOfNulls<SupportFragment>(1)
//    private val mBottomNavigation: BottomBar by bindView(R.id.bottom_navigation_bar)

    companion object {
        private const val FIRST = 0
        private const val SECOND = 1
        private const val THIRD = 2
        private const val FOURTH = 3
    }


    override fun initView(savedInstanceState: Bundle?) {
        mFragments[FIRST] = HomeFragment.newInstance()
//        mFragments[SECOND] = FeedFragment.newInstance()
//        mFragments[THIRD] = FollowFragment.newInstance()
//        mFragments[FOURTH] = ProfileFragment.newInstance()
        loadMultipleRootFragment(R.id.fl_container, FIRST, *mFragments)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {

//        val home = BottomItem(R.drawable.ic_tab_strip_icon_feed_selected, getString(R.string.home))
//        home.setUnSelectedDrawable(R.drawable.ic_tab_strip_icon_feed)
//        val discover = BottomItem(R.drawable.ic_tab_strip_icon_category_selected, getString(R.string.discover))
//        discover.setUnSelectedDrawable(R.drawable.ic_tab_strip_icon_category)
//        val focus = BottomItem(R.drawable.ic_tab_strip_icon_follow_selected, getString(R.string.focus))
//        focus.setUnSelectedDrawable(R.drawable.ic_tab_strip_icon_follow)
//        val mine = BottomItem(R.drawable.ic_tab_strip_icon_profile_selected, getString(R.string.mine))
//        mine.setUnSelectedDrawable(R.drawable.ic_tab_strip_icon_profile)
//
//        with(mBottomNavigation) {
//            addItem(home)
//            addItem(discover)
//            addItem(focus)
//            addItem(mine)
//            initialise()
//            setOnTabSelectedListener(object : BottomBar.onTabSelectedListener {
//                override fun onTabSelected(position: Int, prePosition: Int) {
//                    showHideFragment(mFragments[position])
//                }
//
//                override fun onTabUnselected(position: Int) {
//
//
//                }
//
//                override fun onTabReselected(position: Int) {
//                    if (position == FIRST) {
//                        val categoryFragment = mFragments[FIRST] as HomeFragment
//                        categoryFragment.scrollToTop()
//                    }
//                }
//            })
//
//        }

    }

    override fun getBundleExtras(extras: Bundle) {

    }


    override fun getContentViewLayoutId() = R.layout.activity_simple_eyes
}