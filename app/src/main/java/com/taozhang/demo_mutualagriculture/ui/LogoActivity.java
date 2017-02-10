package com.taozhang.demo_mutualagriculture.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.taozhang.demo_mutualagriculture.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with mUser interaction.
 */
public class LogoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_logo);
        init();
    }


    /**
     * 初始化
     */
    public void init(){
//        SMSSDK.initSDK(this, "ea8331bec160", "b108e3dba9bbf7a46c8a7a4a1b5f7685");

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);//alpha 透明度
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                System.out.println("动画结束---");
                //跳到登录activity
                Intent intent = new Intent(getApplicationContext(), DrawerArrowActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        TextView tv_logoAct_logo = (TextView) findViewById(R.id.tv_logoAct_logo);//微博标记
        tv_logoAct_logo.startAnimation(alphaAnimation);//把动画添加到组件上
        alphaAnimation.setDuration(3000);//动画延时
        alphaAnimation.start();//动画启动
    }

}
