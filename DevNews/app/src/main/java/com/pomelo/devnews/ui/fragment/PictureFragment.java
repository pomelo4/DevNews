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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pomelo.devnews.R;
import com.pomelo.devnews.base.AppApplication;
import com.pomelo.devnews.base.BaseFragment;
import com.pomelo.devnews.cache.PictureCacheUtil;
import com.pomelo.devnews.callback.LoadFinishCallBack;
import com.pomelo.devnews.constant.ToastMsg;
import com.pomelo.devnews.loader.ImageLoader;
import com.pomelo.devnews.model.Picture;
import com.pomelo.devnews.net.JSONParser;
import com.pomelo.devnews.net.Request4Picture;
import com.pomelo.devnews.ui.ImageDetailActivity;
import com.pomelo.devnews.utils.NetWorkUtil;
import com.pomelo.devnews.utils.ShowToast;
import com.pomelo.devnews.utils.TextUtil;
import com.pomelo.devnews.view.AutoLoadRecyclerView;
import com.pomelo.devnews.view.ShowMaxImageView;
import com.pomelo.devnews.view.googleprogressbar.GoogleProgressBar;
import com.pomelo.devnews.view.matchview.MatchTextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PictureFragment extends BaseFragment {

	@Bind(R.id.recycler_view)
	AutoLoadRecyclerView mRecyclerView;
	@Bind(R.id.swipeRefreshLayout)
	SwipeRefreshLayout mSwipeRefreshLayout;
	@Bind(R.id.google_progress)
	GoogleProgressBar google_progress;
	@Bind(R.id.tv_error)
	MatchTextView tv_error;

	private PictureAdapter mAdapter;
	private LoadFinishCallBack mLoadFinisCallBack;
	private ImageLoader mImageLoader;
	private int mImageWidth = 0;
	private int mImageHeight = 0;


	public PictureFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActionBar.setTitle("休息一下");
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_joke, container, false);
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

		mImageLoader = AppApplication.getImageLoader();
		mRecyclerView.setOnPauseListenerParams(mImageLoader, false, true);

		mAdapter = new PictureAdapter();
		mRecyclerView.setAdapter(mAdapter);
		mAdapter.loadFirst();

	}

	public class PictureAdapter extends RecyclerView.Adapter<ViewHolder> {

		private int page;
		private ArrayList<Picture> pictures;
		private int lastPosition = -1;

		public PictureAdapter() {
			pictures = new ArrayList<Picture>();
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
					.inflate(R.layout.item_pic, parent, false);
			return new ViewHolder(v);
		}

		@Override
		public void onBindViewHolder(final ViewHolder holder, final int position) {

			final Picture picture = pictures.get(position);

			String picUrl = picture.getUrl();

			holder.progress.setProgress(0);
			holder.progress.setVisibility(View.VISIBLE);

			mImageLoader.bindBitmap(picUrl, holder.img);
			if (TextUtil.isNull(picture.getDesc().trim())) {
				holder.tv_desc.setVisibility(View.GONE);
			} else {
				holder.tv_desc.setVisibility(View.GONE);
				holder.tv_desc.setText(picture.getDesc().trim());
			}

			holder.img.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(), ImageDetailActivity.class);

					intent.putExtra("url", picture.getUrl());

					startActivity(intent);
				}
			});

			holder.tv_who.setText(picture.getWho());
			holder.tv_time.setText(picture.getDesc());

			setAnimation(holder.card, position);

		}


		@Override
		public int getItemCount() {
			return pictures.size();
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
			executeRequest(new Request4Picture(Picture.getUrlPictures(page),
					new Response.Listener<ArrayList<Picture>>() {
						@Override
						public void onResponse(ArrayList<Picture> response) {
							mLoadFinisCallBack.loadFinish(null);
							google_progress.setVisibility(View.GONE);
							tv_error.setVisibility(View.GONE);

							if (mSwipeRefreshLayout.isRefreshing()) {
								mSwipeRefreshLayout.setRefreshing(false);
							}

							if (page == 1) {
								PictureAdapter.this.pictures.clear();
								PictureCacheUtil.getInstance(getActivity()).clearAllCache();
							}

							PictureAdapter.this.pictures.addAll(response);
							notifyDataSetChanged();

							//加载完毕后缓存
							PictureCacheUtil.getInstance(getActivity()).addResultCache(JSONParser.toString(response), page);
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

		/**
		 * 从缓存中加载
		 */
		private void loadCache() {

			google_progress.setVisibility(View.GONE);
			mLoadFinisCallBack.loadFinish(null);
			if (mSwipeRefreshLayout.isRefreshing()) {
				mSwipeRefreshLayout.setRefreshing(false);
			}

			PictureCacheUtil pictureCacheUtil = PictureCacheUtil.getInstance(getActivity());
			if (page == 1) {
				pictures.clear();
				ShowToast.Short(ToastMsg.LOAD_NO_NETWORK);
			}

			pictures.addAll(pictureCacheUtil.getCacheByPage(page));
			notifyDataSetChanged();

		}

	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		private TextView tv_who;
		private TextView tv_time;
		private TextView tv_desc;
		private ImageView img_share;
		private ShowMaxImageView img;
		private ProgressBar progress;
		private CardView card;

		//用于处理多次点击造成的网络访问
		private boolean isClickFinish;


		public ViewHolder(View contentView) {
			super(contentView);

			isClickFinish = true;

			tv_who = (TextView) contentView.findViewById(R.id.tv_who);
			tv_desc = (TextView) contentView.findViewById(R.id.tv_desc);
			tv_time = (TextView) contentView.findViewById(R.id.tv_time);
			img_share = (ImageView) contentView.findViewById(R.id.img_share);
			img = (ShowMaxImageView) contentView.findViewById(R.id.img);
			progress = (ProgressBar) contentView.findViewById(R.id.progress);
			card = (CardView) contentView.findViewById(R.id.card);

		}
	}
}
