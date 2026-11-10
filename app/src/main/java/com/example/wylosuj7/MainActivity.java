package com.example.wylosuj7;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvCounter, tvHits, tvMessage;
    private Button btnStart, btnStop;

    private Handler handler;
    private Runnable runnable;

    private int currentNumber = 0;
    private int hitsCount = 0;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCounter = findViewById(R.id.tvCounter);
        tvHits = findViewById(R.id.tvHits);
        tvMessage = findViewById(R.id.tvMessage);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        handler = new Handler(Looper.getMainLooper());

        runnable = new Runnable() {
            @Override
            public void run() {
                currentNumber++;
                if (currentNumber > 9) {
                    currentNumber = 0;
                }
                tvCounter.setText(String.valueOf(currentNumber));
                handler.postDelayed(this, 500);
            }
        };

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCounter();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopCounter();
            }
        });
    }

    private void startCounter() {
        isRunning = true;
        currentNumber = 0;
        tvCounter.setText(String.valueOf(currentNumber));
        tvMessage.setText("");

        btnStart.setEnabled(false);
        btnStop.setEnabled(true);

        handler.postDelayed(runnable, 500);
    }

    private void stopCounter() {
        if (!isRunning) return;

        isRunning = false;
        handler.removeCallbacks(runnable);

        btnStart.setEnabled(true);
        btnStop.setEnabled(false);

        if (currentNumber == 7) {
            tvMessage.setText("Trafienie!");
            hitsCount++;
            tvHits.setText("Trafienia: " + hitsCount);
        } else {
            tvMessage.setText("Pudło! Zatrzymano na " + currentNumber);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}