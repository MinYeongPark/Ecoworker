package org.techtown.ecoworker.register;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    //서버 URL 설정(PHP 파일 연동)
    final static private String URL = "http://ecoworker.dothome.co.kr/Register.php";
    private Map<String, String> map;

    public RegisterRequest(String userID, String userPassword, Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userPassword", userPassword);
    }

    //추후 사용을 위한 부분
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
