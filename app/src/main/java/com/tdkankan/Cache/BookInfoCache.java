package com.tdkankan.Cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.tdkankan.Data.BitmapUtils;
import com.tdkankan.Data.Book;
import com.tdkankan.Data.BookInfo;
import com.tdkankan.Data.GlobalConfig;
import com.tdkankan.Data.Picture;
import com.tdkankan.Reptile.GetBook;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ZQZESS
 * @date 12/13/2020.
 * @file BookInfoCache
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class BookInfoCache extends HashMap {
    private static ConcurrentHashMap<String, Bitmap> imageMap = new ConcurrentHashMap<>();
    public static Bitmap loadImage(final String url, String dlink) {
        if (imageMap.containsKey(url)) {
            Log.d("picCacheGet", "图片缓存已存在:" + url);
            return imageMap.get(url);
        } else {
            Log.d("picCacheGet", "图片缓存不存在：" + url);
            initPicture(url,dlink);
            return imageMap.get(url);
        }
    }

    private static void initPicture(String url, String dlink) {
        try {
            //通过传入的图片地址，获取图片
            HttpURLConnection connection = (HttpURLConnection) (new URL("http:" + dlink).openConnection());
            InputStream is = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
//            if (bitmap.getByteCount() != 0) {
                if (bitmap!=null) {
                Log.d("pic", "图片获取成功");
                bitmap = BitmapUtils.compressImage(bitmap);//图片压缩
//                Picture picture2 = new Picture(url, bitmap);
                imageMap.put(url, bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BookInfo loadBook(String id) {
        if (GlobalConfig.bookmap.containsKey(id)) {
            BookInfo book = GlobalConfig.bookmap.get(id);
            Log.d("bookCacheGet", "图书缓存已存在:" + id);
            return book;
        } else {
            Log.d("bookCacheGet", "图书缓存不存在:" + id);
            BookInfo book2 = GetBook.GetBookInfo(id);//爬虫爬取书本信息
//            BookInfo book2 = new BookInfo(id, bookInfo);
            GlobalConfig.bookmap.put(id, book2);//书本信息存入hashmap缓存
            return book2;
        }
    }

}
