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

        Date startDate = null, todayWithZeroTime = null;
        try {
            startDate = dateFormat.parse(START_DATE);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int c_year, c_month, c_day;

        Calendar c_cal = Calendar.getInstance();
        c_cal.setTime(todayWithZeroTime);
        c_year = c_cal.get(Calendar.YEAR);
        c_month = c_cal.get(Calendar.MONTH);
        c_day = c_cal.get(Calendar.DAY_OF_MONTH);

        Calendar e_cal = Calendar.getInstance();
        e_cal.setTime(startDate);

        int e_year = e_cal.get(Calendar.YEAR);
        int e_month = e_cal.get(Calendar.MONTH);
        int e_day = e_cal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(c_year, c_month, c_day);
        date2.clear();
        date2.set(e_year, e_month, e_day);

        long diff = date1.getTimeInMillis() - date2.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        return (int) dayCount + 1;
    }

    public static void setAlarm(Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, getAlarmTime(), pendingIntent);


    }

    private static long getAlarmTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, ALARM_HOUR);
        calendar.set(Calendar.MINUTE, ALARM_MINUTE);
        calendar.set(Calendar.SECOND, ALARM_SECOND);
        return calendar.getTimeInMillis();
    }
}
