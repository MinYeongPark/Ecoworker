package org.techtown.ecoworker.change;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.ecoworker.R;
import org.techtown.ecoworker.SharedPreferenceUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class fragment_change extends Fragment {

    Context context;
    int selectedUseTerm, selectedAlarmTerm;
    String selectedFoodName, selectedExpDate, selectedUseDate;
    String monthStr, expDateStr, useDateStr;
    TextView foodName, expDate, useDateTerm, useDate;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_change, container, false);

        context = getActivity().getApplicationContext();

        //userID 받아오기
        String userID = SharedPreferenceUtil.getSharedPreference(context, "userID");

        //아무것도 선택 안 했을 때
        selectedFoodName = null;
        selectedExpDate = null;
        selectedUseDate = null;

        //품목명 및 소비가능기간 목록 정의
        final String[] food = {"품목명", "요거트", "계란", "식빵", "액상커피", "우유", "슬라이스 치즈", "두부", "김치", "라면", "냉동만두", "고추장", "참기름", "식용유", "참치캔"};
        final int[] useTerm = {0, 10, 25, 18, 30, 45, 70, 90, 180, 240, 365, 730, 910, 1825, 3650};

        //품목명 선택
        foodName = rootview.findViewById(R.id.foodName);
        useDateTerm = rootview.findViewById(R.id.useDateTerm);
        Spinner spinnerFood= (Spinner) rootview.findViewById(R.id.spinnerFood);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, food);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFood.setAdapter(adapter);

        spinnerFood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFoodName = food[position];
                foodName.setText(selectedFoodName);

                selectedUseTerm = useTerm[position];
                useDateTerm.setText(String.valueOf(selectedUseTerm));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //유통기한 선택 및 소비기한 계산
        expDate = rootview.findViewById(R.id.expDate);
        useDate = rootview.findViewById(R.id.useDate);
        CalendarView calendarView = rootview.findViewById(R.id.Calendar1);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month += 1;
                //유통기한 나타내기
                expDate.setText(String.format("%d년 %d월 %d일", year, month, dayOfMonth));

                if (month <= 9) { //9월 이하의 달은 '0n'월로 지정
                    monthStr = "0" + String.valueOf(month);
                }

                expDateStr = String.valueOf(dayOfMonth);
                if (dayOfMonth <= 9) { //9일 이하의 일은 '0n'일로 지정
                    expDateStr = "0" + String.valueOf(dayOfMonth);
                }

                selectedExpDate = String.valueOf(year) + "-" + monthStr + "-" + expDateStr;

                try {
                    Calendar expDateCal = Calendar.getInstance();
                    expDateCal.set(Calendar.YEAR, year);
                    expDateCal.set(Calendar.MONTH, month);
                    expDateCal.set(Calendar.DATE,dayOfMonth);
                    expDateCal.add(Calendar.DATE, selectedUseTerm);  //소비기한 계산

                    int useDateYear = expDateCal.get(Calendar.YEAR);
                    int useDateMonth = expDateCal.get(Calendar.MONTH);
                    int useDateDate = expDateCal.get(Calendar.DATE);

                    //소비기한 나타내기
                    useDate.setText(String.format("%d년 %d월 %d일", useDateYear, useDateMonth, useDateDate));

                    if (useDateMonth <= 9) { //9월 이하의 달은 '0n'월로 지정
                        monthStr = "0" + String.valueOf(useDateMonth);
                    }

                    useDateStr = String.valueOf(useDateDate);
                    if (useDateDate <= 9) {//9일 이하의 일은 '0n'일로 지정
                        useDateStr = "0" + String.valueOf(useDateDate);
                    }

                    selectedUseDate = String.valueOf(year) + "-" + monthStr + "-" + useDateStr;

                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        });

        //알람 기간 선택
        Button expAlarm = (Button) rootview.findViewById(R.id.Alarm);
        expAlarm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String[] versionArray = new String[]{"7일 전", "3일 전", "1일 전", "당일"};

                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                dlg.setTitle("기간 설정");

                selectedAlarmTerm = 7; //기본값은 '7일 전 알람'

                dlg.setSingleChoiceItems(versionArray, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 1) { selectedAlarmTerm = 3; }        //2번째것 선택하면 '3일 전 알람' 선택
                                else if (which == 2) { selectedAlarmTerm = 1; }   //3번째것 선택하면 '1일 전 알람' 선택
                                else if (which == 3) { selectedAlarmTerm = 0; }   //4번째것 선택하면 '당일 알람' 선택
                                else { selectedAlarmTerm = 7; }                   //0번째것 선택하면 '7일 전 알람' 선택
                            }
                        });

                //알람 등록하기
                dlg.setPositiveButton("설정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");

                                    if (success) { //등록에 성공한 경우
                                        Toast.makeText(context, "알림이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "알림 등록에 실패하였습니다.\n다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        String alarmMethod = "소비기한";

                        FoodRegisterRequest foodRegisterRequest = new FoodRegisterRequest(userID, selectedFoodName, selectedExpDate, selectedUseDate, alarmMethod, selectedAlarmTerm, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(context);
                        queue.add(foodRegisterRequest);
                    }
                });

                dlg.show();
            }
        });

        //설명 창
        ImageButton InfoChange = (ImageButton) rootview.findViewById(R.id.InfoChange);
        InfoChange.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                AlertDialog.Builder dlg = new AlertDialog.Builder(
                        getActivity());
                dlg.setTitle("안내");
                dlg.setMessage("<유통기한>\n" +
                        "식품을 소비자에게 판매할 수 있는 기한\n\n" +"<소비기한>\n" +
                        "식품을 섭취해도 건강이나 안전에 이상이 없을 것으로 인정되는 최종 소비 기한");
                dlg.setPositiveButton("확인", null);
                dlg.show();
            }
        });
        return rootview;
    }
}
