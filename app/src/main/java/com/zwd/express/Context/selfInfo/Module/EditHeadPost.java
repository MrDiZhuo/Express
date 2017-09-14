package com.zwd.express.Context.selfInfo.Module;

/**
 * Created by asus-pc on 2017/8/5.
 */

public class EditHeadPost {
    private int id;
    private String heading;

    public EditHeadPost(int id, String heading) {
        this.id = id;
        this.heading = heading;
    }

    public int getId() {
        return id;
    }

    public String getHeading() {
        return heading;
    }
}
