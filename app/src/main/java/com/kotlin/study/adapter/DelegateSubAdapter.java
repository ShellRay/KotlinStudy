package com.kotlin.study.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;

public abstract class DelegateSubAdapter extends DelegateAdapter.Adapter<DelegateSubAdapter.SubViewHolder> {

    protected Context mContext;
    private LayoutHelper mLayoutHelper;
    protected int mCount = 0;
    protected int mViewType;

    public DelegateSubAdapter(Context context, LayoutHelper layoutHelper, int count) {
        this(context, layoutHelper, count, 1);
    }

    public DelegateSubAdapter(Context context, LayoutHelper layoutHelper, int count, int viewType) {
        this.mContext = context;
        this.mLayoutHelper = layoutHelper;
        this.mCount = count;
        this.mViewType = viewType;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public void onBindViewHolder(SubViewHolder holder, int position) {

    }

    @Override
    protected void onBindViewHolderWithOffset(SubViewHolder holder, int position, int offsetTotal) {

    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }

    @Override
    public int getItemViewType(int position) {
        return mViewType;
    }

    public static class SubViewHolder extends RecyclerView.ViewHolder {

        public static volatile int existing = 0;
        public static int createdTimes = 0;

        public SubViewHolder(View itemView) {
            super(itemView);
            createdTimes++;
            existing++;
        }

        @Override
        protected void finalize() throws Throwable {
            existing--;
            super.finalize();
        }
    }
}
