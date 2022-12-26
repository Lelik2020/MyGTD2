package ru.kau.mygtd2.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ru.kau.mygtd2.R;

public class EveryDayFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    private TextView datebegin;
    private TextView dateend;
    private DatePickerDialog datePickerDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.everyday_fragment, null);

        RadioButton rbOnEveryDay = (RadioButton) rootView.findViewById(R.id.rBoneveryday);
        RadioButton rbOnWorkDays = (RadioButton) rootView.findViewById(R.id.rBonworkdays);
        ImageView datebeginchoise = (ImageView) rootView.findViewById(R.id.datebeginchoise);
        ImageView dateendchoise = (ImageView) rootView.findViewById(R.id.dateendchoise);

        View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton rb = (RadioButton)v;
                FragmentTransaction transaction;
                /*switch (rb.getId()) {
                    case R.id.rBoneveryday:
                        //mInfoTextView.setBackgroundColor(Color.parseColor("#ff0000"));

                        break;
                    case R.id.rBonworkdays:
                        //mInfoTextView.setBackgroundColor(Color.parseColor("#0000ff"));

                        break;



                    default:
                        break;
                }*/
            }
        };

        rbOnEveryDay.setOnClickListener(radioButtonClickListener);
        rbOnWorkDays.setOnClickListener(radioButtonClickListener);

        datebegin = (TextView) rootView.findViewById(R.id.datebegin);
        dateend = (TextView) rootView.findViewById(R.id.dateend);
        datebegin.setText((new SimpleDateFormat("dd.MM.yyyy").format(new Date())));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 3);
        Date dt = cal.getTime();
        dateend.setText((new SimpleDateFormat("dd.MM.yyyy").format(dt)));
        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);
        int currentMonth = c.get(Calendar.MONTH);
        final int today = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(getActivity(), this, currentYear, currentMonth, today);
        datebeginchoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    datePickerDialog.getDatePicker().setMinDate((new SimpleDateFormat("dd.MM.yyyy").parse(datebegin.getText().toString())).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                datePickerDialog.show();
            }
        });

        dateendchoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    datePickerDialog.getDatePicker().setMinDate((new SimpleDateFormat("dd.MM.yyyy").parse(dateend.getText().toString())).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                datePickerDialog.show();
            }
        });
        /*
        datebegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
        */

        return rootView;
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar cal = new GregorianCalendar(i, i1, i2);
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        datebegin.setText(df.format(cal.getTime()));
    }
}
