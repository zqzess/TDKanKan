package com.tdkankan.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.dialog.dialog.LoadingDialog;
import com.tdkankan.Cache.BookInfoCache;
import com.tdkankan.Cache.ImageCacheManager;
import com.tdkankan.Data.BookInfo;
import com.tdkankan.Data.GlobalConfig;
import com.tdkankan.R;
import com.tdkankan.Reptile.GetBook;
import com.tdkankan.greendao.DaoHelper;
import com.tdkankan.greendao.green.DaoMaster;
import com.tdkankan.greendao.model.Bookinfodb;

/**
 * @author ZQZESS
 * @date 12/9/2020-9:21 PM
 * @file BookInfoDetailActivity.java
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class BookInfoDetailActivity extends AppCompatActivity {
    TextView tv_title;
    TextView tv_name;
    TextView tv_info;
    TextView tv_lasttime;
    TextView tv_newchapter;
    TextView tv_author;
    ImageView img_pic;
    Button btn_add;
    Button btn_read;
    String title;   //书名
    String info;    //简介
    String author;  //作者
    String picname; //封面id或书本id，例如6_506
    String piclink; //封面链接
    String link;    //书籍简链接，例如/6_506/
    String newchapter;  //最新章节名
    String lasttime;    //最后更新时间
    int chapternum; //总章节
    BookInfo bookInfo;
    LoadingDialog loadingDialog;
    DaoHelper mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookinfodetail);
        getSupportActionBar().hide();

        findId();
//        initView();
        loadingDialog=new LoadingDialog(this);
        new GetDataTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        mDb=DaoHelper.getInstance(BookInfoDetailActivity.this);
        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookInfoDetailActivity.this, ReadingActivity.class);
                intent.putExtra("link", link);
                intent.putExtra("chapternum",chapternum);
                startActivity(intent);
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Long bookid;
                String name;    //书名
                String author;  //作者
                String link;    //书链接
                String piclink; //封面链接
                String info;    //简介
                String lasttime;    //最后更新时间
                String newchapter;  //最新章节
                String newchapterlink;  //最新章节链接
                int chapternum; //总章节
                String linkfrom;    //书源
                 */
//                String name=BookInfoCache.loadBook(link).getName();
                if(btn_add.getText().toString().equals("加入书架"))
                {//加入书架
                    Bookinfodb book=new Bookinfodb(null,title,author,link,piclink,info,lasttime,newchapter,"",chapternum,"biquge");
                    mDb.insertOrReplace(book);
                    if(mDb.search(link)!=null)
                    {
                        Toast.makeText(BookInfoDetailActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                        btn_add.setText("移出书架");
                    }
                }else
                {//移出书架

                }
            }
        });
    }

    private  void GetData()
    {
        Intent intent = getIntent();
        title = intent.getStringExtra("name");
        info = intent.getStringExtra("info");
        link = intent.getStringExtra("link");
        author = intent.getStringExtra("author");
        picname = intent.getStringExtra("picname");
        piclink = intent.getStringExtra("piclink");
        piclink = GlobalConfig.PicLinkCheck(piclink);
        preInitBookInfo(picname);
        newchapter = bookInfo.getNewchapter();
        lasttime = bookInfo.getLasttime();
        chapternum=bookInfo.getChapternum();
        if(mDb.search(link)!=null)
        {
            btn_add.setText("移出书架");
        }
    }
    private void initView() {

//        Bitmap bitmap= BookInfoCache.loadImage(picname,piclink);

        tv_title.setText(title);
        tv_name.setText(title);
        tv_info.setText(info);
        tv_author.setText(author);
//        img_pic.setImageBitmap(bitmap);
        tv_lasttime.setText(lasttime);
        tv_newchapter.setText(newchapter);
    }

    private void findId() {
        tv_title = findViewById(R.id.tv_bookinfo_detail_title);
        tv_name = findViewById(R.id.tv_bookinfo_detail_name);
        tv_info = findViewById(R.id.tv_bookinfo_detail_info);
        tv_lasttime = findViewById(R.id.tv_bookinfo_detail_lasttime);
        tv_newchapter = findViewById(R.id.tv_bookinfo_detail_newchapter);
        tv_author = findViewById(R.id.tv_bookinfo_detail_author);
        img_pic = findViewById(R.id.img_bookinfo_detail_1);
        btn_add = findViewById(R.id.btn_bookinfo_detail_add);
        btn_read = findViewById(R.id.btn_bookinfo_detail_read);
    }

    private Bitmap getBitmapFromRes(int resId) {
        Resources res = this.getResources();
        return BitmapFactory.decodeResource(res, resId);
    }

    private void preInitBookInfo(String url) {
        bookInfo = BookInfoCache.loadBook(url);
    }
    private class GetDataTask extends AsyncTask<Void,Integer,Boolean>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog.setMessage("加载中...");
            loadingDialog.setCancelable(true); // 是否可以按“返回键”消失
            loadingDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
            loadingDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            GetData();
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            loadingDialog.dismiss();
            initView();
            ImageCacheManager.loadImage(piclink, img_pic, getBitmapFromRes(R.drawable.nonepic), getBitmapFromRes(R.drawable.nonepic));
        }
    }
}
