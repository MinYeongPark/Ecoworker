package org.techtown.ecoworker.change;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class FoodRegisterRequest extends StringRequest {
    public FoodRegisterRequest(String userID, String foodName, String expDate, String useDate, String alarmMethod, int alarmTerm, Response.Listener<String> listener){
        super(Request.Method.POST, "http://ecoworker.dothome.co.kr/FoodRegister.php?foodName="+foodName+"&expDate="+expDate+"&useDate="+useDate+"&alarmMethod="+alarmMethod+"&alarmDate="+alarmTerm+"&userID="+userID, listener, null);
    }
}
