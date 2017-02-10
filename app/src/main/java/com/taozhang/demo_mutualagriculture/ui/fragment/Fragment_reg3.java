package com.taozhang.demo_mutualagriculture.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.taozhang.demo_mutualagriculture.R;
import com.taozhang.demo_mutualagriculture.model.Agent;
import com.taozhang.demo_mutualagriculture.util.HttpUtil;
import com.taozhang.demo_mutualagriculture.util.ResponseJson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_reg3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_reg3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_reg3 extends Fragment {
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
    private View mFrag_reg3;
    private EditText mEdt_reg3_psw;
    private EditText mEdt_reg3_confirmPsw;
    private Button mBtn_reg3_commit;
    private EditText mUsername;
    private EditText mLocation;
    private String mPsw;
    private String mUsername1;
    private String mLocation1;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_reg3.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_reg3 newInstance(String param1, String param2) {
        Fragment_reg3 fragment = new Fragment_reg3();
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
     * @param uid 手机号or邮箱(从fragment_reg2传入的)
     * @return Fragment_reg3
     */
    public static Fragment_reg3 newInstance(String uid) {
        Fragment_reg3 fragment = new Fragment_reg3();
        Bundle args = new Bundle();
        args.putString("uid", uid);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_reg3() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //            mParam1 = getArguments().getString(ARG_PARAM1);
            //            mParam2 = getArguments().getString(ARG_PARAM2);
            mUid = getArguments().getString(UID);
            Toast.makeText(getActivity(), "mUid:" + mUid, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFrag_reg3 = inflater.inflate(R.layout.frag_reg3, container, false);
        //获取完根布局，立即初始化
        init();
        return mFrag_reg3;
    }

    public void init() {
        //获取组件
        mEdt_reg3_psw = (EditText) mFrag_reg3.findViewById(R.id.edt_reg3_psw);
        mEdt_reg3_confirmPsw = (EditText) mFrag_reg3.findViewById(R.id.edt_reg3_confirmPsw);
        mUsername = (EditText) mFrag_reg3.findViewById(R.id.edt_reg3_username);
        mLocation = (EditText) mFrag_reg3.findViewById(R.id.edt_reg3_location);
        mBtn_reg3_commit = (Button) mFrag_reg3.findViewById(R.id.btn_reg3_commit);

        //添加监听
        mBtn_reg3_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.检验两次密码非空，是否一致
                //2.把账号、密码，发送给服务器,也可以同时存入sharedPreferences
                mPsw = mEdt_reg3_psw.getText().toString().trim();
                String psw_confirm = mEdt_reg3_confirmPsw.getText().toString().trim();
                mUsername1 = mUsername.getText().toString().trim();
                mLocation1 = mLocation.getText().toString().trim();

                if (!TextUtils.isEmpty(mPsw) && !TextUtils.isEmpty(psw_confirm)) {//都不为空
                    if (!mPsw.equals(psw_confirm)) {
                        Toast.makeText(getActivity(), "两次密码不一致，请重新输入！", Toast.LENGTH_LONG).show();
                        if (TextUtils.isEmpty(mUsername1) || TextUtils.isEmpty(mLocation1)) {
                            Toast.makeText(getActivity(), "昵称或地址不能为空！", Toast.LENGTH_SHORT).show();
                        }
                    } else if (!TextUtils.isEmpty(mUsername1) && !TextUtils.isEmpty(mLocation1) && mPsw.equals(psw_confirm)) {
                        Agent agent = new Agent();
                        agent.setTel(mUid);
                        agent.setPassword(mPsw);
                        agent.setLocation(mLocation1);
                        agent.setName(mUsername1);
                        Gson gson = new Gson();
                        String json = gson.toJson(agent);
                        try {
//                            final String url = "http://192.168.1.127:8080/MutualAgriculture/agentRegister.action?agent="+json;

//                            String j = new String(json.getBytes("utf-8"));
//                            final String j = new String(json.getBytes(),"utf-8");
//                            System.out.println("Fragment-------json"+json+"j:"+j);

//                            final String uri = new String(json.getBytes(),"utf-8");

//                            final String encode = new String(url.getBytes(), "utf-8");

                            final String encode = URLEncoder.encode(json, "utf-8");
//                            URLEncoder
//                            System.out.println("urlnew-----"+encode);
                            new Thread(){
                                @Override
                                public void run() {
                                    super.run();
                                    try {
//                                        String s = HttpUtil.get("http://192.168.1.127:8080/MutualAgriculture/agentRegister.action?agent="+encode, "utf-8");
                                        String s = HttpUtil.get("http://192.168.1.108:8080/MutualAgriculture/agentRegister.action?agent="+encode, "utf-8");
//                                        String s = HttpUtil.get(encode, "utf-8");

                                        Message msg = new Message();
                                        msg.what = 1;
                                        msg.obj = s;
                                        //                                    handler.sendEmptyMessage(1);
                                        handler.sendMessage(msg);
                                        System.out.println(s);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }.start();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        //  json.getBytes()
                        System.out.println("Fragment-------json" + json);


                    }
                } else {//如果为空
                    Toast.makeText(getActivity(), "请输入密码！", Toast.LENGTH_SHORT).show();
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


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            Object obj = msg.obj;
            System.out.println("msg.what"+what+"msg.arg1:"+obj.toString());
            switch (what){
                case 1:
                    ResponseJson responseJson = ResponseJson.parse((String)obj);
                    if (responseJson.success){
                        Toast.makeText(getActivity(),"注册成功,请登录!"+responseJson.message,Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getActivity(), LoginActivity.class);startActivity(intent);
                    }
                    else{
                        Toast.makeText(getActivity(),responseJson.message,Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        }
    };


//    try {
//        HttpUtil.get("http://192.168.1.127:8080/MutualAgriculture/agentRegister.action?agent=" + json, "UTF-8");
//    } catch (IOException e) {
//        e.printStackTrace();
//    }

}
