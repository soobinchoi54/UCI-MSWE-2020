package com.hfad.triviaapp;

import android.os.Bundle;
import android.app.Activity;
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
import java.util.Random;


public class FillBlankActivity extends Activity {

    Button submit;
    TextView fitbQuestion;
    EditText fitbAnswer;
    String fAnswer = QuestionLibrary.fAnswer;
    private int mScore = 0;
    private TextView mScoreView;
    private boolean correctAnswer = false;
    private TextView scoreView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_blank);

        submit = (Button) findViewById(R.id.submit);
        fitbQuestion = (TextView) findViewById(R.id.questionLabel2);
        fitbAnswer = (EditText) findViewById(R.id.answer);
        scoreView = (TextView) findViewById(R.id.score);
        updateScore(QuestionLibrary.score);

        // submit.setVisibility(View.INVISIBLE);

        fitbAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (fitbAnswer.getText().toString().equalsIgnoreCase(fAnswer)) {
                    //mScore = mScore + 1;
                    //updateScore(mScore);
                    //Toast.makeText(FillBlankActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    correctAnswer = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    private void updateScore(int point){
        scoreView.setText("Score: " + QuestionLibrary.score);
    }


    private static int tries = 2;

    public void onClickGetResult (View view) {
        if (correctAnswer == true) {
            //mScore = mScore + 1;
            //updateScore(mScore);
            Toast.makeText(FillBlankActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
            QuestionLibrary.score += 1;
            updateScore(QuestionLibrary.score);
            Intent intent = new Intent(this, ResultActivity.class);
            startActivity(intent);
            QuestionLibrary.pass += 1;
        } else {
            tries = tries - 1;
            Toast.makeText(FillBlankActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();

            Intent intent2 = new Intent(this, FillBlankActivity.class);
            startActivity(intent2);
        }
        if (tries == 0) {
            // jump to result page
            Intent intent3 = new Intent(this, ResultActivity.class);
            startActivity(intent3);
            QuestionLibrary.fail += 1;
        }


    }

}
