package com.tdkankan.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hb.dialog.dialog.LoadingDialog;
import com.tdkankan.Adapter.BookListAdapter2;
import com.tdkankan.R;
import com.tdkankan.Reptile.GetBook;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author ZQZESS
 * @date 12/9/2020-12:56 AM
 * @file BookCityTopFragment.java
 * GitHub：https://github.com/zqzess
 *不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class BookCityTopFragment extends Fragment {
    ListView listView;
    ListView listView2;
    private ArrayList<HashMap<String, String>> list;
    private ArrayList<HashMap<String, String>> list2;
    private ArrayList<HashMap<String, String>> list3;
    LoadingDialog loadingDialog;
    /**
     * 延迟时间
     */
    private static final int DELAY_TIME = 70;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bookcity_topfragment, container, false);

        listView = (ListView)view.findViewById(R.id.listview_fengtui);
        listView2 = (ListView)view.findViewById(R.id.listview_qiangtui);
        loadingDialog= new LoadingDialog(BookCityTopFragment.this.getActivity());
        new FengTuiTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new QiangTuiTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return view;
    }

    public class FengTuiTask extends AsyncTask<Void,Integer,Boolean>
    {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                    list=GetBook.fengtui();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return true;
        }

        //执行前调用
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog.setMessage("loading");
            loadingDialog.setCancelable(true); // 是否可以按“返回键”消失
            loadingDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
            loadingDialog.show();
        }

        //执行完成后调用
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    BookListAdapter2 adapter = new BookListAdapter2(list,BookCityTopFragment.this.getActivity());
                    listView.setAdapter(adapter);
                }
            },DELAY_TIME);

        }
    }

    public class QiangTuiTask extends AsyncTask<Void,Integer,Boolean>
    {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                list2=GetBook.qiangtui();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            loadingDialog.dismiss();
            BookListAdapter2 adapter = new BookListAdapter2(list2,BookCityTopFragment.this.getActivity());
            listView2.setAdapter(adapter);
        }
    }
}
