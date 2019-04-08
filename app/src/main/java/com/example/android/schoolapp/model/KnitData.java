package com.example.android.schoolapp.model;

public class KnitData {

    private String name;
    private String time;
    private String question;
    private String answer_list;
    private String views;

    public KnitData(String name, String time, String question, String answer_list, String views) {
        this.name = name;
        this.time = time;
        this.question = question;
        this.answer_list = answer_list;
        this.views = views;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer_list() {
        return answer_list;
    }

    public void setAnswer_list(String answer_list) {
        this.answer_list = answer_list;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }
}
