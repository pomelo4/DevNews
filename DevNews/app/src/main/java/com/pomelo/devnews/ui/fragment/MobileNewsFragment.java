package com.pomelo.devnews.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pomelo.devnews.R;
import com.pomelo.devnews.base.BaseFragment;
import com.pomelo.devnews.callback.LoadFinishCallBack;
import com.pomelo.devnews.constant.ToastMsg;
import com.pomelo.devnews.model.MobileNews;
import com.pomelo.devnews.net.JSONParser;
import com.pomelo.devnews.net.Request4MobileNews;
import com.pomelo.devnews.utils.NetWorkUtil;
import com.pomelo.devnews.utils.ShowToast;
import com.pomelo.devnews.view.AutoLoadRecyclerView;
import com.pomelo.devnews.view.googleprogressbar.GoogleProgressBar;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class MobileNewsFragment extends BaseFragment {

    @Bind(R.id.recycler_view)
    AutoLoadRecyclerView mRecyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.google_progress)
    GoogleProgressBar google_progress;

    private MobileNewsAdapter mAdapter;
    private LoadFinishCallBack mLoadFinisCallBack;

    // 请求地址
    abstract MobileNews.MobileType RequestUrl();
    // 清除缓存
    abstract void clearAllCache();
    // 添加缓存
    abstract void addResultCache(String cache, int page);

    abstract ArrayList<MobileNews> getCacheByPage(int page);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar.setTitle("移动开发");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mLoadFinisCallBack = mRecyclerView;
        mRecyclerView.setLoadMoreListener(new AutoLoadRecyclerView.onLoadMoreListener() {
            @Override
            public void loadMore() {
                mAdapter.loadNextPage();
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.loadFirst();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new MobileNewsAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.loadFirst();
    }

    public class MobileNewsAdapter extends RecyclerView.Adapter<ViewHolder> {

        private int page;
        private ArrayList<MobileNews> mMobileNews;
        private int lastPosition = -1;

        public MobileNewsAdapter() {
            mMobileNews = new ArrayList<MobileNews>();
        }

        private void setAnimation(View viewToAnimate, int position) {
            if (position > lastPosition) {
                Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R
                        .anim.item_bottom_in);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }

        @Override
        public void onViewDetachedFromWindow(ViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
            holder.card.clearAnimation();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_mobile_news, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            final MobileNews mobileNews = mMobileNews.get(position);

            holder.tv_info.setText("@" +mobileNews.getWho());
            holder.tv_title.setText(mobileNews.getDesc());

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(getActivity(), MobileNewsDetailActivity.class);
//                    intent.putExtra("url", mobileNews.getUrl());
//                    startActivity(intent);
                }
            });
            setAnimation(holder.card, position);

        }

        @Override
        public int getItemCount() {
            return mMobileNews.size();
        }

        public void loadFirst() {
            page = 1;
            loadDataByNetworkType();
        }

        public void loadNextPage() {
            page++;
            loadDataByNetworkType();
        }

        private void loadDataByNetworkType() {

            if (NetWorkUtil.isNetWorkConnected(getActivity())) {

                executeRequest(new Request4MobileNews(MobileNews.getRequestUrl(MobileNews.MobileType.ANDORID, page),
                        new Response.Listener<ArrayList<MobileNews>>() {
                            @Override
                            public void onResponse(ArrayList<MobileNews> response) {

                                google_progress.setVisibility(View.GONE);
                                mLoadFinisCallBack.loadFinish(null);
                                if (mSwipeRefreshLayout.isRefreshing()) {
                                    mSwipeRefreshLayout.setRefreshing(false);
                                }

                                if (page == 1) {
                                    mAdapter.mMobileNews.clear();
                                    clearAllCache();
                                }

                                mAdapter.mMobileNews.addAll(response);
                                notifyDataSetChanged();
                                String cache = JSONParser.toString(response);
                                addResultCache(cache, page);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ShowToast.Short(ToastMsg.LOAD_FAILED);
                        google_progress.setVisibility(View.GONE);
                        mLoadFinisCallBack.loadFinish(null);
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }));
            } else {
                google_progress.setVisibility(View.GONE);
                mLoadFinisCallBack.loadFinish(null);
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }

                if (page == 1) {
                    mMobileNews.clear();
                    ShowToast.Short(ToastMsg.LOAD_NO_NETWORK);
                }

                mMobileNews.addAll(getCacheByPage(page));
                notifyDataSetChanged();
            }

        }

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private TextView tv_info;
        private TextView tv_time;
        private CardView card;

        public ViewHolder(View contentView) {
            super(contentView);
            tv_title = (TextView) contentView.findViewById(R.id.tv_title);
            tv_info = (TextView) contentView.findViewById(R.id.tv_info);
            tv_time = (TextView) contentView.findViewById(R.id.tv_time);
            card = (CardView) contentView.findViewById(R.id.card);
        }
    }
}
