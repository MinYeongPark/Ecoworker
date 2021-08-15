package org.techtown.ecoworker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class DateNewActivity extends AppCompatActivity {

    int expCarFoot, saveCarFoot;
    String num;
    Button date;
    Button numCho, dateNewAdd, dateNewClose;
    TextView foodName, CarFoot, number, calCarFoot, date2;
    EditText editNum;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datenew);

        Button date = findViewById(R.id.date);
        TextView date2 = findViewById(R.id.date2);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                            date.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                            date2.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                            date2.setVisibility(View.INVISIBLE);
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
                if (food[position] != "품목명")
                foodName.setText(food[position]);
                foodName.setVisibility(View.INVISIBLE);

                expCarFoot = carfoot[position];
                CarFoot.setText(String.valueOf(expCarFoot));
                CarFoot.setVisibility(View.INVISIBLE);

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
                date2.setVisibility(View.VISIBLE);
                foodName.setVisibility(View.VISIBLE);
                CarFoot.setVisibility(View.VISIBLE);
                num = editNum.getText().toString();
                number.setText(num);
                saveCarFoot = expCarFoot * Integer.parseInt(editNum.getText().toString());
                calCarFoot.setText(String.valueOf(saveCarFoot));
            }
        });

                dateNewAdd = (Button) findViewById(R.id.dateNewAdd);
        dateNewAdd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "등록되었습니다",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        dateNewClose = (Button) findViewById(R.id.dateNewClose);
        dateNewClose.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });
    }
}