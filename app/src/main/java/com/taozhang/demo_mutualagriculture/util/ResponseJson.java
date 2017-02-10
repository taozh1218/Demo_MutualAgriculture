package com.taozhang.demo_mutualagriculture.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Description:解析服务器反馈信息的类
 * Created by taozhang on 2016/1/19.
 * Company:Geowind,University of South China.
 * ContactQQ:962076337
 *
 * @updateAuthor taozhang
 * @updateDate 2016/1/19
 */
public class ResponseJson {

    public boolean success;
    public String message;

    public static ResponseJson parse(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return ResponseJson.parse(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseJson parse(JSONObject jsonObject) {
        if (null == jsonObject) {
            return null;
        }

        ResponseJson json = new ResponseJson();
        json.success = jsonObject.optBoolean("success", false);//false是默认值
        //        jsonObject.optBoolean("success");//或者这样
        json.message = jsonObject.optString("message", "");
        return json;
    }


}
