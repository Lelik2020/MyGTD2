package ru.kau.mygtd2.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.enums.TypeSetting;

public class Settings {

    static SharedPreferences settings = MyApplication.getContext().getSharedPreferences("MyGTD3",MODE_PRIVATE);
    static SharedPreferences.Editor prefEditor;

    public static String getStringSetting(TypeSetting type){
        String ret = null;

        switch (type){
            case IPSERVERBACKUP:
                ret = settings.getString("ipaddressbackup", "Не установлен");
                break;
            case IPSERVERSYNC:
                ret = settings.getString("ipaddresssync", "Не установлен");
                break;
            case TYPEDEVICE:
                ret = settings.getString("typedevice", "Не установлен");
                break;
        }


        return ret;
    }

    public static void setStringSettings(TypeSetting type, String value){
        prefEditor = settings.edit();

        switch (type){
            case IPSERVERBACKUP:
                prefEditor.putString("ipaddressbackup", value);
                break;
            case IPSERVERSYNC:
                prefEditor.putString("ipaddresssync", value);
                break;
            case TYPEDEVICE:
                prefEditor.putString("typedevice", value);
                break;
        }
        prefEditor.apply();


    }


}
