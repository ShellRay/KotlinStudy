package com.kotlin.study.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kotlin.study.R;

import java.util.ArrayList;
import java.util.List;


public class DemoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mDatas = new ArrayList<>();
    private Context mContext;
    private View mHeaderView;

    private int ITEM_TYPE_NORMAL = 0;
    private int ITEM_TYPE_HEADER = 1;


    public DemoAdapter(Context context) {
        mContext = context;
    }

    public void setDatas(List<String> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    // 创建视图
    @Override
    public RecyclerView.ViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            return new ViewHolder(mHeaderView);
        } else {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_common_text, parent, false);
            return new ViewHolder(v);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (null != mHeaderView && position == 0) {
            return ITEM_TYPE_HEADER;
        }
        return ITEM_TYPE_NORMAL;

    }

    // 为Item绑定数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == ITEM_TYPE_HEADER) {
            return;
        }
        int realPos = getRealItemPosition(position);
        ((ViewHolder) holder).mTextView.setText(mDatas.get(realPos));
    }

    private int getRealItemPosition(int position) {
        if (null != mHeaderView) {
            return position - 1;
        }
        return position;
    }

    @Override
    public int getItemCount() {
        int itemCount = mDatas.size();
        if (null != mHeaderView) {
            itemCount++;
        }
        return itemCount;
    }

    public void addHeaderView(View view) {
        mHeaderView = view;
        notifyItemInserted(0);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.textView);
        }
    }
}
