package com.zwd.express.Context.editSelf.Module;

/**
 * Created by asus-pc on 2017/7/17.
 */

public class EditSelfPost {
    private int id;
    private String name;
    private String username;///手机号码
    private String sex;

    public EditSelfPost(int id, String name, String username, String sex) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getSex() {
        return sex;
    }
}
