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

import com.tdkankan.Cache.ImageCacheManager;
import com.tdkankan.Data.GlobalConfig;
import com.tdkankan.R;
import com.tdkankan.UI.ReadingActivity;
import com.tdkankan.ViewUitl.BookItemTypeThree;
import com.tdkankan.greendao.model.Bookinfodb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ZQZESS
 * @date 2/19/2021.
 * @file BookShelfAdapter
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class BookShelfAdapter extends BaseAdapter {
    private List<Bookinfodb> list;
    private int sumCount;
    Activity activity;
    Bitmap bitmap;
    Bitmap bitmap2;
    Bitmap bitmap3;
    public BookShelfAdapter(List<Bookinfodb> list, Activity activity) {
        super();
        this.list = list;
        this.activity = activity;
    }
    @Override
    public int getCount() {
        int count = list.size();
        if (count % 3 == 0) {
            sumCount = count / 3; // 如果是3倍数
        } else {
            sumCount = (int) Math.floor((double) count / 3) + 1;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final BookShelfAdapter.ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_bookshelf, null);
            holder = new BookShelfAdapter.ViewHolder();
            holder.bookItem1 = (BookItemTypeThree) convertView.findViewById(R.id.bookItemTypeThree);
            holder.bookItem2 = (BookItemTypeThree) convertView.findViewById(R.id.bookItemTypeThree2);
            holder.bookItem3 = (BookItemTypeThree) convertView.findViewById(R.id.bookItemTypeThree3);
            convertView.setTag(holder);
        } else {
            holder = (BookShelfAdapter.ViewHolder) convertView.getTag();
        }
        String piclink=list.get(position * 3).getPiclink();
        piclink= GlobalConfig.PicLinkCheck(piclink);
        holder.bookItem1.setName(list.get(position * 3).getName());
        ImageCacheManager.loadImage(piclink,holder.bookItem1.img_pic,getBitmapFromRes(activity,R.drawable.nonepic),getBitmapFromRes(activity,R.drawable.nonepic));
        if (position * 3 + 1 == list.size()) {
            holder.bookItem2.setVisibility(View.INVISIBLE);
            holder.bookItem2.setVisibility(View.INVISIBLE);
            holder.bookItem2.setVisibility(View.INVISIBLE);
            holder.bookItem2.setVisibility(View.INVISIBLE);
            holder.bookItem3.setVisibility(View.INVISIBLE);
            holder.bookItem3.setVisibility(View.INVISIBLE);
            holder.bookItem3.setVisibility(View.INVISIBLE);
            holder.bookItem3.setVisibility(View.INVISIBLE);
        } else{
            holder.bookItem2.setVisibility(View.VISIBLE);
            holder.bookItem2.setVisibility(View.VISIBLE);
            holder.bookItem2.setVisibility(View.VISIBLE);
            holder.bookItem2.setVisibility(View.VISIBLE);
            String piclink2 = list.get(position * 3 + 1).getPiclink();
            piclink2=GlobalConfig.PicLinkCheck(piclink2);
            holder.bookItem2.setName(list.get(position * 3 + 1).getName());
//            holder.bookItem2.setPiclink(piclink2,GlobalConfig.bitmapnull,GlobalConfig.bitmapnull);
            ImageCacheManager.loadImage(piclink2,holder.bookItem2.img_pic,getBitmapFromRes(activity,R.drawable.nonepic),getBitmapFromRes(activity,R.drawable.nonepic));

        }
        if (position * 3 + 2 >= list.size()) {
            holder.bookItem3.setVisibility(View.INVISIBLE);
            holder.bookItem3.setVisibility(View.INVISIBLE);
            holder.bookItem3.setVisibility(View.INVISIBLE);
            holder.bookItem3.setVisibility(View.INVISIBLE);
        } else {
            holder.bookItem3.setVisibility(View.VISIBLE);
            holder.bookItem3.setVisibility(View.VISIBLE);
            holder.bookItem3.setVisibility(View.VISIBLE);
            holder.bookItem3.setVisibility(View.VISIBLE);
            String piclink3 = list.get(position * 3 + 2).getPiclink();
            piclink3=GlobalConfig.PicLinkCheck(piclink3);
            holder.bookItem3.setName(list.get(position * 3+2).getName());
//            holder.bookItem3.setPiclink(piclink3,GlobalConfig.bitmapnull,GlobalConfig.bitmapnull);
            ImageCacheManager.loadImage(piclink3,holder.bookItem3.img_pic,getBitmapFromRes(activity,R.drawable.nonepic),getBitmapFromRes(activity,R.drawable.nonepic));

        }
        holder.bookItem1.setMyItemClickedListener(new MyOnEvenClick(position*3));
        holder.bookItem2.setMyItemClickedListener(new MyOnEvenClick(position*3+1));
        holder.bookItem3.setMyItemClickedListener(new MyOnEvenClick(position*3+1));
        return convertView;
    }
    private class ViewHolder {
        BookItemTypeThree bookItem1;
        BookItemTypeThree bookItem2;
        BookItemTypeThree bookItem3;
    }
    private Bitmap getBitmapFromRes(Activity activity,int resId) {
        Resources res = activity.getResources();
        return BitmapFactory.decodeResource(res, resId);
    }
    private class MyOnEvenClick implements BookItemTypeThree.MyItemClicked {
        int pos = 0;

        public MyOnEvenClick(int position) {
            this.pos = position;
        }

        @Override
        public void myItemClicked() {
            Log.d("clickposition",pos+"");
            Intent intent=new Intent(activity, ReadingActivity.class);
            intent.putExtra("link",list.get(pos).getLink());
            intent.putExtra("chapternum",list.get(pos).getChapternum());
            activity.startActivity(intent);

        }
    }
}
