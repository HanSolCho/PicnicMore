package com.example.samsung.picnic12;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by samsung on 2018-05-21.
 */

public class MessageRequest extends StringRequest {

    final static private String URL = "http://whgksthf702.vps.phps.kr/AddMessage.php";
    private Map<String, String> parametets;

    public MessageRequest(String userID, String roomID, String msg,String time, Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);
        parametets = new HashMap<>();
        Log.d("이거는 메세지 저장 테스트","!!!!!!!!!!!!!!!!!!!");
        parametets.put("userID", userID);
            parametets.put("roomID",roomID);
        parametets.put("msg",msg);
        parametets.put("time",time);


}

    @Override
    public Map<String, String> getParams(){
        return  parametets;
    }
}