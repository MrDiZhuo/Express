package com.zwd.express.Context.liveList.Module;

/**
 * Created by asus-pc on 2017/7/9.
 */

public class LiveListGet {
    private String url;
    private String id;
    private int num;
    private String name;
    private String context;
    private int TYPE;////1  2

    public LiveListGet(String url, String id, int num, String name, String
            context, int TYPE) {
        this.url = url;
        this.id = id;
        this.num = num;
        this.name = name;
        this.context = context;
        this.TYPE = TYPE;
    }

    public int getTYPE() {
        return TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
