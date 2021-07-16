package org.techtown.ecoworker.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.techtown.ecoworker.MainActivity;
import org.techtown.ecoworker.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText passwd1 = findViewById(R.id.password2);
        EditText passwd2 = findViewById(R.id.checkpasswd);

        ImageView setImage = findViewById(R.id.setImage);

        passwd1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                지우면 안 됨.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(passwd1.getText().toString().equals(passwd2.getText().toString())) {
                    setImage.setImageResource(R.drawable.okay);
                } else {
                    setImage.setImageResource(R.drawable.no);
                }
            }

            @Override
            public void afterTextChanged (Editable s) {
//               지우면 안 됨.
            }
        });

        passwd2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                지우면 안 됨.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(passwd1.getText().toString().equals(passwd2.getText().toString())) {
                    setImage.setImageResource(R.drawable.okay);
                } else {
                    setImage.setImageResource(R.drawable.no);
                }
            }

            @Override
            public void afterTextChanged (Editable s) {
//               지우면 안 됨.
            }
        });

//      회원가입 버튼 누를 때 메인 화면으로 이동
        Button join2 = findViewById(R.id.join2);
        join2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}