package com.taozhang.demo_mutualagriculture.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.taozhang.demo_mutualagriculture.R;

/**
 * 历史订单详情
 */
public class OrderDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent intent = new Intent(getApplicationContext(), HistoryOrderActivity.class);
                startActivity(intent);
                OrderDetailActivity.this.finish();
                break;

            default:
                break;
        }
        return false;
//        return super.onKeyDown(keyCode, event);
    }
}
