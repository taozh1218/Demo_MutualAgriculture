package com.taozhang.demo_mutualagriculture.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.taozhang.demo_mutualagriculture.R;

import java.util.ArrayList;

/**
 * 提交订单的九宫格的适配器
 */
public class ApplyOrderGridImgsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Uri> datas;
    private GridView mGridView;

    public ApplyOrderGridImgsAdapter(Context context, ArrayList<Uri> datas, GridView gridView) {
        this.context = context;
        this.datas = datas;
        this.mGridView = gridView;
    }

    @Override
    public int getCount() {
        return datas.size() > 0 ? datas.size() + 1 : 0;//末尾位置多了+号按钮
        //        return datas.size();
    }

    @Override
    public Uri getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("NewApi")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_grid_image, null);
            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            holder.iv_delete_image = (ImageView) convertView.findViewById(R.id.iv_delete_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //        GridView mGridView = (GridView) parent;
        int horizontalSpacing = mGridView.getHorizontalSpacing();
        int width = (mGridView.getWidth() - horizontalSpacing * 2 - mGridView.getPaddingLeft() - mGridView.getPaddingRight()) / 3;

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, width);
        holder.iv_image.setLayoutParams(params);

        //        int numColumns = mGridView.getNumColumns();
        //        int itemWidth = (mGridView.getWidth() - (numColumns - 1) * horizontalSpacing
        //                - mGridView.getPaddingLeft() - mGridView.getPaddingRight()) / numColumns;
        //
        //        LayoutParams params = new LayoutParams(itemWidth, itemWidth);
        //        holder.iv_image.setLayoutParams(params);

        //        PicUrls urls = getItem(position);
        //        imageLoader.displayImage(urls.getThumbnail_pic(), holder.iv_image);

        //显示
        if (position < getCount() - 1) {//有图片
            //set data
            Uri item = getItem(position);
            holder.iv_image.setImageURI(item);
            holder.iv_delete_image.setVisibility(View.VISIBLE);
            holder.iv_delete_image.setId(position);
            holder.iv_delete_image.setOnClickListener(mOnClickListener);

        } else {
            holder.iv_image.setImageResource(R.drawable.compose_pic_add_more);
            holder.iv_delete_image.setVisibility(View.GONE);
        }

        return convertView;
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            datas.remove(id);
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder {
        public ImageView iv_image;
        public ImageView iv_delete_image;
    }

}
