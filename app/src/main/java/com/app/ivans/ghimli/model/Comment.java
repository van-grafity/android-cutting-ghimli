package com.app.ivans.ghimli.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("currentTime")
    @Expose
    private String currentTime;
    @SerializedName("urlToImage")
    @Expose
    private String urlToImage;
    @SerializedName("description")
    @Expose
    private String description;

    public Comment(String urlToImage, String name, String currentTime, String description) {
        this.name = name;
        this.currentTime = currentTime;
        this.urlToImage = urlToImage;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
