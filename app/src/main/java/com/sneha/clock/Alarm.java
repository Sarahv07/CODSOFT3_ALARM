package com.sneha.clock;

public class Alarm {
    private int hour;
    private int minute;
    private String tone;
    private boolean isEnabled;

    private String time;


    public Alarm(int hour, int minute, String tone, boolean isEnabled) {
        this.hour = hour;
        this.minute = minute;
        this.tone = tone;
        this.isEnabled = isEnabled;

        this.time = String.format("%02d:%02d", hour, minute);
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getTone() {
        return tone;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
    public String getTime() {
        return time;
    }
}




