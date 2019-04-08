package com.example.android.schoolapp.model;

public class InboxData {

    private String mName;
    private String mMessage;
    private String mTime;

    public InboxData(String mName, String mMessage, String mTime) {
        this.mName = mName;
        this.mMessage = mMessage;
        this.mTime = mTime;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }
}
