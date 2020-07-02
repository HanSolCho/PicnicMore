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

public class RoomListRequest extends StringRequest {

    final static private String URL = "http://whgksthf702.vps.phps.kr/RoomList.php";
    private Map<String, String> parametets;

    public RoomListRequest(Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);
        //이거 나옴
        // Log.d("이거 나옴??","궁금한데?");
    }

    @Override
    public Map<String, String> getParams(){
        return  parametets;
    }
}

