package com.tdkankan.greendao.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author ZQZESS
 * @date 2021/5/23.
 * @file BookinfoDao
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
@Entity
public class Bookinfodb {
    @Id(autoincrement = true)
    Long bookid;//数据库书籍id

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
    @Generated(hash = 1233158695)
    public Bookinfodb(Long bookid, String name, String author, String link,
            String piclink, String info, String lasttime, String newchapter,
            String newchapterlink, int chapternum, String linkfrom) {
        this.bookid = bookid;
        this.name = name;
        this.author = author;
        this.link = link;
        this.piclink = piclink;
        this.info = info;
        this.lasttime = lasttime;
        this.newchapter = newchapter;
        this.newchapterlink = newchapterlink;
        this.chapternum = chapternum;
        this.linkfrom = linkfrom;
    }
    @Generated(hash = 61057257)
    public Bookinfodb() {
    }
    public Long getBookid() {
        return this.bookid;
    }
    public void setBookid(Long bookid) {
        this.bookid = bookid;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return this.author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getLink() {
        return this.link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getPiclink() {
        return this.piclink;
    }
    public void setPiclink(String piclink) {
        this.piclink = piclink;
    }
    public String getInfo() {
        return this.info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public String getLasttime() {
        return this.lasttime;
    }
    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }
    public String getNewchapter() {
        return this.newchapter;
    }
    public void setNewchapter(String newchapter) {
        this.newchapter = newchapter;
    }
    public String getNewchapterlink() {
        return this.newchapterlink;
    }
    public void setNewchapterlink(String newchapterlink) {
        this.newchapterlink = newchapterlink;
    }
    public int getChapternum() {
        return this.chapternum;
    }
    public void setChapternum(int chapternum) {
        this.chapternum = chapternum;
    }
    public String getLinkfrom() {
        return this.linkfrom;
    }
    public void setLinkfrom(String linkfrom) {
        this.linkfrom = linkfrom;
    }

    
}
