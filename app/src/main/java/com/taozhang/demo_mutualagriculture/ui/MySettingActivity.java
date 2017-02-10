package com.taozhang.demo_mutualagriculture.ui;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.widget.Toast;

import com.taozhang.demo_mutualagriculture.R;

/**
 * Des:PreferenceActivity,会自动保存用户的操作
 */
public class MySettingActivity extends PreferenceActivity {

    //CheckBoxPreference
    //ListPreference
    //EditTextPreference

    PreferenceManager mPreferenceManager;
    CheckBoxPreference mCheckBox;
    private ListPreference mListPreference;
    private EditTextPreference mEditTextPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        setContentView(R.layout.activity_my_setting);
        addPreferencesFromResource(R.xml.mypreference);//指定布局文件

        //获取组件
        mPreferenceManager = getPreferenceManager();
        mCheckBox = (CheckBoxPreference) mPreferenceManager.findPreference("checkbox");
        Toast.makeText(getApplication(), "当前的状态为：" + mCheckBox.isChecked(), Toast.LENGTH_SHORT).show();

        mListPreference = (ListPreference) mPreferenceManager.findPreference("list");
        Toast.makeText(getApplication(), mListPreference.getEntry() + "的开发环境为：" + mListPreference.getValue(), Toast.LENGTH_SHORT).show();

        mEditTextPreference = (EditTextPreference) mPreferenceManager.findPreference("edittext");
        Toast.makeText(getApplicationContext(), "您的输入为："+mEditTextPreference.getText(), Toast.LENGTH_SHORT).show();
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
                MySettingActivity.this.finish();
                break;

            default:
                break;
        }
        return false;
    }
}
