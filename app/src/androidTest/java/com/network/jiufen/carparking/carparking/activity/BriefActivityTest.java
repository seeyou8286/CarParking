package com.network.jiufen.carparking.carparking.activity;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by asus on 2017/10/7.
 */
@RunWith(AndroidJUnit4.class)
public class BriefActivityTest {
    @Rule
    public ActivityTestRule<BriefActivity> rule = new ActivityTestRule<>(BriefActivity.class);

    @Test
    public void useAppContext() throws Exception {
        BriefActivity briefActivity = rule.getActivity();
    }
}