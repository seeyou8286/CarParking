package com.network.jiufen.carparking.carparking.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.fragment.login.AccountFragment;
import com.network.jiufen.carparking.carparking.fragment.login.MobileFragment;
import com.network.jiufen.carparking.carparking.util.DictionaryUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private FragmentManager fm;
    private FragmentTransaction ft;
    private ActionBar actionBar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fm = getFragmentManager();
            ft = fm.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ft.replace(R.id.fragment_content, new MobileFragment());
                    ft.commit();
                    return true;
                case R.id.navigation_dashboard:
                    ft.replace(R.id.fragment_content, new AccountFragment());
                    ft.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.log_in);
        actionBar.setHomeAsUpIndicator(R.drawable.clear);
        actionBar.setDisplayHomeAsUpEnabled(true);



        initRetrieveMetaData();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_content, new MobileFragment());
        ft.commit();
    }


    private void initRetrieveMetaData() {
        try {
            ApplicationInfo appInfo = this.getPackageManager()
                    .getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
            String appId = appInfo.metaData.getString(DictionaryUtil.APP_KEY);
            String accessKeySecret = appInfo.metaData.getString(DictionaryUtil.APP_SECRET);
            DictionaryUtil.dictionary.put(DictionaryUtil.APP_KEY, appId);
            DictionaryUtil.dictionary.put(DictionaryUtil.APP_SECRET, accessKeySecret);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.registration:
                Intent intent = new Intent(this, RegistrationActivity.class);
                startActivity(intent);
                return true;
        }
        return true;
    }


}
