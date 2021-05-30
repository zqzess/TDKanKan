package com.tdkankan.Data;

import android.graphics.Bitmap;

/**
 * @author ZQZESS
 * @date 12/13/2020.
 * @file Book
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class Book {
    String name;
    BookInfo bookInfo;

    public Book(String name, BookInfo bookInfo) {
        this.name = name;
        this.bookInfo = bookInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BookInfo getBookInfo() {
        return bookInfo;
    }

    public void setBookInfo(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
    }
}
