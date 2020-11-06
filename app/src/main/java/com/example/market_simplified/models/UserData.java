package com.example.market_simplified.models;

import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("owner")
    private Data owner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Data getOwner() {
        return owner;
    }

    public void setOwner(Data owner) {
        this.owner = owner;
    }

}
