package com.zwd.express.internet;

import com.google.gson.annotations.SerializedName;

/**
 * Created by XRY on 2016/10/4.
 */

public class RemoteDataResult<T> {
    @SerializedName("resultCode")
    private int resultCode;
    @SerializedName("resultMessage")
    private String resultMessage;
    @SerializedName("resultData")
    private T data;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
