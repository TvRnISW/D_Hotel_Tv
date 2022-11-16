package com.walton.hoteltv.model.hotel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ExMeetInfo {

    @SerializedName("info")
    @Expose
    private Info info;
    @SerializedName("gallery")
    @Expose
    private List<Gallery> gallery = null;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<Gallery> getGallery() {
        return gallery;
    }

    public void setGallery(List<Gallery> gallery) {
        this.gallery = gallery;
    }

}
