package org.techtown.ecoworker.stat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.ecoworker.R;
import org.techtown.ecoworker.SharedPreferenceUtil;

import java.util.ArrayList;

public class fragment_stat extends Fragment {

    Context context;
    CalendarView calendarView2;
    Button chartOpen;
    Button dateNew;
    Button dateMod;

    ArrayList<Stat> stat;
    ListView customListView;
    private static CustomAdapter2 customAdapter2;
    String foodName;
    int amount, carbonAmount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_stat, container, false);
        context = getActivity().getApplicationContext();
        stat = new ArrayList<>();

        String userID = SharedPreferenceUtil.getSharedPreference(context, "userID");

        customListView = (ListView) rootview.findViewById(android.R.id.list);

        final TextView nowDate = rootview.findViewById(R.id.nowDate);
        calendarView2 = rootview.findViewById(R.id.calendarView2);
        customAdapter2 = new CustomAdapter2(getContext(), R.id.foodName, stat);
        calendarView2.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
       @Override
       public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
           //리스트뷰 초기화
           customAdapter2.clear();

           month += 1;
           nowDate.setText(String.format("%d년 %d월 %d일", year, month, dayOfMonth));

           Response.Listener<String> responseListener = new Response.Listener<String>() {
               @Override
               public void onResponse(String json) {
                   try {
                       JSONArray jsonArray = new JSONArray(json);
                       for (int i = 0; i < jsonArray.length(); i++) {
                           JSONObject jsonObject = jsonArray.getJSONObject(i);
                           boolean success = jsonObject.getBoolean("success");
                           foodName = jsonObject.getString("foodName");
                           amount = jsonObject.getInt("amount");
                           carbonAmount = jsonObject.getInt("carbonAmount");

                           stat.add(new Stat(foodName, amount, carbonAmount));
                       }

                       customListView.setAdapter(customAdapter2);
                       customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                           @Override
                           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                           }
                       });
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
           };
           StatRecRequest statRequest = new StatRecRequest(userID, year, month, dayOfMonth, responseListener);
           RequestQueue queue = Volley.newRequestQueue(context);
           queue.add(statRequest);
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


class Stat {
    private String foodName;
    private int amount;
    private int carbonAmount;

    public Stat(String foodName, int amount, int carbonAmount) {
        this.foodName = foodName;
        this.amount = amount;
        this.carbonAmount = carbonAmount;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getAmount() {
        return amount;
    }

    public int getCarbonAmount() {
        return carbonAmount;
    }

}