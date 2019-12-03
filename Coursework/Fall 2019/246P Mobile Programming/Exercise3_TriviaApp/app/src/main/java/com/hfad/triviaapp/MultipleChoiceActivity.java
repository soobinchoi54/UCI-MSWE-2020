package com.hfad.triviaapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MultipleChoiceActivity extends Activity {

    private QuestionLibrary mQuestionLibrary = new QuestionLibrary();

    private TextView mcCountDown;

    private TextView mQuestionView;
    private TextView scoreView;

    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;
    private Button mButtonChoice4;
    private String mAnswer;

    private Button mButtonChoice1b;
    private Button mButtonChoice2b;
    private Button mButtonChoice3b;
    private Button mButtonChoice4b;
    private String mAnswer2;

    public static int mcTimeLimit = Integer.parseInt(StopwatchActivity.mcCDTimeLimit);

    private int mQuestionNumber = 0;
    private static int mcTries = 2;
    private static int mcTries2 = 2;

    //stopwatch
    public static int secondsMC = 0;
    private boolean running;
    private boolean wasRunning;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        mcCountDown = (TextView) findViewById(R.id.countdown_mc);

        scoreView = (TextView) findViewById(R.id.score);
        mQuestionView = (TextView) findViewById(R.id.questionLabel1);
        mButtonChoice1 = (Button) findViewById(R.id.choice1);
        mButtonChoice2 = (Button) findViewById(R.id.choice2);
        mButtonChoice3 = (Button) findViewById(R.id.choice3);
        mButtonChoice4 = (Button) findViewById(R.id.choice4);

        mButtonChoice1b = (Button) findViewById(R.id.choice1b);
        mButtonChoice2b = (Button) findViewById(R.id.choice2b);
        mButtonChoice3b = (Button) findViewById(R.id.choice3b);
        mButtonChoice4b = (Button) findViewById(R.id.choice4b);

        running = StopwatchActivity.runMC;

        updateQuestion();

        // Start of button listener for button 1
        mButtonChoice1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if (mButtonChoice1.getText() == mAnswer){
                    QuestionLibrary.score += 1;
                    updateScore(QuestionLibrary.score);
                    // correctAnswerNextQuestion();
                    Toast.makeText(MultipleChoiceActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MultipleChoiceActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                    mcTries = mcTries - 1;
                }
                if (QuestionLibrary.score == 2) {
                    correctAnswerNextQuestion();
                } else if (mcTries == 0) {
                    // jump to result page
                    exceededToResult();
                }
            }
        });

        // Start of button listener for button 2
        mButtonChoice2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if (mButtonChoice2.getText() == mAnswer){
                    QuestionLibrary.score += 1;
                    updateScore(QuestionLibrary.score);
                    // correctAnswerNextQuestion();
                    Toast.makeText(MultipleChoiceActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MultipleChoiceActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                    mcTries = mcTries - 1;
                }
                if (QuestionLibrary.score == 2) {
                    correctAnswerNextQuestion();
                } else if (mcTries == 0) {
                    // jump to result page
                    exceededToResult();
                }
            }
        });
        // Start of button listener for button 3
        mButtonChoice3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if (mButtonChoice3.getText() == mAnswer){
                    QuestionLibrary.score += 1;
                    updateScore(QuestionLibrary.score);
                    // correctAnswerNextQuestion();
                    Toast.makeText(MultipleChoiceActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MultipleChoiceActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                    mcTries = mcTries - 1;
                }
                if (QuestionLibrary.score == 2) {
                    correctAnswerNextQuestion();
                } else if (mcTries == 0) {
                    // jump to result page
                    exceededToResult();
                }
            }
        });
        // Start of button listener for button 4
        mButtonChoice4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if (mButtonChoice4.getText() == mAnswer){
                    QuestionLibrary.score += 1;
                    updateScore(QuestionLibrary.score);
                    // correctAnswerNextQuestion();
                    Toast.makeText(MultipleChoiceActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MultipleChoiceActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                    mcTries = mcTries - 1;
                }
                if (QuestionLibrary.score == 2) {
                    correctAnswerNextQuestion();
                } else if (mcTries == 0) {
                    // jump to result page
                    exceededToResult();
                }
            }
        });

        //Start of button listener for button 1b
        mButtonChoice1b.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if (mButtonChoice1b.getText() == mAnswer2) {
                    QuestionLibrary.score += 1;
                    updateScore(QuestionLibrary.score);
                    //correctAnswerNextQuestion();
                    Toast.makeText(MultipleChoiceActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MultipleChoiceActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                    mcTries2 = mcTries2 - 1;
                }
                if (QuestionLibrary.score == 2) {
                    correctAnswerNextQuestion();
                } else if (mcTries2 == 0) {
                    // jump to result page
                    exceededToResult();
                }
            }
        });

        //Start of button listener for button 2b
        mButtonChoice2b.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if (mButtonChoice2b.getText() == mAnswer2) {
                    QuestionLibrary.score += 1;
                    updateScore(QuestionLibrary.score);
                    correctAnswerNextQuestion();
                    Toast.makeText(MultipleChoiceActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MultipleChoiceActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                    mcTries2 = mcTries2 - 1;
                }
                if (QuestionLibrary.score == 2) {
                    correctAnswerNextQuestion();
                } else if (mcTries2 == 0) {
                    // jump to result page
                    exceededToResult();
                }
            }
        });

        //Start of button listener for button 3b
        mButtonChoice3b.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if (mButtonChoice3b.getText() == mAnswer2) {
                    QuestionLibrary.score += 1;
                    updateScore(QuestionLibrary.score);
                    correctAnswerNextQuestion();
                    Toast.makeText(MultipleChoiceActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MultipleChoiceActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                    mcTries2 = mcTries2 - 1;
                }
                if (QuestionLibrary.score == 2) {
                    correctAnswerNextQuestion();
                } else if (mcTries2 == 0) {
                    // jump to result page
                    exceededToResult();
                }
            }
        });

        //Start of button listener for button 1b
        mButtonChoice4b.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if (mButtonChoice4b.getText() == mAnswer2) {
                    QuestionLibrary.score += 1;
                    updateScore(QuestionLibrary.score);
                    correctAnswerNextQuestion();
                    Toast.makeText(MultipleChoiceActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MultipleChoiceActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                    mcTries2 = mcTries2 - 1;
                }
                if (QuestionLibrary.score == 2) {
                    correctAnswerNextQuestion();
                } else if (mcTries2 == 0) {
                    // jump to result page
                    exceededToResult();
                }
            }
        });

        if (savedInstanceState != null ) {
            secondsMC = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
        elapsedTime();

        runCountDownTimer();
    }

    private void updateQuestion() {
        mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
        mButtonChoice1.setText(mQuestionLibrary.getChoice1(mQuestionNumber));
        mButtonChoice2.setText(mQuestionLibrary.getChoice2(mQuestionNumber));
        mButtonChoice3.setText(mQuestionLibrary.getChoice3(mQuestionNumber));
        mButtonChoice4.setText(mQuestionLibrary.getChoice4(mQuestionNumber));

        mAnswer = mQuestionLibrary.getCorrectAnswer(mQuestionNumber);
        mQuestionNumber++;

        mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
        mButtonChoice1b.setText(mQuestionLibrary.getChoice1b(mQuestionNumber));
        mButtonChoice2b.setText(mQuestionLibrary.getChoice2b(mQuestionNumber));
        mButtonChoice3b.setText(mQuestionLibrary.getChoice3b(mQuestionNumber));
        mButtonChoice4b.setText(mQuestionLibrary.getChoice4b(mQuestionNumber));
        mAnswer2 = mQuestionLibrary.getCorrectAnswer(mQuestionNumber);
    }

    private void updateScore(int point){
        scoreView.setText("Score: " + QuestionLibrary.score);
    }

    public void correctAnswerNextQuestion (){
        // running = false;
        Intent intent = new Intent(this, FillBlankActivity.class);
        startActivity(intent);
    }

    public void exceededToResult () {
        QuestionLibrary.fail += 1;
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", secondsMC);
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
        final TextView countdownView = (TextView)findViewById(R.id.countdown_mc);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int sec = mcTimeLimit;
                String time = String.format(Locale.getDefault(), "%02d", sec);
                countdownView.setText(time);
                if (running) {
                    mcTimeLimit--;
                }
                if (mcTimeLimit == 0){
                    exceededToResult();
                    running = false;
                    return;
                }
                if (running) handler.postDelayed(this,1000);
            }
        });
    }

    private void runTimer() {
        final TextView timeView = (TextView)findViewById(R.id.time_view_mc);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = secondsMC / 3600;
                int minutes = (secondsMC % 3600) / 60;
                int secs = secondsMC % 60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if(running) {
                    secondsMC++;
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