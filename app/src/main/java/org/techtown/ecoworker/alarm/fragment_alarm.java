package org.techtown.ecoworker.alarm;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.ListFragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.ecoworker.R;
import org.techtown.ecoworker.SharedPreferenceUtil;

import java.util.ArrayList;

public class fragment_alarm extends ListFragment {

    ArrayList<Alarm> alarm;
    ListView customListView;
    private static CustomAdapter customAdapter;
    RequestQueue queue;
    Context context;
    String foodName, expDateStr, useDateStr;
    int alarmDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        context = getActivity().getApplicationContext();

        alarm = new ArrayList<>();

        String userID = SharedPreferenceUtil.getSharedPreference(context, "userID");

        customListView = (ListView) view.findViewById(android.R.id.list);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String json) {
                try {
                    JSONArray jsonArray = new JSONArray(json);
                    for (int i = 0; i < jsonArray.length(); i++) { //각 알람들을 받아오고 목록들을 추가하는 과정
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        boolean success = jsonObject.getBoolean("success");
                        foodName = jsonObject.getString("foodName");    //식품명
                        expDateStr = jsonObject.getString("expDate");   //유통기한(String 타입이고, yyyy-MM-dd 형식)
                        useDateStr = jsonObject.getString("useDate");   //소비기한(String 타입이고, yyyy-MM-dd 형식)
                        alarmDate = jsonObject.getInt("alarmDate");     //몇 일 전에 알람을 울릴 것인지

                        alarm.add(new Alarm(foodName, expDateStr, useDateStr, alarmDate));
                    }

                    //리스트뷰에 나타내기
                    customAdapter = new CustomAdapter(getContext(), R.id.foodName, alarm);
                    customListView.setAdapter(customAdapter);

                    customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selectedItem = (String) view.findViewById(R.id.foodName).getTag().toString();
                            Toast.makeText(getContext(), "Clicked : " + position + " " + selectedItem, Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        AlarmRequest alarmRequest = new AlarmRequest(userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(alarmRequest);

        return view;
    }
}


//data class
class Alarm {
    private String foodName;
    private String expDate;
    private String useDate;
    private int alarmDate;

    public Alarm(String foodName, String expDate, String useDate, int alarmDate) {
        this.foodName = foodName;
        this.expDate = expDate;
        this.useDate = useDate;
        this.alarmDate = alarmDate;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getExpDate() {
        return expDate;
    }

    public String getUseDate() {
        return useDate;
    }

    public int getAlarmDate() {
        return alarmDate;
    }
}