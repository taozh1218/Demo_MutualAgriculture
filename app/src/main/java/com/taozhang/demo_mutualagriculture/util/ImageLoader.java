package com.taozhang.demo_mutualagriculture.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @description: 异步获取图片并且把图片变圆
 *
 * @author taoZhang
 * @created 2015-12-5 下午3:27:29
 *
 */
public class ImageLoader {
    //圆形头像
    private ImageView nImageView;
    //微博图片
    private ImageView contentView;
    //输入流
    InputStream inputStream;
    //头像的地址
    private String murl;

    public Bitmap getBitmapFromURL(String urlString){

        Bitmap bitmap;

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            inputStream = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(inputStream);
            connection.disconnect();

            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void showImageByAsyncTask(ImageView imageView,String url){
        new NewsAsyncTask(imageView,url).execute(url);
    }

    //    public void showContentImageByAsyncTask(ImageView imageView,String url){
    //    	new NewsAsyncTask(imageView,url).execute(url);
    //    }


    /**
     * 将图片变圆
     * @param bitmap
     * @param ratio
     * @return
     */
    public Bitmap toRoundCorner(Bitmap bitmap, float ratio) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, bitmap.getWidth() / ratio,
                bitmap.getHeight() / ratio, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //		System.out.println("pixels+++++++" + String.valueOf(ratio));
        return output;

    }

    /**
     *
     * @description: 异步下载
     *
     * @author taoZhang
     * @created 2015-12-5 下午3:28:43
     *
     */
    private class NewsAsyncTask extends AsyncTask<String,Void,Bitmap>{

        //圆形头像
        private ImageView mImageView;
        //微博图片
        private ImageView contentImageView;
        //图片地址
        private String mUrl;

        public NewsAsyncTask(ImageView imageView,String url){
            mImageView = imageView;
            mUrl = url;
        }
        //        public NewsAsyncTask(ImageView imageView,String url){
        //        	contentImageView = imageView;
        //        	mUrl = url;
        //        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return getBitmapFromURL(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (mImageView.getTag().equals(mUrl)){
                Bitmap bitmap2 = toRoundCorner(bitmap,2);
                mImageView.setImageBitmap(bitmap2);
            }
            //            	else if(contentImageView.getTag()!=null){
            //            	if (contentImageView.getTag().equals(mUrl)){
            //            		contentImageView.setImageBitmap(bitmap);
            //                }
            //            }
        }
    }

}
