package ru.kau.mygtd2.utils.info.wrapper;

import android.content.Context;
import android.content.SharedPreferences;

import ru.kau.mygtd2.utils.Objects;
import ru.kau.mygtd2.utils.TxtUtils;

public class PasswordState {

    private static PasswordState instance = new PasswordState();

    public static PasswordState get() {
        return instance;
    }

    private static final String PASSWORD_STATE = "PasswordState";

    public boolean isFingerPrintPassword = false;
    public String appPassword = "";


    public boolean hasPassword(){
        return TxtUtils.isNotEmpty(PasswordState.get().appPassword) || PasswordState.get().isFingerPrintPassword;

    }

    public void save(final Context a) {
        if (a == null) {
            return;
        }
        SharedPreferences sp = a.getSharedPreferences(PASSWORD_STATE, Context.MODE_PRIVATE);
        Objects.saveToSP(PasswordState.get(), sp);
    }

    public void load(final Context a) {
        if (a == null) {
            return;
        }
        SharedPreferences sp = a.getSharedPreferences(PASSWORD_STATE, Context.MODE_PRIVATE);
        Objects.loadFromSp(this, sp);
    }

}
