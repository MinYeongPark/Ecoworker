package org.techtown.ecoworker.stat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class DateNewRequest extends StringRequest {
    public DateNewRequest(int recDate_year, int recDate_mon, int recDate_day, String foodName, int amount, int carbonAmount, String userID, Response.Listener<String> listener){
        super(Request.Method.POST, "http://ecoworker.dothome.co.kr/CarbonRegister.php?recDate_year="+recDate_year+"&recDate_mon="+recDate_mon+"&recDate_day="+recDate_day+"&foodName="+foodName+"&amount="+amount+"&carbonAmount="+carbonAmount+"&userID="+userID, listener, null);
    }
}
