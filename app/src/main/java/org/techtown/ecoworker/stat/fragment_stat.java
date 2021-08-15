package org.techtown.ecoworker.stat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.techtown.ecoworker.DateModActivity;
import org.techtown.ecoworker.DateNewActivity;
import org.techtown.ecoworker.R;
import org.techtown.ecoworker.SharedPreferenceUtil;
import org.techtown.ecoworker.StatActivity;

public class fragment_stat extends Fragment {

    Context context;
    CalendarView calendarView2;
    Button chartOpen;
    Button dateNew;
    Button dateMod;
    TextView nowDate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_stat, container, false);

        final TextView nowDate = rootview.findViewById(R.id.nowDate);
        CalendarView calendarView2 = rootview.findViewById(R.id.calendarView2);
        calendarView2.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
       @Override
       public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
           month += 1;
           nowDate.setText(String.format("%d년 %d월 %d일", year, month, dayOfMonth));
       }
   });

        chartOpen = (Button) rootview.findViewById(R.id.chartOpen);
        chartOpen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StatActivity.class);
                startActivity(intent);
            }
        });

        dateNew = (Button) rootview.findViewById(R.id.dateNew);
        dateNew.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DateNewActivity.class);
                startActivity(intent);
            }
        });

        dateMod = (Button) rootview.findViewById(R.id.dateMod);
        dateMod.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DateModActivity.class);
                startActivity(intent);
            }
        });



        return rootview;
        }
    }


