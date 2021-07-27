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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.ecoworker.MainActivity;
import org.techtown.ecoworker.R;
import org.techtown.ecoworker.login.LoginActivity;

import java.lang.reflect.Method;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    private EditText idname, passwd1, passwd2;
    private Button btn_check, join2;
    private ImageView setImage;
    RequestQueue requestQueue; //아이디 중복확인 용

    private AlertDialog dialog;
    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        idname = findViewById(R.id.idname2);

        btn_check = findViewById(R.id.btn_check);
        passwd1 = findViewById(R.id.password2);
        passwd2 = findViewById(R.id.checkpasswd);

        setImage = findViewById(R.id.setImage);

        requestQueue = Volley.newRequestQueue(RegisterActivity.this);

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = idname.getText().toString();
                if(validate){
                    return;//검증 완료
                }
                //ID 값을 입력하지 않았다면
                if(userID.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디 값을 입력하세요.")
                            .setPositiveButton("OK", null)
                            .create();
                    dialog.show();
                    return;
                }


                //검증시작
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){ //사용할 수 있는 아이디라면
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용 가능한 아이디입니다.")
                                        .setPositiveButton("OK", null)
                                        .create();
                                dialog.show();
                                idname.setEnabled(false); //아이디값을 바꿀 수 없도록 함
                                validate = true; //검증완료
                                idname.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                btn_check.setBackgroundColor(getResources().getColor(R.color.colorGray));
                            }else{ //사용할 수 없는 아이디라면
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("이미 존재하는 아이디 입니다.")
                                        .setNegativeButton("OK", null)
                                        .create();
                                dialog.show();
                            }

                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };//Response.Listener 완료

                //Volley 라이브러리를 이용해서 실제 서버와 통신을 구현하는 부분
                ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });


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
                String userPass = null;
                if(passwd1.getText().toString().equals(passwd2.getText().toString())) {
                    userPass = passwd2.getText().toString();
                } else {
                    Toast.makeText(getApplicationContext(),"비밀번호 확인 부분이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디 중복확인이 필요합니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                if (userID.equals("")||userPass.equals("")||passwd1.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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
                            if (success) { //회원 등록에 성공한 경우 데이터베이스에 회원 데이터가 저장되고 로그인 화면으로 이동
                                //회원 가입 성공시 success값이 true
                                Toast.makeText(getApplicationContext(),"회원등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else { //회원 등록에 실패한 경우
                                Toast.makeText(getApplicationContext(),"회원등록에 실패하였습니다. 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }; //responseListener 끝
                //서버로 Volley를 이용해서 요청을 함.
                //1. RequestObject를 생성한다. 이때 서버로부터 데이터를 받을 responseListener를 반드시 넘겨준다.
                RegisterRequest registerRequest = new RegisterRequest(userID, userPass, responseListener);
                //2. RequestQueue를 생성한다.
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                //3. RequestQueue에 RequestObject를 넘겨준다.
                queue.add(registerRequest);
            }
        });
    }
}