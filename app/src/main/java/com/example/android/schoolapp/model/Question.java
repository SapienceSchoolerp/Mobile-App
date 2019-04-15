package com.example.android.schoolapp.model;

public class Question {

    private String id;
    private String userName;
    private String QuestionDes;

    public Question() {
    }

    public Question(String id, String userName, String questionDes) {
        this.id = id;
        this.userName = userName;
        QuestionDes = questionDes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getQuestionDes() {
        return QuestionDes;
    }

    public void setQuestionDes(String questionDes) {
        QuestionDes = questionDes;
    }
}
