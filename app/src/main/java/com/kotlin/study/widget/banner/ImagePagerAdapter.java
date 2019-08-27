/*
 * Copyright 2014 trinea.cn All right reserved. This software is the
 * confidential and proprietary information of trinea.cn
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with trinea.cn.
 */
package com.kotlin.study.widget.banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kotlin.study.R;
import com.kotlin.study.utils.GlideLoadUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * ImagePagerAdapter
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-23
 */
public class ImagePagerAdapter extends RecyclingPagerAdapter {

    private Context context;
    private List<String> carouseList;
    private LayoutInflater mInflater;

    private int size;
    private boolean isInfiniteLoop;
    public ImagePagerAdapter(Context context, ArrayList<String> carouseList) {
        this.context = context;
        this.carouseList = carouseList;
        this.mInflater = LayoutInflater.from(context);
        this.size = getSize(carouseList);
        isInfiniteLoop = false;
    }


    @Override
    public int getCount() {
        return isInfiniteLoop ? 10000 : getSize(carouseList);
    }

    public <V> int getSize(List<V> sourceList) {
        return sourceList == null ? 0 : sourceList.size();
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.image_item_layout, null);
            holder.imageView = (ImageView) view.findViewById(R.id.draweeview);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        String url = carouseList.get(isInfiniteLoop ? position % size : position);
        GlideLoadUtils.getInstance().LoadRoundImage(context, url, holder.imageView, 4);


        holder.imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != myOnItemListening) {
                    myOnItemListening.ItemListening(carouseList.get(isInfiniteLoop ? position % size : position));
                }

            }
        });
        return view;
    }

    private static class ViewHolder {

        ImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }

    private OnItemListening myOnItemListening;

    public interface OnItemListening {
        void ItemListening(String ur);
    }

    public void setOnItemListening(OnItemListening onItemListening) {
        myOnItemListening = onItemListening;
    }

}
