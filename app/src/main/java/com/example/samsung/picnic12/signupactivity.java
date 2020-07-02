package com.example.samsung.picnic12;

import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samsung on 2018-04-18.
 */

public class signupactivity extends AppCompatActivity {

    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        final EditText idtext = (EditText) findViewById(R.id.idtext);
        final EditText pwtext = (EditText) findViewById(R.id.passwordtext);
        final EditText pw2text = (EditText) findViewById(R.id.passwordtext2);
        final EditText nametext = (EditText) findViewById(R.id.nametext);

        Button signupbtn = (Button) findViewById(R.id.signupbtn);
        final Button validateButton = (Button) findViewById(R.id.validateButton);


        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = idtext.getText().toString();

                if (validate) {
                    return;
                }
                if (userID.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(signupactivity.this);
                    builder.setMessage("아이디는 빈 칸 일 수 없습니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                    return;
                }
                Response.Listener<String> responseListner = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("validate respon",response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(signupactivity.this);
                                builder.setMessage("사용 가능한 아이디.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                idtext.setEnabled(false);
                                validate = true;
                                idtext.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                validateButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(signupactivity.this);
                                builder.setMessage("사용 할 수 없는 아이디.")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(userID, responseListner);
                RequestQueue queue = Volley.newRequestQueue(signupactivity.this);
                queue.add(validateRequest);
            }
        });


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = idtext.getText().toString();
                String userPassword = pwtext.getText().toString();
                String userPassword2 = pw2text.getText().toString();
                String userName = nametext.getText().toString();
                Log.d("클릭 확인!!", "클릭확인");
                Log.d("ID확인", userID);
                Log.d("Pw확인", userPassword);

                if (!validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(signupactivity.this);
                    builder.setMessage("중복 체크를 진행해주세요")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                    return;
                }

                if (userID.equals("") || userPassword.equals("") || userPassword2.equals("") || userName.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(signupactivity.this);
                    builder.setMessage("빈칸을 모두 입력해주세요")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                    return;
                }

                if(!userPassword.equals(userPassword2)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(signupactivity.this);
                    builder.setMessage("비밀번호를 확인해주세요")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                    return;
                }

                Response.Listener<String> responseListner = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(signupactivity.this);
                                builder.setMessage("회원가입에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(signupactivity.this);
                                builder.setMessage("회원가입에 실패했습니다..")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                signupRequest signupRequest = new signupRequest(userID, userPassword, userPassword2, userName, responseListner);
                RequestQueue queue = Volley.newRequestQueue(signupactivity.this);
                queue.add(signupRequest);
            }

        });


    }


}
