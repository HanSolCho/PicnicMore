package com.example.samsung.picnic12;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.android.volley.VolleyLog.TAG;

public class CustomAdapter extends BaseAdapter {

    String mJsonString;

    String profileimg;
    String userid;


    HashMap<String, String> hashMap = new HashMap<>();

    private static final String TAG_JSON = "webnautes";
    private static final String TAG_IMAGE = "proimg";
    private static final String TAG_ID = "userid";

    public class ListContents{

        String id;
        String myid;
        String yourid;



        String msg;

        String time;

        int type;

        ListContents(String _msg, String _id,String _time)
        {
            //??????
            this.msg = _msg;
            this.id = _id;
            this.time = _time;

        }
    }


    private ArrayList<ListContents> m_List;

    public CustomAdapter() {
        m_List = new ArrayList<ListContents>();
    }


    public void add(String _msg, String _id,String _time) {

        m_List.add(new ListContents(_msg,_id,_time));
    }


    public void remove(int _position) {
        m_List.remove(_position);
    }

    @Override
    public int getCount() {
        return m_List.size();
    }

    @Override
    public Object getItem(int position) {
        return m_List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //?????
        final int pos = position;
        final Context context = parent.getContext();

        //넘어온 메시지에서 아이디 떼오는거
        int ididx = m_List.get(position).msg.indexOf(":");
        String idcheck = m_List.get(position).msg.substring(0,ididx);

        int msgidx = m_List.get(position).msg.indexOf(":");
        String remsg = m_List.get(position).msg.substring(msgidx+1);

        String mtime =  m_List.get(position).time;

       // Log.d("채팅어뎁터상대아이디",idcheck);



        TextView myidtext = null;
        TextView youridtext = null;

        ImageView image;
        ImageView yourimg;
        ImageView myimg;



        TextView text = null;

        CustomHolder holder  = null;
        LinearLayout layout  = null;


        View viewRight = null;
        View viewLeft = null;

//        long now = System.currentTimeMillis();
//        Date date = new Date(now);
//        SimpleDateFormat sdf = new SimpleDateFormat("a K:mm");
//        String nowDate = sdf.format(date);


        if ( convertView == null ) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_chatitem, parent, false);


            layout    = (LinearLayout) convertView.findViewById(R.id.layout);

            myidtext = (TextView) convertView.findViewById(R.id.myid);
            youridtext = (TextView)convertView.findViewById(R.id.yourid);
            text    = (TextView) convertView.findViewById(R.id.text);
            viewRight    = (View) convertView.findViewById(R.id.imageViewright);
            viewLeft    = (View) convertView.findViewById(R.id.imageViewleft);
            image = (ImageView)convertView.findViewById(R.id.image);
            yourimg = (ImageView)convertView.findViewById(R.id.youimg);
            myimg = (ImageView)convertView.findViewById(R.id.myimg);


            holder = new CustomHolder();
            holder.m_myidText = myidtext;
            holder.m_youridText = youridtext;
            holder.m_TextView   = text;
            holder.layout = layout;
            holder.viewRight = viewRight;
            holder.viewLeft = viewLeft;
            holder.m_image = image;
            holder.m_yourimg = yourimg;
            holder.m_myimg = myimg;
            convertView.setTag(holder);
        }
        else {
            holder  = (CustomHolder) convertView.getTag();
            myidtext = holder.m_myidText;
            youridtext = holder.m_youridText;
            text    = holder.m_TextView;
            layout  = holder.layout;
            viewRight = holder.viewRight;
            viewLeft = holder.viewLeft;
            image = holder.m_image;
            yourimg = holder.m_yourimg;
            myimg = holder.m_myimg;
        }
