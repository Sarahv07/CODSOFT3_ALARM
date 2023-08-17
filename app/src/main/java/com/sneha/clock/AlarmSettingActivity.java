package com.sneha.clock;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AlarmSettingActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private Spinner toneSpinner;
    private Button setAlarmButton;
    private RecyclerView alarmsRecyclerView;
    private AlarmListAdapter alarmListAdapter;
    private List<Alarm> alarms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setting);

        timePicker = findViewById(R.id.timePicker);
        toneSpinner = findViewById(R.id.toneSpinner);
        setAlarmButton = findViewById(R.id.setAlarmButton);
        alarmsRecyclerView = findViewById(R.id.alarmsRecyclerView);

        setupToneSpinner();

        alarms = new ArrayList<>();

        alarmListAdapter = new AlarmListAdapter(alarms);
        alarmsRecyclerView.setAdapter(alarmListAdapter);
        alarmsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Create a notification channel
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Alarm Channel";
            String description = "Channel for alarm notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("alarm_channel", name, importance);
            channel.setDescription(description);

            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                String selectedTone = toneSpinner.getSelectedItem().toString();

                Alarm newAlarm = new Alarm(hour, minute, selectedTone, true);
                alarms.add(newAlarm);

                alarmListAdapter.notifyDataSetChanged();

                Toast.makeText(AlarmSettingActivity.this,
                        "Alarm set for " + hour + ":" + minute + " with tone: " + selectedTone,
                        Toast.LENGTH_SHORT).show();

                createAlarmNotification();
            }
        });
    }

    private void setupToneSpinner() {
        List<String> tones = new ArrayList<>();
        tones.add("Default Tone");
        tones.add("Tone 1");
        tones.add("Tone 2");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, tones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toneSpinner.setAdapter(adapter);
    }

    private void createAlarmNotification() {
        Intent snoozeIntent = new Intent(this, AlarmReceiver.class);
        snoozeIntent.setAction("snooze_action");
        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(
                this, 0, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        Intent dismissIntent = new Intent(this, AlarmReceiver.class);
        dismissIntent.setAction("dismiss_action");
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(
                this, 0, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "alarm_channel")
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle("Alarm is ringing")
                .setContentText("Time to wake up!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.drawable.ic_snooze, "Snooze", snoozePendingIntent)
                .addAction(R.drawable.ic_dismiss, "Dismiss", dismissPendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Request permission or handle accordingly
            return;
        }
        notificationManager.notify(123, builder.build());
    }
}
