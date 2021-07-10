package ru.kau.mygtd2.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Fill;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.activities.MainActivity;
import ru.kau.mygtd2.adapters.StatisticAdapter;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.objects.Statistic;
import ru.kau.mygtd2.utils.DemoBase;
import ru.kau.mygtd2.utils.MyAxisStatFormatter;
import ru.kau.mygtd2.utils.MyDateAxisValueFormatter;
import ru.kau.mygtd2.utils.MyMarkerView;
import ru.kau.mygtd2.utils.Utils;
import ru.kau.mygtd2.utils.XYMarkerView;

import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT;
import static ru.kau.mygtd2.utils.Const.LSTCATEGORYBROWN;
import static ru.kau.mygtd2.utils.Const.LSTCATEGORYGREEN;
import static ru.kau.mygtd2.utils.Const.LSTCATEGORYRED;
import static ru.kau.mygtd2.utils.Const.LSTSTATUSCOMPLETED;

public class StatisticFragment extends DemoBase implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    private RecyclerView recyclerView;
    private StatisticAdapter mainAdapter;

    private BarChart chart2;
    private SeekBar seekBarX2, seekBarY2;
    private TextView tvX2, tvY2;


    private BarChart chart;
    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;

    List<Statistic> lstStat;
    //List<Statistic> lstStat2;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_barchart);
        View rootView = inflater.inflate(R.layout.statistic_fragment2, null);

        //setTitle("BarChartActivityMultiDataset");

        tvX = rootView.findViewById(R.id.tvXMax);
        tvX.setTextSize(10);
        tvY = rootView.findViewById(R.id.tvYMax);

        seekBarX = rootView.findViewById(R.id.seekBar1);
        seekBarX.setMax(50);
        seekBarX.setOnSeekBarChangeListener(this);

        seekBarY = rootView.findViewById(R.id.seekBar2);
        seekBarY.setOnSeekBarChangeListener(this);

        chart = rootView.findViewById(R.id.chart1);
        chart.setOnChartValueSelectedListener(this);
        chart.getDescription().setEnabled(false);

