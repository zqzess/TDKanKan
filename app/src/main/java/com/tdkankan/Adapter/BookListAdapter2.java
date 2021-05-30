package com.tdkankan.Adapter;

import android.app.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.tdkankan.Cache.BookInfoCache;
import com.tdkankan.Data.GlobalConfig;
import com.tdkankan.ViewUitl.BookItem;
import com.tdkankan.R;
import com.tdkankan.UI.BookInfoDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author ZQZESS
 * @date 12/9/2020.
 * @file BookListAdapter2
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class BookListAdapter2 extends BaseAdapter {
    private ArrayList<HashMap<String, String>> list;
    private int sumCount;
    Activity activity;
    Bitmap bitmap;
    Bitmap bitmap2;
//    BookInfoCache cache=new BookInfoCache();

    public BookListAdapter2(ArrayList<HashMap<String, String>> list, Activity activity) {
        super();
        this.list = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        int count = list.size();
        if (count % 2 == 0) {
            sumCount = count / 2; // 如果是双数直接减半
        } else {
            sumCount = (int) Math.floor((double) count / 2) + 1;
        }

        return sumCount;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
//        int count=position;
        final BookListAdapter2.ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_bookitem, null);
            holder = new BookListAdapter2.ViewHolder();
            holder.bookItem1 = (BookItem) convertView.findViewById(R.id.bookItem1);
            holder.bookItem2 = (BookItem) convertView.findViewById(R.id.bookItem2);
            convertView.setTag(holder);
        } else {
            holder = (BookListAdapter2.ViewHolder) convertView.getTag();
        }
//        HashMap<String, String> map = list.get(count);
        //获取缓存的图片
//        final String picname = list.get(position * 2).get("picname");
//        final String picname2 = list.get(position * 2 + 1).get("picname");
        String piclink=list.get(position * 2).get("piclink");
        piclink= GlobalConfig.PicLinkCheck(piclink);
        String piclink2 = list.get(position * 2 + 1).get("piclink");
        piclink2=GlobalConfig.PicLinkCheck(piclink2);


        /*
        *从本地读取图片
         */
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                bitmap = BitmapUtils.readBitmapFromFileDescriptor("/data/data/com.tdkankan/temp/images/" + finapicname + ".jpg", 50, 80);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                bitmap2 = BitmapUtils.readBitmapFromFileDescriptor("/data/data/com.tdkankan/temp/images/" + finapicname2 + ".jpg", 50, 80);
            }
        }).start();*/

        //从内存读取
//        bitmap= BookInfoCache.loadImage(picname,piclink);
//        bitmap2= BookInfoCache.loadImage(picname2,piclink2);


        holder.bookItem1.setName(list.get(position * 2).get("name"));
        holder.bookItem1.setAuthor(list.get(position * 2).get("author"));
        holder.bookItem1.setInfo(list.get(position * 2).get("info"));
        holder.bookItem1.setPic(piclink,GlobalConfig.bitmapnull);
//        holder.img.setText(list.get(count).get("name"));
//            count++;
        if (position * 2 + 1 == list.size()) {
            holder.bookItem2.setVisibility(View.INVISIBLE);
            holder.bookItem2.setVisibility(View.INVISIBLE);
            holder.bookItem2.setVisibility(View.INVISIBLE);
            holder.bookItem2.setVisibility(View.INVISIBLE);
        } else {
            holder.bookItem2.setVisibility(View.VISIBLE);
            holder.bookItem2.setVisibility(View.VISIBLE);
            holder.bookItem2.setVisibility(View.VISIBLE);
            holder.bookItem2.setVisibility(View.VISIBLE);
            holder.bookItem2.setName(list.get(position * 2 + 1).get("name"));
            holder.bookItem2.setAuthor(list.get(position * 2 + 1).get("author"));
            holder.bookItem2.setInfo(list.get(position * 2 + 1).get("info"));
            holder.bookItem2.setPic(piclink2,getBitmapFromRes(activity,R.drawable.nonepic));
        }

        holder.bookItem1.setMyItemClickedListener(new MyOnEvenClick(position*2));
        holder.bookItem2.setMyItemClickedListener(new MyOnEvenClick(position*2+1));
        return convertView;
    }

    private Bitmap getBitmapFromRes(Activity activity,int resId) {
        Resources res = activity.getResources();
        return BitmapFactory.decodeResource(res, resId);
    }
    private class ViewHolder {
        BookItem bookItem1;
        BookItem bookItem2;
    }

    private class MyOnEvenClick implements BookItem.MyItemClicked {
        int pos = 0;

        public MyOnEvenClick(int position) {
            this.pos = position;
        }

        @Override
        public void myItemClicked() {
            Log.d("clickposition",pos+"");
            Intent intent=new Intent(activity, BookInfoDetailActivity.class);
            intent.putExtra("name",list.get(pos).get("name"));
            intent.putExtra("author",list.get(pos).get("author"));
            intent.putExtra("info",list.get(pos).get("info"));
            intent.putExtra("picname",list.get(pos).get("picname"));
            intent.putExtra("link",list.get(pos).get("link"));
            intent.putExtra("piclink",list.get(pos).get("piclink"));
            activity.startActivity(intent);

        }
    }


}
