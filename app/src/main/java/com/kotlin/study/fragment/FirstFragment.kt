package com.kotlin.study.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.study.R
import com.kotlin.study.adapter.CirclIconAdapter
import com.kotlin.study.utils.ToastUtils
import com.kotlin.study.widget.banner.BannerView
import com.tmall.wireless.tangram.util.LogUtils

/**
 * @author ShellRay
 * Created  on 2019/9/16.
 * @description
 */
class FirstFragment: Fragment(), CirclIconAdapter.OnClickCallback {
    override fun onLongClick(position: Int): Boolean {
        ToastUtils.showToast(context,"long song")
        return false
    }

    private var mBannerView: BannerView? = null
    private var recyclerHeader: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = layoutInflater.inflate(R.layout.fragment_first, null)
        return inflate
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mUrlList = arrayListOf<String>()
        mUrlList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4130468043,1521101046&fm=26&gp=0.jpg")
        mUrlList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4130468043,1521101046&fm=26&gp=0.jpg")
        mUrlList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2178583793,2129783828&fm=26&gp=0.jpg")
        mUrlList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3414136933,3831453068&fm=26&gp=0.jpg")
        mUrlList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1183218783,3867456582&fm=26&gp=0.jpg")

        val mHeaderUrlList = arrayListOf<String>()
        mHeaderUrlList.add("https://img04.sogoucdn.com/app/a/100520093/398e25e72e0c6d43-7eed559ea6387758-7f5589703fb4cfc9d8cb9eabb4d319ea.jpg")
        mHeaderUrlList.add("https://img03.sogoucdn.com/app/a/100520093/39ccc87f3e85c326-d833987af7322860-5feb6742c87cf9761dbaa23bf800d644.jpg")
        mHeaderUrlList.add("https://i03piccdn.sogoucdn.com/7e91fa2e39fe2afb")
        mHeaderUrlList.add("https://i04piccdn.sogoucdn.com/34b924fab8e8fd75")
        mHeaderUrlList.add("https://i04piccdn.sogoucdn.com/57b1acda3a4a8e81")
        mHeaderUrlList.add("https://i02piccdn.sogoucdn.com/9399af99ffb6a85d")
        mHeaderUrlList.add("https://i01piccdn.sogoucdn.com/1d923832762875d0")
        mHeaderUrlList.add("https://i02piccdn.sogoucdn.com/dab96183e4af5fa9")
        mHeaderUrlList.add("https://i02piccdn.sogoucdn.com/eb1f49bd9d14243b")
        mHeaderUrlList.add("https://i02piccdn.sogoucdn.com/109e4ffda16c5199")
        mHeaderUrlList.add("https://i02piccdn.sogoucdn.com/6e9700268253638f")

        mBannerView = view!!.findViewById(R.id.BannerView) as BannerView
        mBannerView!!.build(mUrlList)
        mBannerView!!.setBannerClickListener(object : BannerView.BannerClickListener {
            override fun bannerClick(url: String) {
                val substring = url.substring(url.length - 20)
                ToastUtils.showToast(context,substring)
            }
        })
        recyclerHeader = view.findViewById(R.id.recyclerHeader) as RecyclerView
        val overlapManager = OverlapManager()
        recyclerHeader!!.layoutManager = overlapManager
        recyclerHeader!!.adapter = CirclIconAdapter(context,mHeaderUrlList,this)
    }

    class OverlapManager: RecyclerView.LayoutManager() {

        init {
            isAutoMeasureEnabled = true
        }
        override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
            return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
        }

        override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
            detachAndScrapAttachedViews(recycler)
            //定义竖直方向的偏移量
            var offsetX = 0
            for(i in 0 until itemCount){
                //这里就是从缓存里面取出
                var view = recycler!!.getViewForPosition (i)
                //将View加入到RecyclerView中
                addView(view)
                //对子View进行测量
                measureChildWithMargins(view, 0, 0)
                //把宽高拿到，宽高都是包含ItemDecorate的尺寸
                val width = getDecoratedMeasuredWidth (view)
                val height = getDecoratedMeasuredHeight (view)
                //最后，将View布局
                layoutDecorated(view, offsetX, 0, offsetX + width, height)
                //将竖直方向偏移量增大height
                offsetX += (width * 0.72).toInt()
                LogUtils.e("shell","offsetX=" + offsetX)
            }
        }
    }
}