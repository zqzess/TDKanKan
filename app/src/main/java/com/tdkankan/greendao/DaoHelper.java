package com.tdkankan.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tdkankan.greendao.green.BookinfodbDao;
import com.tdkankan.greendao.green.DaoMaster;
import com.tdkankan.greendao.green.DaoSession;
import com.tdkankan.greendao.model.Bookinfodb;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZQZESS
 * @date 2021/5/23.
 * @file DaoHelper
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class DaoHelper {
    //数据库名称
    private static final String DATABASE_NAME = "booklist.db";
    //用来获取Dao的
    private static DaoSession daoSession;
    private static  DaoHelper mDaoHelper;
    private BookinfodbDao bookinfodbDao;

    public DaoHelper(Context context) {
//        init(context);
                //使用自定义的OpenHelper的到SQLiteDatabase
        MyOpenHelper helper = new MyOpenHelper(context, DATABASE_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        bookinfodbDao=daoSession.getBookinfodbDao();
    }

//    public static void init(Context context) {
//        //使用自定义的OpenHelper的到SQLiteDatabase
//        MyOpenHelper helper = new MyOpenHelper(context, DATABASE_NAME, null);
//        SQLiteDatabase db = helper.getWritableDatabase();
//        DaoMaster daoMaster = new DaoMaster(db);
//        daoSession = daoMaster.newSession();
//    }
    public static DaoSession getDaoSession() {
        return daoSession;
    }

    //单例模式
    public static DaoHelper getInstance(Context context){
        if (mDaoHelper == null){
            synchronized (DaoHelper.class){
                if (mDaoHelper == null) {
                    mDaoHelper = new DaoHelper(context);
                }
            }
        }
        return mDaoHelper;
    }
    //插入
    public Long insert(Bookinfodb book)
    {
        return bookinfodbDao.insert(book);
    }
    public Long insertOrReplace(Bookinfodb book)
    {
        return bookinfodbDao.insertOrReplace(book);
    }
    //删除
    public void delete(Long bookid)
    {
        bookinfodbDao.queryBuilder().where(BookinfodbDao.Properties.Bookid.eq(bookid)).buildDelete().executeDeleteWithoutDetachingEntities();
    }
    //更新
    public void update(Bookinfodb book)
    {
        Bookinfodb old=bookinfodbDao.queryBuilder().where(BookinfodbDao.Properties.Bookid.eq(book.getBookid())).build().unique();
        if(old!=null)
        {
            bookinfodbDao.update(book);
        }
    }
    //查询
    public List<Bookinfodb> searchAll()
    {
        List<Bookinfodb> book=bookinfodbDao.queryBuilder().list();
        return book;
    }
    public Bookinfodb search(String link)
    {
        Bookinfodb book=bookinfodbDao.queryBuilder().where(BookinfodbDao.Properties.Link.eq(link)).build().unique();
        return book;
    }
}
