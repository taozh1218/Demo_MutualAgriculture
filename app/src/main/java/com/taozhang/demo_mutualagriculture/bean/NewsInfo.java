package com.taozhang.demo_mutualagriculture.bean;

import java.util.ArrayList;

/**
 * Description:news 实体类
 * Created by taozhang on 2016/1/15.
 * Company:Geowind,University of South China.
 * ContactQQ:962076337
 *
 * @updateAuthor taozhang
 * @updateDate 2016/1/15
 */
public class NewsInfo {
    private String title;
    /**作者的用户名*/
    private String author;
    private String time;
    private String text;
//    /**图片的url*/
//    private String url;
    /** 微博配图地址。多图时返回多图链接。无配图返回"[]" */
    public ArrayList<String> pic_urls;


    /**
     * 无配图的新闻
     * @param title
     * @param author
     * @param time
     * @param text
     */
    public NewsInfo(String title, String author, String time, String text) {
        this.title = title;
        this.author = author;
        this.time = time;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getTime() {
        return time;
    }

    public String getText() {
        return text;
    }


    public ArrayList<String> getPic_urls() {
        return pic_urls;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPic_urls(ArrayList<String> pic_urls) {
        this.pic_urls = pic_urls;
    }

    @Override
    public String toString() {
        return "NewsInfo{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", time='" + time + '\'' +
                ", text='" + text + '\'' +
                ", pic_urls=" + pic_urls +
                '}';
    }
}
