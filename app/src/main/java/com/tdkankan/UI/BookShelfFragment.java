package com.tdkankan.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.tdkankan.Adapter.BookShelfAdapter;
import com.tdkankan.Data.GlobalConfig;
import com.tdkankan.MainActivity;
import com.tdkankan.R;
import com.tdkankan.SplashScreenActivity;
import com.tdkankan.greendao.DaoHelper;
import com.tdkankan.greendao.model.Bookinfodb;

import java.util.List;

/**
 * @author ZQZESS
 * @date 12/8/2020-9:39 PM
 * @file BookShelfFragment.java
 * GitHub：https://github.com/zqzess
 *不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class BookShelfFragment extends Fragment {
    TextView tv_search;
    private DaoHelper mDb;
    ListView listView;
    BookShelfAdapter adapter;
    SwipeRefreshLayout refreshP;
    List<Bookinfodb> list;
    Context context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bookshelf, container, false);
        initView(view);
        ClickEvent();
        return view;
    }
    private void initView(View view)
    {
        context=this.getActivity();
        tv_search=(TextView) view.findViewById(R.id.tv_bookshelf_search);
        listView=(ListView)view.findViewById(R.id.listview_bookshelf);
        refreshP=(SwipeRefreshLayout)view.findViewById(R.id.bookshelf_refresh);
        mDb=DaoHelper.getInstance(context);
        list=mDb.searchAll();
        adapter=new BookShelfAdapter(list,BookShelfFragment.this.getActivity());
        listView.setAdapter(adapter);

    }
    private void ClickEvent()
    {
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BookShelfFragment.this.getActivity(),SearchActivity.class);
                startActivity(intent);
            }
        });
        refreshP.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });
    }

    void Refresh()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list.clear();
                list.addAll(mDb.searchAll());
                refreshP.setRefreshing(false);
            }
        }, 1000);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onStart() {
        super.onStart();
        refreshP.post(new Runnable() {
            @Override
            public void run() {
                refreshP.setRefreshing(true);
                Refresh();
            }
        });
    }
}
