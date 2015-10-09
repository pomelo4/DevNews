package com.pomelo.devnews.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.pomelo.devnews.R;
import com.pomelo.devnews.base.BaseActivity;
import com.pomelo.devnews.loader.ImageLoader;


import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

/**
 * 图片详情页
 */
public class ImageDetailActivity extends BaseActivity implements View.OnClickListener {

	@Bind(R.id.img_back)
	ImageButton img_back;
	@Bind(R.id.img)
	PhotoView img;

	public static final int ANIMATION_DURATION = 400;

	private String img_url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_detail);
		ButterKnife.bind(this);
		initView();
		initData();
	}

	@Override
	public void initView() {
		img_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.img_back:
				finish();
				break;
		}

	}

	@Override
	public void initData() {
		Intent intent = getIntent();

		img_url = intent.getStringExtra("url");

		ImageLoader.build(this).bindBitmap(img_url, img);
	}

}
