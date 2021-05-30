package com.tdkankan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.hb.dialog.dialog.LoadingDialog;
import com.tdkankan.Data.GlobalConfig;
import com.tdkankan.UI.BookShelfFragment;
import com.tdkankan.UI.SearchActivity;
import com.tdkankan.greendao.DaoHelper;

/**
 * @author ZQZESS
 * @date 12/8/2020-9:25 PM
 * @file SplashScreenActivity.java
 * GitHub：https://github.com/zqzess
 *不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class SplashScreenActivity extends AppCompatActivity {
    /**
     * 延迟时间
     */
    private static final int DELAY_TIME = 1000;
    private DaoHelper mDb;
    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);// 隐藏android系统的状态栏
        loadingDialog= new LoadingDialog(SplashScreenActivity.this);
        new LoadData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    private  class LoadData extends AsyncTask<Void,Integer,Boolean>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            mDb=DaoHelper.getInstance(SplashScreenActivity.this);
//            GlobalConfig.booklist=mDb.searchAll();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
                    // 利用消息处理器实现延迟跳转到主窗口
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 启动登录窗口
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                // 关闭启动画面
                finish();//启动后销毁自身
            }
        }, DELAY_TIME);
        }
    }
}
