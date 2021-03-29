package com.example.quizapp;

public class CustomChoice {
    private String choice;
    private Boolean isTrue;

    public CustomChoice(String choice, Boolean isTrue)
    {
        this.choice = choice;
        this.isTrue = isTrue;
    }

    public String getChoice() {
        return choice;
    }

    public Boolean getTrue() {
        return isTrue;
    }
}
