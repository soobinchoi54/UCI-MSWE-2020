package com.hfad.triviaapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MultipleChoiceActivity extends Activity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_multiple_choice);
//        Spinner spinner = (Spinner) findViewById(R.id.color);
//        spinner.setOnItemSelectedListener(this);
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.mcOptions, android.R.layout.simple_spinner_item);
//        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // Apply the adapter to the spinner
//        spinner.setAdapter(adapter);
//    }
//
//    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//        // An item was selected. You can retrieve the selected item using
//        // parent.getItemAtPosition(pos)
//        // Intent intent = new Intent(this, FillBlankActivity.class);
//    }
//
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }

    private QuestionLibrary mQuestionLibrary = new QuestionLibrary();

    private TextView mQuestionView;
    private TextView scoreView;
    private TextView chancesView;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;
    private Button mButtonChoice4;


    private String mAnswer;
    private int mScore = 0;
    private int mQuestionNumber = 0;
    private int tries = 2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        scoreView = (TextView) findViewById(R.id.score);
        mQuestionView = (TextView) findViewById(R.id.questionLabel1);
        mButtonChoice1 = (Button) findViewById(R.id.choice1);
        mButtonChoice2 = (Button) findViewById(R.id.choice2);
        mButtonChoice3 = (Button) findViewById(R.id.choice3);
        mButtonChoice4 = (Button) findViewById(R.id.choice4);

        updateQuestion();

        // Start of button listener for button 1
        mButtonChoice1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if (mButtonChoice1.getText() == mAnswer){
                    QuestionLibrary.score += 1;
                    updateScore(QuestionLibrary.score);
                    correctAnswerNextQuestion();

                    // updateQuestion();
                    Toast.makeText(MultipleChoiceActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MultipleChoiceActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                    tries = tries - 1;
                   // updateChances(tries);
                }
                if (tries == 0) {
                    // jump to result page
                    QuestionLibrary.fail += 1;
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
                    correctAnswerNextQuestion();

                    // updateQuestion();
                    Toast.makeText(MultipleChoiceActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MultipleChoiceActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                    tries = tries - 1;
                    // updateChances(tries);

                }
                if (tries == 0) {
                    // jump to result page
                    QuestionLibrary.fail += 1;
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
                    correctAnswerNextQuestion();

                    // updateQuestion();
                    Toast.makeText(MultipleChoiceActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MultipleChoiceActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                    tries = tries - 1;
                    // updateChances(tries);

                }
                if (tries == 0) {
                    // jump to result page
                    QuestionLibrary.fail += 1;
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
                    correctAnswerNextQuestion();

                    // updateQuestion();
                    Toast.makeText(MultipleChoiceActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MultipleChoiceActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                    tries = tries - 1;
                    // updateChances(tries);

                }
                if (tries == 0) {
                    // jump to result page
                    QuestionLibrary.fail += 1;
                    exceededToResult();
                }
            }
        });
    }

    private void updateQuestion() {
        mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
        mButtonChoice1.setText(mQuestionLibrary.getChoice1(mQuestionNumber));
        mButtonChoice2.setText(mQuestionLibrary.getChoice2(mQuestionNumber));
        mButtonChoice3.setText(mQuestionLibrary.getChoice3(mQuestionNumber));
        mButtonChoice4.setText(mQuestionLibrary.getChoice4(mQuestionNumber));

        mAnswer = mQuestionLibrary.getCorrectAnswer(mQuestionNumber);
        mQuestionNumber++;
    }

    private void updateScore(int point){
        scoreView.setText("Score: " + QuestionLibrary.score);
    }
    // private void updateChances(int point) {chancesView.setText("Chances: " + tries); }

    public void correctAnswerNextQuestion (){
        Intent intent = new Intent(this, FillBlankActivity.class);
        startActivity(intent);
    }

    public void exceededToResult () {
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }
    }