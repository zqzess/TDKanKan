package com.tdkankan.Data;

import android.content.Context;
import android.content.SharedPreferences;

import com.tdkankan.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author ZQZESS
 * @date 1/6/2021.
 * @file ReadConfig
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class ReadConfig {
    public static int FontSize;      //字体
    public static int bgColor;     //背景色
    public static int fontColor;      //字体颜色
    public static Boolean isDownload;     //是否缓存本书
    public static Boolean isDark;     //是否夜间模式打开
    public static Boolean isSystemLight;      //是否系统亮度
    public static Boolean isFavourite;        //是否加入书架
    public static boolean isSydLight;     //是否系统亮度
    public static int appLight;       //app亮度

    public static void SaveSetting(Context context)
    {
        SharedPreferences sp=context.getSharedPreferences("setting",MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt("Fontsize",FontSize);
        edit.putInt("bgColor",bgColor);
        edit.putInt("fontcolor",fontColor);
        edit.putBoolean("isDownload",isDownload);
        edit.putBoolean("isDark",isDark);
        edit.putBoolean("isSystemLight",isSystemLight);
        edit.putBoolean("isFavourite",isFavourite);
        edit.putBoolean("isSydLight",isSydLight);
        edit.putInt("appLight",appLight);
        edit.commit();
    }

    public static void ReadSetting(Context context)
    {
        SharedPreferences sp=context.getSharedPreferences("setting",MODE_PRIVATE);
        FontSize=sp.getInt("Fontsize",62);
        bgColor=sp.getInt("bgColor",R.color.default_read_color);
        fontColor=sp.getInt("fontcolor", R.color.default_font_color);
        isDownload=sp.getBoolean("isDownload",false);
        isDark=sp.getBoolean("isDark",false);
        isSystemLight=sp.getBoolean("isSystemLight",false);
        isFavourite=sp.getBoolean("isFavourite",false);
        isSydLight=sp.getBoolean("isSydLight",false);
        appLight=sp.getInt("appLight",0);
    }
}
