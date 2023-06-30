package ru.kau.mygtd2.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.enums.TypeSetting;

public class Settings {

    static SharedPreferences settings = MyApplication.getContext().getSharedPreferences("MyGTD4",MODE_PRIVATE);
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
            /*case CURRENTDATE:
                ret = Utils.dateToString(DEFAULT_DATEFORMAT, new Date(settings.getLong("currentdate", (new Date()).getTime())));
                break;
            case USECURRENTSYSTEMDATE:
                ret = settings.getBoolean("usecurrentsystemdate", true) ? "TRUE" : "FALSE";
                break;*/
        }


        return ret;
    }

    public static Long getLongSetting(TypeSetting type){
        Long ret = null;

        switch (type){

            case CURRENTDATE:
                //ret = settings.getLong("currentdate", (new Date()).getTime());
                ret = settings.getLong("currentdate", 0);
                break;

        }


        return ret;
    }

    public static Boolean getBooleanSetting(TypeSetting type){
        Boolean ret = null;

        switch (type){

            case USECURRENTSYSTEMDATE:
                //ret = settings.getLong("currentdate", (new Date()).getTime());
                ret = settings.getBoolean("usecurrentsystemdate", true);
                break;

            case SHOWONLYACTIVEPROJECTS:
                //ret = settings.getLong("currentdate", (new Date()).getTime());
                ret = settings.getBoolean("showonlyactiveprojects", false);
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

    public static void setLongSettings(TypeSetting type, Long value){
        prefEditor = settings.edit();

        switch (type){
            case CURRENTDATE:
                prefEditor.putLong("currentdate", value);
                break;
        }
        prefEditor.apply();
    }

    public static void setBooleanSettings(TypeSetting type, Boolean value){

        prefEditor = settings.edit();

        switch (type) {
            case USECURRENTSYSTEMDATE:
                prefEditor.putBoolean("usecurrentsystemdate", value);
                break;

            case SHOWONLYACTIVEPROJECTS:
                //ret = settings.getLong("currentdate", (new Date()).getTime());
                prefEditor.putBoolean("showonlyactiveprojects", value);
                break;
        }
        prefEditor.apply();

    }


}
