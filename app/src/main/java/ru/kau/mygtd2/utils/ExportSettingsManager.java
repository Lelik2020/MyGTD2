package ru.kau.mygtd2.utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExportSettingsManager {

    public static String getSampleJsonConfigName(Context a, String ext) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String time = format.format(new Date());
            String name = String.format("%s%s", time, ext);
            return name;
        } catch (Exception e) {
            LOG.e(e);
            return "pdf_reader" + ext;
        }
    }




}
