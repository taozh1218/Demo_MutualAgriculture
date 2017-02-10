package com.taozhang.demo_mutualagriculture.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.taozhang.demo_mutualagriculture.R;
import com.taozhang.demo_mutualagriculture.model.News;

/**
 * 新闻详情页(首页的新闻)
 */
public class NewsDetailActivity extends AppCompatActivity {
    public static News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
    }

    /**
     * 监听返回键
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent intent = new Intent(getApplicationContext(), DrawerArrowActivity.class);
                startActivity(intent);
                NewsDetailActivity.this.finish();
                break;

            default:
                break;
        }
        return false;
    }
}
