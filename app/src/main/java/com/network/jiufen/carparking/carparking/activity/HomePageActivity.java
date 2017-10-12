package com.network.jiufen.carparking.carparking.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.fragment.home.BookingFragment;
import com.network.jiufen.carparking.carparking.fragment.home.PortalFragment;
import com.network.jiufen.carparking.carparking.fragment.home.ProfileFragment;

public class HomePageActivity extends AppCompatActivity {
    private FragmentManager fm;
    private FragmentTransaction ft;

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
    }
}
