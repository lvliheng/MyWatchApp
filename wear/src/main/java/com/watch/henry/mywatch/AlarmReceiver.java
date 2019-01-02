package com.watch.henry.mywatch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context);
    }

    private void showNotification(Context context) {

        String day = String.valueOf(Utils.getDays());
        String days = day + " days";

        SpannableString spannableString = new SpannableString(days);
        ForegroundColorSpan foregroundColorSpanOrange = new ForegroundColorSpan(context.getColor(R.color.orange));
        spannableString.setSpan(foregroundColorSpanOrange, 0, day.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ForegroundColorSpan foregroundColorSpanGray = new ForegroundColorSpan(context.getColor(android.R.color.darker_gray));
        spannableString.setSpan(foregroundColorSpanGray, day.length(), days.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        StyleSpan styleSpan = new StyleSpan(Typeface.ITALIC);
        spannableString.setSpan(styleSpan, 0, days.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.vivid_icon)
                .setContentTitle(spannableString);

        NotificationManagerCompat.from(context).notify(0, builder.build());
    }
}
