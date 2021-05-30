package com.tdkankan.Data;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.Settings;
import android.view.WindowManager;

import com.tdkankan.greendao.model.Bookinfodb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author ZQZESS
 * @date 12/13/2020.
 * @file GlobalConfig
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class GlobalConfig {
//    public static Map<String,Picture> map=new HashMap<String,Picture>();
//    public static ConcurrentHashMap<String,Picture> map=new ConcurrentHashMap<String,Picture>();
//    public static Map<String,Book> bookmap=new HashMap<String,Book>();
    public static Map<String,BookInfo> bookmap=new ConcurrentHashMap<String,BookInfo>();
    public static ConcurrentHashMap<Integer,String> contentMap=new ConcurrentHashMap();//页对应的内容
    public static int measuredWidth=0;//控件列宽度
    public static int measuredHeigtt=0;//控件高度
    public static int screenWidth=0; // 屏幕宽
    public static int screenHeight=0; // 屏幕高
    public static int mPageLineNum = 0;// 每一页显示的行数
    public static int chapternow=0;//当前章节数
    public static Bitmap mutableBitmap;
    public static int Page=0;//单章当前所在页
    public static int PageTotal=1;//单章总页数
    public static int mFontHeight = 0;// 绘制字体高度
    public static String BookUrl="";//书籍链接
    public static int chapternum=0; //书籍总章节
    public static Bitmap bitmapnull=null;
    //    public static int sysLight=0;//系统亮度
    public static ArrayList<ConcurrentHashMap<String, String>> list =new ArrayList<ConcurrentHashMap<String, String>>();
//    public static Map<String,BookChapter> bookchapter=new HashMap<String,BookChapter>();
    public static List<Bookinfodb> booklist;

    /**
     * 获取屏幕的亮度
     */
    public static int getScreenBrightness(Context context) {
        int nowBrightnessValue = 0;
        ContentResolver resolver = context.getContentResolver();
        try {
            nowBrightnessValue = android.provider.Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nowBrightnessValue;
    }
    /**
     * 设置当前Activity显示时的亮度
     * 屏幕亮度最大数值一般为255，各款手机有所不同
     * screenBrightness 的取值范围在[0,1]之间
     */
    public static void setBrightness(Activity activity, int brightness) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
        activity.getWindow().setAttributes(lp);
        ReadConfig.appLight=brightness;
    }
    /**
     * 保存亮度设置状态，退出app也能保持设置状态
     * 修改系统亮度
     */
    public static void saveBrightness(Context context, int brightness) {
        ContentResolver resolver = context.getContentResolver();
        Uri uri = android.provider.Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
        android.provider.Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
        resolver.notifyChange(uri, null);
    }
    public static void SaveReadSetting(Context context)
    {
        SharedPreferences sp=context.getSharedPreferences(BookUrl.replace("/",""),MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt("Page",Page);
        edit.putInt("PageTotal",PageTotal);
        edit.putInt("chapternow",chapternow);
        edit.commit();
    }

    public static void GetReadSetting(Context context)
    {
        SharedPreferences sp=context.getSharedPreferences(BookUrl.replace("/",""),MODE_PRIVATE);
        Page=sp.getInt("Page",0);
        PageTotal=sp.getInt("PageTotal",1);
        chapternow=sp.getInt("chapternow",0);
    }
    public static String PicLinkCheck(String url)
    {
        String url2=url.substring(0,2);
        String url3="";
        String regEx="http.*";
        Pattern pattern= Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(url);
        boolean rs = matcher.find();
        if(url2.equals("//"))
        {
            url3="https:"+url;
            return url3;
        }else if(rs==true)
        {
            return url;
        }
        else
        {
            url3="https://www.biqugeu.net"+url;
            return url3;
        }
    }
}
