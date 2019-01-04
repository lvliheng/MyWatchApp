package com.watch.henry.mywatch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context);
    }

    private void showNotification(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.vivid_icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.header))
                .setContentTitle(getNotificationContent(context));

        NotificationManagerCompat.from(context).notify(0, builder.build());

        new MyHandler(context).sendEmptyMessageDelayed(0, 1000 * 60);
    }

    private SpannableString getNotificationContent(Context context) {
        String day = String.valueOf(Utils.getDays());
        String days = day + " days";

        SpannableString spannableString = new SpannableString(days);

        ForegroundColorSpan foregroundColorSpanOrange = new ForegroundColorSpan(context.getColor(R.color.orange));
        spannableString.setSpan(foregroundColorSpanOrange, 0, day.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(1.2f);
        spannableString.setSpan(relativeSizeSpan, 0, day.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        ForegroundColorSpan foregroundColorSpanGray = new ForegroundColorSpan(context.getColor(android.R.color.darker_gray));
        spannableString.setSpan(foregroundColorSpanGray, day.length(), days.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        StyleSpan styleSpan = new StyleSpan(Typeface.ITALIC);
        spannableString.setSpan(styleSpan, 0, days.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        return spannableString;
    }

    private static class MyHandler extends Handler{

        private Context context;

        private MyHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Utils.setAlarm(context);
                    break;
            }
        }
    }

}
