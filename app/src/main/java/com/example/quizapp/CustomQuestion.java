package com.example.quizapp;

public class CustomQuestion {
    private int flagDrawable;
    private CustomChoice[] choices;

    // Constructor
    public CustomQuestion(int flagImg, CustomChoice choiceA, CustomChoice choiceB, CustomChoice choiceC, CustomChoice choiceD)
    {
        this.flagDrawable = flagImg;
        this.choices = new CustomChoice[]{choiceA, choiceB, choiceC, choiceD};
    }

    public int getFlagDrawable() {
        return flagDrawable;
    }

    public CustomChoice[] getChoices() {
        return choices;
    }
}
