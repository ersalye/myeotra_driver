package com.myeotra.driver.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("provider")
    @Expose
    private Provider provider;

    @SerializedName("d_status")
    @Expose
    private Integer dStatus;
    @SerializedName("d_message")
    @Expose
    private String dMessage;

    public Integer getDStatus() {
        return dStatus;
    }

    public void setDStatus(Integer dStatus) {
        this.dStatus = dStatus;
    }

    public String getDMessage() {
        return dMessage;
    }

    public void setDMessage(String dMessage) {
        this.dMessage = dMessage;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
