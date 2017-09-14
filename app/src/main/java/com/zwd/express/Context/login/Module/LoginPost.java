package com.zwd.express.Context.login.Module;

/**
 * Created by asus-pc on 2017/7/16.
 */

public class LoginPost {
    private String username;
    private String password;

    public LoginPost(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
