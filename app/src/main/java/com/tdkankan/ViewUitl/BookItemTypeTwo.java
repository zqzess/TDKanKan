package com.tdkankan.ViewUitl;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdkankan.Cache.ImageCacheManager;
import com.tdkankan.MyApplication;
import com.tdkankan.R;

/**
 * @author ZQZESS
 * @date 1/7/2021.
 * @file BookItemTypeTwo
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class BookItemTypeTwo extends RelativeLayout {
    private BookItemTypeTwo.MyItemClicked myItemClicked;
    private RelativeLayout relativeLayout1;// 容器包含项目
    private TextView tv_name;// 书名
    private TextView tv_info;// 简介
    private TextView tv_author;// 作者
    public ImageView img_pic;//封面

    private String name;
    private String info;
    private String author;
    private Bitmap pic;

    //    private Boolean isSelected = false;// 是否被选中
    public BookItemTypeTwo(Context context) {
        super(context);
    }

    public BookItemTypeTwo(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.bookitem2,this);
        relativeLayout1 = (RelativeLayout) findViewById(R.id.relativeLayout2);
        tv_name=(TextView)findViewById(R.id.tv_bookitem_fengtui_name_2);
        tv_info=(TextView)findViewById(R.id.tv_bookitem_fengtui_info_2);
        tv_author=(TextView)findViewById(R.id.tv_bookitem_fengtui_author_2);
        img_pic=(ImageView)findViewById(R.id.img_bookitem_fengtui_2);
        relativeLayout1.setOnClickListener(new BookItemTypeTwo.MyOnClick());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        tv_name.setText(name);
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
        tv_info.setText(info);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
        tv_author.setText(author);
    }

    public Bitmap getPic() {
        return pic;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
        img_pic.setImageBitmap(pic);
    }

    private class MyOnClick implements OnClickListener {

        @Override
        public void onClick(View v) {
		/*	if (!isSelected) {
				relativeLayout1.setBackgroundResource(R.drawable.selectedborder);
				myItemClicked.myItemClicked();
			} else {
				relativeLayout1.setBackgroundResource(R.drawable.border);
				myItemClicked.myItemClicked();
			}*/
            myItemClicked.myItemClicked();
        }
    }
    public interface MyItemClicked {
        public void myItemClicked();
    }

    public void setMyItemClickedListener(BookItemTypeTwo.MyItemClicked myItemClicked) {
        this.myItemClicked = myItemClicked;
    }
    private Bitmap getBitmapFromRes(int resId) {
        Resources res = MyApplication.myApplication.getResources();
        return BitmapFactory.decodeResource(res, resId);

    }
}
