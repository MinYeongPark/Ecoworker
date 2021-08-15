package org.techtown.ecoworker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DateModActivity extends AppCompatActivity {

    Button dateModClose;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datemod);

        dateModClose = (Button) findViewById(R.id.dateModClose);
        dateModClose.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });
    }
}