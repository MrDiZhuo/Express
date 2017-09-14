package com.zwd.express.Context.liveList.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/7/21.
 */

public class CreateRoomGet {
    @SerializedName("id")
    private String roomId;

    public CreateRoomGet(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }
}
