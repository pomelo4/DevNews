package com.pomelo.devnews.base;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pomelo.devnews.net.RequestManager;
import com.pomelo.devnews.ui.MainActivity;
import com.pomelo.devnews.utils.ShowToast;

/**
 * Fragment基类
 */
public abstract class BaseFragment extends Fragment {

	protected ActionBar mActionBar;
	protected TabLayout mTabLayout;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		mActionBar = ((AppCompatActivity) activity).getSupportActionBar();

		if (activity instanceof MainActivity) {
			mTabLayout = ((MainActivity) activity).getTabLayout();
		}
	}

	protected void executeRequest(Request request) {
		RequestManager.addRequest(request, this);
	}

	protected Response.ErrorListener errorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				ShowToast.Short(error.getMessage());
			}
		};
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		RequestManager.cancelAll(this);
	}
}
