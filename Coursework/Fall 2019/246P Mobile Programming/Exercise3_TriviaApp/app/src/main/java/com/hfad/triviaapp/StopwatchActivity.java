package com.hfad.triviaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Locale;

public class StopwatchActivity extends Activity {

    private EditText mcCountdown;
    private EditText fCountdown;
    public static String mcCDTimeLimit;
    public static String fCDTimeLimit;

    public static int seconds = 0;
    public static boolean running;
    public static boolean runMC = true;
    public static boolean runF = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        mcCountdown = (EditText) findViewById(R.id.edit_timer_mc);
        fCountdown = (EditText) findViewById(R.id.edit_timer_f);

        Switch mcSwitch = (Switch) findViewById(R.id.mc_switch);
        mcSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    runMC = true;
                } else {
                    runMC = false;
                }
            }
        });

        Switch fSwitch = (Switch) findViewById(R.id.f_switch);
        fSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    runF = true;
                } else {
                    runF = false;
                }
            }
        });

        final Switch imageButton = (Switch) findViewById(R.id.toggle_default_image);
        imageButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final ImageButton imgButton = (ImageButton)findViewById(R.id.start_imageButton);
                final Button button = (Button)findViewById(R.id.start_button);
                if (isChecked) {
                    imgButton.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                } else {
                    imgButton.setVisibility(View.VISIBLE);
                    button.setVisibility(View.GONE);
                }
            }
        });

        if (savedInstanceState != null ) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
        }
        runTimer();
    }


    public void onClickStart(View view) {
        running = true;
        mcCDTimeLimit = mcCountdown.getText().toString();
        fCDTimeLimit = fCountdown.getText().toString();
        Intent intent = new Intent(this, MultipleChoiceActivity.class);
        startActivity(intent);
    }

    private void runTimer() {
        final TextView timeView = (TextView)findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if(running) {
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running",running);
    }




}
