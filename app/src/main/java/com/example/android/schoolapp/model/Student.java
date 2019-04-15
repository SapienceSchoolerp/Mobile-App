package com.example.android.schoolapp.model;

public class Student {

    private String id;
    private String name;
    private String mClass;
    private String mobileNumber;
    private String imgUrl;

    public Student() {
    }

    public Student(String id, String name, String mClass, String mobileNumber, String imgUrl) {
        this.id = id;
        this.name = name;
        this.mClass = mClass;
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

    public String getmClass() {
        return mClass;
    }

    public void setmClass(String mClass) {
        this.mClass = mClass;
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
