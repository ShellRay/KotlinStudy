package com.kotlin.study.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.kotlin.study.R;
import com.kotlin.study.adapter.DemoAdapter;

import java.util.ArrayList;
import java.util.List;


public class DemoFragment extends Fragment {
    private RecyclerView recyclerView;
    private View layout_fragment_header;
    private DemoAdapter demoAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int top = -1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_demo, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        layout_fragment_header = view.findViewById(R.id.layout_fragment_header);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        List<String> data = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            data.add("第"+i+"名");
        }
        demoAdapter = new DemoAdapter(getActivity());
        final View header3 = LayoutInflater.from(getContext()).inflate(R.layout.view_header, recyclerView, false);
        initStickView(header3);
        demoAdapter.addHeaderView(header3);
        demoAdapter.setDatas(data);
        recyclerView.setAdapter(demoAdapter);
    }


    private void initStickView(final View view) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                top = view.getTop();
                addScrollListener();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
    }



    private int mScrollY = 0;


    private void addScrollListener() {

        layout_fragment_header.setTranslationY(top);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy == 0) return;
                mScrollY += dy;
                int translationY = top - mScrollY;
                if (translationY < 0) translationY = 0;
                layout_fragment_header.setTranslationY(translationY);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

}
