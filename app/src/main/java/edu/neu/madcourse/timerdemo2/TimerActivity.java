package edu.neu.madcourse.timerdemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class TimerActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 40000; //20sec

    //    TODO Accept Input from user & store it in  START_TIME_IN_MILLIS
    int progress;

    private TextView CountDown_Tv;
    private Button StartPauseButton;
    private Button ResetButton;

    private CountDownTimer MyCountDownTimer;
    private boolean TimerRunning;

    private long TimeLeftInMillis = START_TIME_IN_MILLIS;

    MaterialProgressBar MyProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        CountDown_Tv = findViewById(R.id.TimerTV);

        StartPauseButton = findViewById(R.id.StartPauseButton);
        ResetButton = findViewById(R.id.ResetButton);
        MyProgressBar = findViewById(R.id.circular_progress_bar);

        StartPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        ResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateCountDownText();

        MyProgressBar.setVisibility(View.VISIBLE);
        MyProgressBar.setProgress(0);
        MyProgressBar.setMax(100);


    }

    private void startTimer() {
        MyCountDownTimer = new CountDownTimer(START_TIME_IN_MILLIS, 1000) {
            double numberOfSeconds = START_TIME_IN_MILLIS/1000; // Ex : 20000/1000 = 20
            double  factor = 100/numberOfSeconds; // 100/20 = 5, for each second multiply this, for sec 1 progressPercentage = 1x5 =5, for sec 5 progressPercentage = 5x5 = 25, for sec 20 progressPercentage = 20x5 =100
            @Override
            public void onTick(long millisUntilFinished) {
                TimeLeftInMillis = millisUntilFinished;
                updateCountDownText(); //  Updating CountDown_Tv
                double secondsRemaining = millisUntilFinished / 1000;
                double progressPercentage = (numberOfSeconds-secondsRemaining) * factor ;
                MyProgressBar.setProgress((int)progressPercentage);
            }

            @Override
            public void onFinish() {
                TimerRunning = false;
                CountDown_Tv.setText("00:00");
                MyProgressBar.setProgress(100);
                StartPauseButton.setText("Start");
                StartPauseButton.setVisibility(View.INVISIBLE);
                ResetButton.setVisibility(View.VISIBLE);
            }
        }.start();

        TimerRunning = true;
        StartPauseButton.setText("Pause");
        ResetButton.setVisibility(View.INVISIBLE);


    }

    private void pauseTimer() {
        MyCountDownTimer.cancel();
        TimerRunning = false;
        StartPauseButton.setText("Resume");
        ResetButton.setVisibility(View.VISIBLE);
        MyProgressBar.clearAnimation();
    }

    private void resetTimer() {
        TimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        ResetButton.setVisibility(View.INVISIBLE);
        StartPauseButton.setVisibility(View.VISIBLE);

        MyProgressBar.setProgress(0);
        StartPauseButton.setText("Start");
    }

    private void updateCountDownText() {
        int minutes = (int) (TimeLeftInMillis / 1000) / 60;
        int seconds = (int) (TimeLeftInMillis / 1000) % 60;


        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        //        String newtime = hours + ":" + minutes + ":" + seconds;

        CountDown_Tv.setText(timeLeftFormatted);

    }

}