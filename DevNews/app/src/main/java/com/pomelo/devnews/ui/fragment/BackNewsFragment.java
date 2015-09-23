package com.pomelo.devnews.ui.fragment;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pomelo.devnews.R;
import com.pomelo.devnews.base.BaseFragment;
import com.pomelo.devnews.callback.LoadFinishCallBack;
import com.pomelo.devnews.constant.ToastMsg;
import com.pomelo.devnews.loader.ImageLoader;
import com.pomelo.devnews.model.BackNews;
import com.pomelo.devnews.net.Request4BackNews;
import com.pomelo.devnews.utils.NetWorkUtil;
import com.pomelo.devnews.utils.ShowToast;
import com.pomelo.devnews.view.AutoLoadRecyclerView;
import com.pomelo.devnews.view.googleprogressbar.GoogleProgressBar;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class BackNewsFragment extends BaseFragment {

    @Bind(R.id.recycler_view)
    AutoLoadRecyclerView mRecyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.google_progress)
    GoogleProgressBar google_progress;

    private BackNewsAdapter mAdapter;
    private LoadFinishCallBack mLoadFinisCallBack;
    private ImageLoader mImageLoader;
    private int mImageWidth = 0;
    private int mImageHeight = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar.setTitle("后端开发");
    }

    abstract BackNews.BackType RequestUrl();

//    abstract void clearAllCache();

//    abstract void addResultCache(String cache, int page);
//
//    abstract ArrayList<BackNews> getCacheByPage(int page);

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

        mImageLoader = ImageLoader.build(getActivity());
        mRecyclerView.setOnPauseListenerParams(mImageLoader, false, true);

        mAdapter = new BackNewsAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.loadFirst();
    }

    public class BackNewsAdapter extends RecyclerView.Adapter<ViewHolder> {

        private int page;
        private ArrayList<BackNews> mBackNewses;
        private int lastPosition = -1;

        public BackNewsAdapter() {
            mBackNewses = new ArrayList<BackNews>();
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
                    .inflate(R.layout.back_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final BackNews backNews = mBackNewses.get(position);

            // 获取控件大小
            ImageView image = holder.image;
            ViewGroup.LayoutParams params = image.getLayoutParams();
            mImageWidth = image.getWidth();
            mImageHeight = image.getHeight();

            mImageLoader.bindBitmap(backNews.getCover(), holder.image, mImageWidth, mImageHeight);
            holder.title.setText(backNews.getTitle());
            holder.description.setText(backNews.getDescription());
            holder.time.setText(backNews.getFormatDate());
            holder.author.setText(backNews.getUser());
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    WebViewActivity.actionStart(getActivity(), backNews.getId());
                }
            });
            setAnimation(holder.card, position);
        }

        @Override
        public int getItemCount() {
            return mBackNewses.size();
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

                executeRequest(new Request4BackNews(BackNews.getRequestUrl(RequestUrl(), page),
                        new Response.Listener<ArrayList<BackNews>>() {
                            @Override
                            public void onResponse(ArrayList<BackNews> response) {

                                google_progress.setVisibility(View.GONE);
                                mLoadFinisCallBack.loadFinish(null);
                                if (mSwipeRefreshLayout.isRefreshing()) {
                                    mSwipeRefreshLayout.setRefreshing(false);
                                }

                                if (page == 1) {
                                    mAdapter.mBackNewses.clear();
//                                    clearAllCache();
                                }

                                mAdapter.mBackNewses.addAll(response);
                                notifyDataSetChanged();
//                                String cache = JSONParser.toString(response);
//
//                                addResultCache(cache, page);

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
                    mBackNewses.clear();
                    ShowToast.Short(ToastMsg.LOAD_NO_NETWORK);
                }

//                mBackNewses.addAll(getCacheByPage(page));
                notifyDataSetChanged();
            }

        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView title;
        protected TextView description;
        protected TextView time;
        protected TextView author;
        protected ImageView image;
        protected CardView card;

        public ViewHolder(View contentView) {
            super(contentView);
            title = (TextView) itemView.findViewById(R.id.item_post_title);
            description = (TextView) itemView.findViewById(R.id.item_post_description);
            image = (ImageView) itemView.findViewById(R.id.item_post_img);
            time = (TextView) itemView.findViewById(R.id.item_post_date);
            author = (TextView) itemView.findViewById(R.id.item_post_user_name);
            card = (CardView) contentView.findViewById(R.id.card);
        }
    }
}
