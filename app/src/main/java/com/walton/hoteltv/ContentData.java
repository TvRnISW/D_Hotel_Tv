package com.walton.hoteltv;

public class ContentData {

    private String title;
    private String details;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public ContentData(String title, String details) {
        this.title = title;
        this.details = details;
    }
}
