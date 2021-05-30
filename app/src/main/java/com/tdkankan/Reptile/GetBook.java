package com.tdkankan.Reptile;

import android.content.Context;
import android.util.Log;

import com.tdkankan.Cache.BookInfoCache;
import com.tdkankan.Data.BookInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author ZQZESS
 * @date 12/9/2020.
 * @file GetBook
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class GetBook {
    /*public static ArrayList fengtui()
    {
        ArrayList<HashMap<String,String>>list;
        Document alldoc;
        String name;//书名
        String author;//作者
        String info;//简介
        String link;//书链接
        String piclink;//封面链接
        String picname;
        list=new ArrayList<HashMap<String, String>>();
//        BookInfoCache cache=new BookInfoCache();
        try{
//            alldoc = Jsoup.connect("http://www.biquge.com").get();
            alldoc= Jsoup.connect("http://www.biquge.com/").data("query", "Java").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36").get();
            Elements listClass = alldoc.getElementsByAttributeValue("class", "item");
            for(Element listitem:listClass)
            {
                HashMap<String,String> book = new HashMap<String,String>();
                try {
                    link = listitem.getElementsByTag("a").attr("href");//获取书籍link
                } catch (Exception e) {
                    link = "";
                }
                try{
                    piclink=listitem.getElementsByTag("img").attr("src");//获取书籍封面link
                }catch (Exception e)
                {
                    piclink="";
                }
                try{
                    name=listitem.getElementsByTag("img").attr("alt");//获取书籍名称
                }catch (Exception e)
                {
                    name="";
                }
                try{
                    author=listitem.getElementsByTag("span").text().trim();//获取书籍作者
                }catch (Exception e)
                {
                    author="";
                }
                try{
                    info=listitem.getElementsByTag("dd").text().trim();//获取书籍简介
                }catch (Exception e)
                {
                    info="";
                }

                *//*
                *
                * 子线程爬取封面
                *
                 *//*
                picname=link.replace("/","");
//                String[] arry=picname.split("_");
//                final String finalPicName=arry[1];
                final String finalPicName=picname;
//                final String finalPiclink = piclink;
                BookInfoCache.loadImage(finalPicName,piclink);//采用Haspmap缓存图片至内存

                //边爬书籍边下载图片并保存本地

                *//*class MyPicThread implements Runnable
                {

                    @Override
                    public void run() {
                        Bitmap bitmap = null;
                        File f=new File("/data/data/com.tdkankan/temp/images/"+finalPicName+".jpg");
                        if(f.exists())
                        {
                            Log.d("pic","图片已存在");
                        }else
                        {
                            try {
                                //通过传入的图片地址，获取图片
                                HttpURLConnection connection = (HttpURLConnection) (new URL("http:"+ finalPiclink).openConnection());
                                InputStream is = connection.getInputStream();
                                bitmap = BitmapFactory.decodeStream(is);
                                if(bitmap.getByteCount()!=0)
                                {
                                    Log.d("pic","图片获取成功");
                                    BitmapUtils.writeBitmapToFile("/data/data/com.tdkankan/temp/images/","/data/data/com.tdkankan/temp/images/"+finalPicName+".jpg",bitmap,50);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                }*//*

                *//*
                *
                * 书籍信息存入HashMap
                *
                 *//*

                //new MyPicThread().run();

                book.put("name",name);
                book.put("link",link);
                book.put("author",author);
                book.put("info",info);
                book.put("finalPicName",finalPicName);
                book.put("piclink",piclink);

                Log.d("name",name);
                Log.d("link",link);
                Log.d("author",author);
                Log.d("info",info);
                Log.d("finalPicName",finalPicName);
                Log.d("piclink",piclink);

                list.add(book);

//                try{
//
//                }catch (Exception e)
//                {
//
//                }

            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return list;
    }*/


    public static ArrayList fengtui()
    {
        ArrayList<HashMap<String,String>>list;
        Document alldoc;
        String link;//书链接
        list=new ArrayList<HashMap<String, String>>();
        try{
            alldoc= Jsoup.connect("http://www.biquge.com/").data("query", "Java").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36").get();
            Elements listClass = alldoc.getElementsByAttributeValue("class", "item");
            for(Element listitem:listClass)
            {
                HashMap<String,String> book = new HashMap<String,String>();

                try {
                     link = listitem.getElementsByTag("a").attr("href");//获取书籍link
                } catch (Exception e) {
                    link = "";
                }

                String id=link.replace("/","");
                final BookInfo bookInfo= BookInfoCache.loadBook(id);

                /*
                 *
                 * 子线程爬取封面
                 *
                 */

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        BookInfoCache.loadImage(bookInfo.getPicname(),bookInfo.getPiclink());//采用Haspmap缓存图片至内存
//                    }
//                }).start();
//                BookInfoCache.loadImage(bookInfo.getPicname(),bookInfo.getPiclink());//采用Haspmap缓存图片至内存

                book.put("name",bookInfo.getName());
                book.put("author",bookInfo.getAuthor());
                book.put("link",link);
                book.put("picname",bookInfo.getPicname());
                book.put("piclink",bookInfo.getPiclink());
                book.put("info",bookInfo.getInfo());
                book.put("lasttime",bookInfo.getLasttime());
                book.put("newchapter",bookInfo.getNewchapter());
                book.put("newchapterlink",bookInfo.getNewchapterlink());

                Log.d("name",bookInfo.getName());
                Log.d("author",bookInfo.getAuthor());
                Log.d("link",link);
                Log.d("picname",bookInfo.getPicname());
                Log.d("piclink",bookInfo.getPiclink());
                Log.d("info",bookInfo.getInfo());
                Log.d("lasttime",bookInfo.getLasttime());
                Log.d("newchapter",bookInfo.getNewchapter());
                Log.d("newchapterlink",bookInfo.getNewchapterlink());
                Log.d("chapternum",bookInfo.getChapternum()+"");

                list.add(book);

            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList qiangtui()
    {
        Document alldoc;
        final ArrayList<HashMap<String,String>>list;
        list=new ArrayList<HashMap<String, String>>();
        try{
            alldoc= Jsoup.connect("http://www.biquge.com/").data("query", "Java").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36").get();
//            Elements listClass=alldoc.getElementsByAttributeValue("class","r");
            Elements listClass=alldoc.select("#hotcontent > div.r > ul:nth-child(4) > li");
            for(Element listitem:listClass) {

                final String url = listitem.getElementsByTag("a").attr("href");//获取书籍link
                final String id = url.replace("/", "");

                final BookInfo bookInfo= BookInfoCache.loadBook(id);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        BookInfoCache.loadImage(bookInfo.getPicname(),bookInfo.getPiclink());//采用Haspmap缓存图片至内存
//                    }
//                }).start();
//                BookInfoCache.loadImage(bookInfo.getPicname(),bookInfo.getPiclink());//采用Haspmap缓存图片至内存

                HashMap<String, String> book = new HashMap<String, String>();
                book.put("name",bookInfo.getName());
                book.put("author",bookInfo.getAuthor());
                book.put("link",url);
                book.put("picname",bookInfo.getPicname());
                book.put("piclink",bookInfo.getPiclink());
                book.put("info",bookInfo.getInfo());
                book.put("lasttime",bookInfo.getLasttime());
                book.put("newchapter",bookInfo.getNewchapter());
                book.put("newchapterlink",bookInfo.getNewchapterlink());

                list.add(book);

                Log.d("name",bookInfo.getName());
                Log.d("author",bookInfo.getAuthor());
                Log.d("link",url);
                Log.d("picname",bookInfo.getPicname());
                Log.d("piclink",bookInfo.getPiclink());
                Log.d("info",bookInfo.getInfo());
                Log.d("lasttime",bookInfo.getLasttime());
                Log.d("newchapter",bookInfo.getNewchapter());
                Log.d("newchapterlink",bookInfo.getNewchapterlink());
                Log.d("chapternum",bookInfo.getChapternum()+"");

            }


        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList ruku()
    {
        Document alldoc;
        ArrayList<HashMap<String,String>>list;
        list=new ArrayList<HashMap<String, String>>();
        try{
            alldoc= Jsoup.connect("http://www.biquge.com/").data("query", "Java").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36").get();
//            Elements listClass=alldoc.getElementsByAttributeValue("class","r");
            Elements listClass=alldoc.select("#newscontent > div.r > ul > li");
            for(Element listitem:listClass)
            {
                HashMap<String,String> book = new HashMap<String,String>();
                String url=listitem.getElementsByTag("a").attr("href");//获取书籍link
                String id=url.replace("/","");
                BookInfo bookInfo= BookInfoCache.loadBook(id);

                book.put("name",bookInfo.getName());
                book.put("author",bookInfo.getAuthor());
                book.put("link",url);
                book.put("picname",bookInfo.getPicname());
                book.put("piclink",bookInfo.getPiclink());
                book.put("info",bookInfo.getInfo());
                book.put("lasttime",bookInfo.getLasttime());
                book.put("newchapter",bookInfo.getNewchapter());
                book.put("newchapterlink",bookInfo.getNewchapterlink());

                list.add(book);

                Log.d("name",bookInfo.getName());
                Log.d("author",bookInfo.getAuthor());
                Log.d("link",url);
                Log.d("picname",bookInfo.getPicname());
                Log.d("piclink",bookInfo.getPiclink());
                Log.d("info",bookInfo.getInfo());
                Log.d("lasttime",bookInfo.getLasttime());
                Log.d("newchapter",bookInfo.getNewchapter());
                Log.d("newchapterlink",bookInfo.getNewchapterlink());
                Log.d("chapternum",bookInfo.getChapternum()+"");

            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return list;
    }


    public static BookInfo GetBookInfo(String url)
    {
        Document alldoc;
        String name="";    //书名
        String author="";  //作者
//        String link="";    //书链接
        String picname=""; //封面名字
        String piclink=""; //封面链接
        String info="";    //简介
        String lasttime="";    //最后更新时间
        String newchapter="";  //最新章节
        String newchapterlink="";  //最新章节链接
        int chapternum=0; //总章节
        try{
            alldoc= Jsoup.connect("https://www.biqugeu.net/"+url+"/").data("query", "Java").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36").get();
            name=alldoc.select("#info > h1").text().trim();
            author=alldoc.select("#info > p:nth-child(2)").text().trim().replace("作 者：","");
            lasttime=alldoc.select("#info > p:nth-child(4)").text().trim().replace("最后更新：","");
            newchapter=alldoc.select("#info > p:nth-child(5)").text().trim().replace("最新章节：","");
            newchapterlink=alldoc.select("#info > p:nth-child(5) > a").attr("href");
            info=alldoc.select("#intro").text().trim();
            picname=url.replace("/","");
            piclink=alldoc.getElementsByTag("img").attr("src");

            Elements listClass = alldoc.select("#list > dl > dd");
            int i=0;
            for(Element listitem:listClass)
            {
                listitem.getElementsByTag("a").attr("href");//获取书籍link
                i++;
            }
            if(i>24)
            {
                chapternum=i-12;
            }else if(i<=24&&i>0)
            {
                chapternum=i/2;
            }else
            {
                chapternum=0;
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        BookInfo book=new BookInfo(name,author,url,picname,piclink,info,lasttime,newchapter,newchapterlink,chapternum);
        return book;
    }

}
