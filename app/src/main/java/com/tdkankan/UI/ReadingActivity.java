package com.tdkankan.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hb.dialog.dialog.LoadingDialog;
import com.tdkankan.Data.GlobalConfig;
import com.tdkankan.Data.ReadConfig;
import com.tdkankan.Presente.ReadPresenter;
import com.tdkankan.R;
import com.tdkankan.Reptile.GetAndRead;
import com.tdkankan.ViewUitl.BatteryView;

/**
 * @author ZQZESS
 * @date 1/6/2021-7:06 PM
 * @file ReadingActivity.java
 * GitHub：https://github.com/zqzess
 *不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class ReadingActivity extends AppCompatActivity {
    public TextView tv_title;
    public TextView tv_foot;
    public TextView tv_battery_valuel;
    public ImageView tv_read;
    public LinearLayout linearLayout;
    public LinearLayout layout_title;
    public LinearLayout layout_foot;
    public DrawerLayout drawerLayout;
    public TextView tv_book_chapter;
    public TextView tv_chapter_sort;
    public LinearLayout layout_read_chapter_list_view;

    Bitmap bitmap;
    Bitmap bitmap2;
    private BatteryView mBatteryView;
    public ReadPresenter mReadPresenter;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_read);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 隐藏android系统的状态栏
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());

        GlobalConfig.screenWidth = getWindowManager().getDefaultDisplay().getWidth(); // 屏幕宽
        GlobalConfig.screenHeight = getWindowManager().getDefaultDisplay().getHeight(); // 屏幕高

        loadingDialog=new LoadingDialog(this);
        findId();
//        if (!GlobalConfig.BookUrl.isEmpty()) {
//            GlobalConfig.BookUrl = "";
//        }
//        Intent intent = getIntent();
//        GlobalConfig.BookUrl=intent.getStringExtra("link");
//        ReadConfig.ReadSetting(this);
//        initContent("https://www.biqugeu.net/"+GlobalConfig.BookUrl, 508);
        new initReadTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
//        mReadPresenter = new ReadPresenter(this);
//
//        if (!ReadConfig.isDark) {
//            intChapterStyle(R.color.default_read_color, R.color.default_font_color);
//        } else {
//            intChapterStyle(R.color.default_read_color, R.color.dark_font_color);
//        }
//
//
//        mReadPresenter.LoadChapterContent();
//        intStyle();

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//注册接收器以获取电量信息
        registerReceiver(broadcastReceiver, intentFilter);
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Boolean isRight = event.getX() > (GlobalConfig.screenWidth / 3 * 2);
                Boolean isLeft = event.getX() < (GlobalConfig.screenWidth / 3);
                Boolean isCenter = event.getX() > (GlobalConfig.screenWidth / 3) && event.getX() < (GlobalConfig.screenWidth / 3 * 2);
                if (isRight) {
                    GlobalConfig.Page = GlobalConfig.Page + 1;
                    if (GlobalConfig.chapternow == GlobalConfig.list.size() - 1 && GlobalConfig.Page == GlobalConfig.PageTotal) {//本书最后一章最后一页
                        GlobalConfig.chapternow = GlobalConfig.list.size() - 1;
                        GlobalConfig.Page = GlobalConfig.Page - 1;
                    } else {
                        if (GlobalConfig.Page == GlobalConfig.PageTotal) {
                            /*
                             *本章末尾，切换章节标签，跳转下一章节
                             */
                            GlobalConfig.chapternow += 1;
                            mReadPresenter.LoadChapterContent();
                            GlobalConfig.Page = 0;
                            if (!ReadConfig.isDownload) {
                                GetAndRead.ReadingBackground(GlobalConfig.chapternow);
//                                Log.d("isdownload","2");
                            }
                        }
                    }

                    Log.d("PageSet", "Page=" + GlobalConfig.Page + "Cahapter:" + GlobalConfig.chapternow);
                    bitmap2 = mReadPresenter.changePageContent(GlobalConfig.Page);
                    GlobalConfig.SaveReadSetting(getApplicationContext());//保存阅读进度
                    tv_read.setImageBitmap(bitmap2);
                    try {
                        bitmap.recycle();
                        bitmap = null;
                        System.gc();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    Toast.makeText(getApplicationContext(), "右：X="+event.getX()+"Y="+event.getY(), Toast.LENGTH_SHORT).show();
                }
                /*
                 *左侧
                 */
                if (isLeft) {
                    GlobalConfig.Page = GlobalConfig.Page - 1;
                    if (GlobalConfig.Page < 0 && GlobalConfig.chapternow != 0) {
                        /*
                         *本章起始，切换章节标签，跳转上一章节
                         */
                        GlobalConfig.chapternow = GlobalConfig.chapternow - 1;
                        mReadPresenter.LoadChapterContent();
                        GlobalConfig.Page = GlobalConfig.PageTotal - 1;
                    } else if (GlobalConfig.Page <= 0 && GlobalConfig.chapternow == 0) {
                        GlobalConfig.chapternow = 0;
                        mReadPresenter.LoadChapterContent();
                        GlobalConfig.Page = 0;
                    }
                    Log.d("PageSet", "Page=" + GlobalConfig.Page + "Cahapter:" + GlobalConfig.chapternow);
                    bitmap = mReadPresenter.changePageContent(GlobalConfig.Page);
                    GlobalConfig.SaveReadSetting(getApplicationContext());//保存阅读进度
                    tv_read.setImageBitmap(bitmap);
                    try {
                        bitmap2.recycle();
                        bitmap2 = null;
                        System.gc();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                /*
                 *中央
                 */
                if (isCenter) {
                    mReadPresenter.showSettingView();
//                    Toast.makeText(getApplicationContext(), "中：X="+event.getX()+"Y="+event.getY(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    public void intStyle() {
        linearLayout.setBackgroundResource(ReadConfig.bgColor);
        layout_foot.setBackgroundResource(ReadConfig.bgColor);
        layout_title.setBackgroundResource(ReadConfig.bgColor);

        bitmap = mReadPresenter.changePageContent(GlobalConfig.Page);
        tv_read.setImageBitmap(bitmap);
    }

    public void intChapterStyle(int color, int fontColor) {
        layout_read_chapter_list_view.setBackgroundResource(color);
        tv_chapter_sort.setTextColor(getResources().getColor(fontColor));
        tv_book_chapter.setTextColor(getResources().getColor(fontColor));
    }

    private void findId() {
        tv_title = findViewById(R.id.tv_title);
        tv_foot = findViewById(R.id.tv_foot);
        tv_read = findViewById(R.id.tv_read);
        tv_battery_valuel = findViewById(R.id.tv_battery_value);
        linearLayout = findViewById(R.id.layout_read);
        layout_title = findViewById(R.id.layout_read_title);
        layout_foot = findViewById(R.id.layout_read_foot);
        mBatteryView = (BatteryView) findViewById(R.id.tv_battery);
        drawerLayout = findViewById(R.id.dl_read_activity);
        tv_book_chapter = findViewById(R.id.tv_book_chapter);
        tv_chapter_sort = findViewById(R.id.tv_chapter_sort);
        layout_read_chapter_list_view = findViewById(R.id.layout_read_chapter_list_view);
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //获取当前电量，如未获取具体数值，则默认为0
            int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            //获取最大电量，如未获取到具体数值，则默认为100
            int batteryScale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
            //显示电量
            tv_battery_valuel.setText((batteryLevel * 100 / batteryScale) + " % ");
        }
    };

    public void initContent(String url, int chapternum) {
        //初始化数据
        GlobalConfig.measuredWidth = GlobalConfig.screenWidth;//控件列宽度
        GlobalConfig.measuredHeigtt = GlobalConfig.screenHeight;//控件高度
        if (GlobalConfig.list.size() > 0) {
            GlobalConfig.list.clear();
        }

        if (GlobalConfig.contentMap.size() > 0) {
            GlobalConfig.contentMap.clear();
        }

        GlobalConfig.GetReadSetting(getApplicationContext());//读取阅读进度
        if (!ReadConfig.isDownload) {//isDownload默认未下载，isDownload==true返回false
            GetAndRead.getChapter(url, chapternum);
            GetAndRead.ReadingBackground(GlobalConfig.chapternow);
        }
    }
    private class initReadTask extends AsyncTask<Void,Integer,Boolean>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog.setMessage("加载中...");
            loadingDialog.setCancelable(true); // 是否可以按“返回键”消失
            loadingDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
            loadingDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (!GlobalConfig.BookUrl.isEmpty()) {
                GlobalConfig.BookUrl = "";
            }
            Intent intent = getIntent();
            GlobalConfig.BookUrl=intent.getStringExtra("link");
            GlobalConfig.chapternum=intent.getIntExtra("chapternum",0);
            ReadConfig.ReadSetting(ReadingActivity.this);
            initContent("https://www.biqugeu.net/"+GlobalConfig.BookUrl, GlobalConfig.chapternum);
            mReadPresenter = new ReadPresenter(ReadingActivity.this);
//            if (!ReadConfig.isDark) {
//                intChapterStyle(R.color.default_read_color, R.color.default_font_color);
//            } else {
//                intChapterStyle(R.color.default_read_color, R.color.dark_font_color);
//            }
            mReadPresenter.LoadChapterContent();
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
//            mReadPresenter = new ReadPresenter(ReadingActivity.this);
            if (!ReadConfig.isDark) {
                intChapterStyle(R.color.default_read_color, R.color.default_font_color);
            } else {
                intChapterStyle(R.color.default_read_color, R.color.dark_font_color);
            }
//            mReadPresenter.LoadChapterContent();
            loadingDialog.dismiss();
            intStyle();
        }
    }
}
