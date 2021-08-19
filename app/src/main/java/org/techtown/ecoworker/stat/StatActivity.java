package org.techtown.ecoworker.stat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.ecoworker.R;
import org.techtown.ecoworker.SharedPreferenceUtil;

import java.util.ArrayList;

public class StatActivity extends AppCompatActivity {

    PieChart pieChart;
    Button chartClose, select;

    String userID,foodName;
    int year, month;
    EditText et_year, et_month;
    float value;
    float sum_carbonAmount_i;

    ArrayList<MonthStat> monthStat;
    private static CustomAdapter3 customAdapter3;
    ListView customListView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        //탄소발자국 비율 리스트
        monthStat = new ArrayList<>();
        customAdapter3 = new CustomAdapter3(StatActivity.this, R.id.foodName, monthStat);
        customListView = (ListView) findViewById(android.R.id.list);

        //차트
        pieChart = (PieChart)findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();


        //사용자 아이디, 사용자가 입력한 연도, 사용자가 입력한 월 받아오기
        userID = SharedPreferenceUtil.getSharedPreference(StatActivity.this, "userID");

        try {
            et_year = findViewById(R.id.year);
            et_year.setFilters(new InputFilter[]{new InputFilterMinMax("1", "2100")});
            et_year.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == event.KEYCODE_ENTER)
                        return true;
                    return false;
                }
            });

            et_month = findViewById(R.id.month);
            et_month.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});
            et_month.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == event.KEYCODE_ENTER)
                        return true;
                    return false;
                }
            });

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //'선택'버튼 클릭 시 차트와 리스트를 띄움
        select = (Button) findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                year = Integer.parseInt(et_year.getText().toString());
                month = Integer.parseInt(et_month.getText().toString());

                //전체 탄소발자국 값 연동
                Response.Listener<String> responseListener0 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            String sum_carbonAmount = jsonObject.getString("carbonAmount");
                            sum_carbonAmount_i = Float.parseFloat(sum_carbonAmount); //전체 탄소발자국 양

                            //품목별 탄소발자국 값 연동 및 비율 구하기
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String json) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(json);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                                            String carbonAmount = jsonObject.getString("carbonAmount");
                                            float carbonAmount_i = Float.parseFloat(carbonAmount);
                                            value = Math.round(carbonAmount_i / sum_carbonAmount_i * 100); //해당 품목 탄소발자국 비율

                                            yValues.add(new PieEntry(value, foodName));                    //차트에 추가

                                            monthStat.add(new MonthStat(foodName, carbonAmount_i, value)); //리스트에 추가
                                        }

                                        customListView.setAdapter(customAdapter3);
                                        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                            }
                                        });

                                        PieDataSet dataSet = new PieDataSet(yValues," ");
                                        dataSet.setSliceSpace(3f);
                                        dataSet.setSelectionShift(5f);
                                        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

                                        PieData data = new PieData((dataSet));
                                        data.setValueTextSize(5f);
                                        data.setValueTextColor(Color.YELLOW);

                                        pieChart.setData(data);


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            StatChartRequest statChartRequest = new StatChartRequest(userID, year, month, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(StatActivity.this);
                            queue.add(statChartRequest);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                SumCarbonRequest sumCarbonRequest = new SumCarbonRequest(userID, year, month, responseListener0);
                RequestQueue queue0 = Volley.newRequestQueue(StatActivity.this);
                queue0.add(sumCarbonRequest);

            }
        });

        //창 닫기
        chartClose = (Button) findViewById(R.id.chartClose);
        chartClose.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                finish();
            }
        });
    }
}

class MonthStat {
    private String foodName;
    private float carbonAmount;
    private float carbonValue;

    public MonthStat(String foodName, float carbonAmount, float carbonValue) {
        this.foodName = foodName;
        this.carbonAmount = carbonAmount;
        this.carbonValue = carbonValue;
    }

    public String getFoodName() {
        return foodName;
    }

    public float getCarbonAmount() {
        return carbonAmount;
    }

    public float getCarbonValue() {
        return carbonValue;
    }

}