package org.techtown.ecoworker.stat;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.ecoworker.R;
import org.techtown.ecoworker.SharedPreferenceUtil;
import org.techtown.ecoworker.register.RegisterActivity;
import org.techtown.ecoworker.register.RegisterRequest;

public class DateNewActivity extends AppCompatActivity {

    int expCarFoot, saveCarFoot;
    String num;
    Button date;
    Button numCho, dateNewAdd, dateNewClose;
    TextView foodName, CarFoot, number, calCarFoot, date2;
    EditText editNum;

    //데이터베이스에 올릴 항목
    int recDate_year, recDate_mon, recDate_day, amount, carbonAmount;
    String recFoodName;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datenew);

        String userID = SharedPreferenceUtil.getSharedPreference(DateNewActivity.this, "userID");

        Button date = findViewById(R.id.date);
        TextView date2 = findViewById(R.id.date2);

        //날짜 선택
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
            new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                date.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                date2.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                date2.setVisibility(View.INVISIBLE);

                recDate_year = year;
                recDate_mon = month+1;
                recDate_day = dayOfMonth;
            }
        }, 2021, 7, 13);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (date.isClickable()) {
                    datePickerDialog.show();
                }
            }
        });

        //품목 선택 및 탄소발자국 계산
        final String[] food = {"품목명", "요거트", "계란", "식빵", "액상커피", "우유", "슬라이스 치즈", "두부", "김치", "라면", "냉동만두", "고추장", "참기름", "식용유", "참치캔"};
        final int[] carfoot = {0, 240, 449, 260,224, 1020, 100, 16,76,1,1,1,1,1,533};

        Spinner spinnerFood= (Spinner) findViewById(R.id.spinnerFood);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DateNewActivity.this,android.R.layout.simple_spinner_item, food);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFood.setAdapter(adapter);

        foodName = findViewById(R.id.foodName);
        CarFoot = findViewById(R.id.CarFoot);
        editNum = findViewById(R.id.editNum);
        number = findViewById(R.id.number);
        calCarFoot = findViewById(R.id.calCarFoot);

        spinnerFood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (food[position] != "품목명") {
                    foodName.setText(food[position]);
                    foodName.setVisibility(View.INVISIBLE);

                    recFoodName = food[position];

                    expCarFoot = carfoot[position];
                    CarFoot.setText(String.valueOf(expCarFoot));
                    CarFoot.setVisibility(View.INVISIBLE);
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
                foodName.setText("품목을 선택하세요");
            }
        });


        editNum.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER)
                    return true;
                return false;
            }
        });

        numCho = findViewById(R.id.numCho);
        numCho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    date2.setVisibility(View.VISIBLE);
                    foodName.setVisibility(View.VISIBLE);
                    CarFoot.setVisibility(View.VISIBLE);
                    num = editNum.getText().toString();
                    number.setText(num);

                    amount = Integer.valueOf(num); //데이터베이스에 올릴 인분 수치

                    saveCarFoot = expCarFoot * Integer.parseInt(editNum.getText().toString());
                    calCarFoot.setText(String.valueOf(saveCarFoot));

                    carbonAmount = saveCarFoot; //데이터베이스에 올릴 탄소발차국 수치

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "인분을 입력해주세요.", Toast.LENGTH_SHORT).show(); //인분을 미입력했을 때에만 예외가 발생해서 이렇게 넣었음.
                }
            }
        });

        //탄소발자국 내역 등록
        dateNewAdd = (Button) findViewById(R.id.dateNewAdd);
        dateNewAdd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                Toast.makeText(getApplicationContext(), "등록되었습니다.",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "내역이 등록되지 않았습니다.\n다시 확인해주세요.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                DateNewRequest dateNewRequest = new DateNewRequest(recDate_year, recDate_mon, recDate_day, recFoodName, amount, carbonAmount, userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(DateNewActivity.this);
                queue.add(dateNewRequest);
            }
        });

        //창 닫기
        dateNewClose = (Button) findViewById(R.id.dateNewClose);
        dateNewClose.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });
    }
}