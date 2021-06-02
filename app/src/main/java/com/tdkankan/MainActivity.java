package com.tdkankan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TabHost;

import com.tdkankan.Data.GlobalConfig;
import com.tdkankan.Data.TabItem;
import com.tdkankan.UI.BookCityFragment;
import com.tdkankan.UI.BookShelfFragment;
import com.tdkankan.UI.SettingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZQZESS
 * @date 12/8/2020-9:29 PM
 * @file MainActivity.java
 * GitHub：https://github.com/zqzess
 *不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class MainActivity extends AppCompatActivity {
    public static final int TAB_APPROVAL = 0;
    public static final int TAB_SEARCH = 1;
    public static final int TAB_CLASS = 2;

    private List<TabItem> mFragmentList;

    private FragmentTabHost mFragmentTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainview);
        getSupportActionBar().hide();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        initTabItemData();
        GlobalConfig.bitmapnull=getBitmapFromRes(this,R.drawable.nonepic);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy( builder.build() );
        }
    }

    private void initTabItemData() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new TabItem(
                R.mipmap.ic_launcher_shujia,
                R.mipmap.ic_launcher_shujia_select,
                "书架",
                BookShelfFragment.class, R.color.courseTable3
        ));

        mFragmentList.add(new TabItem(
                R.mipmap.ic_launcher_bookcity,
                R.mipmap.ic_launcher_bookcity_select,
                "书城",
                BookCityFragment.class, R.color.courseTable3
        ));

        mFragmentList.add(new TabItem(
                R.mipmap.ic_launcher_setting,
                R.mipmap.ic_launcher_setting_select,
                "设置",
                SettingFragment.class, R.color.courseTable3
        ));

        mFragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        // 绑定 FragmentManager
        mFragmentTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        // 删除分割线
//        mFragmentTabHost.getTabWidget().setDividerDrawable(null);

        for (int i = 0; i < mFragmentList.size(); i++) {
            TabItem tabItem = mFragmentList.get(i);
            // 创建 tab
            TabHost.TabSpec tabSpec = mFragmentTabHost.newTabSpec(
                    tabItem.getTabText()).
                    setIndicator(tabItem.getTabView(MainActivity.this));
            // 将创建的 tab 添加到底部 tab 栏中（ @android:id/tabs ）
            // 将 Fragment 添加到页面中（ @android:id/tabcontent ）
            mFragmentTabHost.addTab(tabSpec, tabItem.getFragmentClass(), null);
            // 底部 tab 栏设置背景图片
            //mFragmentTabHost.getTabWidget().setBackgroundResource(R.drawable.bottom_bar);
            mFragmentTabHost.getTabWidget().setBackgroundResource(R.color.courseTable6);

            // 默认选中第一个 tab
            if (i == 0) {
                tabItem.setChecked(true);
            } else {
                tabItem.setChecked(false);
            }
        }

        // 切换 tab 时，回调此方法
        mFragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for (int i = 0; i < mFragmentList.size(); i++) {
                    TabItem tabItem = mFragmentList.get(i);
                    // 通过 tag 检查用户点击的是哪个 tab
                    if (tabId.equals(tabItem.getTabText())) {
                        tabItem.setChecked(true);
                    } else {
                        tabItem.setChecked(false);
                    }
                }
            }
        });
    }
    private Bitmap getBitmapFromRes(Activity activity, int resId) {
        Resources res = activity.getResources();
        return BitmapFactory.decodeResource(res, resId);
    }
}
