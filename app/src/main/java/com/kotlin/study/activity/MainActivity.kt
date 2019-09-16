package com.kotlin.study.activity

import android.content.Intent
import android.os.Build
import android.support.design.widget.Snackbar

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.kotlin.study.BaseActivity
import com.kotlin.study.R
import com.kotlin.study.fragment.FirstFragment
import com.kotlin.study.fragment.FourthFragment
import com.kotlin.study.fragment.SecondFragment
import com.kotlin.study.fragment.ThirdFragment
import com.kotlin.study.utils.GlideLoadUtils

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*


class MainActivity : BaseActivity(), RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        //设置右边toolbar图标
        toolbar.overflowIcon = getDrawable(R.mipmap.search_icon)

        val firstFragment = FirstFragment()
        val secondFragment = SecondFragment()
        val thirdFragment = ThirdFragment()
        val fourthFragment = FourthFragment()

        val listFragments = ArrayList<Fragment>()
        listFragments.add(firstFragment)
        listFragments.add(secondFragment)
        listFragments.add(thirdFragment)
        listFragments.add(fourthFragment)

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager,listFragments)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter
        container.offscreenPageLimit = 4

        container.currentItem = 0
        groupView.check(R.id.first)

        container.setOnPageChangeListener(this)
        groupView.setOnCheckedChangeListener(this)

        fab.setOnClickListener {
            Snackbar.make(it, "Open your new world", Snackbar.LENGTH_LONG)
                    .setAction("Jump Import", View.OnClickListener {
                        val intent = Intent(this,StartBeginActivity::class.java)
                        startActivity(intent)
                    }).show()
        }

    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        when(position){
            0 -> groupView.check(R.id.first)
            1 -> groupView.check(R.id.second)
            2 -> groupView.check(R.id.third)
            3 -> groupView.check(R.id.fourth)
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {

        when(checkedId){
            R.id.first  -> container.currentItem = 0
            R.id.second -> container.currentItem = 1
            R.id.third  -> container.currentItem = 2
            R.id.fourth -> container.currentItem = 3
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            startActivity(Intent(this,SearchHotActivity::class.java))
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    inner class SectionsPagerAdapter(fm: FragmentManager, listFragments: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {

        private var listFragments = listFragments

        override fun getItem(position: Int): Fragment {
//            return PlaceholderFragment.newInstance(position)
            return listFragments[position]
        }

        override fun getCount(): Int {
            return listFragments.size
        }
    }


    class PlaceholderFragment : Fragment() {
        val RES = intArrayOf(R.mipmap.dead1, R.mipmap.dragen, R.mipmap.dead3, R.mipmap.beauty, R.mipmap.dragen, R.mipmap.tiger, R.mipmap.xingzai)

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val int = arguments.getInt(ARG_SECTION_NUMBER)
//            R.layout.constraint_layout_use
            var rootView = inflater.inflate(R.layout.fragment_main , container, false)
            rootView!!.section_label.text = getString(R.string.section_format, int)
            val url = "http://res.img002.com/pic//6194_9.gif"
            val url1 = "http://res.img002.com/pic//6195_9.gif"
            when(int){
                0 -> loadGif(rootView,url)
                2 -> loadGif(rootView, url1)
                else -> GlideLoadUtils.getInstance().loadImageAsBitmap(context,RES[int],rootView!!.pic)
//                    rootView!!.pic.setImageResource(RES[int])
            }

            return rootView
        }

        private fun loadGif(rootView: View?, url: String?) {
            GlideLoadUtils.getInstance().loadImageAsGif(context,url,rootView!!.pic)
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private const val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }

    override fun toggleOverridePendingTransition() = true

    override fun getOverridePendingTransition() = TransitionMode.FADE
}
