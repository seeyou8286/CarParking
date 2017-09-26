package com.network.jiufen.carparking.carparking.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.fragment.AccountFragment;
import com.network.jiufen.carparking.carparking.fragment.MobileFragment;
import com.network.jiufen.carparking.carparking.fragment.RegistrationFragment;
import com.network.jiufen.carparking.carparking.util.DictionaryUtil;

public class LoginActivity extends AppCompatActivity {
    private FragmentManager fm;
    private FragmentTransaction ft;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fm = getFragmentManager();
            ft = fm.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ft.replace(R.id.fragment_content,new MobileFragment());
                    ft.commit();
                    return true;
                case R.id.navigation_dashboard:
                    ft.replace(R.id.fragment_content,new AccountFragment());
                    ft.commit();
                    return true;
                case R.id.navigation_regist:
                    ft.replace(R.id.fragment_content,new RegistrationFragment());
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
        initRetrieveMetaData();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
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
}
