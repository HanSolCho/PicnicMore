package com.example.samsung.picnic12;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by samsung on 2018-05-16.
 */

public class NewRoom extends AppCompatActivity {

    EditText roomname;
    Button crebtn;

    int FOUR = 1;

    String Roomname,id;

    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newroom);

        roomname = (EditText)findViewById(R.id.newroom);

        crebtn = (Button)findViewById(R.id.btnadd);

        final SharedPreferences idsdf = getSharedPreferences("id", Activity.MODE_PRIVATE);


        crebtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Roomname = roomname.getText().toString();
                id =  idsdf.getString("id","");

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            Log.d("room response",response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(NewRoom.this);
                                dialog = builder.setMessage("방 만들기 성공")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();

                                Intent intent = new Intent(NewRoom.this, MainActivity.class);
                                setResult(333,intent);
                              //  NewRoom.this.startActivity(intent);
                                finish();
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(NewRoom.this);
                                dialog = builder.setMessage("방 만들기 실패")
                                        .setNegativeButton("다시 시도",null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                NewRoomRequest newRoomRequest = new NewRoomRequest(Roomname, id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(NewRoom.this);
                queue.add(newRoomRequest);
            }
        }));

    }

    @Override
    protected  void  onStop(){
        super.onStop();
        if(dialog != null)
        {
            dialog.dismiss();
            dialog=null;
        }
    }


//    public void onClick(View v){
//        if(v.getId() == R.id.btncancel){ //취소버튼
//            finish();
//        }else if(v.getId() == R.id.btnadd){ //추가 버튼이라면
//
//            //db추가
//            Roomname = roomname.getText().toString();
//            id =  idsdf.getString("id","");
//
//            finish();
//
//        }
//    }

}
