package ru.kau.mygtd2.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import ru.kau.mygtd2.MyGTDApp;

public class Vibro {

    public static void vibrate() {
        vibrate(100);
    }

    @TargetApi(26)
    public static void vibrate(long time) {
        if (AppState.get().isVibration) {
            if (Build.VERSION.SDK_INT >= 26) {
                ((Vibrator) MyGTDApp.context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(time, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                ((Vibrator) MyGTDApp.context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(time);
            }
        }
        LOG.d("Vibro", "vibrate", time);
    }

}
