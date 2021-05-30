package com.tdkankan.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tdkankan.greendao.green.DaoMaster;

/**
 * @author ZQZESS
 * @date 2021/5/23.
 * @file MyOpenHelper
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class MyOpenHelper extends DaoMaster.OpenHelper {
    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    /**
     * 更新数据库
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion){
            case 1:
                break;
        }
    }
}
