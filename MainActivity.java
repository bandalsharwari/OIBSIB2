package com.example.oasistask5;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvTimer;
    private Button btnStartStop;
    private Button btnHold;

    private Handler handler;
    private boolean isRunning;
    private long startTime;
    private long elapsedTime;
    private long holdTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTimer = findViewById(R.id.tvTimer);
        btnStartStop = findViewById(R.id.btnStartStop);
        btnHold = findViewById(R.id.btnHold);

        handler = new Handler();
        isRunning = false;
        startTime = 0;
        elapsedTime = 0;
        holdTime = 0;
    }

    public void onStartStopClicked(View view) {
        if (isRunning) {
            // Stop the stopwatch
            elapsedTime += System.currentTimeMillis() - startTime;
            handler.removeCallbacks(updateTimer);
            isRunning = false;
            btnStartStop.setText("Start");
            btnHold.setEnabled(false);
        } else {
            // Start the stopwatch
            startTime = System.currentTimeMillis();
            handler.post(updateTimer);
            isRunning = true;
            btnStartStop.setText("Stop");
            btnHold.setEnabled(true);
        }
    }

    public void onHoldClicked(View view) {
        if (isRunning) {
            // Hold the stopwatch
            holdTime = System.currentTimeMillis();
            handler.removeCallbacks(updateTimer);
            isRunning = false;
            btnHold.setText("Resume");
        } else {
            // Resume the stopwatch
            long holdDuration = System.currentTimeMillis() - holdTime;
            startTime += holdDuration;
            handler.post(updateTimer);
            isRunning = true;
            btnHold.setText("Hold");
        }
    }

    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - startTime;

            int hours = (int) (elapsedTime / (1000 * 60 * 60));
            int minutes = (int) ((elapsedTime / (1000 * 60)) % 60);
            int seconds = (int) ((elapsedTime / 1000) % 60);

            String timerText = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            tvTimer.setText(timerText);

            handler.postDelayed(this, 1000); // Update every second
        }
    };
}
