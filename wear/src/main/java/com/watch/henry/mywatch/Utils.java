package com.watch.henry.mywatch;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {


    private static String START_DATE = "19/08/2016";
    private static int ALARM_HOUR = 8;
    private static int ALARM_MINUTE = 19;
    private static int ALARM_SECOND = 0;

    public static int getDays() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Date startDate = null;
        Date currDate = null;
        try {
            startDate = dateFormat.parse(START_DATE);
            currDate = dateFormat.parse(dateFormat.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (startDate != null && currDate != null) {
            long diff = currDate.getTime() - startDate.getTime();
            float dayCount = (float) diff / (24 * 60 * 60 * 1000);
            return (int) dayCount + 1;
        }

        return 0;
    }

    public static void setAlarm(Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getAlarmTime(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private static long getAlarmTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, ALARM_HOUR);
        calendar.set(Calendar.MINUTE, ALARM_MINUTE);
        calendar.set(Calendar.SECOND, ALARM_SECOND);
        return calendar.getTimeInMillis();
    }
}
