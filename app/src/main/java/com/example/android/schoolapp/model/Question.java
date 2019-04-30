package com.example.android.schoolapp.model;

import com.example.android.schoolapp.QuestionPostId;

public class Question extends QuestionPostId {

    private String id;
    private String name;
    private String question;

    public Question() {
    }

    public Question(String id, String name, String question) {
        this.id = id;
        this.name = name;
        this.question = question;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
