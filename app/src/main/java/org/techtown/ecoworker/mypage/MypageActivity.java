package org.techtown.ecoworker.mypage;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.techtown.ecoworker.R;

public class MypageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        EditText passwd3 = findViewById(R.id.password3);
        EditText passwd4 = findViewById(R.id.checkpasswd2);

        ImageView setImage2 = findViewById(R.id.setImage2);

        passwd3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                지우면 안 됨.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(passwd3.getText().toString().equals(passwd4.getText().toString())) {
                    setImage2.setImageResource(R.drawable.okay);
                } else {
                    setImage2.setImageResource(R.drawable.no);
                }
            }

            @Override
            public void afterTextChanged (Editable s) {
//               지우면 안 됨.
            }
        });

        passwd4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                지우면 안 됨.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(passwd3.getText().toString().equals(passwd4.getText().toString())) {
                    setImage2.setImageResource(R.drawable.okay);
                } else {
                    setImage2.setImageResource(R.drawable.no);
                }
            }

            @Override
            public void afterTextChanged (Editable s) {
//               지우면 안 됨.
            }
        });

    }
}