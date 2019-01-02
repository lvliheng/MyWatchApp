package com.watch.henry.mywatch;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Calendar;

/**
 * Created by lvliheng on 16/11/24.
 */
public class SplashActivity extends Activity {


    private static final int HANDLER_INTENT_MAIN = 0;

    private TextView splashDaysTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        initView();
        startCountAnimation();
    }

    private void startCountAnimation() {
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, Utils.getDays());
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                splashDaysTv.setText(String.valueOf((int) animation.getAnimatedValue()));
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                MyHandler handler = new MyHandler(SplashActivity.this);
                handler.sendEmptyMessageDelayed(HANDLER_INTENT_MAIN, 1000);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    private void initView() {
        splashDaysTv = (TextView) findViewById(R.id.splash_days_tv);
    }

    private static class MyHandler extends Handler{

        WeakReference<SplashActivity> weakReference;

        private MyHandler(SplashActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SplashActivity activity = weakReference.get();
            switch (msg.what) {
                case HANDLER_INTENT_MAIN:
                    activity.intentToMain();
                    activity.setAlarm();
                    break;
            }
        }
    }

    private void intentToMain() {
        // TODO: 2019/1/2  
        finish();
    }

    private void setAlarm() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, getAlarmTime(), pendingIntent);
    }

    private long getAlarmTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 22);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }
}
