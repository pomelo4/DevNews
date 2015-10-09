package com.pomelo.devnews.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.pomelo.devnews.R;
import com.pomelo.devnews.base.BaseActivity;
import com.pomelo.devnews.ui.fragment.SettingFragment;

/**
 * 设置界面
 */
public class SettingActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		ActionBar actionBar = getSupportActionBar();

		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);

		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new SettingFragment())
				.commit();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

}
