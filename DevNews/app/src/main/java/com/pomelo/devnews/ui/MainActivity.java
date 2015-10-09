package com.pomelo.devnews.ui;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pomelo.devnews.R;
import com.pomelo.devnews.base.Initialable;
import com.pomelo.devnews.ui.fragment.BackFragment;
import com.pomelo.devnews.ui.fragment.HappyFragment;
import com.pomelo.devnews.ui.fragment.MobileFragment;
import com.pomelo.devnews.utils.StatusBarCompat;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Initialable {

    @Bind(R.id.dl_main_drawer)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.tabs)
    TabLayout mTabLayout;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.nv_main_navigation)
    NavigationView mNavigationView;

    // 接收网络状态广播
    private BroadcastReceiver netStateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        initView();
        initData();
    }

    @Override
    public void initView() {
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        if (mNavigationView != null) {
            setupDrawerContent(mNavigationView);
        }

        replaceFragment(R.id.frame_container, new BackFragment());
    }

    @Override
    public void initData() {

    }

    public TabLayout getTabLayout() {
        return mTabLayout;
    }

    public void replaceFragment(int id_content, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(id_content, fragment);
        transaction.commit();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()) {
                            case R.id.nav_mobile:
                                replaceFragment(R.id.frame_container, new MobileFragment());
                                return true;
                            case R.id.nav_back:
                                replaceFragment(R.id.frame_container, new BackFragment());
                                return true;

                            case R.id.nav_happy:
                                replaceFragment(R.id.frame_container, new HappyFragment());
                                return true;
                            case R.id.nav_setting:
                                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                                startActivity(intent);
                                break;

                        }
                        return true;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_navigation_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true ;
        }
        return super.onOptionsItemSelected(item);
    }

}
