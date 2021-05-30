package com.tdkankan.Data;

/**
 * @author ZQZESS
 * @date 12/13/2020.
 * @file BookInfo
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class BookInfo {
    String name;    //书名
    String author;  //作者
    String link;    //书链接
    String picname; //封面名字
    String piclink; //封面链接
    String info;    //简介
    String lasttime;    //最后更新时间
    String newchapter;  //最新章节
    String newchapterlink;  //最新章节链接
    int chapternum; //总章节
    String linkfrom;    //书源

    public BookInfo(String name, String author, String link, String picname, String piclink, String info, String lasttime, String newchapter, String newchapterlink, int chapternum) {
        this.name = name;
        this.author = author;
        this.link = link;
        this.picname = picname;
        this.piclink = piclink;
        this.info = info;
        this.lasttime = lasttime;
        this.newchapter = newchapter;
        this.newchapterlink = newchapterlink;
        this.chapternum = chapternum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPicname() {
        return picname;
    }

    public void setPicname(String picname) {
        this.picname = picname;
    }

    public String getPiclink() {
        return piclink;
    }

    public void setPiclink(String piclink) {
        this.piclink = piclink;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public String getNewchapter() {
        return newchapter;
    }

    public void setNewchapter(String newchapter) {
        this.newchapter = newchapter;
    }

    public int getChapternum() {
        return chapternum;
    }

    public void setChapternum(int chapternum) {
        this.chapternum = chapternum;
    }

    public String getNewchapterlink() {
        return newchapterlink;
    }

    public void setNewchapterlink(String newchapterlink) {
        this.newchapterlink = newchapterlink;
    }
}
