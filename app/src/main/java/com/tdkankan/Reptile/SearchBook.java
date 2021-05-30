package com.tdkankan.Reptile;


import android.util.Log;

import com.tdkankan.Cache.BookInfoCache;
import com.tdkankan.Cache.ImageCacheManager;
import com.tdkankan.Data.BookInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author ZQZESS
 * @date 1/6/2021.
 * @file SearchBook
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class SearchBook {
    public static ArrayList SearchBookEvent(String KeyWords)
    {
        ArrayList<HashMap<String,String>>list;
        Document alldoc;
        String link;//书链接
        String name="";
        String author="";
        String info="";
        String Piclink="";
        String picname;
        list=new ArrayList<HashMap<String, String>>();
        try {
            alldoc = Jsoup.connect("https://www.biqugeu.net/searchbook.php?keyword="+KeyWords).data("query", "Java").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36").get();
            Elements listClass = alldoc.getElementsByAttributeValue("class", "item");
            for(Element listitem:listClass)
            {
                HashMap<String,String> book = new HashMap<String,String>();

                try {
                    link = listitem.getElementsByTag("a").attr("href");//获取书籍link
                    name=listitem.select("dt > a").text().trim();//获取书名
                    author=listitem.getElementsByTag("span").text().trim();//获取作者名
                    info=listitem.getElementsByTag("dd").text().trim();//获取简介
                    Piclink = listitem.getElementsByTag("img").attr("src");//获取封面link
                } catch (Exception e) {
                    link = "";
                }
                picname=link.replace("/","");
//                final String id=link.replace("/","");
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        BookInfo bookInfo= BookInfoCache.loadBook(id);
//                    }
//                }).start();
                final String finalPicname = picname;
                final String finalPiclink = Piclink;
//                class MyRunnable implements Runnable{
//                    @Override
//                    public void run() {
//                        BookInfoCache.loadImage(finalPicname, finalPiclink);//采用Haspmap缓存图片至内存
//                    }
//                }
//                new Thread(new MyRunnable()).start();


                /*
                 *
                 * 子线程爬取封面
                 *
                 */

//                BookInfoCache.loadImage(bookInfo.getPicname(),bookInfo.getPiclink());//采用Haspmap缓存图片至内存


//                book.put("name",bookInfo.getName());
//                book.put("author",bookInfo.getAuthor());
//                book.put("link",link);
//                book.put("picname",bookInfo.getPicname());
//                book.put("piclink",bookInfo.getPiclink());
//                book.put("info",bookInfo.getInfo());
//                book.put("lasttime",bookInfo.getLasttime());
//                book.put("newchapter",bookInfo.getNewchapter());
//                book.put("newchapterlink",bookInfo.getNewchapterlink());
                book.put("name",name);
                book.put("author",author);
                book.put("link",link);
                book.put("picname",picname);
                book.put("piclink",Piclink);
                book.put("info",info);

//                Log.d("name",bookInfo.getName());
//                Log.d("author",bookInfo.getAuthor());
//                Log.d("link",link);
//                Log.d("picname",bookInfo.getPicname());
//                Log.d("piclink",bookInfo.getPiclink());
//                Log.d("info",bookInfo.getInfo());
//                Log.d("lasttime",bookInfo.getLasttime());
//                Log.d("newchapter",bookInfo.getNewchapter());
//                Log.d("newchapterlink",bookInfo.getNewchapterlink());
//                Log.d("chapternum",bookInfo.getChapternum()+"");

                list.add(book);

            }
        }catch (Exception e)
        {

        }
        return list;
    }
}
