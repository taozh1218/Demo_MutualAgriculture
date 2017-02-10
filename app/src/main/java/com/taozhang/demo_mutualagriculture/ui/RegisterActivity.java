package com.taozhang.demo_mutualagriculture.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.taozhang.demo_mutualagriculture.R;
import com.taozhang.demo_mutualagriculture.ui.fragment.Fragment_reg1;
import com.taozhang.demo_mutualagriculture.ui.fragment.Fragment_reg2;
import com.taozhang.demo_mutualagriculture.ui.fragment.Fragment_reg3;

import cn.smssdk.SMSSDK;

/**
 * 注册界面
 *
 * @author taozhang
 */
public class RegisterActivity extends AppCompatActivity implements Fragment_reg1.OnFragmentInteractionListener, Fragment_reg2.OnFragmentInteractionListener, Fragment_reg3.OnFragmentInteractionListener {


    private EditText mEdt_reg2_code;
    private Button mBtn_reg2_commitCheckCode;
    private EditText mEdt_reg3_psw;
    private EditText mEdt_reg3_confirmPsw;
    private Button mBtn_reg3_commit;

    private boolean checked = true;

    private ImageView mImg_reg_back;
    private Fragment_reg1 mFragment_reg1;
    /**
     * 记录当前的fragment
     */
    private Fragment current_frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.taozhang.demo_mutualagriculture.R.layout.activity_register);
        init();
    }

    private void init() {
        //初始化短信验证码
        SMSSDK.initSDK(this, "ea8331bec160", "b108e3dba9bbf7a46c8a7a4a1b5f7685");

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //获取组件
        mImg_reg_back = (ImageView) findViewById(R.id.img_reg_back);//返回的img
        mImg_reg_back.setOnClickListener(mListener);

        mFragment_reg1 = new Fragment_reg1();
        mFragment_reg1.mContext = this;
        //        System.out.println("----创建完fragment");
        current_frag = mFragment_reg1;
        fragmentTransaction.add(R.id.frameLayout_RegAct, current_frag);
        fragmentTransaction.commit();
        //        System.out.println("-----添加完frag");

        //        Fragment_reg3 fragment_reg3 = Fragment_reg3.newInstance("18187204246");
        //        fragmentTransaction.add(R.id.frameLayout_RegAct, fragment_reg3);
        //        fragmentTransaction.commit();
        //        Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();

        //        //获取组件
        //        mEdt_reg2_code = (EditText) findViewById(R.id.edt_reg2_code);//验证码
        //        mBtn_reg2_commitCheckCode = (Button) findViewById(R.id.btn_reg2_commitCheckCode);//提交验证码
        //        mEdt_reg3_psw = (EditText) findViewById(R.id.edt_reg3_psw);
        //        mEdt_reg3_confirmPsw = (EditText) findViewById(R.id.edt_reg3_confirmPsw);
        //        mBtn_reg3_commit = (Button) findViewById(R.id.btn_reg3_commit);
        //
        //        String s = mTv_reg1_protocol.getText().toString();
        //        mTv_reg1_protocol.setText(Html.fromHtml("<a href=\"http://www.baidu.com\">"+s+"</a>"));
        //
        //
        //        //添加监听器
        //        mBtn_reg1_getCheckCode.setOnClickListener(mListener);
        //        mCheckbox_reg1.setOnClickListener(mListener);
        //        mTv_reg1_protocol.setOnClickListener(mListener);
        //        mBtn_reg2_commitCheckCode.setOnClickListener(mListener);
        //        mBtn_reg3_commit.setOnClickListener(mListener);

    }

    //监听器
    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            Intent intent;
            switch (id) {
                case R.id.img_reg_back:
                    //返回到登录界面
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    @Override
    public void onFragmentInteraction(Uri uri) {
        System.out.println("RegisterActivity-----------onFragmentInteraction()");

    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
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
            case KeyEvent.KEYCODE_BACK://返回键
                //                AlertDialog.Builder build = new AlertDialog.Builder(this);
                //                build.setTitle("注意")
                //                        .setMessage("确定要退出吗？")
                //                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                //
                //                            @Override
                //                            public void onClick(DialogInterface dialog, int which) {
                //                                //1.Dalvik VM的本地方法
                //                                android.os.Process.killProcess(android.os.Process.myPid());    //获取PID
                //                                System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出
                //                            }
                //                        })
                //                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                //
                //                            @Override
                //                            public void onClick(DialogInterface dialog, int which) {
                //                                // TODO Auto-generated method stub
                //                            }
                //                        })
                //                        .show();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                RegisterActivity.this.finish();
                break;

            default:
                break;
        }
        return false;
    }

    //    public FragmentTransaction getFragmentTransaction(){
    //        return FragmentTransaction;
    //    }
}
