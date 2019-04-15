package com.example.android.schoolapp.model;

public class Teacher {

    private String id;
    private String name;
    private String mobileNumber;
    private String imgUrl;

    public Teacher() {
    }

    public Teacher(String id, String name, String mobileNumber, String imgUrl) {
        this.id = id;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
