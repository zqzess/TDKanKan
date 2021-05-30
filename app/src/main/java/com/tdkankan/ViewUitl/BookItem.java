package com.tdkankan.ViewUitl;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdkankan.Cache.ImageCacheManager;
import com.tdkankan.R;

/**
 * @author ZQZESS
 * @date 12/9/2020.
 * @file BookItem
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class BookItem extends RelativeLayout {
    private MyItemClicked myItemClicked;
    private RelativeLayout relativeLayout1;// 容器包含项目
    private TextView tv_name;// 书名
    private TextView tv_info;// 简介
    private TextView tv_author;// 作者
    private ImageView img_pic;//封面

    private String name;
    private String info;
    private String author;
    private String pic;


    //    private Boolean isSelected = false;// 是否被选中
    public BookItem(Context context) {
        super(context);
    }

    public BookItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.bookitem,this);
        relativeLayout1 = (RelativeLayout) findViewById(R.id.relativeLayout1);
        tv_name=(TextView)findViewById(R.id.tv_bookitem_fengtui_name_1);
        tv_info=(TextView)findViewById(R.id.tv_bookitem_fengtui_info_1);
        tv_author=(TextView)findViewById(R.id.tv_bookitem_fengtui_author_1);
        img_pic=(ImageView)findViewById(R.id.img_bookitem_fengtui_1);
        relativeLayout1.setOnClickListener(new MyOnClick());
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic,Bitmap bitmap) {
        this.pic = pic;
        ImageCacheManager.loadImage(pic,img_pic,bitmap,bitmap);
//        img_pic.setImageBitmap(pic);
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

    public void setMyItemClickedListener(MyItemClicked myItemClicked) {
        this.myItemClicked = myItemClicked;
    }
}
