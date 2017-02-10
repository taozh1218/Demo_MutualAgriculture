package com.taozhang.demo_mutualagriculture.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.taozhang.demo_mutualagriculture.R;
import com.taozhang.demo_mutualagriculture.model.News;
import com.taozhang.demo_mutualagriculture.model.User;

import java.util.ArrayList;

/**
 * Description:
 * Created by taozhang on 2016/1/15.
 * Company:Geowind,University of South China.
 * ContactQQ:962076337
 *
 * @updateAuthor taozhang
 * @updateDate 2016/1/15
 */
public class NewsAdapter extends BaseAdapter {
    Context context = null;
    Activity activity = null;
    /** 新闻队列 */
    public ArrayList<News> arrayList_news = new ArrayList<News>();
    private News mNews;
    private User mUser;

    public NewsAdapter(Context applicationContext, Activity activity) {
        this.context = applicationContext;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        if (arrayList_news != null && arrayList_news.size() > 0) {
            return arrayList_news.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (arrayList_news != null && arrayList_news.size() > 0) {
            return arrayList_news.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("----NewsAdapter getView()--position:" + position);
        //获取信息
        mNews = arrayList_news.get(position);
        mUser = mNews.mUser;//作者信息
        ViewHolder holder = null;
        if(holder==null){
            //反射出item
            holder = new ViewHolder();
            convertView = activity.getLayoutInflater().inflate(R.layout.list_item_homenews,null);//convertView就是listItem
            holder.title = (TextView) convertView.findViewById(R.id.tv_lvitem_homenews_title);
            holder.author = (TextView) convertView.findViewById(R.id.tv_lvitem_homenews_author);
            holder.time = (TextView) convertView.findViewById(R.id.tv_lvitem_homenews_timeAndWhere);
            holder.figure = (ImageView) convertView.findViewById(R.id.img_lvitem_homenews_figure);
            holder.text = (TextView) convertView.findViewById(R.id.tv_lvitem_homenews_text);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        //设置内容
        holder.title.setText(mNews.title);
        holder.author.setText(mNews.author);
        holder.time.setText(mNews.time);
        holder.figure.setTag(mUser.profile_image_url);//暂时用户头像设置为把配图
       //TODO:开启线程下载图片（url）   holder.figure.setImageResource(R.drawable.avatar_default);//暂时用户头像设置为把配图
//        new ImageLoader().showImageByAsyncTask(holder.figure, mUser.profile_image_url);//把头像重绘成圆形
        holder.text.setText(mNews.text);
        return convertView;
    }




    static class ViewHolder {
        /** 标题 */
        private TextView title;
        /** 作者 */
        private TextView author;
        /** 时间地点 */
        private TextView time;
        /** 配图 */
        private ImageView figure;
        /** 微博正文 */
        private TextView text;
        /** 微博略缩图片 */
//        private ImageView thumbnail_pic;
    }

}
