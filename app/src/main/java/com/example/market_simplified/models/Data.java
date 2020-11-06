package com.example.market_simplified.models;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("login")
    private String name;

    @SerializedName("type")
    private String userType;

    @SerializedName("avatar_url")
    private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
