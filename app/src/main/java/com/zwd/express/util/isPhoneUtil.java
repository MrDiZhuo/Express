package com.zwd.express.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by asus-pc on 2017/5/9.
 */

public class isPhoneUtil {
    public static boolean isPhone(String phone){
        Pattern pattern = Pattern.compile("^((14[0-9])|(13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
    public static boolean isSex(String sex){
        Pattern pattern = Pattern.compile("男|女\\d{1}$");
        Matcher matcher = pattern.matcher(sex);
        return matcher.matches();
    }
}
