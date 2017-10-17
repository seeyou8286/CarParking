package com.network.jiufen.carparking.carparking.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.fragment.home.BookingFragment;
import com.network.jiufen.carparking.carparking.fragment.home.PortalFragment;
import com.network.jiufen.carparking.carparking.fragment.home.ProfileFragment;
import com.network.jiufen.carparking.carparking.model.NavigationDrawerItem;
import com.network.jiufen.carparking.carparking.navigationdrawer.NavigationDrawerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import timber.log.Timber;

public class HomePageActivity extends AppCompatActivity {
    private FragmentManager fm;
    private FragmentTransaction ft;




    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    private int currentSelectedPosition = 0;

    @BindView(R.id.navigationDrawerListViewWrapper)
    NavigationDrawerView
            mNavigationDrawerListViewWrapper;

    @BindView(R.id.linearDrawer)
    LinearLayout mLinearDrawerLayout;

    @BindView(R.id.homepage)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.leftDrawerListView)
    ListView leftDrawerListView;

    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mTitle;

    private CharSequence mDrawerTitle;

    private List<NavigationDrawerItem> navigationItems;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fm = getFragmentManager();
            ft = fm.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_portal:
                    ft.replace(R.id.fragment_home_content, new PortalFragment());
                    ft.commit();
                    return true;
                case R.id.navigation_bookings:
                    ft.replace(R.id.fragment_home_content, new BookingFragment());
                    ft.commit();
                    return true;
                case R.id.navigation_profile:
                    ft.replace(R.id.fragment_home_content,new ProfileFragment());
                    ft.commit();
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_home_page);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_home_content, new PortalFragment());
        ft.commit();




        ButterKnife.bind(this);
        mTitle = mDrawerTitle = getTitle();
        getSupportActionBar().setIcon(R.drawable.ic_action_ab_transparent);

        Timber.tag("LifeCycles");
        Timber.d("Activity Created");

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.contentFrame,
//                            Fragment.instantiate(MainActivity.this, Fragments.ONE.getFragment()))
//                    .commit();
//        } else {
//            currentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
//        }

        navigationItems = new ArrayList<>();
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_one), true));
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_two), true));
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_three), true));
        navigationItems.add(
                new NavigationDrawerItem(getString(R.string.fragment_about), R.drawable.ic_action_about,
                        false));

        mNavigationDrawerListViewWrapper.replaceWith(navigationItems);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_navigation_drawer,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        selectItem(currentSelectedPosition);
    }



    @Override public void onResume() {
        super.onResume();
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, currentSelectedPosition);
    }

    @Override protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnItemClick(R.id.leftDrawerListView) public void OnItemClick(int position, long id) {
        if (mDrawerLayout.isDrawerOpen(mLinearDrawerLayout)) {
            mDrawerLayout.closeDrawer(mLinearDrawerLayout);
//            onNavigationDrawerItemSelected(position);

            selectItem(position);
        }
    }

    private void selectItem(int position) {

        if (leftDrawerListView != null) {
            leftDrawerListView.setItemChecked(position, true);

            navigationItems.get(currentSelectedPosition).setSelected(false);
            navigationItems.get(position).setSelected(true);

            currentSelectedPosition = position;
            getSupportActionBar().setTitle(navigationItems.get(currentSelectedPosition).getItemName());
        }

        if (mLinearDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mLinearDrawerLayout);
        }
    }
}
