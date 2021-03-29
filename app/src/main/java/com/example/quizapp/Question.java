package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


/*
Code ideas credit to everyone who used a class to store question data
 */

public class Question extends AppCompatActivity {
    // Save widgets to variables
    TextView roundNumTV, choiceATV, choiceBTV, choiceCTV, choiceDTV;
    Button startOverBTN;
    ImageView imageView;

    // Global score variable
    int score = 0;
    // Create array of Question objects
    CustomQuestion[] questions = new CustomQuestion[]
            {
                    new CustomQuestion(R.drawable.no, new CustomChoice("Norway", true), new CustomChoice("Denmark", false), new CustomChoice("Netherlands", false), new CustomChoice("Austria", false)),
                    new CustomQuestion(R.drawable.sy, new CustomChoice("Syria", true), new CustomChoice("South Sudan", false), new CustomChoice("Zimbabwe", false), new CustomChoice("Jordan", false)),
                    new CustomQuestion(R.drawable.us, new CustomChoice("United States", true), new CustomChoice("Canada", false), new CustomChoice("Russia", false), new CustomChoice("Austria", false)),
                    new CustomQuestion(R.drawable.zw, new CustomChoice("Zimbabwe", true), new CustomChoice("Namibia", false), new CustomChoice("Mozambique", false), new CustomChoice("Zambia", false)),
                    new CustomQuestion(R.drawable.jp, new CustomChoice("Japan", true), new CustomChoice("South Korea", false), new CustomChoice("Canada", false), new CustomChoice("Sweden", false)),
                    new CustomQuestion(R.drawable.il, new CustomChoice("Israel", true), new CustomChoice("Poland", false), new CustomChoice("Egypt", false), new CustomChoice("Finland", false)),
                    new CustomQuestion(R.drawable.fr, new CustomChoice("France", true), new CustomChoice("Finland", false), new CustomChoice("Netherlands", false), new CustomChoice("Luxembourg", false)),
                    new CustomQuestion(R.drawable.de, new CustomChoice("Germany", true), new CustomChoice("Liberia", false), new CustomChoice("Belgium", false), new CustomChoice("Luxembourg", false)),
                    new CustomQuestion(R.drawable.cn, new CustomChoice("China", true), new CustomChoice("Vietnam", false), new CustomChoice("North Korea", false), new CustomChoice("Thailand", false)),
                    new CustomQuestion(R.drawable.au, new CustomChoice("Australia", true), new CustomChoice("New Zealand", false), new CustomChoice("United Kingdom", false), new CustomChoice("Austria", false)),

            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Shuffle question array
        shuffleQuestions(questions);
        // Call display question method starting at question 0
        displayQuestion(0);
    }

    public void shuffleQuestions(CustomQuestion[] array)
    {
        // Fisher Yates algorithm
        Random random = new Random();

        for (int i = array.length - 1; i > 0; i--)
        {
            int index = random.nextInt(i + 1);

            // Swap
            CustomQuestion customQuestion = array[index];
            array[index] = array[i];
            array[i] = customQuestion;

        }
    }

    public void shuffleTextViews(TextView[] array)
    {
        // Fisher Yates algorithm
        Random random = new Random();

        for (int i = array.length - 1; i > 0; i--)
        {
            int index = random.nextInt(i + 1);

            // Swap
            TextView textView = array[index];
            array[index] = array[i];
            array[i] = textView;

        }
    }

    public void displayQuestion(int questionId)
    {
        // Check if questionId is 10
        if (questionId == 10)
        {
            Intent intent = new Intent(Question.this, Results.class);
            // Pass score value as extra
            intent.putExtra("score", score);
            startActivity(intent);
        }
        else
        {
            roundNumTV = findViewById(R.id.roundNumberTextView);
            choiceATV = findViewById(R.id.choice0);
            choiceBTV = findViewById(R.id.choice1);
            choiceCTV = findViewById(R.id.choice2);
            choiceDTV = findViewById(R.id.choice3);
            imageView = findViewById(R.id.imageView);
            startOverBTN = findViewById(R.id.button2);

            // Remove background colors for all textviews
            // Add all TextField to array
            TextView[] textViews = new TextView[] {choiceATV, choiceBTV, choiceCTV, choiceDTV};
            for (TextView textView : textViews)
            {
                textView.setBackgroundResource(R.color.white);
            }

            // Get current question
            CustomQuestion question = questions[questionId];
            CustomChoice[] choices = question.getChoices();

            // Change values of widgets based on questionID
            roundNumTV.setText(getString(R.string.roundNumber, questionId + 1));
            imageView.setImageResource(question.getFlagDrawable());


            // Shuffle text views and set choices as text
            shuffleTextViews(textViews);
            textViews[0].setText(choices[0].getChoice());
            textViews[1].setText(choices[1].getChoice());
            textViews[2].setText(choices[2].getChoice());
            textViews[3].setText(choices[3].getChoice());

            // Add onclicks to all textviews
            for (TextView tv : textViews)
            {
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer(questionId, tv.getId());
                    }
                });
            }
        }
    }

    public void checkAnswer(int questionId, int choiceId)
    {
        CustomQuestion customQuestion = questions[questionId];
        CustomChoice[] choices = customQuestion.getChoices();
        String correctChoice = null;
        for (CustomChoice choice : choices)
        {
            if (choice.getTrue())
            {
                correctChoice = choice.getChoice();
            }
        }
        TextView textView = findViewById(choiceId);

        if (textView.getText().toString().equals(correctChoice))
        {
            textView.setBackgroundResource(R.color.green);
            MediaPlayer mp =  MediaPlayer.create(Question.this, R.raw.right);
            mp.start();
            score++;
        }
        else
        {
            MediaPlayer mp =  MediaPlayer.create(Question.this, R.raw.wrong);
            mp.start();
            textView.setBackgroundResource(R.color.red);
        }

        // Move on to next question
        displayQuestion(questionId + 1);
    }
}