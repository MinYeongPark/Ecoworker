package org.techtown.ecoworker.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.ecoworker.MainActivity;
import org.techtown.ecoworker.R;
import org.techtown.ecoworker.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText idname, passwd1, passwd2;
    private Button join2;
    private ImageView setImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        idname = findViewById(R.id.idname2);

        passwd1 = findViewById(R.id.password2);
        passwd2 = findViewById(R.id.checkpasswd);

        setImage = findViewById(R.id.setImage);

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

        join2 = findViewById(R.id.join2);
        join2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = idname.getText().toString();
                String userPass = passwd2.getText().toString();
                Log.d("asdf", "잘나옴2-1");

                //콜백 처리부분(volley 사용을 위한 ResponseListener 구현 부분)
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    //서버로부터 여기서 데이터를 받음
                    public void onResponse(String response) {
                        try {
                            Log.d("asdf", "잘나옴2-2");
                            //서버로부터 받는 데이터는 JSON타입의 객체이다
                            JSONObject jsonObject = new JSONObject(response);
                            //그중 Key값이 "success"인 것을 가져온다.
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { //회원 등록에 성공한 경우 데이터베이스에 회원 데이터가 저장되고 로그인 화면으로 이동
                                //회원 가입 성공시 success값이 true
                                Toast.makeText(getApplicationContext(),"회원등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else { //회원 등록에 실패한 경우
                                Toast.makeText(getApplicationContext(),"회원등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            Log.d("asdf", "잘나옴-catch");
                            e.printStackTrace();
                        }

                    }
                }; //responseListener 끝
                Log.d("asdf", "잘나옴-끝남");
                //서버로 Volley를 이용해서 요청을 함.
                //1. RequestObject를 생성한다. 이때 서버로부터 데이터를 받을 responseListener를 반드시 넘겨준다.
                RegisterRequest registerRequest = new RegisterRequest(userID, userPass, responseListener);
                //2. RequestQueue를 생성한다.
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                //3. RequestQueue에 RequestObject를 넘겨준다.
                queue.add(registerRequest);
                Log.d("asdf", "잘나옴-완전끝");
            }
        });
    }
}