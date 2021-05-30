package com.tdkankan.Data;

import android.graphics.Bitmap;

/**
 * @author ZQZESS
 * @date 12/13/2020.
 * @file Picture
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class Picture {
    String name;
    Bitmap bitmap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Picture(String name, Bitmap bitmap) {
        this.name = name;
        this.bitmap = bitmap;
    }
}
