package com.example.samsung.picnic12;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by samsung on 2018-04-27.
 */

public class FourFragment extends Fragment implements View.OnClickListener {

    String mac;
    private FloatingActionButton button1,button2,button3;

    public SharedPreferences prefs2;

    private static String TAG = "phptest_MainActivity";

    private boolean lastItemVisibleFlag = false;    // 리스트 스크롤이 마지막 셀(맨 바닥)로 이동했는지 체크할 변수
    private int page = 0;                           // 페이징변수. 초기 값은 0 이다.
    private final int OFFSET = 20;                  // 한 페이지마다 로드할 데이터 갯수.
    private ProgressBar progressBar;                // 데이터 로딩중을 표시할 프로그레스바
    private boolean mLockListView = false;          // 데이터 불러올때 중복안되게 하기위한 변수

    private static final String TAG_JSON="webnautes";
    private static final String TAG_RID = "rid";
    private static final String TAG_TITLE = "title";
    private static final String TAG_ID ="id";

    private TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList,mArrayList1;
   // ListView mlistView;
    String mJsonString;

    private AlertDialog dialog;

    String rid;
    String title;
    String id;

    int FOUR = 444;

    ListView m_ListView1;
    ListView m_ListView2;

    RoomAdapter m_Adapter,m_Adapter1;

    HashMap<String,String> hashMap;


    public FourFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_four, container, false);


        mArrayList = new ArrayList<>();
        mArrayList1 = new ArrayList<>();

        // 커스텀 어댑터 생성
        m_Adapter = new RoomAdapter();
        m_Adapter1 = new RoomAdapter();

        // Xml에서 추가한 ListView 연결
        m_ListView1 = (ListView)layout.findViewById(R.id.listView1);
        m_ListView2 = (ListView)layout.findViewById(R.id.listView2);

        // ListView에 어댑터 연결
        m_ListView1.setAdapter(m_Adapter1);
        m_ListView2.setAdapter(m_Adapter);

        SharedPreferences idsdf = getActivity().getSharedPreferences("id", Activity.MODE_PRIVATE);
        mac = idsdf.getString("id",""); //id넘어오도록


//        GetData task = new GetData();
//        task.execute("http://whgksthf702.vps.phps.kr/RoomList.php");


        button1 = (FloatingActionButton)layout.findViewById(R.id.button1);

        button1.setOnClickListener(this);

        prefs2 = getActivity().getSharedPreferences("Pref2",MODE_PRIVATE);
        boolean isFirstRun = prefs2.getBoolean("isFirstRun2",true);


        if(isFirstRun) {
            SharedPreferences sf = getActivity().getSharedPreferences("mylist",0);
            String str = sf.getString(mac,"");
            //  storage.add(new restaurant(str,getTime(),));
            prefs2.edit().putBoolean("isFirstRun2",false).apply();
        }
        else {
            SharedPreferences sf = getActivity().getSharedPreferences("mylist", Activity.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sf.getString(mac, "");
            Type myType = new TypeToken<ArrayList<HashMap<String, String>>>() {
            }.getType();
            mArrayList1 = gson.fromJson(json, myType); //추가할 필요없이 저장된 json가져다 쓰는거임 ㅇㅇ
            Log.d("메모장 STORAGE GSON", json); // 결국 다시 여기가 문제네....................
            Log.d("메모장 STORAGE GSON", mArrayList1.toString());
            for(int i=0; i<mArrayList1.size();i++) {
                m_Adapter1.add(mArrayList1);
                //통과
            }

        }

        GetData task = new GetData();
        task.execute("http://whgksthf702.vps.phps.kr/RoomList.php");

       // m_Adapter1.notifyDataSetChanged();

        m_ListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {  //한번 눌러서 액티비티이동
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int pos = i;
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                dialog = builder.setMessage("방에 참가하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //리스트뷰 추가
                               // Log.d("?????????????????",mArrayList.get(pos).toString());
                                mArrayList1.add(mArrayList.get(pos));
                                m_Adapter1.add(mArrayList1) ;
                                m_Adapter1.notifyDataSetChanged();

                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                    }
                                };
                               AddRoomuserRequest addRoomuserRequest = new AddRoomuserRequest(mac,Integer.toString(pos), responseListener);
                                RequestQueue queue = Volley.newRequestQueue(getContext());
                                queue.add(addRoomuserRequest);


                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 그냥 취소
                            }
                        })
                        .create();
                dialog.show();
            }
        });




        m_ListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {  //한번 눌러서 액티비티이동
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), Chat1.class);
              //  m_List.get(position).inputValue.get(position).get("title")
                mArrayList1.get(i).get(TAG_RID);
                Log.d("아이템 찾기", mArrayList1.get(i).get(TAG_RID));
                intent.putExtra("rid", mArrayList1.get(i).get(TAG_RID));
                startActivity(intent);
            }
        });

//나가면서 리스트뷰 어레이리스트 크기문제발생 생각해볼것
        m_ListView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {  //롱클릭시 제거메세지
                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext()); // 다이얼로그 사용
                final int pos = i;
                dlg.setTitle("방 나가기")
                        .setMessage("나가시겠습니까?")
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                              //  Log.d("?????????????????",mArrayList1.toString());
                              //  Log.d("?????????????????",mArrayList1.get(pos).toString());
                                mArrayList1.remove(mArrayList1.get(pos));
                                m_Adapter1.remove(pos);
                              //  Log.d("?????????????????",mArrayList1.toString());
                              //  m_Adapter1.add(mArrayList1);
                                m_Adapter1.notifyDataSetChanged();

                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                    }
                                };
                                DeleteRoomuserRequest deleteRoomuserRequest = new DeleteRoomuserRequest(mac,rid, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(getContext());
                                queue.add(deleteRoomuserRequest);



                            }
                        }).show();
                return true;
            }
        });


        return layout;
    }


    public void onClick(View v){
        Intent intent = null;
        switch (v.getId()){
            case R.id.button1:
                intent = new Intent(getContext(), NewRoom.class);
        }
        startActivityForResult(intent,444);

    }

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getContext(),
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
         //   mTextViewResult.setText(result);
            Log.d(TAG, "response  - " + result);

            if (result == null){

             //   mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                rid = item.getString(TAG_RID);
                title = item.getString(TAG_TITLE);
                id = item.getString(TAG_ID);

                hashMap = new HashMap<>();

                hashMap.put(TAG_RID, rid);
                hashMap.put(TAG_TITLE, title);
                hashMap.put(TAG_ID, id);
                Log.d("여기오나!!!!!!!!!!",hashMap.get(TAG_TITLE));
                mArrayList.add(hashMap);

                refresh(mArrayList);
            }

//            ListAdapter adapter = new SimpleAdapter(
//                    FourFragment.this, mArrayList, R.layout.item_list,
//                    new String[]{TAG_RID,TAG_TITLE, TAG_ID},
//                    new int[]{R.id.textView_list_id, R.id.textView_list_name, R.id.textView_list_address}
//            );
//
//            m_listView.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }




    private void refresh (ArrayList<HashMap<String, String>> inputValue) {
        m_Adapter.add(inputValue) ;
        m_Adapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("onstop",mArrayList1.toString());
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<HashMap<String,String>>>() {}.getType();
        String json = gson.toJson(mArrayList1, listType); //json변환
        SharedPreferences sf =getActivity().getSharedPreferences("mylist",Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sf.edit();
        prefsEditor.putString(mac, json);
        prefsEditor.commit();

        Log.d("gson확인하자",json);
        Log.d("shared확인하자",sf.getString(mac,""));

    }

    @Override
    public void onResume() {
        super.onResume();

    }


}