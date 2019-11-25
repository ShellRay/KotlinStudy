package com.kotlin.study.activity

import android.graphics.Canvas
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.kotlin.study.BaseActivity
import android.widget.TextView
import com.kotlin.study.R
import com.kotlin.study.adapter.DragItemAdapter
import com.kotlin.study.widget.banner.BannerView
import com.kotlin.study.widget.banner.ViewPagerIndicator
import kotlinx.android.synthetic.main.activity_banner_view.*
import com.kotlin.study.callback.ItemTouchHelperAdapter
import com.kotlin.study.inter.OnStartDragListener
import java.util.*


/**
 * @author ShellRay
 * Created  on 2019/8/26.
 * @description
 */
class BannerAndDragActivity : BaseActivity() ,OnStartDragListener{

    private var mTv_hint: TextView? = null
    private var mBannerView: BannerView? = null
    private var mIndicatorDefault: ViewPagerIndicator? = null
    private var mIndicatorNotAnim: ViewPagerIndicator? = null
    var dragItemAdapter:DragItemAdapter? = null
    var mItemTouchHelper:ItemTouchHelper? = null

    val dataList : ArrayList<String> = arrayListOf("自强","明主","富强","奇迹","归来","释放","勿忘","自由","抱负","强健","主见")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner_view)
        val mUrlList = arrayListOf<String>()
        mUrlList.add("http://t2.hddhhn.com/uploads/tu/201301/149/2.jpg")
        mUrlList.add("http://t2.hddhhn.com/uploads/tu/201301/149/4.jpg")
        mUrlList.add("https://t2.hddhhn.com/uploads/tu/201409/036/5.jpg")
        mUrlList.add("https://t2.hddhhn.com/uploads/tu/201301/189/4.jpg")
        mUrlList.add("http://t2.hddhhn.com/uploads/tu/201409/036/1.jpg")

        mTv_hint = findViewById<TextView>(R.id.tv_hint)
        mBannerView = findViewById<BannerView>(R.id.BannerView)
        mBannerView!!.build(mUrlList)

        mIndicatorDefault = findViewById<ViewPagerIndicator>(R.id.indicator_default)
        mIndicatorNotAnim = findViewById<ViewPagerIndicator>(R.id.indicator_not_anim)
        // if mBannerView.setCircle(true);
        mIndicatorDefault!!.setViewPager(mBannerView!!.viewPager, mUrlList.size)
        mIndicatorNotAnim!!.setViewPager(mBannerView!!.viewPager, mUrlList.size)

        indicator_circle.setViewPager(mBannerView!!.viewPager,mUrlList.size)
        indicator_line.setViewPager(mBannerView!!.viewPager,mUrlList.size)
        indicator_circle_line.setViewPager(mBannerView!!.viewPager,mUrlList.size)
        indicator_bezier.setViewPager(mBannerView!!.viewPager,mUrlList.size)
        indicator_spring.setViewPager(mBannerView!!.viewPager,mUrlList.size)

        // if mBannerView.setCircle(false);
        //        mIndicatorDefault.setViewPager(mBannerView.getViewPager(),false);
        //        mIndicatorNotAnim.setViewPager(mBannerView.getViewPager(),false);

        recyclerDragView.layoutManager = GridLayoutManager(this,3)
        dragItemAdapter = DragItemAdapter(this,dataList,this)
        recyclerDragView.setHasFixedSize(true)
        recyclerDragView.adapter = dragItemAdapter
        val dragItemHelperCallBack = DragItemHelperCallBack(dragItemAdapter)
        mItemTouchHelper = ItemTouchHelper(dragItemHelperCallBack)
        mItemTouchHelper!!.attachToRecyclerView(recyclerDragView)
    }

    inner class DragItemHelperCallBack(private val mAdapter: DragItemAdapter?) : ItemTouchHelper.Callback() {

        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            if (recyclerView.layoutManager is GridLayoutManager) {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                val swipeFlags = 0
                return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
            } else {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
                return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
            }
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            if (viewHolder.itemViewType != target.itemViewType) {
                return false
            }

            mAdapter!!.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
            return true // true 可以拖拽，false 不能。
        }

        override fun isLongPressDragEnabled(): Boolean {
            return true
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            mAdapter!!.onItemDismiss(viewHolder.adapterPosition)
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            // 不在闲置状态
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                if (viewHolder is DragItemAdapter.TextViewHolder) {
                    val itemViewHolder = viewHolder as DragItemAdapter.TextViewHolder?
                    itemViewHolder!!.onItemSelected()
                }
            }
            super.onSelectedChanged(viewHolder, actionState)
        }

        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                // Fade out the view as it is swiped out of the parent's bounds
                val alpha = 1.0f - Math.abs(dX) / viewHolder.itemView.width.toFloat()
                viewHolder.itemView.alpha = alpha
                viewHolder.itemView.translationX = dX
            } else {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder) {
            if (viewHolder is DragItemAdapter.TextViewHolder) {
                val itemViewHolder = viewHolder
                itemViewHolder.onItemFinish()
            }
            super.clearView(recyclerView, viewHolder)
        }

    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        mItemTouchHelper!!.startDrag(viewHolder)
    }


}