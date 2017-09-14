package com.zwd.express.Context.sign.Module;

/**
 * Created by asus-pc on 2017/7/29.
 */

public class SignGet {
    private int status;//0今天还没签1今天已签
    private int sign;//已经连续签到天数

    public SignGet(int status, int sign) {
        this.status = status;
        this.sign = sign;
    }

    public int getStatus() {
        return status;
    }

    public int getSign() {
        return sign;
    }
}
