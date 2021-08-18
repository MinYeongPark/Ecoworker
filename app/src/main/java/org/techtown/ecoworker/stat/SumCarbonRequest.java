package org.techtown.ecoworker.stat;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class SumCarbonRequest extends StringRequest {
    public SumCarbonRequest (String userID, int year, int month, Response.Listener<String> listener){
        super(Method.GET, "http://ecoworker.dothome.co.kr/SumCarbon.php?userID="+userID+"&year="+year+"&month="+month, listener, null);
    }
}
