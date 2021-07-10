package ru.kau.mygtd2.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
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
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.adapters.StatisticAdapter;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.objects.Statistic;
import ru.kau.mygtd2.utils.DemoBase;
import ru.kau.mygtd2.utils.MyAxisStatFormatter;
import ru.kau.mygtd2.utils.MyDateAxisValueFormatter;
import ru.kau.mygtd2.utils.MyMarkerView;
import ru.kau.mygtd2.utils.Utils;
import stream.custombutton.CustomButton;

import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT;
import static ru.kau.mygtd2.utils.Const.LSTCATEGORYBROWN;
import static ru.kau.mygtd2.utils.Const.LSTCATEGORYGREEN;
import static ru.kau.mygtd2.utils.Const.LSTCATEGORYRED;
import static ru.kau.mygtd2.utils.Const.LSTSTATUSCOMPLETED;

public class StatisticFragment2 extends DemoBase implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    private BarChart chart;
    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;

    private RecyclerView recyclerView;
    private CustomButton btnsevendays;
    private CustomButton btnfourteendays;
    private CustomButton btnthirtydays;
    private StatisticAdapter mainAdapter;
    List<Statistic> lstStat;
    List<Statistic> lstStat2;
    private int days = 7;
    List<String> lstDates2;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_barchart);
        View rootView = inflater.inflate(R.layout.activity_barchart, null);

        // ------------------------------------------------------------------------------------------------


        btnsevendays = rootView.findViewById(R.id.btnsevendays);
        btnfourteendays = rootView.findViewById(R.id.btnfourteendays);
        btnthirtydays = rootView.findViewById(R.id.btnthirtydays);

        btnsevendays.setPressStatus(true);

        btnsevendays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllButtonUpPress();
                btnsevendays.setPressStatus(true);
                days = 7;
                viewGraph(days, rootView);
            }
        });

        btnfourteendays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllButtonUpPress();
                btnfourteendays.setPressStatus(true);
                days = 14;
                viewGraph(days, rootView);
            }
        });

        btnthirtydays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllButtonUpPress();
                btnthirtydays.setPressStatus(true);
                days = 30;
                viewGraph(days, rootView);
            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.main_recyclerview);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(2, R.color.black_1);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(20);

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


        // ------------------------------------------------------------------------------------------------

        viewGraph(days, rootView);



        return rootView;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


        //float groupSpace = 0.08f;
        //float barSpace = 0.03f; // x4 DataSet
        //loat barWidth = 0.2f; // x4 DataSet
        float groupSpace = 0.1f;
        float barSpace = 0.05f; // x4 DataSet
        float barWidth = 0.25f; // x4 DataSet
        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"
        // (0.25 + 0.05) * 3 + 0.1

        int groupCount = seekBarX.getProgress();
        int startYear = 0;
        int endYear = startYear + groupCount;

        tvX.setText(String.format(Locale.ENGLISH, "%d-%d", startYear, endYear));
        tvY.setText(String.valueOf(seekBarY.getProgress()));

        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
        ArrayList<BarEntry> values3 = new ArrayList<>();
        //ArrayList<BarEntry> values4 = new ArrayList<>();

        //float randomMultiplier = seekBarY.getProgress() * 100000f;

        for (int i = startYear; i < endYear; i++) {
            values1.add(new BarEntry(i, (float) lstStat2.get(i).getCount1()));
            values2.add(new BarEntry(i, (float) lstStat2.get(i).getCount2()));
            values3.add(new BarEntry(i, (float) lstStat2.get(i).getCount3()));
            //values4.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
        }

        BarDataSet set1, set2, set3;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) chart.getData().getDataSetByIndex(1);
            set3 = (BarDataSet) chart.getData().getDataSetByIndex(2);
            //set4 = (BarDataSet) chart.getData().getDataSetByIndex(3);
            set1.setValues(values1);
            set2.setValues(values2);
            set3.setValues(values3);
            //set4.setValues(values4);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            // create 4 DataSets
            set1 = new BarDataSet(values1, "Количество закрытых 'зеленых' задач");
            //set1.setColor(Color.rgb(104, 241, 175));
            set1.setColor(Color.parseColor("#3CB371"));
            set2 = new BarDataSet(values2, "Количество закрытых 'красных' задач");
            //set2.setColor(Color.rgb(164, 228, 251));
            set2.setColor(Color.parseColor("#FF0000"));
            set3 = new BarDataSet(values3, "Количество закрытых 'коричневых' задач");
            //set3.setColor(Color.rgb(242, 247, 158));
            set3.setColor(Color.parseColor("#8B4513"));
            //set4 = new BarDataSet(values4, "Company D");
            //set4.setColor(Color.rgb(255, 102, 0));

            BarData data = new BarData(set1, set2, set3);
            data.setValueFormatter(new LargeValueFormatter());
            data.setValueTypeface(tfLight);

            chart.setData(data);
        }

        // specify the width each bar should have
        chart.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        chart.getXAxis().setAxisMinimum(startYear);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        //chart.getXAxis().setAxisMaximum(startYear + chart.getBarData().getGroupWidth(groupSpace, barSpace) * (groupCount + 1));
        chart.getXAxis().setAxisMaximum(groupCount);
        //chart.getXAxis().setAxisMaximum(4);
        chart.groupBars(startYear, groupSpace, barSpace);
        chart.invalidate();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        /*switch (item.getItemId()) {
            case R.id.viewGithub: {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/com/xxmassdeveloper/mpchartexample/BarChartActivityMultiDataset.java"));
                startActivity(i);
                break;
            }
            case R.id.actionToggleValues: {
                for (IBarDataSet set : chart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                chart.invalidate();
                break;
            }
            case R.id.actionTogglePinch: {
                if (chart.isPinchZoomEnabled())
                    chart.setPinchZoom(false);
                else
                    chart.setPinchZoom(true);

                chart.invalidate();
                break;
            }
            case R.id.actionToggleAutoScaleMinMax: {
                chart.setAutoScaleMinMaxEnabled(!chart.isAutoScaleMinMaxEnabled());
                chart.notifyDataSetChanged();
                break;
            }
            case R.id.actionToggleBarBorders: {
                for (IBarDataSet set : chart.getData().getDataSets())
                    ((BarDataSet) set).setBarBorderWidth(set.getBarBorderWidth() == 1.f ? 0.f : 1.f);

                chart.invalidate();
                break;
            }
            case R.id.actionToggleHighlight: {
                if (chart.getData() != null) {
                    chart.getData().setHighlightEnabled(!chart.getData().isHighlightEnabled());
                    chart.invalidate();
                }
                break;
            }
            *//*case R.id.actionSave: {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveToGallery();
                } else {
                    requestStoragePermission(chart);
                }
                break;
            }*//*
            case R.id.animateX: {
                chart.animateX(2000);
                break;
            }
            case R.id.animateY: {
                chart.animateY(2000);
                break;
            }
            case R.id.animateXY: {
                chart.animateXY(2000, 2000);
                break;
            }
        }*/
        return true;
    }

    /*@Override
    protected void saveToGallery() {
        saveToGallery(chart, "BarChartActivityMultiDataset");
    }*/

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Activity", "Selected: " + e.toString() + ", dataSet: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Activity", "Nothing selected.");
    }

    public void setAllButtonUpPress() {
        btnfourteendays.setPressStatus(false);
        btnsevendays.setPressStatus(false);
        btnthirtydays.setPressStatus(false);
    };

    public void viewGraph(int daysOfPeriod, View rootView){
        //setTitle("BarChartActivityMultiDataset");

        lstDates2 = new ArrayList<String>();
        try {
            Calendar calendar = Calendar.getInstance();
            //Date today = calendar.getTime();

            calendar.add(Calendar.DAY_OF_YEAR, - daysOfPeriod + 1);
            Date d1 = Utils.getStartOfDay(calendar.getTime());
            lstDates2 = Utils.getListDateBetweenDates(d1, new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        lstStat2 = new ArrayList<Statistic>();
        //lstStat2 = new ArrayList<Statistic>();
        for(int i = 0; i < lstDates2.size(); i++){
            Statistic stat = new Statistic();
            //Statistic stat2 = new Statistic();
            stat.setTitle1(lstDates2.get(i));
            try {
                stat.setCount1(MyApplication.getDatabase().taskDao().getCountTasks(Utils.getStartOfDay(DEFAULT_DATEFORMAT.parse(lstDates2.get(i))).getTime(),
                        Utils.getEndOfDay(DEFAULT_DATEFORMAT.parse(lstDates2.get(i))).getTime(),
                        LSTSTATUSCOMPLETED, LSTCATEGORYGREEN));

                stat.setCount2(MyApplication.getDatabase().taskDao().getCountTasks(Utils.getStartOfDay(DEFAULT_DATEFORMAT.parse(lstDates2.get(i))).getTime(),
                        Utils.getEndOfDay(DEFAULT_DATEFORMAT.parse(lstDates2.get(i))).getTime(),
                        LSTSTATUSCOMPLETED, LSTCATEGORYRED));

                stat.setCount3(MyApplication.getDatabase().taskDao().getCountTasks(Utils.getStartOfDay(DEFAULT_DATEFORMAT.parse(lstDates2.get(i))).getTime(),
                        Utils.getEndOfDay(DEFAULT_DATEFORMAT.parse(lstDates2.get(i))).getTime(),
                        LSTSTATUSCOMPLETED, LSTCATEGORYBROWN));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            lstStat2.add(stat);
            //lstStat2.add(stat2);
        }




        tvX = rootView.findViewById(R.id.tvXMax);
        tvX.setTextSize(10);
        tvY = rootView.findViewById(R.id.tvYMax);

        seekBarX = rootView.findViewById(R.id.seekBar1);
        seekBarX.setMax(lstDates2.size());
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

        seekBarX.setProgress(lstDates2.size());
        seekBarY.setProgress(1);

        Legend l = chart.getLegend();
        //l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        //l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        //l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //l.setDrawInside(true);
        l.setTypeface(tfLight);
        //l.setYOffset(0f);
        //l.setXOffset(0f);
        //l.setYEntrySpace(1f);
        l.setTextSize(6f);
        //l.setYOffset();

        XAxis xAxis = chart.getXAxis();
        xAxis.setTypeface(tfLight);
        xAxis.setTextSize(6f);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);

        IAxisValueFormatter xAxisFormatter = new MyDateAxisValueFormatter(lstStat2, chart);
        xAxis.setValueFormatter(xAxisFormatter);
        //xAxis.setLabelRotationAngle(90);
        xAxis.setLabelCount(lstDates2.size());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        /*xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((int) value);
            }
        });*/
        IAxisValueFormatter custom = new MyAxisStatFormatter();
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        //leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setValueFormatter(custom);
        leftAxis.setLabelCount(1, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        chart.getAxisRight().setEnabled(false);
    };


}
