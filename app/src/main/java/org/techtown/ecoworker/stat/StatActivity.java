package org.techtown.ecoworker.stat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
    int year, month, carbonAmount, sum_carbonAmount;
    EditText et_year, et_month;
    float value;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        pieChart = (PieChart)findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();


        userID = SharedPreferenceUtil.getSharedPreference(StatActivity.this, "userID");

        try {
            et_year = (EditText) findViewById(R.id.year);
            et_year.setFilters(new InputFilter[]{new InputFilterMinMax("1", "2100")});
            et_year.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == event.KEYCODE_ENTER)
                        return true;
                    return false;
                }
            });
            year = Integer.parseInt(et_year.getText().toString());

            et_month = (EditText) findViewById(R.id.month);
            et_month.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});
            et_month.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == event.KEYCODE_ENTER)
                        return true;
                    return false;
                }
            });
            month = Integer.parseInt(et_month.getText().toString());


        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        select = (Button) findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener0 = new Response.Listener<String>() { //전체 탄소발자국 구하기
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("asdf", "잘나옴000");
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            Log.d("asdf", "잘나옴001");
                            sum_carbonAmount = jsonObject.getInt("carbon_Amount");

                        } catch (JSONException e) {
                            Log.d("asdf", "잘나옴002");
                            e.printStackTrace();
                        }

                    }
                };

                SumCarbonRequest sumCarbonRequest = new SumCarbonRequest(userID, year, month, responseListener0);
                Log.d("asdf", "잘나옴003");
                RequestQueue queue0 = Volley.newRequestQueue(StatActivity.this);
                queue0.add(sumCarbonRequest);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String json) {
                        try {
                            JSONArray jsonArray = new JSONArray(json);
                            Log.d("asdf", "잘나옴0");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.d("asdf", "잘나옴1");
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                foodName = jsonObject.getString("foodName");
                                carbonAmount = jsonObject.getInt("carbon_Amount");
                                Log.d("asdf", "잘나옴2");

                                value = (float) carbonAmount/sum_carbonAmount; //비율 구함

                                yValues.add(new PieEntry(value, foodName));
                                Log.d("asdf", "잘나옴3");
                            }

                            PieDataSet dataSet = new PieDataSet(yValues,"Countries");
                            Log.d("asdf", "잘나옴7");
                            dataSet.setSliceSpace(3f);
                            dataSet.setSelectionShift(5f);
                            Log.d("asdf", "잘나옴8");
                            dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

                            PieData data = new PieData((dataSet));
                            Log.d("asdf", "잘나옴9");
                            data.setValueTextSize(10f);
                            data.setValueTextColor(Color.YELLOW);
                            Log.d("asdf", "잘나옴10");

                            pieChart.setData(data);

                        } catch (JSONException e) {
                            Log.d("asdf", "잘나옴4");
                            e.printStackTrace();
                        }
                    }
                };
                Log.d("asdf", "잘나옴5");
                StatChartRequest statChartRequest = new StatChartRequest(userID, year, month, responseListener);
                RequestQueue queue = Volley.newRequestQueue(StatActivity.this);
                Log.d("asdf", "잘나옴6");
                queue.add(statChartRequest);


            }
        });







        chartClose = (Button) findViewById(R.id.chartClose);
        chartClose.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                finish();
            }
        });
    }
}