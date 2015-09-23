package com.pomelo.devnews.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pomelo.devnews.R;
import com.pomelo.devnews.base.BaseFragment;
import com.pomelo.devnews.cache.VideoCacheUtil;
import com.pomelo.devnews.callback.LoadFinishCallBack;
import com.pomelo.devnews.constant.ToastMsg;
import com.pomelo.devnews.loader.ImageLoader;
import com.pomelo.devnews.model.Video;
import com.pomelo.devnews.net.JSONParser;
import com.pomelo.devnews.net.Request4Video;
import com.pomelo.devnews.utils.NetWorkUtil;
import com.pomelo.devnews.utils.ShowToast;
import com.pomelo.devnews.view.AutoLoadRecyclerView;
import com.pomelo.devnews.view.googleprogressbar.GoogleProgressBar;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 视频
 */
public class VideoFragment extends BaseFragment {

    @Bind(R.id.recycler_view)
    AutoLoadRecyclerView mRecyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.google_progress)
    GoogleProgressBar google_progress;

    private VideoAdapter mAdapter;
    private LoadFinishCallBack mLoadFinisCallBack;

    private ImageLoader mImageLoader;
    private int mImageWidth = 0;
    private int mImageHeight = 0;

    public VideoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar.setTitle("休息一下");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
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

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mImageLoader = ImageLoader.build(getActivity());
        mAdapter = new VideoAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.loadFirst();

    }

    public class VideoAdapter extends RecyclerView.Adapter<ViewHolder> {

        private int page;
        private ArrayList<Video> mVideos;
        private int lastPosition = -1;

        public VideoAdapter() {
            mVideos = new ArrayList<Video>();
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
        public ViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_video, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            final Video video = mVideos.get(position);
            holder.tv_title.setText(video.getTitle());

            ImageView imageView = holder.img;
            ViewGroup.LayoutParams params =  imageView.getLayoutParams();

            mImageWidth = params.width;
            mImageHeight = params.height;

            mImageLoader.bindBitmap(video.getImgUrl(), holder.img, mImageWidth, mImageHeight);

//            holder.img_share.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    new MaterialDialog.Builder(getActivity())
//                            .items(R.array.joke_dialog)
//                            .itemsCallback(new MaterialDialog.ListCallback() {
//                                @Override
//                                public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
//
//                                    switch (which) {
//                                        //分享
//                                        case 0:
//                                            ShareUtil.shareText(getActivity(), video
//                                                    .getTitle().trim() + " " + video.getUrl() +
//                                                    ToastMsg
//                                                            .SHARE_TAIL);
//                                            break;
//                                        //复制
//                                    }
//
//                                }
//                            })
//                            .show();
//                }
//            });

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(getActivity(), VideoDetailActivity.class);
//                    intent.putExtra("url", video.getUrl());
//                    startActivity(intent);
                }
            });

            setAnimation(holder.card, position);

        }

        @Override
        public int getItemCount() {
            return mVideos.size();
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
                loadData();
            } else {
                loadCache();
            }

        }

        private void loadData() {

            executeRequest(new Request4Video(Video.getUrlVideos(page),
                    new Response.Listener<ArrayList<Video>>() {
                        @Override
                        public void onResponse(ArrayList<Video> response) {

                            google_progress.setVisibility(View.GONE);
                            mLoadFinisCallBack.loadFinish(null);
                            if (mSwipeRefreshLayout.isRefreshing()) {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }

                            if (page == 1) {
                                mVideos.clear();
                                VideoCacheUtil.getInstance(getActivity()).clearAllCache();
                            }

                            mVideos.addAll(response);
                            notifyDataSetChanged();

                            VideoCacheUtil.getInstance(getActivity()).addResultCache(JSONParser.toString
                                    (response), page);

                            //防止加载不慢一页的情况
                            if (mVideos.size() < 10) {
                                loadNextPage();
                            }
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
            }).setRetryPolicy(new DefaultRetryPolicy(15 * 1000, 1, 1.0f)));
        }

        private void loadCache() {

            google_progress.setVisibility(View.GONE);
            mLoadFinisCallBack.loadFinish(null);
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }

            VideoCacheUtil videoCacheUtil = VideoCacheUtil.getInstance(getActivity());
            if (page == 1) {
                mVideos.clear();
                ShowToast.Short(ToastMsg.LOAD_NO_NETWORK);
            }
            mVideos.addAll(videoCacheUtil.getCacheByPage(page));
            notifyDataSetChanged();

        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private ImageView img_share;
        private ImageView img;
        private CardView card;

        //用于处理多次点击造成的网络访问
        private boolean isClickFinish;

        public ViewHolder(View contentView) {
            super(contentView);

            isClickFinish = true;

            img = (ImageView) contentView.findViewById(R.id.img);
            tv_title = (TextView) contentView.findViewById(R.id.tv_title);
            img_share = (ImageView) contentView.findViewById(R.id.img_share);
            card = (CardView) contentView.findViewById(R.id.card);

        }
    }
}

