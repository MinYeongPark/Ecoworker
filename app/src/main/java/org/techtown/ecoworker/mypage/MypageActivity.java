package org.techtown.ecoworker.mypage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.ecoworker.R;
import org.techtown.ecoworker.SharedPreferenceUtil;
import org.techtown.ecoworker.login.LoginActivity;
import org.techtown.ecoworker.register.RegisterActivity;
import org.techtown.ecoworker.register.RegisterRequest;

public class MypageActivity extends AppCompatActivity {

    private AlertDialog dialog;

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

        TextView idname3 = findViewById(R.id.idname3);


        String userID = SharedPreferenceUtil.getSharedPreference(MypageActivity.this, "userID");
        idname3.setText(userID);

        Button btn_edit = findViewById(R.id.join3);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userPass = null;
                if(passwd3.getText().toString().equals(passwd4.getText().toString())) {
                    userPass = passwd4.getText().toString();
                } else {
                    Toast.makeText(getApplicationContext(),"비밀번호 확인 부분이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (userPass.equals("")||passwd3.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MypageActivity.this);
                    dialog = builder.setMessage("공백 항목이 있습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                //콜백 처리부분(volley 사용을 위한 ResponseListener 구현 부분)
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    //서버로부터 여기서 데이터를 받음
                    public void onResponse(String response) {
                        try {
                            //서버로부터 받는 데이터는 JSON타입의 객체이다
                            JSONObject jsonObject = new JSONObject(response);
                            //그중 Key값이 "success"인 것을 가져온다.
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(),"비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MypageActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"비밀번호 변경이 되지 않았습니다.\n다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }; //responseListener 끝
                //서버로 Volley를 이용해서 요청을 함.
                //1. RequestObject를 생성한다. 이때 서버로부터 데이터를 받을 responseListener를 반드시 넘겨준다.
                EditPwRequest editPwRequest = new EditPwRequest(userID, userPass, responseListener);
                //2. RequestQueue를 생성한다.
                RequestQueue queue = Volley.newRequestQueue(MypageActivity.this);
                //3. RequestQueue에 RequestObject를 넘겨준다.
                queue.add(editPwRequest);
            }
        });
    }
}