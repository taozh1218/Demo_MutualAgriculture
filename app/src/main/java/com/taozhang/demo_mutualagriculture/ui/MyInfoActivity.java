package com.taozhang.demo_mutualagriculture.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.taozhang.demo_mutualagriculture.R;

/**
 * 个人信息界面
 */
public class MyInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
    }

    /**
     * 监听返回键
     *
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
                MyInfoActivity.this.finish();
                break;

            default:
                break;
        }
        return false;
    }
}
