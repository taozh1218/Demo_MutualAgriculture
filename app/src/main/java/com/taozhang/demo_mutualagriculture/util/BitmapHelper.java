package com.taozhang.demo_mutualagriculture.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Description:
 * Created by taozhang on 2016/1/16.
 * Company:Geowind,University of South China.
 * ContactQQ:962076337
 *
 * @updateAuthor taozhang
 * @updateDate 2016/1/16
 */
public class BitmapHelper {

    /**
     * 下载图片的方法
     *
     * @param strurl
     *            图片地址
     * @return bitmap
     */
    public static Bitmap getBitmapByUrl(final String strurl) {

        Bitmap bitmap = null;
        URL url;
        InputStream in = null;
        try {
            url = new URL(strurl);
            URLConnection connection = url.openConnection();
            connection.connect();
            in = connection.getInputStream();
            System.out.println("----获取到图片的输入流------" + in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        bitmap = BitmapFactory.decodeStream(in);
        return bitmap;
    }

}
