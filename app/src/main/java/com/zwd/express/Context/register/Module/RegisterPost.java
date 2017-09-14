package com.zwd.express.Context.register.Module;

/**
 * Created by asus-pc on 2017/7/16.
 */

public class RegisterPost {
    private String username;
    private String password;
    private String name;
    private String sex;

    public RegisterPost(String username, String password, String name, String
            sex) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }
}
