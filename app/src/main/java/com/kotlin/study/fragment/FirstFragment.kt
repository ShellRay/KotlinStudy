package com.kotlin.study.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.study.R
import com.kotlin.study.utils.ToastUtils
import com.kotlin.study.widget.banner.BannerView

/**
 * @author ShellRay
 * Created  on 2019/9/16.
 * @description
 */
class FirstFragment: Fragment() {
    private var mBannerView: BannerView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = layoutInflater.inflate(R.layout.fragment_first, null)
        return inflate
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mUrlList = arrayListOf<String>()
        mUrlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574237397710&di=af87d6f1d3f2d6af0553a1ae44c83664&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F543cec0b4e765.jpg")
        mUrlList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4130468043,1521101046&fm=26&gp=0.jpg")
        mUrlList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2178583793,2129783828&fm=26&gp=0.jpg")
        mUrlList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3414136933,3831453068&fm=26&gp=0.jpg")
        mUrlList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1183218783,3867456582&fm=26&gp=0.jpg")

        mBannerView = view!!.findViewById(R.id.BannerView) as BannerView
        mBannerView!!.build(mUrlList)
        mBannerView!!.setBannerClickListener(object : BannerView.BannerClickListener {
            override fun bannerClick(url: String) {
                val substring = url.substring(url.length - 20)
                ToastUtils.showToast(context,substring)
            }

        })
    }

}