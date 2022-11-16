package com.walton.hoteltv.model.hotel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Manik {
    @SerializedName("info")
    @Expose
    private Info info;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
