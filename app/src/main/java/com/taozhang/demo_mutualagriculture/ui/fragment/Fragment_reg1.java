package com.taozhang.demo_mutualagriculture.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.taozhang.demo_mutualagriculture.R;
import com.taozhang.demo_mutualagriculture.util.Util_checkPhoneNum;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_reg1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_reg1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_reg1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private static Fragment_reg1 frag;
    private View mView_frag_reg1;//frag布局
    private EditText mEdt_reg1_phoneNum;
    private Button mBtn_reg1_getCheckCode;
    private CheckBox mCheckbox_reg1;
    private TextView mTv_reg1_protocol;
    private boolean checked = true;
    public Context mContext;
    /**
     * 电话号码
     */
    private String mPhoneNum;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_reg1.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_reg1 newInstance(String param1, String param2) {
        Fragment_reg1 fragment = new Fragment_reg1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public Fragment_reg1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //registerEventHandler用来往SMSSDK中注册一个事件接收器，SMSSDK允许开发者注册任意数量的接收器，所有接收器都会在事件 被触发时收到消息。
        SMSSDK.registerEventHandler(eh); //注册短信回调
        System.out.println("frag_reg1----onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("frag_reg1----onCreateView()");
        // Inflate the layout for this fragment
        mView_frag_reg1 = inflater.inflate(R.layout.frag_reg1, container, false);
        //只有在获取到frag的布局之后，才能其上的获取组件
        init();
        return mView_frag_reg1;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        System.out.println("frag_reg1----onAttach()");
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        System.out.println("frag_reg1----onDetach()");
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        System.out.println("register1-----onDestroy()");
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    /**
     * 单例模式获取frag对象
     *
     * @return Fragment_reg1
     */
    public static Fragment_reg1 getInstance() {
        if (frag == null) {
            frag = new Fragment_reg1();
        }
        return frag;
    }

    private void init() {
        //获取组件
        mEdt_reg1_phoneNum = (EditText) mView_frag_reg1.findViewById(R.id.edt_reg1_phoneNum);
        mBtn_reg1_getCheckCode = (Button) mView_frag_reg1.findViewById(R.id.btn_reg1_getCheckCode);//reg1获取验证码按钮
        mCheckbox_reg1 = (CheckBox) mView_frag_reg1.findViewById(R.id.checkbox_reg1);//reg1 checkBox
        mTv_reg1_protocol = (TextView) mView_frag_reg1.findViewById(R.id.tv_reg1_protocol);//reg1 协议标签
        String s = mTv_reg1_protocol.getText().toString();
        mTv_reg1_protocol.setText(Html.fromHtml("<u>" + s + "</u>"));

        //添加监听
        mBtn_reg1_getCheckCode.setOnClickListener(mOnClickListener);
        mCheckbox_reg1.setOnClickListener(mOnClickListener);
        mCheckbox_reg1.setOnCheckedChangeListener(mOnCheckedChangeListener);//CheckBox
        mTv_reg1_protocol.setOnClickListener(mOnClickListener);

    }


    //监听器
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {


        @Override
        public void onClick(View v) {
            int id = v.getId();
            Intent intent;
            switch (id) {
                case R.id.btn_reg1_getCheckCode:
                    //只有当checkBox选中时，才能获取验证码
                    //1.发送验证码
                    //2.换布局文件或者换Activity——》改成切换fragment
                    if (mCheckbox_reg1.isChecked()) {
                        //获取手机号
                        mPhoneNum = mEdt_reg1_phoneNum.getText().toString().trim();
                        System.out.println("输入的手机号：" + mPhoneNum);
                        if (Util_checkPhoneNum.isMobileNO(mPhoneNum) || Util_checkPhoneNum.isEmail(mPhoneNum)) {
                            SMSSDK.getVerificationCode("86", mPhoneNum);//发送验证码，默认是中国，会有一个回调
                            //                            SMSSDK.getVerificationCode(String country, String phone)
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "手机格式不正确!", Toast.LENGTH_SHORT).show();
                            //alerDialog("手机或邮箱格式不正确");
                        }
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "注册前必须同意互农协议", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.checkbox_reg1:
                    System.out.println("----------" + checked);
                    break;
                case R.id.tv_reg1_protocol:
                    //TODO:协议的链接

                    //                    String s = "www.baidu.com";
                    //                    Uri uri = Uri.parse(s);
                    //                    intent = new Intent("互农协议", uri);

                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));
                    intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                    startActivity(intent);
                    break;
            }
        }
    };


    CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int id = buttonView.getId();
            switch (id) {
                case R.id.checkbox_reg1:
                    if (isChecked) {
                        checked = true;
                        //                        mCheckbox_reg1.setText("选中");
                    } else {
                        checked = false;
                        //                        mCheckbox_reg1.setText("取消选中");
                    }
                    break;

            }
        }
    };

    //事件接收器
    EventHandler eh = new EventHandler() {

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                System.out.println("RESULT_COMPLETE");
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    System.out.println("EVENT_SUBMIT_VERIFICATION_CODE");
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    System.out.println("Fragment reg1--------获取验证码成功！");
                    handler.sendEmptyMessage(1);
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                    System.out.println("EVENT_GET_SUPPORTED_COUNTRIES");
                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case 1:
                    //跳转到frag2
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    Fragment_reg2 fragment_reg2 = Fragment_reg2.newInstance(mPhoneNum);
                    fragmentTransaction.remove(Fragment_reg1.this);
                    fragmentTransaction.add(R.id.frameLayout_RegAct, fragment_reg2);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    Toast.makeText(getActivity(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


}