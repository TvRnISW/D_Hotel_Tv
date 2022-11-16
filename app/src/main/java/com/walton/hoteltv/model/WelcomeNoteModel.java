package com.walton.hoteltv.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WelcomeNoteModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private WelcomeData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public WelcomeData getData() {
        return data;
    }

    public void setData(WelcomeData data) {
        this.data = data;
    }
}