//        chart.setDrawBorders(true);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawBarShadow(false);

        chart.setDrawGridBackground(false);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart

        seekBarX.setProgress(10);
        seekBarY.setProgress(100);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setTypeface(tfLight);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setTypeface(tfLight);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((int) value);
            }
        });

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        chart.getAxisRight().setEnabled(false);

        return rootView;
    }


    @SuppressLint("ResourceAsColor")
    //@Override
    public View onCreateView2222(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.statistic_fragment2, null);

        //com.github.mikephil.charting.charts.BarChart;


        ActionBar toolbar = ((MainActivity) getActivity()).getSupportActionBar();

        //toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.main_recyclerview);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(2, R.color.black_1);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(20);

        //ShapeDrawable drawable = new ShapeDrawable();
        //drawable.getPaint().setColor(Color.BLACK);
        //drawable.getPaint().setStrokeWidth(5);


        recyclerView.setBackground(drawable);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<String> lstDates = new ArrayList<String>();
        try {
            lstDates = Utils.getListDateBetweenDates(new Date(MyApplication.getDatabase().taskDao().getMinDateClose()), new Date(MyApplication.getDatabase().taskDao().getMaxDateClose()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        lstStat = new ArrayList<Statistic>();
        //lstStat2 = new ArrayList<Statistic>();
        for(int i = 0; i < lstDates.size(); i++){
            Statistic stat = new Statistic();
            //Statistic stat2 = new Statistic();
            stat.setTitle1(lstDates.get(i));
            try {
                stat.setCount1(MyApplication.getDatabase().taskDao().getCountTasks(Utils.getStartOfDay(DEFAULT_DATEFORMAT.parse(lstDates.get(i))).getTime(),
                                                                                    Utils.getEndOfDay(DEFAULT_DATEFORMAT.parse(lstDates.get(i))).getTime(),
                                                                                    LSTSTATUSCOMPLETED, LSTCATEGORYGREEN));

                stat.setCount2(MyApplication.getDatabase().taskDao().getCountTasks(Utils.getStartOfDay(DEFAULT_DATEFORMAT.parse(lstDates.get(i))).getTime(),
                        Utils.getEndOfDay(DEFAULT_DATEFORMAT.parse(lstDates.get(i))).getTime(),
                        LSTSTATUSCOMPLETED, LSTCATEGORYRED));

                stat.setCount3(MyApplication.getDatabase().taskDao().getCountTasks(Utils.getStartOfDay(DEFAULT_DATEFORMAT.parse(lstDates.get(i))).getTime(),
                        Utils.getEndOfDay(DEFAULT_DATEFORMAT.parse(lstDates.get(i))).getTime(),
                        LSTSTATUSCOMPLETED, LSTCATEGORYBROWN));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            lstStat.add(stat);
            //lstStat2.add(stat2);
        }



        mainAdapter = new StatisticAdapter(getActivity(), lstStat);
        //mainAdapter.setClickListener(this);

        recyclerView.setAdapter(mainAdapter);

        tvX2 = rootView.findViewById(R.id.tvXMax2);
        tvY2 = rootView.findViewById(R.id.tvYMax2);

        seekBarX2 = rootView.findViewById(R.id.seekBar3);
        seekBarY2 = rootView.findViewById(R.id.seekBar4);

        seekBarY2.setOnSeekBarChangeListener(this);
        seekBarX2.setOnSeekBarChangeListener(this);


        chart2 = rootView.findViewById(R.id.chart2);

        chart2.setDrawBarShadow(false);
        chart2.setDrawValueAboveBar(true);

        chart2.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        //chart2.setMaxVisibleValueCount(60);
        chart2.setMaxVisibleValueCount(Utils.getMaxFromArray(lstStat));

        // scaling can now only be done on x- and y-axis separately
        chart2.setPinchZoom(false);

        chart2.setDrawGridBackground(false);

        //IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(chart2);
        IAxisValueFormatter xAxisFormatter = new MyDateAxisValueFormatter(lstStat, chart2);



        XAxis xAxis = chart2.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(tfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1); // only intervals of 1 day
        xAxis.setLabelCount(15);
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setLabelRotationAngle(90);

        //IAxisValueFormatter custom = new MyAxisValueFormatter();
        IAxisValueFormatter custom = new MyAxisStatFormatter();



        YAxis leftAxis = chart2.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setLabelCount(2, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = chart2.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(tfLight);
        rightAxis.setLabelCount(2, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = chart2.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        XYMarkerView mv = new XYMarkerView(this.getContext(), xAxisFormatter);
        mv.setChartView(chart2); // For bounds control
        chart2.setMarker(mv); // Set the marker to the chart

        // setting data
        seekBarY2.setProgress(3);
        seekBarX2.setProgress(5);


        /*tvX = rootView.findViewById(R.id.tvXMax);
        tvY = rootView.findViewById(R.id.tvYMax);

        seekBarX = rootView.findViewById(R.id.seekBar1);
        seekBarY = rootView.findViewById(R.id.seekBar2);

        seekBarY.setOnSeekBarChangeListener(this);
        seekBarX.setOnSeekBarChangeListener(this);

        chart = rootView.findViewById(R.id.chart1);
        chart.setOnChartValueSelectedListener(this);

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);

        chart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);
        // chart.setDrawYLabels(false);

        xAxisFormatter = new DayAxisValueFormatter(chart);

        xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(tfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        custom = new MyAxisValueFormatter();

        leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(tfLight);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        mv = new XYMarkerView(this.getContext(), xAxisFormatter);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart

        // setting data
        seekBarY.setProgress(50);
        seekBarX.setProgress(12);*/


        return rootView;

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    private final RectF onValueSelectedRectF = new RectF();

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;

        RectF bounds = onValueSelectedRectF;
        chart2.getBarBounds((BarEntry) e, bounds);
        MPPointF position = chart.getPosition(e, YAxis.AxisDependency.LEFT);

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        Log.i("x-index",
                "low: " + chart2.getLowestVisibleX() + ", high: "
                        + chart2.getHighestVisibleX());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        float groupSpace = 0.08f;
        float barSpace = 0.03f; // x4 DataSet
        float barWidth = 0.2f; // x4 DataSet
        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"

        int groupCount = seekBarX.getProgress() + 1;
        int startYear = 1980;
        int endYear = startYear + groupCount;

        tvX.setText(String.format(Locale.ENGLISH, "%d-%d", startYear, endYear));
        tvY.setText(String.valueOf(seekBarY.getProgress()));

        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
        ArrayList<BarEntry> values3 = new ArrayList<>();
        ArrayList<BarEntry> values4 = new ArrayList<>();

        float randomMultiplier = seekBarY.getProgress() * 100000f;

        for (int i = startYear; i < endYear; i++) {
            values1.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            values2.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            values3.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            values4.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
        }

        BarDataSet set1, set2, set3, set4;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) chart.getData().getDataSetByIndex(1);
            set3 = (BarDataSet) chart.getData().getDataSetByIndex(2);
            set4 = (BarDataSet) chart.getData().getDataSetByIndex(3);
            set1.setValues(values1);
            set2.setValues(values2);
            set3.setValues(values3);
            set4.setValues(values4);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            // create 4 DataSets
            set1 = new BarDataSet(values1, "Company A");
            set1.setColor(Color.rgb(104, 241, 175));
            set2 = new BarDataSet(values2, "Company B");
            set2.setColor(Color.rgb(164, 228, 251));
            set3 = new BarDataSet(values3, "Company C");
            set3.setColor(Color.rgb(242, 247, 158));
            set4 = new BarDataSet(values4, "Company D");
            set4.setColor(Color.rgb(255, 102, 0));

            BarData data = new BarData(set1, set2, set3, set4);
            data.setValueFormatter(new LargeValueFormatter());
            data.setValueTypeface(tfLight);

            chart.setData(data);
        }

        // specify the width each bar should have
        chart.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        chart.getXAxis().setAxisMinimum(startYear);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        chart.getXAxis().setAxisMaximum(startYear + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        chart.groupBars(startYear, groupSpace, barSpace);
        chart.invalidate();
    }

    /*@Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Activity", "Selected: " + e.toString() + ", dataSet: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Activity", "Nothing selected.");
    }*/

    //@Override
    public void onProgressChanged444(SeekBar seekBar, int progress, boolean fromUser) {

        //tvX.setText(String.valueOf(seekBarX.getProgress()));
        //tvY.setText(String.valueOf(seekBarY.getProgress()));

        //setData(seekBarX.getProgress(), seekBarY.getProgress());
        //chart.invalidate();

        tvX2.setText(String.valueOf(seekBarX2.getProgress()));
        tvY2.setText(String.valueOf(seekBarY2.getProgress()));

        setData2(seekBarX2.getProgress(), seekBarY2.getProgress());
        chart2.invalidate();
    }

    private void setData2(int count, float range) {

        float start = 0f;

        float groupSpace = 0.08f;
        float barSpace = 0.03f; // x4 DataSet
        float barWidth = 0.2f; // x4 DataSet

        int groupCount = seekBarX2.getProgress() + 1;
        int startYear = 1;
        //int endYear = startYear + groupCount;

        ArrayList<BarEntry> values = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
        ArrayList<BarEntry> values3 = new ArrayList<>();

        for (int i = (int) start; i < lstStat.size(); i++) {
            float val = lstStat.get(i).getCount1();
            float val2 = lstStat.get(i).getCount2();
            float val3 = lstStat.get(i).getCount2();

            //values.add(new BarEntry(i, val));
            //values2.add(new BarEntry(i, val2));
            //values3.add(new BarEntry(i, val3));

            values.add(new BarEntry(i, 2));
            values2.add(new BarEntry(i, 4));
            values3.add(new BarEntry(i, 1));


            /*if (Math.random() * 100 < 25) {
                values.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.star)));
            } else {
                values.add(new BarEntry(i, val));
            }*/
        }

        BarDataSet set1;
        BarDataSet set2;
        BarDataSet set3;

        /*if (chart2.getData() != null &&
                chart2.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart2.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart2.getData().notifyDataChanged();
            chart2.notifyDataSetChanged();

        } else*/

        if (chart2.getData() != null && chart2.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) chart2.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) chart2.getData().getDataSetByIndex(1);
            set3 = (BarDataSet) chart2.getData().getDataSetByIndex(2);
            //set4 = (BarDataSet) chart.getData().getDataSetByIndex(3);
            set1.setValues(values);
            set2.setValues(values2);
            set3.setValues(values3);
            //set4.setValues(values4);
            chart2.getData().notifyDataChanged();
            chart2.notifyDataSetChanged();

        } else  {
            set1 = new BarDataSet(values, "Количество закрытых задач");
            set2 = new BarDataSet(values2, "Количество закрытых задач 222");
            set3 = new BarDataSet(values3, "Количество закрытых задач 333");


            set1.setDrawIcons(false);
            set2.setDrawIcons(false);
            set3.setDrawIcons(false);

            int startColor1 = ContextCompat.getColor(this.getContext(), android.R.color.holo_orange_light);
            int startColor2 = ContextCompat.getColor(this.getContext(), android.R.color.holo_blue_light);
            int startColor3 = ContextCompat.getColor(this.getContext(), android.R.color.holo_orange_light);
            int startColor4 = ContextCompat.getColor(this.getContext(), android.R.color.holo_green_light);
            int startColor5 = ContextCompat.getColor(this.getContext(), android.R.color.holo_red_light);
            int endColor1 = ContextCompat.getColor(this.getContext(), android.R.color.holo_blue_dark);
            int endColor2 = ContextCompat.getColor(this.getContext(), android.R.color.holo_purple);
            int endColor3 = ContextCompat.getColor(this.getContext(), android.R.color.holo_green_dark);
            int endColor4 = ContextCompat.getColor(this.getContext(), android.R.color.holo_red_dark);
            int endColor5 = ContextCompat.getColor(this.getContext(), android.R.color.holo_orange_dark);

            List<Fill> gradientFills = new ArrayList<>();
            gradientFills.add(new Fill(startColor1, endColor1));
            gradientFills.add(new Fill(startColor2, endColor2));
            gradientFills.add(new Fill(startColor3, endColor3));
            gradientFills.add(new Fill(startColor4, endColor4));
            gradientFills.add(new Fill(startColor5, endColor5));

            //set1.setFills(gradientFills);
            //set2.setFills(gradientFills);
            set1.setColor(Color.rgb(104, 241, 175));
            set2.setColor(Color.rgb(164, 228, 251));
            set3.setColor(Color.rgb(242, 247, 158));

            //ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            //dataSets.add(set1);

            //BarData data = new BarData(set1, set2);
            BarData data = new BarData(set1, set2, set3);
            data.setValueTextSize(10f);
            data.setValueTypeface(tfLight);
            data.setBarWidth(0.9f);



            chart2.setData(data);

            chart2.getBarData().setBarWidth(barWidth);

            // restrict the x-axis range
            chart2.getXAxis().setAxisMinimum(startYear);

            // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
            chart2.getXAxis().setAxisMaximum(startYear + chart2.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
            chart2.groupBars(startYear, groupSpace, barSpace);
            chart2.invalidate();
        }
    }

    private void setData(int count, float range) {

        float start = 1f;

        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = (int) start; i < start + count; i++) {
            float val = (float) (Math.random() * (range + 1));

            if (Math.random() * 100 < 25) {
                values.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.star)));
            } else {
                values.add(new BarEntry(i, val));
            }
        }

        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            set1 = new BarDataSet(values, "The year 2017");

            set1.setDrawIcons(false);

            int startColor1 = ContextCompat.getColor(this.getContext(), android.R.color.holo_orange_light);
            int startColor2 = ContextCompat.getColor(this.getContext(), android.R.color.holo_blue_light);
            int startColor3 = ContextCompat.getColor(this.getContext(), android.R.color.holo_orange_light);
            int startColor4 = ContextCompat.getColor(this.getContext(), android.R.color.holo_green_light);
            int startColor5 = ContextCompat.getColor(this.getContext(), android.R.color.holo_red_light);
            int endColor1 = ContextCompat.getColor(this.getContext(), android.R.color.holo_blue_dark);
            int endColor2 = ContextCompat.getColor(this.getContext(), android.R.color.holo_purple);
            int endColor3 = ContextCompat.getColor(this.getContext(), android.R.color.holo_green_dark);
            int endColor4 = ContextCompat.getColor(this.getContext(), android.R.color.holo_red_dark);
            int endColor5 = ContextCompat.getColor(this.getContext(), android.R.color.holo_orange_dark);

            List<Fill> gradientFills = new ArrayList<>();
            gradientFills.add(new Fill(startColor1, endColor1));
            gradientFills.add(new Fill(startColor2, endColor2));
            gradientFills.add(new Fill(startColor3, endColor3));
            gradientFills.add(new Fill(startColor4, endColor4));
            gradientFills.add(new Fill(startColor5, endColor5));

            set1.setFills(gradientFills);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(tfLight);
            data.setBarWidth(0.9f);

            chart.setData(data);
        }
    }

    @Override
    public void onNothingSelected() { }

}
