package com.tdkankan.Adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdkankan.Cache.ImageCacheManager;
import com.tdkankan.Data.BitmapUtils;
import com.tdkankan.Data.GlobalConfig;
import com.tdkankan.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author ZQZESS
 * @date 12/9/2020.
 * @file FengTuiAdapter
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class FengTuiAdapter extends BaseAdapter {
    public ArrayList<HashMap<String, String>> list;
    private int sumCount;
    Activity activity;
    Bitmap bitmap;
    Bitmap bitmap2;

    public FengTuiAdapter(ArrayList<HashMap<String, String>> list, Activity activity) {
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
        final FengTuiAdapter.ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_topfragment, null);
            holder = new FengTuiAdapter.ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.tv_topfragment_fengtui_name_1);
            holder.name2 = (TextView) convertView.findViewById(R.id.tv_topfragment_fengtui_name_2);
            holder.author = (TextView) convertView.findViewById(R.id.tv_topfragment_fengtui_author_1);
            holder.author2 = (TextView) convertView.findViewById(R.id.tv_topfragment_fengtui_author_2);
            holder.info = (TextView) convertView.findViewById(R.id.tv_topfragment_fengtui_info_1);
            holder.info2 = (TextView) convertView.findViewById(R.id.tv_topfragment_fengtui_info_2);
            holder.img = (ImageView) convertView.findViewById(R.id.img_topfragment_fengtui_1);
            holder.img2 = (ImageView) convertView.findViewById(R.id.img_topfragment_fengtui_2);

            holder.name.setEllipsize(TextUtils.TruncateAt.END);
            holder.name2.setEllipsize(TextUtils.TruncateAt.END);
            holder.author.setEllipsize(TextUtils.TruncateAt.END);
            holder.author2.setEllipsize(TextUtils.TruncateAt.END);
            holder.info.setEllipsize(TextUtils.TruncateAt.END);
            holder.info2.setEllipsize(TextUtils.TruncateAt.END);


            convertView.setTag(holder);
        } else {
            holder = (FengTuiAdapter.ViewHolder) convertView.getTag();
        }
//        HashMap<String, String> map = list.get(count);
        //获取缓存的图片
//         String picname=list.get(position*2).get("finalPicName");
//         String picname2=list.get(position*2+1).get("finalPicName");
//        bitmap= BitmapUtils.readBitmapFromFileDescriptor("/data/data/com.tdkankan/temp/images/"+picname+".jpg",50,80);
//        bitmap2=BitmapUtils.readBitmapFromFileDescriptor("/data/data/com.tdkankan/temp/images/"+picname2+".jpg",50,80);

        String piclink=list.get(position*2).get("piclink");
        piclink= GlobalConfig.PicLinkCheck(piclink);
        String piclink2=list.get(position*2+1).get("piclink");
        piclink2=GlobalConfig.PicLinkCheck(piclink2);

        holder.name.setText(list.get(position * 2).get("name"));
        holder.author.setText(list.get(position * 2).get("author"));
        holder.info.setText(list.get(position * 2).get("info"));
//        holder.img.setImageBitmap(bitmap);
        ImageCacheManager.loadImage(piclink,holder.img,getBitmapFromRes(activity,R.drawable.nonepic),getBitmapFromRes(activity,R.drawable.nonepic));
//        holder.img.setText(list.get(count).get("name"));
//            count++;
        if (position * 2 + 1 == list.size()) {
            holder.name2.setVisibility(View.INVISIBLE);
            holder.author2.setVisibility(View.INVISIBLE);
            holder.info2.setVisibility(View.INVISIBLE);
            holder.img2.setVisibility(View.INVISIBLE);
        } else {
            holder.name2.setVisibility(View.VISIBLE);
            holder.author2.setVisibility(View.VISIBLE);
            holder.info2.setVisibility(View.VISIBLE);
            holder.img2.setVisibility(View.VISIBLE);
            holder.name2.setText(list.get(position * 2 + 1).get("name"));
            holder.author2.setText(list.get(position * 2 + 1).get("author"));
            holder.info2.setText(list.get(position * 2 + 1).get("info"));
//            holder.img2.setImageBitmap(bitmap2);
            ImageCacheManager.loadImage(piclink2,holder.img2,getBitmapFromRes(activity,R.drawable.nonepic),getBitmapFromRes(activity,R.drawable.nonepic));
        }

        return convertView;
    }

    private Bitmap getBitmapFromRes(Activity activity,int resId) {
        Resources res = activity.getResources();
        return BitmapFactory.decodeResource(res, resId);
    }
    private class ViewHolder {
        TextView name;
        TextView author;
        TextView info;
        ImageView img;

        TextView name2;
        TextView author2;
        TextView info2;
        ImageView img2;
    }


}




