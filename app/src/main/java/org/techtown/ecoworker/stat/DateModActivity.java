package org.techtown.ecoworker.stat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.techtown.ecoworker.R;

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