package com.hfad.triviaapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends Activity {

    TextView finalScore;
    TextView scoreView;
    TextView passView;
    TextView failView;
    Button shareButton;
    Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        scoreView = (TextView) findViewById(R.id.score);
        passView = (TextView) findViewById(R.id.passes);
        finalScore = (TextView) findViewById(R.id.result);
        failView = (TextView) findViewById(R.id.fails);
        shareButton = (Button) findViewById(R.id.submit);
        returnButton = (Button) findViewById(R.id.returnMC);


        updateScore(QuestionLibrary.score);
        updatePass(QuestionLibrary.pass);
        updateFail(QuestionLibrary.fail);
        updateFinalScore(QuestionLibrary.score);

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
    public void onClickShareResult() {
        String results = ("You passed " + Integer.toString(QuestionLibrary.pass) + " times and failed " + Integer.toString(QuestionLibrary.fail) + " times.");
        // String[] results = new String[]{"Your score is " + Integer.toString(QuestionLibrary.score), "Passed: " + Integer.toString(QuestionLibrary.pass),"Failed: " + Integer.toString(QuestionLibrary.fail)};
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, results);
        String chooserTitle = getString(R.string.chooser);
        Intent chosenIntent = Intent.createChooser(intent, chooserTitle);
        startActivity(chosenIntent);
    }

    public void onClickReturn() {
        Intent intent2 = new Intent(this, MultipleChoiceActivity.class);
        startActivity(intent2);
    }
}
