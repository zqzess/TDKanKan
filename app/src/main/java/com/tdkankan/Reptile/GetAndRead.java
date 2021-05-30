package com.tdkankan.Reptile;

import android.content.Context;
import android.text.TextPaint;

import com.tdkankan.Cache.BookContentCache;
import com.tdkankan.Data.GlobalConfig;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ZQZESS
 * @date 1/6/2021.
 * @file GetAndRead
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class GetAndRead {
    Context mContext;

    public GetAndRead(Context mContext) {
        this.mContext = mContext;
    }
    public static ArrayList getChapter(String url, int chapternum)
    {
        Document alldoc;
//        ArrayList<HashMap<String, String>> list ;
//        list=new ArrayList<HashMap<String, String>>();
        try
        {
            alldoc = Jsoup.connect(url).data("query", "Java").
                    userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36")
                    .timeout(5000).get();

            Elements listClass = alldoc.select("#list > dl > dd> a");//trim() 删除字符串首尾空白字符
            int i=0;
            for (Element listitem:listClass)
            {
                ConcurrentHashMap<String,String > chaptermap=new ConcurrentHashMap<>();
                if(chapternum>12)
                {
                    i++;
                    if(i>=13)
                    {
                        String link=listitem.getElementsByTag("a").attr("href");//获取章节link
                        String title=listitem.getElementsByTag("a").text();//获取章节名
                        chaptermap.put("link","https://www.biqugeu.net/"+link);
                        chaptermap.put("title",title);
                        GlobalConfig.list.add(chaptermap);
                    }

                }else
                {
                    i++;
                    if(i>=chapternum)
                    {
                        String link=listitem.getElementsByTag("a").attr("href");//获取章节link
                        String title=listitem.getElementsByTag("a").text();//获取章节名
                        chaptermap.put("link",link);
                        chaptermap.put("title",title);
                        GlobalConfig.list.add(chaptermap);
                    }

                }
            }
        } catch (MalformedURLException e)
        {
//            Toast.makeText(this.mContext,"书本链接错误,获取章节爬虫执行失败",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch(IOException e)
        {
            e.printStackTrace();
        }
        return GlobalConfig.list;
    }

    public static void ReadingBackground(final int chapternow)
    {
//        final ArrayList<HashMap<String, String>> list ;
//        list=new ArrayList<HashMap<String, String>>();
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                int tmpcount=chapternow-2;
                for(int i=0;i<5;i++)
                {
                    tmpcount+=1;
                    if(tmpcount<=GlobalConfig.list.size()-1||tmpcount>=0)
                    {
                        //                    String tmpstring=GetBookContent(GlobalConfig.list.get(i).get("link"));//爬取章节内容
//                    tmpstring=splitContentFirst(tmpstring);//分段
                        try{
                            BookContentCache.getCache(GlobalConfig.list.get(tmpcount).get("link"));//检查是否存在缓存
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        thread1.start();
//        try {
//            thread1.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public static String GetBookContent(String url)
    {
        Document alldoc;
        String content="";
        try
        {
            alldoc = Jsoup.connect(url).data("query", "Java").
                    userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36")
                    .timeout(5000).get();
            content = alldoc.select("#content").text();//trim() 删除字符串首尾空白字符
//            String title = alldoc.select("#wrapper > div.content_read > div.box_con > div.bookname > h1").text().trim();
        }catch(Exception e)
        {
            e.printStackTrace();
//            Toast.makeText(mContext, "章节链接不完整或错误", Toast.LENGTH_SHORT).show();
        }
        return content;
    }

    public String splitContentFirst(String stringcontent)
    {
        /*
         *处理文章第一步，分段落
         */
        if(stringcontent==""||stringcontent==null)
        {
            stringcontent="错误！可能原因：\n1.网络错误，笔趣阁网络连接超时或您的网络错误，请刷新重试\n2.内容错误，该书不存在内容，笔趣阁网站部分书籍没有内容，请等待新书源\n3.链接错误，笔趣阁已更换该书籍内容链接，请提交反馈";
        }
        int count = stringcontent.length();
        int istart = 0;
        String tmp2=stringcontent.substring(1,2);
        String tmp=" ";
        String contentstring="";
        for(int i=1;i<=count;i++)
        {
            if(i!=count)
            {
                String nowWord=stringcontent.substring(istart,i);
                String nextWord=stringcontent.substring(i,i+1);
                if(nowWord.equals("　")&&nextWord.equals("　"))
                {
                    contentstring+="    "+"  ";
                    istart=i+1;
                }
                else if(nowWord.equals(tmp)&&nextWord.equals(tmp2))
                {
                    contentstring+="\n          \n"+" "+" ";
                    istart=i+1;
//                        mRealLine++;
                }
                else
                {
                    contentstring+=(stringcontent.substring(istart,i));
                    istart=i;
                }
            }else
            {
                contentstring+=stringcontent.substring(i-1,i);
            }
        }
        return contentstring;
    }

    public void PageSet(String content, int mPageLineNum, ConcurrentHashMap contentMap)
    {
        /*
         *单章节分页,并将内容存入Hashmap
         */
        String[] arrtmp=content.split("\n");
        String contenttmp="";
        int tmpcount=0;//单页行数
        int Pagecount=1;//单章页数
        try{
            contentMap.put(0,GlobalConfig.list.get(GlobalConfig.chapternow).get("title"));
        }catch (IndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }
        for(int i=0;i<arrtmp.length;i++)
        {
            contenttmp+=arrtmp[i]+"\n";
            tmpcount++;
            if(tmpcount==mPageLineNum)
            {
                contentMap.put(Pagecount,contenttmp);
                Pagecount++;
                tmpcount=0;
                contenttmp="";
            }
        }
        if(!contenttmp.isEmpty())
        {
            contentMap.put(Pagecount,contenttmp);
            Pagecount++;
        }
        /*
         *如果章节过短
         */
        if(Pagecount==0)
        {
            contentMap.put(1,contenttmp);
            GlobalConfig.PageTotal=1;
        }else if(Pagecount==1)
        {
            contentMap.put(1,contenttmp);
            GlobalConfig.PageTotal=2;
            Pagecount=2;
        }else
        {
            GlobalConfig.PageTotal=Pagecount;
        }
//        /*
//        *清除上一章节残留内容影响
//         */
//        if(contentMap.size()>Pagecount)
//        {
//            for(int i=Pagecount;i<=contentMap.size()-Pagecount;i++)
//            {
//                contentMap.remove(i);
//            }
//        }
    }

    public String splitcontentSecond(String content,int FontSize,int measuredWidth)
    {
        /*
         *段落添加分隔符,自动换行
         */
        String[] arrtmp=content.split("\n");
        TextPaint textPaint2 = new TextPaint ( );
        String returntmp="";
        for(int i=0;i<arrtmp.length;i++)
        {
            if(!arrtmp[i].isEmpty())
            {
                int start=0;
                for(int j=0;j<arrtmp[i].length();j++)
                {
                    textPaint2.setTextSize(FontSize);
                    float textwidth=textPaint2.measureText(arrtmp[i].substring(start,j));
                    if(textwidth>=measuredWidth-FontSize)
                    {
                        arrtmp[i]=arrtmp[i].substring(0,j)+"\n"+arrtmp[i].substring(j);
                        start=j;
                    }
                    textPaint2.reset();
                }
            }
            if(returntmp.isEmpty())
            {
                returntmp=returntmp+arrtmp[i];
            }else
            {
                returntmp=returntmp+"\n"+arrtmp[i];
            }
        }
        return returntmp;
    }
}