//        Log.d("id??",m_List.get(position).myid);
//        Log.d("msg??",m_List.get(position).msg);
//        Log.d("idcheck??",idcheck);
//        Log.d("idcheck??",Boolean.toString(m_List.get(position).id.equals(idcheck)));

        youridtext.setText(idcheck);
        GetData task = new GetData();
        task.execute("http://whgksthf702.vps.phps.kr/profileimage.php", idcheck);
        myidtext.setText(m_List.get(position).id);
       // text.setText(remsg);
        Uri uri =Uri.parse(remsg);
       // text.setText(m_List.get(position).msg);
        boolean imgcheck = remsg.contains(":");
        boolean imgcheck2 = remsg.contains("jpg");


        if(imgcheck || imgcheck2){
            if(imgcheck && !imgcheck2){
                image.setVisibility(View.VISIBLE);
                text.setVisibility(View.GONE);
                image.setImageURI(uri);
            }
            else if(!imgcheck && imgcheck2){
                youridtext.setGravity(Gravity.TOP);
                image.setVisibility(View.VISIBLE);
                text.setVisibility(View.GONE);
                Picasso.with(context)
                        .load("http://whgksthf702.vps.phps.kr/image/"+remsg)
                        .into(image);

                // image.setImageBitmap(m_List.get(position).bitmap);//여기까지 해봄 이뒤로 이어나가야됨
                // 비트맵 쓰레드를 메인으로 빼서
            }
        }
        else {
            text.setVisibility(View.VISIBLE);
            text.setText(remsg);
            image.setVisibility(View.GONE);
        }

//img있으면 text gone 주고
        if( m_List.get(position).id.equals(idcheck) ) {
            youridtext.setText(mtime);
          //  youridtext.setGravity(Gravity.BOTTOM);
            text.setBackgroundResource(R.drawable.outbox2);
            // text.setBackgroundResource(R.drawable.inbox2);
            myidtext.setVisibility(View.GONE);
            youridtext.setVisibility(View.VISIBLE);
            layout.setGravity(Gravity.RIGHT);
            yourimg.setVisibility(View.GONE);
            myimg.setVisibility(View.GONE);
            // layout.setGravity(Gravity.LEFT);
            viewRight.setVisibility(View.GONE);
            viewLeft.setVisibility(View.GONE);
        }
        else{
            Picasso.with(context)
                    .load("http://whgksthf702.vps.phps.kr/uploads/"+hashMap.get(idcheck))
                    .into(yourimg);
            myidtext.setText(mtime);
            youridtext.setGravity(Gravity.TOP);
            myidtext.setGravity(Gravity.BOTTOM);
            text.setBackgroundResource(R.drawable.inbox2);
            //   text.setBackgroundResource(R.drawable.outbox2);
            layout.setGravity(Gravity.LEFT);
            //  layout.setGravity(Gravity.RIGHT);
            myidtext.setVisibility(View.VISIBLE);
            youridtext.setVisibility(View.VISIBLE);
            yourimg.setVisibility(View.VISIBLE);
            myimg.setVisibility(View.GONE);
            viewRight.setVisibility(View.GONE);
            viewLeft.setVisibility(View.GONE);
        }

        return convertView;
    }

    private class CustomHolder {
        TextView m_myidText;
        TextView m_youridText;
        TextView m_TextView;
        LinearLayout layout;
        View viewRight;
        View viewLeft;
        ImageView m_image;
        ImageView m_yourimg;
        ImageView m_myimg;
    }

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //   mTextViewResult.setText(result);
            //Log.d(TAG, "response  - " + result);

            if (result == null) {

                //   mTextViewResult.setText(errorString);
            } else {
                //여기에 리퀘스트 넣어야되나?
                //five frag에서 num정보 받고 인텐트로 여기로 넘겨주도록 하자
                //여까지온다
                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            // num 값 받아옴
            String number = params[1];

            HashMap<String, String> nummap;

            nummap = new HashMap<>();

            nummap.put("num", number);


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("test").append("=").append(number);

                PrintWriter pw = new PrintWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"));
                pw.write(stringBuffer.toString());
                pw.flush();

                int responseStatusCode = httpURLConnection.getResponseCode();
             //   Log.d(TAG, "response code - " + responseStatusCode);


                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

            //    Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void showResult() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);


            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                userid = item.getString(TAG_ID);
                 profileimg = item.getString(TAG_IMAGE);
                hashMap.put(userid,profileimg);
                // Log.d("채팅어뎁터프로필이미지이름",profileimg);

               // Log.d("쇼리설트userid",hashMap.toString());
            }

        } catch (JSONException e) {

           // Log.d(TAG, "showResult : ", e);
        }

    }

}
