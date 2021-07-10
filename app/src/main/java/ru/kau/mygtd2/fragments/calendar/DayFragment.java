package ru.kau.mygtd2.fragments.calendar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.desai.vatsal.mydynamiccalendar.EventModel;
import com.desai.vatsal.mydynamiccalendar.GetEventListListener;
import com.desai.vatsal.mydynamiccalendar.MyDynamicCalendar;
import com.desai.vatsal.mydynamiccalendar.OnEventClickListener;
import com.desai.vatsal.mydynamiccalendar.OnWeekDayViewClickListener;

import java.util.ArrayList;

import ru.kau.mygtd2.R;

public class DayFragment extends Fragment {

    private MyDynamicCalendar myCalendar;

    public static DayFragment newInstance(String s){
        DayFragment dayFragment = new DayFragment();
        Bundle bundle = new Bundle();
        bundle.putString("",s);
        dayFragment.setArguments(bundle);
        return dayFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.day_fragment, container, false);

        myCalendar = (MyDynamicCalendar) view.findViewById(R.id.myCalendar);

        myCalendar.setCalendarBackgroundColor("#eeeeee");
//        myCalendar.setCalendarBackgroundColor(R.color.gray);

        myCalendar.setHeaderBackgroundColor("#454265");
//        myCalendar.setHeaderBackgroundColor(R.color.black);

        myCalendar.setHeaderTextColor("#ffffff");
//        myCalendar.setHeaderTextColor(R.color.white);

        myCalendar.setNextPreviousIndicatorColor("#245675");
//        myCalendar.setNextPreviousIndicatorColor(R.color.black);

        myCalendar.setWeekDayLayoutBackgroundColor("#965471");
//        myCalendar.setWeekDayLayoutBackgroundColor(R.color.black);

        myCalendar.setWeekDayLayoutTextColor("#246245");
//        myCalendar.setWeekDayLayoutTextColor(R.color.black);

        myCalendar.isSaturdayOff(true, "#ffffff", "#ff0000");
        myCalendar.isSaturdayOff(true, R.color.white, R.color.red);

        myCalendar.isSundayOff(true, "#658745", "#254632");
        myCalendar.isSundayOff(true, R.color.white, R.color.red);

        myCalendar.setExtraDatesOfMonthBackgroundColor("#324568");
//        myCalendar.setExtraDatesOfMonthBackgroundColor(R.color.black);

        myCalendar.setExtraDatesOfMonthTextColor("#756325");
//        myCalendar.setExtraDatesOfMonthTextColor(R.color.black);

//        myCalendar.setDatesOfMonthBackgroundColor(R.drawable.event_view);
        myCalendar.setDatesOfMonthBackgroundColor("#145687");
//        myCalendar.setDatesOfMonthBackgroundColor(R.color.black);

        myCalendar.setDatesOfMonthTextColor("#745632");
//        myCalendar.setDatesOfMonthTextColor(R.color.black);

//        myCalendar.setCurrentDateBackgroundColor("#123412");
//        myCalendar.setCurrentDateBackgroundColor(R.color.black);

        myCalendar.setCurrentDateTextColor("#00e600");
//        myCalendar.setCurrentDateTextColor(R.color.black);

        myCalendar.setEventCellBackgroundColor("#852365");
//        myCalendar.setEventCellBackgroundColor(R.color.black);

        myCalendar.setEventCellTextColor("#425684");
//        myCalendar.setEventCellTextColor(R.color.black);

        myCalendar.setBelowMonthEventTextColor("#425684");
//        myCalendar.setBelowMonthEventTextColor(R.color.black);

        myCalendar.setBelowMonthEventDividerColor("#635478");
//        myCalendar.setBelowMonthEventDividerColor(R.color.black);

        myCalendar.setHolidayCellBackgroundColor("#654248");
//        myCalendar.setHolidayCellBackgroundColor(R.color.black);

        myCalendar.setHolidayCellTextColor("#d590bb");
//        myCalendar.setHolidayCellTextColor(R.color.black);

        myCalendar.showDayView();
        //myCalendar.addHoliday("2-11-2016");
        myCalendar.getEventList(new GetEventListListener() {
            @Override
            public void eventList(ArrayList<EventModel> eventList) {

                Log.e("tag", "eventList.size():-" + eventList.size());
                for (int i = 0; i < eventList.size(); i++) {
                    Log.e("tag", "eventList.getStrName:-" + eventList.get(i).getStrName());
                }

            }
        });
        //myCalendar.addEvent("5-10-2016", "8:00", "8:15", "Today Event 1");

        myCalendar.setOnEventClickListener(new OnEventClickListener() {
            @Override
            public void onClick() {
                Log.e("showDayView", "from setOnEventClickListener onClick");

            }

            @Override
            public void onLongClick() {
                Log.e("showDayView", "from setOnEventClickListener onLongClick");

            }
        });

        myCalendar.setOnWeekDayViewClickListener(new OnWeekDayViewClickListener() {
            @Override
            public void onClick(String date, String time) {
                Log.e("showDayView", "from setOnWeekDayViewClickListener onClick");
                Log.e("tag", "date:-" + date + " time:-" + time);
            }

            @Override
            public void onLongClick(String date, String time) {
                Log.e("showDayView", "from setOnWeekDayViewClickListener onLongClick");
                Log.e("tag", "date:-" + date + " time:-" + time);
            }
        });

        return view;
    }
}
