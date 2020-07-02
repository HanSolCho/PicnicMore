package com.example.samsung.picnic12;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by samsung on 2018-05-17.
 */

public class NewRoomRequest extends StringRequest {

    final static private String URL = "http://whgksthf702.vps.phps.kr/Room.php";
    private Map<String, String> parametets;

    public NewRoomRequest(String Title, String userID, Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);
        Log.d("방만들기 리퀘스트 확인!!","방만들기 리퀘스트 확인");
        parametets = new HashMap<>();
        parametets.put("Title", Title);
        parametets.put("userID",userID);


    }

    @Override
    public Map<String, String> getParams(){
        return  parametets;
    }
}