package com.zwd.express.Context.roastDetail.Module;

/**
 * Created by asus-pc on 2017/8/8.
 */

public class DetailPost {
    private int userId;
    private int chatId;

    public DetailPost(int userId, int chatId) {
        this.userId = userId;
        this.chatId = chatId;
    }

    public int getUserId() {
        return userId;
    }

    public int getChatId() {
        return chatId;
    }
}
