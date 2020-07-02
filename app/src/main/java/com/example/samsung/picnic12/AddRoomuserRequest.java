package com.example.samsung.picnic12;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by samsung on 2018-06-26.
 */

public class AddRoomuserRequest extends StringRequest {

    final static private String URL = "http://whgksthf702.vps.phps.kr/AddRoomuser.php";
    private Map<String, String> parametets;

    public AddRoomuserRequest(String userID, String roomID, Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);
        parametets = new HashMap<>();
        Log.d("룸유저추가디비리퀘스트","!!!!!!!!!!!!!!!!!!!");
        parametets.put("userID", userID);
        parametets.put("roomID",roomID);

    }

    @Override
    public Map<String, String> getParams(){
        return  parametets;
    }
}