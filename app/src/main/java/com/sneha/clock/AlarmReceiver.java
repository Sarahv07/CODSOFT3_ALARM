package com.sneha.clock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action != null) {
            if (action.equals("snooze_action")) {
                // Handle snooze action
            } else if (action.equals("dismiss_action")) {
                // Handle dismiss action
            }
        }
    }
}

