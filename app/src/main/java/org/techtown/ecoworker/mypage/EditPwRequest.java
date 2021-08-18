package org.techtown.ecoworker.mypage;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EditPwRequest extends StringRequest {

    final static private String URL = "http://ecoworker.dothome.co.kr/updateMypage.php";
    private Map<String, String> map;

    public EditPwRequest (String userID, String password, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userPassword", password);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
