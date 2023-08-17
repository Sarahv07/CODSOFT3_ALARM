package com.sneha.clock;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView timeTextView;

    private final Handler handler = new Handler();
    private final Runnable updateTimeTask = new Runnable() {
        public void run() {
            updateCurrentTime();
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeTextView = findViewById(R.id.timeTextView);
        View setAlarmButton = findViewById(R.id.setAlarmButton);

        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlarmSetting();
            }
        });

        handler.post(updateTimeTask);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateTimeTask);
    }

    private void updateCurrentTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String currentTime = timeFormat.format(new Date());
        timeTextView.setText(currentTime);
    }
    
    private void openAlarmSetting() {
        Intent intent = new Intent(this, AlarmSettingActivity.class);
        startActivity(intent);
    }
}
