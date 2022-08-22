package ru.kau.mygtd2.utils;

//import android.arch.persistence.room.TypeConverter;

import androidx.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.kau.mygtd2.common.enums.PrStatus;
import ru.kau.mygtd2.common.enums.Status;
import ru.kau.mygtd2.common.enums.TypeOfInfo;
import ru.kau.mygtd2.common.enums.TypeOfTask;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String dateToShortString(Date date) {
        return date == null ? null : Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), date);
    }

    @TypeConverter
    public static Status intToStatus(int status){

        return Status.from(status);
    }

    @TypeConverter
    public static TypeOfTask intToTypeOfTask(int typeOfTask) {return TypeOfTask.from(typeOfTask); }

    @TypeConverter
    public static int typeOfTaskToInt(TypeOfTask typeOfTask) {return TypeOfTask.to(typeOfTask); }

    @TypeConverter
    public static TypeOfInfo intToTypeOfInfo(int typeOfInfo) {return TypeOfInfo.from(typeOfInfo); }

    @TypeConverter
    public static int typeOfInfoToInt(TypeOfInfo typeOfInfo) {return TypeOfInfo.to(typeOfInfo); }

    @TypeConverter
    public static int statusToInt(Status status){

        return Status.to(status);
    }

    @TypeConverter
    public static int prStatusToInt(PrStatus prStatus){return PrStatus.to(prStatus);}

    @TypeConverter
    public static PrStatus intToPrStatus(int status){
        return PrStatus.from(status);
    }

    @TypeConverter
    public static Boolean intToBoolean(int value){
        return value != 0? true: false;
    }
    @TypeConverter
    public static int booleanToInt(Boolean value){
        if (value == null){
            value = false;
        }
        return value.booleanValue() == true? 1 : 0;
    }

}
