package com.example.android.schoolapp;

import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class QuestionPostId {

    @Exclude
    public String QuestionPPostId;

    public <T extends QuestionPostId> T withId(@NonNull final String id){
        this.QuestionPPostId = id;
        return (T) this;
    }

}
