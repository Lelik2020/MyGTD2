package ru.kau.mygtd2.utils;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

import ru.kau.mygtd2.objects.Statistic;

public class MyDateAxisValueFormatter implements IAxisValueFormatter {

    private final List<Statistic> myValue;

    private final BarLineChartBase<?> chart;

    public MyDateAxisValueFormatter(List<Statistic> myValue, BarLineChartBase<?> chart) {
        this.myValue = myValue;
        this.chart = chart;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if (value >= 0 && value < myValue.size()) {
            return myValue.get((int) value).getTitle1();
        } else {
            return "";
        }
    }
}
