package org.techtown.ecoworker.alarm;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AlarmRequest extends StringRequest {
    private Map<String, String> map;

    public AlarmRequest(String userID, Response.Listener<String> listener){
        super(Request.Method.GET, "http://ecoworker.dothome.co.kr/Alarm.php?userID="+userID, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
