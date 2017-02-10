package com.taozhang.demo_mutualagriculture.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.taozhang.demo_mutualagriculture.R;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_reg2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_reg2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_reg2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String UID = "uid";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    /**
     * 从fragment1传入的手机号(or邮箱)
     */
    private String mUid;

    private OnFragmentInteractionListener mListener;
    private View mFrag_reg2;//当前fragment的布局
    private EditText mEdt_reg2_code;
    private Button mBtn_reg2_commitCheckCode;
    public int time = 60;
    private Button mGetcode;
    private TextView mRemind;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_reg2.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_reg2 newInstance(String param1, String param2) {
        Fragment_reg2 fragment = new Fragment_reg2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 重载方法
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param uid 手机号or邮箱(从fragment_reg1传入的)
     * @return Fragment_reg2
     */
    public static Fragment_reg2 newInstance(String uid) {
        Fragment_reg2 fragment = new Fragment_reg2();
        Bundle args = new Bundle();
        args.putString("uid", uid);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_reg2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取传入的参数
        if (getArguments() != null) {
            //            mParam1 = getArguments().getString(ARG_PARAM1);
            //            mParam2 = getArguments().getString(ARG_PARAM2);
            mUid = getArguments().getString(UID);
            System.out.println("Fragment2------------mUid:" + mUid);
//            Toast.makeText(getActivity(), "mUid:" + mUid, Toast.LENGTH_SHORT).show();
        }
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFrag_reg2 = inflater.inflate(R.layout.frag_reg2, container, false);

        init();
        return mFrag_reg2;
    }

    public void init() {
        mEdt_reg2_code = (EditText) mFrag_reg2.findViewById(R.id.edt_reg2_code);
        mBtn_reg2_commitCheckCode = (Button) mFrag_reg2.findViewById(R.id.btn_reg2_commitCheckCode);
        //添加监听器
        mBtn_reg2_commitCheckCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                switch (id) {
                    //                    case R.id.btn_reg2_getcode:
                    //                        获取验证码
                    //                        SMSSDK.getVerificationCode("86", mUid);//默认是中国
                    //                        mGetcode.setVisibility(View.GONE);//按钮隐藏
                    //                        reminderText();
                    //                        break;
                    case R.id.btn_reg2_commitCheckCode:
                        //1.获取editText文本
                        String code = mEdt_reg2_code.getText().toString().trim();
                        if (!TextUtils.isEmpty(code)) {
                            if (code.length() == 4) {
                                mBtn_reg2_commitCheckCode.setBackgroundColor(getResources().getColor(R.color.custom_green));
                                SMSSDK.submitVerificationCode("86", mUid, code);
                                //                Fragment_reg3 fragment_reg3 = new Fragment_reg3();

                            } else {
                                Toast.makeText(getActivity(), "请输入正确的验证码", Toast.LENGTH_LONG).show();
                                mEdt_reg2_code.requestFocus();
                            }
                        }
                        break;
                }
            }
        });


    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
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
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
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

    //    //验证码送成功后提示文字
    //    private void reminderText() {
    //        mRemind.setVisibility(View.VISIBLE);
    //        handlerText.sendEmptyMessageDelayed(1, 1000);
    //    }
    //    Handler handlerText =new Handler(){
    //        public void handleMessage(Message msg) {
    //            if(msg.what==1){
    //                if(time>0){
    //                    mRemind.setText("验证码已发送"+time+"秒");
    //                    time--;
    //                    handlerText.sendEmptyMessageDelayed(1, 1000);
    //                }else{
    //                    mRemind.setText("提示信息");
    //                    time = 60;
    //                    mRemind.setVisibility(View.GONE);
    //                    mGetcode.setVisibility(View.VISIBLE);
    //                }
    //            }else{
    //                mEdt_reg2_code.setText("");
    //                mRemind.setText("提示信息");
    //                time = 60;
    //                mRemind.setVisibility(View.GONE);
    //                mGetcode.setVisibility(View.VISIBLE);
    //            }
    //        };
    //    };

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what){
                case 1:
                    //跳转到下一个Fragment
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();//获取事务
                    Fragment_reg3 fragment_reg3 = Fragment_reg3.newInstance(mUid);
                    fragmentTransaction.remove(Fragment_reg2.this);
                    fragmentTransaction.add(R.id.frameLayout_RegAct, fragment_reg3);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
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
                System.out.println("-----RESULT_COMPLETE");
                if(event==SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                    //验证码校验成功,跳转到下一个Fragment
                    mHandler.sendEmptyMessage(1);
                }
                else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    System.out.println("提交验证码成功---------EVENT_SUBMIT_VERIFICATION_CODE");

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
//                    Toast.makeText(getActivity(), "验证码已经发送", Toast.LENGTH_SHORT).show();

                    System.out.println("EVENT_GET_VERIFICATION_CODE");
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                    System.out.println("EVENT_GET_SUPPORTED_COUNTRIES");
                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    };

}
