package com.hfad.triviaapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class ResultActivity extends Activity {

    TextView finalScore;
    TextView scoreView;
    TextView passView;
    TextView failView;
    Button shareButton;
    Button returnButton;
    TextView totalElapsedTime;
    TextView elapsedQ1;
    TextView elapsedQ2;


    //stopwatch
    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        scoreView = (TextView) findViewById(R.id.score);
        finalScore = (TextView) findViewById(R.id.result);
        passView = (TextView) findViewById(R.id.passes);
        failView = (TextView) findViewById(R.id.fails);
        shareButton = (Button) findViewById(R.id.submit);
        returnButton = (Button) findViewById(R.id.returnMC);
        totalElapsedTime = (TextView) findViewById(R.id.total_elapsed_view);
        elapsedQ1 = (TextView) findViewById(R.id.time_view_mc);
        elapsedQ2 = (TextView) findViewById(R.id.time_view_f);

        running = false;

        updateScore(QuestionLibrary.score);
        updatePass(QuestionLibrary.pass);
        updateFail(QuestionLibrary.fail);
        updateFinalScore(QuestionLibrary.score);
        updateTotalElapsedTime(StopwatchActivity.seconds);
        updateTimeQ1(MultipleChoiceActivity.secondsMC);
        updateTimeQ2(FillBlankActivity.secondsF);

        shareButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                onClickShareResult();
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                onClickReturn();
            }
        });

        if (savedInstanceState != null ) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

    }

    private void updateFinalScore(int point){
        finalScore.setText("Your Score: " + QuestionLibrary.score);
    }
    private void updateScore(int point){
        scoreView.setText("Score: " + QuestionLibrary.score);
    }

    private void updatePass(int c) {
        passView.setText("Passed: " + QuestionLibrary.pass);
    }
    private void updateFail (int c) {
        failView.setText("Failed: " + QuestionLibrary.fail);
    }

    private void updateTotalElapsedTime (int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
        totalElapsedTime.setText("Total Elapsed Time: " + time);
    }

    private void updateTimeQ1 (int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
        elapsedQ1.setText("Question 1: " + time);
    }

    private void updateTimeQ2 (int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
        elapsedQ2.setText("Question 2: " + time);
    }

    public void onClickShareResult() {
        String results = ("You passed " + Integer.toString(QuestionLibrary.pass) + " times and failed " + Integer.toString(QuestionLibrary.fail) + " times.");
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, results);
        String chooserTitle = getString(R.string.chooser);
        Intent chosenIntent = Intent.createChooser(intent, chooserTitle);
        startActivity(chosenIntent);
    }

    public void onClickReturn() {
        Intent intent2 = new Intent(this, StopwatchActivity.class);
        startActivity(intent2);
        // set main timer to stop and reset
        StopwatchActivity.running = false;
        StopwatchActivity.seconds = 0;
        MultipleChoiceActivity.secondsMC = 0;
        FillBlankActivity.secondsF = 0;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running",running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }
//    @Override
//    protected void onStop() {
//        super.onStop(); wasRunning = running; running = false;
//    }
//    @Override
//    protected void onStart() {
//        super.onStart(); if (wasRunning) {
//            running = true; }
//    }

}
