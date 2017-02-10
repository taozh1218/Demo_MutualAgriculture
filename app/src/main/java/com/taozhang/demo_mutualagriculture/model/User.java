package com.taozhang.demo_mutualagriculture.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Description:经纪人结构体
 * Created by taozhang on 2016/1/15.
 * Company:Geowind,University of South China.
 * ContactQQ:962076337
 *
 * @updateAuthor taozhang
 * @updateDate 2016/1/15
 */
public class User {

    public String uid;
    public String username;
    public String password;
    public String address;
    /** 用户头像地址，50×50像素 */
    public String profile_image_url;

    public static User parse(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return User.parse(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static User parse(JSONObject jsonObject) {
        if (null == jsonObject) {
            return null;
        }

        User user = new User();
        user.uid             = jsonObject.optString("uid", "");
        user.username        = jsonObject.optString("username", "");
        user.password        = jsonObject.optString("password", "");
        user.address         = jsonObject.optString("address", "");

        return user;
    }
}
