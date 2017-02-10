package com.taozhang.demo_mutualagriculture.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.taozhang.demo_mutualagriculture.R;
import com.taozhang.demo_mutualagriculture.adapter.ApplyOrderGridImgsAdapter;
import com.taozhang.demo_mutualagriculture.util.ImageUtil;

import java.util.ArrayList;

/**
 * 提交订单
 */
public class ApplyOrderActivity extends AppCompatActivity {

    private Button mBtn_date;//日期按钮

    private Button mBtn_return;//返回按钮
    private LinearLayout mLLout_userInfo;//个人信息的线性布局
    private Button mBtn_payMethod;//支付方法
    private Button mBtn_agricultureType;//庄稼类型
    private EditText mEdt_farmlandSize;//农田大小
    private EditText mEdt_farmlandPerPrice;//收割单价
    private EditText mEdt_farmlandLocation;//农田位置
    private EditText mEdt_farmlandDescription;//农田描述
    private EditText mEdt_remark;//备注
    private Button mBtn_applyOrder;//提交订单
    private String mDateS;//日期
    private String mTimeS;//时间
    private String mSizeS;//农田大小
    private String mPerPriceS;//单价
    private String mLocationS;//地址
    private String mRemark;
    private double mTotalPrice;//订单总价
    private ArrayList<Uri> imageUris = new ArrayList<Uri>();
    private GridView mGridView;
    private ApplyOrderGridImgsAdapter mGridViewAdapter;
    private Button mBtn_addImg;
    //    private TextView mTv_totalPrice;//订单总价

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);

        init();
    }

    private void init() {
        //获取组件
        //        ImageView img_return = (ImageView) findViewById(R.id.img_applyAct_return);
        mBtn_return = (Button) findViewById(R.id.btn_applyAct_return);
        mLLout_userInfo = (LinearLayout) findViewById(R.id.lLout_applyAct_userInfo);//经纪人信息的线性布局
        mBtn_date = (Button) findViewById(R.id.btn_applyAct_date);
        mBtn_payMethod = (Button) findViewById(R.id.btn_applyAct_payMethod);
        mBtn_agricultureType = (Button) findViewById(R.id.btn_applyAct_agricultureType);
        mEdt_farmlandSize = (EditText) findViewById(R.id.edt_applyAct_farmlandSize);
        mEdt_farmlandPerPrice = (EditText) findViewById(R.id.edt_applyAct_farmlandPerPrice);
        mEdt_farmlandLocation = (EditText) findViewById(R.id.edt_applyAct_farmlandLocation);
        mEdt_farmlandDescription = (EditText) findViewById(R.id.edt_applyAct_farmlandDescription);
        mEdt_remark = (EditText) findViewById(R.id.edt_applyAct_remark);
        //        mTv_totalPrice = (TextView) findViewById(R.id.tv_applyAct_totalPrice);
        mBtn_addImg = (Button) findViewById(R.id.btn_applyAct_addImg);
        mGridView = (GridView) findViewById(R.id.gv_applyAct);
        mBtn_applyOrder = (Button) findViewById(R.id.btn_applyAct_applyOrder);



        mBtn_return.setOnClickListener(mOnClickListener);
        mLLout_userInfo.setOnClickListener(mOnClickListener);
        mBtn_date.setOnClickListener(mOnClickListener);
        mBtn_payMethod.setOnClickListener(mOnClickListener);
        mBtn_addImg.setOnClickListener(mOnClickListener);
        mBtn_applyOrder.setOnClickListener(mOnClickListener);
        //监听EditText内容变化
        //        mEdt_farmlandPerPrice.addTextChangedListener(mTextWatcher);
        mEdt_farmlandPerPrice.setOnFocusChangeListener(mFocusChangeListener);//监听焦点的得失

        mGridViewAdapter = new ApplyOrderGridImgsAdapter(this, imageUris, mGridView);//将空的集合设置到适配器中
        mGridView.setAdapter(mGridViewAdapter);
        mGridView.setOnItemClickListener(mOnItemClickListener);
    }

    /**
     * 视图监听
     */
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            int id = v.getId();
            switch (id) {
                case R.id.btn_applyAct_return://返回
                    intent = new Intent(ApplyOrderActivity.this, DrawerArrowActivity.class);
                    startActivity(intent);
                    ApplyOrderActivity.this.finish();
                    break;
                case R.id.lLout_applyAct_userInfo:
                    //TODO
                    //跳转到个人信息编辑界面
                    Toast.makeText(getApplicationContext(), "更改收货地址", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_applyAct_date://设置时间

                    new TimePickerDialog(ApplyOrderActivity.this, new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            mTimeS = String.format("%d:%d", hourOfDay, minute);
                            System.out.println(mTimeS);
                            mBtn_date.setText(mDateS + " " + mTimeS);
                        }
                    }, 8, 30, true).show();

                    new DatePickerDialog(ApplyOrderActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            mDateS = String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth);
                            System.out.println(mDateS);
                            //                            mBtn_date.setText(dateS);
                        }
                    }, 2016, 0, 29).show();
                    break;
                case R.id.btn_applyAct_payMethod://设置支付方式
                    //TODO
                    //显示一个dialog,供用户选择
                    break;
                case R.id.btn_applyAct_agricultureType://农田类型
                    //TODO
                    //显示一个dialog
                    break;
                case R.id.btn_applyAct_addImg://添加图片
                    ImageUtil.showImagePickDialog(ApplyOrderActivity.this);
