package com.taozhang.demo_mutualagriculture.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 * Created by taozhang on 2016/1/14.
 * Company:University of South China
 * ContactQQ:962076337
 *
 * @updateAuthor taozhang
 * @updateDate 2016/1/14
 * @updateDate
 */
public class Util_checkPhoneNum {
    boolean isPhone = isMobileNO("1684565463453");

    boolean isEmail = isEmail("dthtterge@trytryvv.com");

    boolean isnumber = isNumeric("564465");

    //判断手机格式是否正确
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    //判断email格式是否正确
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    //判断是否全是数字
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

}
