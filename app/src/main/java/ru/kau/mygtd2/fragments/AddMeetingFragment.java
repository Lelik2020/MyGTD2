package ru.kau.mygtd2.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.objects.Meeting;

public class AddMeetingFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog, timePickerDialog2;
    private TextView title;
    private EditText timebegin, timeend;
    private EditText date;
    private CheckBox checkBoxRemember;
    private GridLayout gridlayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.addmeeting_fragment, null);

        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);
        int currentMonth = c.get(Calendar.MONTH);
        final int today = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        //date.setText(i2 + "/" + String.valueOf(i1 + 1) + "/" + i);

        ImageView timestartmeeting = (ImageView) rootView.findViewById(R.id.timestartmeeting);
        ImageView datameeting = (ImageView) rootView.findViewById(R.id.datameeting);
        checkBoxRemember = (CheckBox) rootView.findViewById(R.id.checkBoxRemember);
        gridlayout = (GridLayout) rootView.findViewById(R.id.gridlayout);
        if (!checkBoxRemember.isChecked()){
            gridlayout.setVisibility(View.INVISIBLE);
        } else {
            gridlayout.setVisibility(View.VISIBLE);
        }

        checkBoxRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    gridlayout.setVisibility(View.VISIBLE);
                else {
                    gridlayout.setVisibility(View.INVISIBLE);
                }
            }
        });

        title = (TextView) rootView.findViewById(R.id.inputMeetingTitle);
        date = (EditText) rootView.findViewById(R.id.datemeeting);
        //date.setText(currentYear + "/" + String.valueOf(currentMonth + 1) + "/" + today);
        //date.setText(today + "." + String.valueOf(currentMonth + 1) + "." + currentYear);
        date.setText((new SimpleDateFormat("dd.MM.yyyy").format(new Date())));
        datePickerDialog = new DatePickerDialog(getActivity(), this, currentYear, currentMonth, today);
        datameeting.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View view) {
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                try {
                    datePickerDialog.getDatePicker().setMinDate((new SimpleDateFormat("dd.MM.yyyy").parse(date.getText().toString())).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                datePickerDialog.show();
            }
        });


        timebegin = (EditText) rootView.findViewById(R.id.timebeginmeeting);

        TimePickerDialog.OnTimeSetListener myCallBack = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar cal = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                cal.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                timebegin.setText(df.format(cal.getTime()));
            }
        };

        timePickerDialog = new TimePickerDialog(getActivity(), myCallBack, hour, minute, true);
        timestartmeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.e("timePickerDialog.show()", "timePickerDialog.show();");
                //timePickerDialog.set
                //datePickerDialog.getDatePicker().setMinTime(System.currentTimeMillis() - 1000);
                timePickerDialog.show();
            }
        });


        timeend = (EditText) rootView.findViewById(R.id.timeendmeeting2);
        ImageView timefinishmeeting = (ImageView) rootView.findViewById(R.id.timefinishmeeting);

        timePickerDialog2 = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar cal = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                cal.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                timeend.setText(df.format(cal.getTime()));
            }
        }, hour, minute, true);
        timefinishmeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.e("timePickerDialog2.show", "timePickerDialog2.show();");
                timePickerDialog2.show();
            }
        });

        RadioButton rbEveryDay = (RadioButton) rootView.findViewById(R.id.rBeveryday);
        RadioButton rbEveryWeek = (RadioButton) rootView.findViewById(R.id.rBeveryweek);
        RadioButton rbEveryMonth = (RadioButton) rootView.findViewById(R.id.rBeverymonth);


        View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton rb = (RadioButton)v;
                FragmentTransaction transaction;
                switch (rb.getId()) {
                    case R.id.rBeveryday:
                        //mInfoTextView.setBackgroundColor(Color.parseColor("#ff0000"));
                        EveryDayFragment everyDayFragment = new EveryDayFragment();
                        transaction = getChildFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameevery_container, everyDayFragment).commit();
                        break;
                    case R.id.rBeveryweek:
                        //mInfoTextView.setBackgroundColor(Color.parseColor("#0000ff"));
                        EveryWeekFragment everyWeekFragment = new EveryWeekFragment();
                        transaction = getChildFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameevery_container, everyWeekFragment).commit();
                        break;
                    case R.id.rBeverymonth:
                        //mInfoTextView.setBackgroundColor(Color.parseColor("#0000ff"));
                        EveryMonthFragment everyMonthFragment = new EveryMonthFragment();
                        transaction = getChildFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameevery_container, everyMonthFragment).commit();
                        break;


                    default:
                        break;
                }
            }
        };

        rbEveryDay.setOnClickListener(radioButtonClickListener);
        rbEveryWeek.setOnClickListener(radioButtonClickListener);
        rbEveryMonth.setOnClickListener(radioButtonClickListener);

        Button btnSave = (Button) rootView.findViewById(R.id.btnsave);
        btnSave.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Meeting meeting = new Meeting();
                meeting.setTitle(title.getText().toString());
                try {
                    meeting.setDateBegin(new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(date.getText() + " " + timebegin.getText()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    meeting.setDateEnd(new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(date.getText() + " " + timeend.getText()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                meeting.setDateBeginStr(date.getText() + " " + timebegin.getText());
                meeting.setDateEndStr(date.getText() + " " + timeend.getText());
                //if (meeting.getTitle().trim().equals("")){
                    //meeting.setTitle(null);
                //}
                if (checkFields(meeting)) {
                    MyApplication.getDatabase().meetingDao().insert(meeting);
                    assert getFragmentManager() != null;
                    getFragmentManager().popBackStack();
                } else {
                    //Log.e("ОШИБКА", "ОШИБКА");

                }
            }
        });

        Button btnCancel = (Button) rootView.findViewById(R.id.btncancel);
        btnCancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //Log.e("CANCEL", "CANCEL");
                assert getFragmentManager() != null;
                //Log.e("CANCEL222", "CANCEL333");
                getFragmentManager().popBackStack();
            }
        });

        //if ()

        //getSupportFragmentManager().beginTransaction().addToBackStack("AddTaskFragment").replace(R.id.frame_container,new AddTaskFragment()).commit();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        EveryDayFragment everyDayFragment = new EveryDayFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frameevery_container, everyDayFragment).commit();
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar cal = new GregorianCalendar(i, i1, i2);
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        date.setText(df.format(cal.getTime()));
        //date.setText((new SimpleDateFormat("dd.MM.yyyy").format(new Date())));

    }

    public boolean checkFields(Meeting meeting){
        boolean check = true;
        if (meeting.getTitle() == null || meeting.getTitle().trim().equals("")){
            check = false;
            Toast.makeText(getContext(), "Введены не все поля", Toast.LENGTH_LONG).show();
            return check;
        }
        if (date.getText().toString().trim().equals("") || date.getText() == null){
            check = false;
            Toast.makeText(getContext(), "Введены не все поля", Toast.LENGTH_LONG).show();
            return check;
        }
        if (timebegin.getText().toString().trim().equals("") || timebegin.getText() == null){
            check = false;
            Toast.makeText(getContext(), "Введены не все поля", Toast.LENGTH_LONG).show();
            return check;
        }
        if (timeend.getText().toString().trim().equals("") || timeend.getText() == null){
            check = false;
            Toast.makeText(getContext(), "Введены не все поля", Toast.LENGTH_LONG).show();
            return check;
        }
        return check;
    }


}