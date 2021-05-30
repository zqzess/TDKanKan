package com.tdkankan.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.toolbox.ImageLoader;
import com.tdkankan.Cache.BookInfoCache;
import com.tdkankan.Cache.ImageCacheManager;
import com.tdkankan.Cache.ImageCacheUtil;
import com.tdkankan.Cache.VolleyRequestQueueManager;
import com.tdkankan.Data.BookInfo;
import com.tdkankan.Data.GlobalConfig;
import com.tdkankan.R;
import com.tdkankan.UI.BookInfoDetailActivity;
import com.tdkankan.UI.SearchActivity;
import com.tdkankan.ViewUitl.BookItem;
import com.tdkankan.ViewUitl.BookItemTypeTwo;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author ZQZESS
 * @date 1/6/2021.
 * @file SearchListAdapter
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class SearchListAdapter extends BaseAdapter {
    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    Bitmap bitmap;
    SearchActivity searchActivity;

    public SearchListAdapter(ArrayList<HashMap<String, String>> list, Activity activity) {
        super();
        this.list = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        int count = list.size();

        return count;
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
        final SearchListAdapter.ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_bookitem2, null);
            holder = new SearchListAdapter.ViewHolder();
            holder.bookItem = (BookItemTypeTwo) convertView.findViewById(R.id.bookItemTypeTwo);

            convertView.setTag(holder);
        } else {
            holder = (SearchListAdapter.ViewHolder) convertView.getTag();
        }
//        HashMap<String, String> map = list.get(count);
        //获取缓存的图片
        String picname = list.get(position).get("picname");
//        final Stripiclink = "//bqgxs.cdn.bcebos.com/web//files/article/image/107/106151/106151.jpg "ng piclink=list.get(position ).get("piclink");
//        String piclink= BookInfoCache.loadBook(picname).getBookInfo().getPiclink();
        String piclink= list.get(position).get("piclink");

//        bitmap= BookInfoCache.loadImage(picname,piclink);
//        searchActivity.imageCacheManager.loadImage("http"+piclink, holder.bookItem, getBitmapFromRes(activity,R.drawable.jay_icon),getBitmapFromRes(activity,R.drawable.jay_icon),80,110);
        piclink= GlobalConfig.PicLinkCheck(piclink);
        ImageCacheManager.loadImage(piclink,holder.bookItem.img_pic,getBitmapFromRes(activity,R.drawable.nonepic),getBitmapFromRes(activity,R.drawable.nonepic));

        holder.bookItem.setName(list.get(position).get("name"));
        holder.bookItem.setAuthor(list.get(position ).get("author"));
        holder.bookItem.setInfo(list.get(position ).get("info"));
//        holder.bookItem.setPic("http:"+piclink);

        holder.bookItem.setMyItemClickedListener(new MyOnEvenClick(position));
        return convertView;
    }

    private class ViewHolder {
        BookItemTypeTwo bookItem;
    }
    private Bitmap getBitmapFromRes(Activity activity,int resId) {
        Resources res = activity.getResources();
        return BitmapFactory.decodeResource(res, resId);
    }
    private class MyOnEvenClick implements BookItemTypeTwo.MyItemClicked{
        int pos = 0;

        public MyOnEvenClick(int position) {
            this.pos = position;
        }

        @Override
        public void myItemClicked() {
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
