package com.taozhang.demo_mutualagriculture.Test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.taozhang.demo_mutualagriculture.R;
import com.taozhang.demo_mutualagriculture.model.MachineOwner;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        new Thread() {
            @Override
            public void run() {
                link();
                //                liakStruts2();
            }
        }.start();
    }

    /**
     * 链接struts2的测试
     */
    private void liakStruts2() {
        String path = "http://192.168.1.108:8080/MutualAgricultrue/machine/login.android?machineOwerJson={}&name="+"来来来'";
        try {
            URL url = new URL(path);
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            connection.setDoOutput(true);
            connection.setReadTimeout(3000);
            Log.e("iii", "hhh");
            InputStream inputStream = connection.getInputStream();
            byte[] bytes = new byte[1024];
            int read = inputStream.read(bytes);
            String value = new String(bytes, 0, read);
            Log.e("value", value);
            inputStream.close();
            Log.e("iii", "hhh");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void link() {
        String path = "http://192.168.1.108:8080//MutualAgricultrue/machine/login.android?machineOwerJson=";
        MachineOwner machineOwner = new MachineOwner();
        machineOwner.setId(2);
        Gson gson = new Gson();
        String s1 = gson.toJson(machineOwner);
        path += s1;
        path +="&name='来来来'";

        try {
            URL url = new URL(path);
            Log.e("index", "begin");
            URLConnection connection = url.openConnection();
            connection.setReadTimeout(3000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            Log.e("index", "end1");
            InputStream inputStream = connection.getInputStream();
            Log.e("index", "end");
            byte[] bytes = new byte[1024];
            int i = inputStream.read(bytes);
            String s = new String(bytes, 0, i);
            Log.e("values", s);
            inputStream.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
