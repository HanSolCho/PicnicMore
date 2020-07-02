package com.example.samsung.picnic12;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by samsung on 2018-04-19.
 */

public class ValidateRequest extends StringRequest {

    final static private String URL = "http://whgksthf702.vps.phps.kr/UserValidate.php";
    private Map<String, String> parametets;

    public ValidateRequest(String userID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        Log.d("회원가입 리퀘스트 확인!!","회원가입 리퀘스트 확인");
        parametets = new HashMap<>();
        parametets.put("userID", userID);

    }

    @Override
    public Map<String, String> getParams(){
        return  parametets;
    }

}
