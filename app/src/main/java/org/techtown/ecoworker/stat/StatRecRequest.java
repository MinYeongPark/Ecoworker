package org.techtown.ecoworker.stat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class StatRecRequest extends StringRequest {
    public StatRecRequest(String userID, int year, int month, int dayOfMonth, Response .Listener<String> listener){
        super(Method.GET, "http://ecoworker.dothome.co.kr/StatRec.php?userID="+userID+"&year="+year+"&month="+month+"&dayOfMonth="+dayOfMonth, listener, null);
    }
}
