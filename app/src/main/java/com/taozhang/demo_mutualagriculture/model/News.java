package com.taozhang.demo_mutualagriculture.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Description:新闻结构体，包含了获取的方法
 * Created by taozhang on 2016/1/15.
 * Company:Geowind,University of South China.
 * ContactQQ:962076337
 *
 * @updateAuthor taozhang
 * @updateDate 2016/1/15
 */
public class News {
    public String title;
    /**作者的用户名*/
    public String author;
    public String time;
    public String text;//正文
    /** 缩略图片地址（小图），没有时不返回此字段 */
    public String thumbnail_pic;
    /** 中等尺寸图片地址（中图），没有时不返回此字段 */
    public String bmiddle_pic;
    /** 原始图片地址（原图），没有时不返回此字段 */
    public String original_pic;
    /** 微博作者的用户信息字段 */
    public User mUser;
    /** 微博配图地址。多图时返回多图链接。无配图返回"[]" */
    public ArrayList<String> pic_urls;

    public static News parse(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return News.parse(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static News parse(JSONObject jsonObject) {
        if (null == jsonObject) {
            return null;
        }

        News news = new News();
        news.title       = jsonObject.optString("title");
        news.author      = jsonObject.optString("username");
        news.time        = jsonObject.optString("time");
        news.text = jsonObject.optString("text");

        // Have NOT supported
        news.thumbnail_pic    = jsonObject.optString("thumbnail_pic");
        news.bmiddle_pic      = jsonObject.optString("bmiddle_pic");
        news.original_pic     = jsonObject.optString("original_pic");
        news.mUser = User.parse(jsonObject.optJSONObject("mUser"));

        JSONArray picUrlsArray = jsonObject.optJSONArray("pic_urls");
        if (picUrlsArray != null && picUrlsArray.length() > 0) {
            int length = picUrlsArray.length();
            news.pic_urls = new ArrayList<String>(length);
            JSONObject tmpObject = null;
            for (int ix = 0; ix < length; ix++) {
                tmpObject = picUrlsArray.optJSONObject(ix);
                if (tmpObject != null) {
                    news.pic_urls.add(tmpObject.optString("thumbnail_pic"));
                }
            }
        }

        return news;
    }
}
