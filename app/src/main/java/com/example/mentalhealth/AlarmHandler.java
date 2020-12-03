package com.example.mentalhealth;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmHandler {
    private Context context;

    public AlarmHandler(Context context) {
        this.context = context;
    }
    public void setAlarmManager(){
        Intent intent = new Intent(context,ExecuteService.class);
        PendingIntent sender = PendingIntent.getBroadcast(context,2,intent,0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP,120000,60000,sender);

    }
    public void cancelAlarmManager(){
        Intent intent = new Intent(context,ExecuteService.class);
        PendingIntent sender = PendingIntent.getBroadcast(context,2,intent,0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(sender);
    }
}
