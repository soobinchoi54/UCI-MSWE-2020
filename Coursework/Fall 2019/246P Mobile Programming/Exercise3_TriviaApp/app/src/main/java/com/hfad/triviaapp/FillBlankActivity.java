package com.hfad.triviaapp;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;


public class FillBlankActivity extends Activity {

    private TextView scoreView;
    private TextView fCountdown;

    private Button submit;
    private TextView fitbQuestion;
    private EditText fitbAnswer;
    private String fAnswer = QuestionLibrary.fAnswer;
    private boolean correctAnswer = false;

    //stopwatch
    public static int secondsF = 0;
    private boolean running;
    private boolean wasRunning;

    public static int fTimeLimit = Integer.parseInt(StopwatchActivity.fCDTimeLimit);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_blank);

        fCountdown = (TextView) findViewById(R.id.countdown_f);

        submit = (Button) findViewById(R.id.submit);
        fitbQuestion = (TextView) findViewById(R.id.questionLabel2);
        fitbAnswer = (EditText) findViewById(R.id.answer);
        scoreView = (TextView) findViewById(R.id.score);

        running = StopwatchActivity.runF;

        updateScore(QuestionLibrary.score);

        fitbAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (fitbAnswer.getText().toString().equalsIgnoreCase(fAnswer)) {
                    correctAnswer = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        if (savedInstanceState != null ) {
            secondsF = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
        elapsedTime();

        runCountDownTimer();

    }
    private void updateScore(int point){
        scoreView.setText("Score: " + QuestionLibrary.score);
    }

    private static int fTries = 2;

    public void onClickGetResult (View view) {
        if (correctAnswer == true) {
            Toast.makeText(FillBlankActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
            QuestionLibrary.score += 1;
            updateScore(QuestionLibrary.score);
            QuestionLibrary.pass += 1;
            running = false;
            Intent intent = new Intent(this, ResultActivity.class);
            startActivity(intent);
        } else {
            fTries = fTries - 1;
            Toast.makeText(FillBlankActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
            Intent intent2 = new Intent(this, FillBlankActivity.class);
            startActivity(intent2);
        }
        if (correctAnswer == false && fTries == 0) {
            // jump to result page
            running = false;
            QuestionLibrary.fail += 1;
            Intent intent3 = new Intent(this, ResultActivity.class);
            startActivity(intent3);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", secondsF);
        savedInstanceState.putBoolean("running",running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }
    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    private void runCountDownTimer() {
        final TextView countdownView = (TextView)findViewById(R.id.countdown_f);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int sec = fTimeLimit;
                String time = String.format(Locale.getDefault(), "%02d", sec);
                countdownView.setText(time);
                if (running) {
                    fTimeLimit--;
                }
                if (fTimeLimit == 0){
                Intent intent = new Intent(FillBlankActivity.this, ResultActivity.class);
                startActivity(intent);
                QuestionLibrary.fail+=1;
                running = false;
                return;
                }
                if (running) handler.postDelayed(this,1000);
            }
        });
    }

    private void runTimer() {
        final TextView timeView = (TextView)findViewById(R.id.time_view_f);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = secondsF / 3600;
                int minutes = (secondsF % 3600) / 60;
                int secs = secondsF % 60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if(running) {
                    secondsF++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }

    private void elapsedTime() {
        final TextView timeView = (TextView)findViewById(R.id.total_elapsed_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = StopwatchActivity.seconds / 3600;
                int minutes = (StopwatchActivity.seconds % 3600) / 60;
                int secs = StopwatchActivity.seconds % 60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                handler.postDelayed(this,1000);
            }
        });
    }

}