//                    mBtn_addImg.setVisibility(View.GONE);
                    break;
                case R.id.btn_applyAct_applyOrder://提交订单
                    //1.获取各项输入
                    mLocationS = mEdt_farmlandLocation.getText().toString();
                    mRemark = mEdt_remark.getText().toString();
                    //2..判断是否有一项为空
                    if (!TextUtils.isEmpty(mSizeS) && !TextUtils.isEmpty(mPerPriceS) && !TextUtils.isEmpty(mLocationS)) {//都不为空


                    }
                    break;
            }
        }
    };

    //焦点监听器
    View.OnFocusChangeListener mFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            System.out.println("hasFocus:" + hasFocus);
            if (!hasFocus) {
                //计算总价
                mSizeS = mEdt_farmlandSize.getText().toString();
                mPerPriceS = mEdt_farmlandPerPrice.getText().toString();
                if (!TextUtils.isEmpty(mSizeS) && !TextUtils.isEmpty(mPerPriceS)) {//都不为空
                    double sizeD = Double.parseDouble(mSizeS);
                    double perPrice = Double.parseDouble(mPerPriceS);
                    mTotalPrice = sizeD * perPrice;
                    System.out.println("mTotalPrice:" + mTotalPrice);
                }
            }
        }
    };

    AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Adapter itemAdapter = parent.getAdapter();
            if (itemAdapter instanceof ApplyOrderGridImgsAdapter) {//因为每个页面的表情面板也是GridView,会有单独的onclick事件
                if (position == mGridViewAdapter.getCount() - 1) {//最后一张图片
                    ImageUtil.showImagePickDialog(ApplyOrderActivity.this);
                }
            }
        }
    };

    //    private TextWatcher mTextWatcher = new TextWatcher() {
    //
    //        @Override
    //        public void onTextChanged(CharSequence s, int start, int before, int count) {
    //        }
    //
    //        @Override
    //        public void beforeTextChanged(CharSequence s, int start, int count,
    //                                      int after) {
    //        }
    //
    //        @Override
    //        public void afterTextChanged(Editable s) {
    //            //计算总价
    //            mSizeS = mEdt_farmlandSize.getText().toString();
    //            mPerPriceS = mEdt_farmlandPerPrice.getText().toString();
    //            if (!TextUtils.isEmpty(mSizeS) && !TextUtils.isEmpty(mPerPriceS)) {//都不为空
    //                double sizeD = Double.parseDouble(mSizeS);
    //                double perPrice = Double.parseDouble(mPerPriceS);
    //                mTotalPrice = sizeD*perPrice;
    //                System.out.println("mTotalPrice:"+mTotalPrice);
    //            }
    //        }
    //    };


    /**
     * 处理其他Activity返回的数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {//取消操作
            return;//结束方法
        }
        switch (requestCode) {
            case ImageUtil.REQUEST_CODE_FROM_ALBUM://相册
                Uri imageUri = data.getData();//图片地址
                imageUris.add(imageUri);
                updateImgs();
                break;
            case ImageUtil.REQUEST_CODE_FROM_CAMERA://拍照
                //                data.getData();//如果不output，可以获取到Bitmap的压缩图
                if (resultCode == RESULT_CANCELED) {//取消操作
                    ImageUtil.deleteImageUri(this, ImageUtil.imageUriFromCamera);
                } else {
//                    Uri data1 = data.getData();
//                    System.out.println("data1："+data1);
                    Uri imageUriFromCamera = ImageUtil.imageUriFromCamera;//uri的内容可能会空，因为可能会不做拍照操作，直接返回，这时意味着这条uri地址对应的数据为空，造成多余的数据,需要删除
                    imageUris.add(imageUriFromCamera);
                    updateImgs();
                }
                break;
        }
    }

    /**
     * 更新九宫格图片显示
     */
    private void updateImgs() {
        //即是更新适配器
        if (imageUris.size() > 0) {//只有有图片才显示
            mGridView.setVisibility(View.VISIBLE);
            mGridViewAdapter.notifyDataSetChanged();
        } else {
            mGridView.setVisibility(View.GONE);
        }
    }


    /**
     * Android虚拟按键监听
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
                ApplyOrderActivity.this.finish();
                break;
            default:
                break;
        }
        return false;
        //return super.onKeyDown(keyCode, event);
    }
}
