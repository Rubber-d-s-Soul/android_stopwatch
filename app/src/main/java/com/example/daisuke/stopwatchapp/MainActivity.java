package com.example.daisuke.stopwatchapp;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //メンバ変数
    private long startTime;
    private long elaplsedTime = 0l;

    private Handler handler = new Handler();
    private Runnable updateTimer;

    private Button startButton;
    private Button stopButton;
    private Button resetButton;
    private TextView timerLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        resetButton = (Button) findViewById(R.id.resetButton);
        timerLabel = (TextView) findViewById(R.id.timer_label);

        setButtonState(true,false,false);
    }

    public void setButtonState(boolean start,boolean stop,boolean reset){
        startButton.setEnabled(start);
        stopButton.setEnabled(stop);
        resetButton.setEnabled(reset);
    }

    public void startTimer(View view){
        //startTimeの取得
        startTime = SystemClock.elapsedRealtime(); //起動してからの経過時間（ミリ秒）

        //一定時間ごとに現在の経過時間を表示
        //Handler -> Runnable -> UI
        updateTimer = new Runnable() {
            @Override
            public void run() {
                long t = SystemClock.elapsedRealtime() - startTime + elaplsedTime;
                SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS", Locale.US);
                timerLabel.setText(sdf.format(t));
                handler.removeCallbacks(updateTimer);
                handler.postDelayed(updateTimer,10);
            }
        };
        handler.postDelayed(updateTimer,10);


        //ボタンの操作
        setButtonState(false,true,false);
    }

    public void stopTimer(View view){
        elaplsedTime += SystemClock.elapsedRealtime() -startTime;

        //タイマーを止めるにはHandlerからrannableを削除
        handler.removeCallbacks(updateTimer);

        //ボタンの操作
        setButtonState(true,false,true);
    }

    public void resetTimer(View view){
        elaplsedTime = 0l;
        timerLabel.setText(R.string.timer_label);
        setButtonState(true,false,false);
    }
}
