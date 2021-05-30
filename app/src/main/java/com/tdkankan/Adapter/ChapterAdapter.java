package com.tdkankan.Adapter;

import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;

import com.hb.dialog.dialog.LoadingDialog;
import com.tdkankan.Data.GlobalConfig;
import com.tdkankan.Data.ReadConfig;
import com.tdkankan.R;
import com.tdkankan.Reptile.GetAndRead;
import com.tdkankan.Reptile.GetBook;
import com.tdkankan.UI.ReadingActivity;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ZQZESS
 * @date 1/6/2021.
 * @file ChapterAdapter
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class ChapterAdapter extends BaseAdapter {
    private ArrayList<ConcurrentHashMap<String, String>> list;
    ReadingActivity activity;
    LoadingDialog loadingDialog;

    public ChapterAdapter(ArrayList<ConcurrentHashMap<String, String>> list, ReadingActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(activity).inflate(R.layout.listview_chapter_list, null);
        TextView textView=v.findViewById(R.id.tv_listview_chapter_list_name);
        if(position== GlobalConfig.chapternow)
        {
            textView.setTextColor(Color.RED);
        }else
        {
            textView.setTextColor(activity.getResources().getColor(ReadConfig.fontColor));
        }
        textView.setText(list.get(position).get("title"));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog=new LoadingDialog(activity);
                GlobalConfig.chapternow=position;
                GlobalConfig.Page=0;
//                activity.mReadPresenter.LoadChapterContent();
//                GlobalConfig.SaveReadSetting(activity);//保存阅读进度
//                activity.tv_read.setImageBitmap(activity.mReadPresenter.changePageContent(GlobalConfig.Page));
//                GetAndRead.ReadingBackground(GlobalConfig.chapternow);
                new setChapter().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                activity.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        return v;
    }
    private class setChapter extends AsyncTask<Void,Integer,Boolean>
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
            activity.mReadPresenter.LoadChapterContent();
            GlobalConfig.SaveReadSetting(activity);//保存阅读进度
            GetAndRead.ReadingBackground(GlobalConfig.chapternow);
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            activity.tv_read.setImageBitmap(activity.mReadPresenter.changePageContent(GlobalConfig.Page));
            loadingDialog.dismiss();
        }
    }
}
