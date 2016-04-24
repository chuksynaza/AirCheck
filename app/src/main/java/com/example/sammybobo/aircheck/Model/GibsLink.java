package com.example.sammybobo.aircheck.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Agbede on 23/04/2016.
 */
public class GibsLink {
    public String getUrl() {
        return url;
    }
    /**
     *
     * @param url
     * The humidity
     */

    public void setUrl(String url) {
        this.url = url;
    }
    @SerializedName("url")
    @Expose
    private String url;
}
