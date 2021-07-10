package ru.kau.mygtd2.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

public class MyAxisStatFormatter implements IAxisValueFormatter
{

    private final DecimalFormat mFormat;

    public MyAxisStatFormatter() {
        mFormat = new DecimalFormat("###");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mFormat.format(value);
    }
}
