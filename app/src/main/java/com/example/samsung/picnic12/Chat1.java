package com.example.samsung.picnic12;

/**
 * Created by samsung on 2018-05-11.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import android.os.Handler;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by samsung on 2018-05-08.
 */

public class Chat1 extends AppCompatActivity {

    ListView m_ListView,m_ListView2;

    ArrayList<HashMap<String, String>> mArrayList;
    //m_Listview2는 드로워에 나타날 거임
    CustomAdapter m_Adapter;
    RoomuserAdapter m_Adapter2;

    Button btnSend, sendimg; //btnConnect,btnname
    EditText editMessage; // editIp,editPort,
    Handler msgHandler;

    SocketClient client;
    ReceiveThread receive;
    SendThread send;
    ImageThread imgthread;
    Socket socket,socket2;

    String mJsonString;
    //LinkedList<SocketClient> threadList;
    Context context;

    String mac, rid;
    String phpid;
    String phprid;
    String phpmsg, phptime;

    EditText id;

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_CAMERA = 2;

    private Uri mImageCaptureUri;
    Uri ivuri;
    private ImageView imagetest;

    String absoultePath;
    String filePath, filename,fileurl;

    private static String TAG = "phptest_MainActivity";

    private static final String TAG_JSON = "webnautes";
    private static final String TAG_ID = "id";
    private static final String TAG_RID = "rid";
    private static final String TAG_MSG = "msg";
    private static final String TAG_TIME = "time";
    private static final String TAG_PROFILE = "proimg";

    String idcheck;
    String nowDate;

    String msgcheck;

    private DrawerLayout mDrawerLayout;

    String dbname,proimg;

    HashMap<String, String> hashMap;



    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.chat1);

        context = this; // 현재 액티비티 정보를 context에 백업


        editMessage = (EditText) findViewById(R.id.editMessage);
        btnSend = (Button) findViewById(R.id.btnSend);
        sendimg = (Button) findViewById(R.id.imgbtn);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
//        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mArrayList = new ArrayList<>();

        SharedPreferences idsdf = getSharedPreferences("id", Activity.MODE_PRIVATE);
        mac = idsdf.getString("id", ""); //id넘어오도록


        Intent rintent = getIntent();
        rid = rintent.getExtras().getString("rid");
        Log.d("rid찾기", rid);

        client = new SocketClient("115.71.239.41", "5003");
        client.start();

        // 커스텀 어댑터 생성
        m_Adapter = new CustomAdapter();
        m_Adapter2 = new RoomuserAdapter();

        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) findViewById(R.id.listView1);

        m_ListView2 = (ListView)findViewById(R.id.nav_list);

        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);
        m_ListView2.setAdapter(m_Adapter2);

        Chat1.GetData task = new Chat1.GetData();
        task.execute("http://whgksthf702.vps.phps.kr/MessageList.php");

        Chat1.GetData2 task2 = new Chat1.GetData2();
        task2.execute("http://whgksthf702.vps.phps.kr/RoomuserList.php",rid);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                return true;
            }
        });


        //RecevieThread를 통해 받은 메세지를 Handler로 MainmThread에서 처리
        msgHandler = new Handler() {
            @Override
            public void handleMessage(Message hdmsg) {
                if (hdmsg.what == 1111) {
                    //채팅서버로부터 수신한 메세지를 텍스트뷰에 추가
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat sdf = new SimpleDateFormat("a K:mm");
                    nowDate = sdf.format(date);
                    String rmsg = hdmsg.obj.toString() + "\n";
                    Log.d("rmsg확인", rmsg);
                    String inputValue = rmsg;
                    refresh(inputValue, mac, nowDate);
                }
            }
        };


        //전송버튼 클릭 이벤트,메세지 전송
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //사용자 입력 메세지
                //사용자 입력 메세지
                String message = editMessage.getText().toString();
                //Sendthread 시작
                //입력 메세지가 비어있지않다면
                if (message != null || !message.equals("")) {
                    //메시지 송신\
                    Log.d("내용확인해야됨", message);
                    Log.d("메세지 비워두면 안들어와야되", "xxxx");
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat sdf = new SimpleDateFormat("a K:mm");
                    nowDate = sdf.format(date);

                    String smsg = mac + ":" + editMessage.getText().toString() + "\n";
                    String inputValue = smsg;
                    refresh(inputValue, mac, nowDate);

                    send = new SendThread(socket,message);
                    send.start();

                    //시작후 edittext 초기화
                    editMessage.setText("");
                }
//                else if (message == null || message.equals("")) {
//                    Log.d("메세지 비워두면  여기로 들어와야되", "ㅇㅇㅇㅇ");
//                    long now = System.currentTimeMillis();
//                    Date date = new Date(now);
//                    SimpleDateFormat sdf = new SimpleDateFormat("a K:mm");
//                    nowDate = sdf.format(date);
//
//                    //클라단에서 이미지 보여주고 보내도록 나중에 추가해야됨
//                    //클라단에서 이미지 바로 올리는 부분은 앨범가져오기부분에 넣어둠
//                    //아래 db저장 부분도 나주엥 추가
////                    m_Adapter.add(mac+":"+ivuri.toString(),mac,nowDate) ;
////                    m_Adapter.notifyDataSetChanged();
//                    refresh(fileurl, mac ,nowDate);
//
//                    message = filename;
//
//                    imgthread = new ImageThread(filename);
//                    imgthread.start();
//                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                //여기에 시간추가해야됨..
                Log.d("시간 나오나?", nowDate);
                MessageRequest messageRequest = new MessageRequest(mac, rid, message, nowDate, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Chat1.this);
                queue.add(messageRequest);
            }
        });

        sendimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakePhotoAction();
                    }
                };

                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakeAlbumAction();
                    }
                };

                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };

                new android.app.AlertDialog.Builder(v.getContext())
                        .setTitle("업로드할 이미지 선택")
//                        .setPositiveButton("사진촬영", cameraListener)
                        .setNeutralButton("앨범선택", albumListener)
                        .setNegativeButton("취소", cancelListener)
                        .show();


            }

        });

        m_ListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Chat1.this,mArrayList.get(position).get("id").toString(), Toast.LENGTH_SHORT).show();
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("a K:mm");
                nowDate = sdf.format(date);

                String smsg = mac + ":" + mArrayList.get(position).get("id").toString()+"videocall" + "\n";
                String inputValue = smsg;
                refresh(inputValue, mac, nowDate);

                //버튼에서는 여기에 socket,edit
                //여기서는 socket,smsg
                //저장도해야함
                send = new SendThread(socket,mArrayList.get(position).get("id").toString()+"videocall");
                send.start();

                Intent vcallintent = new Intent(Chat1.this,ConnectActivity2.class);
                vcallintent.putExtra("roomname",mArrayList.get(position).get("id").toString());
                startActivity(vcallintent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }




    private void refresh(String inputValue, String id, String time) {
        m_Adapter.add(inputValue, id, time);
        m_Adapter.notifyDataSetChanged();
    }

    //소켓 연결하는 스레드
    class SocketClient extends Thread {
        boolean threadAlive; //동작 여부 back키에서 액티비티 생명주기에서 앱 종류시 스레드도 종료시키기위함
        String ip;
        String port;


        OutputStream outputStream = null;
        BufferedReader br = null;

        private DataOutputStream output = null;

        public SocketClient(String ip, String port) {
            threadAlive = true;
            this.ip = ip;
            this.port = port;
        }

        @Override
        public void run() {

            try {
                //연결후 바로  ReceiveThread 시작
                //채팅서버에 접속
                socket = new Socket(ip, Integer.parseInt(port));
                //서버에 메세지를 전달하기 위한 스트림 생성
                output = new DataOutputStream(socket.getOutputStream());
                //메세지 수신용 스레드 생성
                receive = new ReceiveThread(socket);
                receive.start();


                //mac주소받아오기위해 설정
                //와이파이 정보 관리자 객체에서 폰의 mac 주소 가져와서
                // 채팅 서버에ㅐ 전달해줌
                WifiManager mng = (WifiManager) context.getSystemService(WIFI_SERVICE);
                WifiInfo info = mng.getConnectionInfo();
                // mac = info.getMacAddress();

                //mac전송
                output.writeUTF(mac);
                output.writeUTF(rid);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } //end of socketclient

    //서버에서 도착한 메시지를 받아서 핸들러에게 요청하면 화면에 보여주는 스레드
    //메시지 수신용 스레드
    class ReceiveThread extends Thread {
        private Socket socket = null;
        DataInputStream input;

        public ReceiveThread(Socket socket) {
            this.socket = socket;

            try {
                //채팅 서버로부터 메시지를 받기 위한 스트림
                input = new DataInputStream(socket.getInputStream());
            } catch (IOException e) {

            }

        }

        //메세지 수신후 hanlder로 전달
        //여기서 저장하면될듯
        public void run() {
            try {
                //인풋스트림이 존재하면
                while (input != null) {
                    //채팅서버로 부터 받은 메세지
                    //msg변수에는 id:msg 형식으로 저장된다.
                    String msg = input.readUTF();

                    //msgidx에 ":"의 위치값을 넣는다
                    int msgidx = msg.indexOf(":");

                    Log.d("메세지 저장 확인", msg);
                    //idcheck에 처음부터 msgidx값까지의 문자를 넣는다.
                    idcheck = msg.substring(0, msgidx);

                    //msgcheck에 msgidx값 이후의 문자를 넣는다.
                    msgcheck = msg.substring(msgidx + 1);


                    if (msg != null && !idcheck.equals(mac)) {
                        Log.d(ACTIVITY_SERVICE, "test");
                        //핸들러에게 전달한 메시지객체
                        Message hdmsg = msgHandler.obtainMessage();
                        //메시지의 식별자, 메시지 식별자로 방 나눌수 있을까????
                        hdmsg.what = 1111;
                        //메시지 본문 내용
                        hdmsg.obj = msg;
                        //핸들러에게 메시지 전달 (화면 변경)
                        msgHandler.sendMessage(hdmsg);
                        Log.d(ACTIVITY_SERVICE, hdmsg.obj.toString());
                    }

                    if(msg.contains(mac+"videocall")){
                        Intent testintent = new Intent(Chat1.this,ConnectActivity.class);
                        testintent.putExtra("receiveroom",idcheck);
                        testintent.putExtra("receivemyid",mac);
                        startActivity(testintent);

                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class SendThread extends Thread {
        private Socket socket;
        //사용자가 입력한 텍스트
        String sendmsg; // = editMessage.getText().toString();
        DataOutputStream output;

        public SendThread(Socket socket,String msg) {
            this.socket = socket;
            this.sendmsg = msg;
            try {
                //채팅 서버로 메시지를 보내기 위한 스트림 생성
                output = new DataOutputStream(socket.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                //스트림이 존재하고
                if (output != null) {
                    //메시지가 존재하면
                    if (sendmsg != null) {
                        //채팅서버에 메시지 전달
                        output.writeUTF(rid);
                        output.writeUTF("text");
                        output.writeUTF(mac + ":" + sendmsg);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class ImageThread extends Thread {
        private String msg;

        DataOutputStream image_output;
        DataOutputStream output;

        public ImageThread(String msg) {
            this.msg = msg;
            //밖에서 소켓 받아오지말고 새로 소켓 만들어서 그걸로 스트림 전개해볼것!!!!!!!!!!!!!!!!!!!!
            try {
                //채팅 서버로 메시지를 보내기 위한 스트림 생성
                output = new DataOutputStream(socket.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                socket2 = new Socket("115.71.239.41", 5004);

                try {
                    output.writeUTF(rid);
                    output.writeUTF("img");

                    PrintWriter out = new PrintWriter(new BufferedWriter(new
                            OutputStreamWriter(socket2.getOutputStream())), true);
                    out.println(filename);
                    out.flush();

                    Log.d("소케스레드파일경로", msg);
                    DataInputStream dis = new DataInputStream(new FileInputStream(new File(filePath + filename)));
                    Log.d("file이름", filePath + filename);

                    File f = new File(filePath + filename);
                    f.length();

                    DataOutputStream dos = new

                            DataOutputStream(socket2.getOutputStream());

                    byte[] buf = new byte[4096];

                    int read_length = 0;

                    while ((read_length = dis.read(buf)) != -1)

                    {
                        dos.write(buf, 0, read_length);
                        Log.d("파일 길이", Long.toString(read_length));
                        dos.flush();
                    }
                    dos.close();
//                    socket = new Socket("115.71.239.41",Integer.parseInt("5001"));
//                    output = new DataOutputStream(socket.getOutputStream());
                } catch (Exception e) {

                    e.printStackTrace();

                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Chat1.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //   mTextViewResult.setText(result);
            Log.d(TAG, "response  - " + result);

            if (result == null) {

                //   mTextViewResult.setText(errorString);
            } else {

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


             //   bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
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

                phpid = item.getString(TAG_ID);
                phprid = item.getString(TAG_RID);
                phpmsg = item.getString(TAG_MSG);
                phptime = item.getString(TAG_TIME);

                String phpidmsg = phpid + ":" + phpmsg;

                if (phprid.equals(rid)) {
                    m_Adapter.add(phpidmsg, mac, phptime);
                    m_Adapter.notifyDataSetChanged();
                    // Log.d("이거는 메세지 저장 테스트",phpidmsg);
                }
            }


        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }


    private void doTakePhotoAction() {
    /*
     * 참고 해볼곳
     * http://2009.hfoss.org/Tutorial:Camera_and_Gallery_Demo
     * http://stackoverflow.com/questions/1050297/how-to-get-the-url-of-the-captured-image
     * http://www.damonkohler.com/2009/02/android-recipes.html
     * http://www.firstclown.us/tag/android/
     */

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        //절대경로
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        //절대경로 사진을 가져온다.
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        // 특정기기에서 사진을 저장못하는 문제가 있어 다음을 주석처리 합니다.
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    private void doTakeAlbumAction() {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case CROP_FROM_CAMERA: {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                final Bundle extras = data.getExtras();

                filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SmartWheel/";

                filename = Long.toString(System.currentTimeMillis()) + ".jpg";
                Log.d("파일경로", filePath);
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    //이게 필요없나?? 이거 대신 uri값을 어뎁터로 보내서 보여줘야될것같은데
                    // imagetest.setImageBitmap(photo); // ivuri를 만들어야대
                    //imgage uri 주소값
                    ivuri = getImageUri(this, photo);

                    storeCropImage(photo, filePath + filename);
                    absoultePath = filePath;


                    //     Uri uri =Uri.parse("content://media/external/images/media/2336");
                    //   imagetest.setImageURI(uri);
//                    long now = System.currentTimeMillis();
//                    Date date = new Date(now);
//                    SimpleDateFormat sdf = new SimpleDateFormat("a K:mm");
//                    nowDate = sdf.format(date);

                    fileurl = mac+":"+ivuri.toString();
                    //어뎁터로가는 메세지는  "id:content:url주소"
//                    m_Adapter.add(mac+":"+ivuri.toString(),mac,nowDate) ;
//                    m_Adapter.notifyDataSetChanged();

                }

                // 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) {
                    f.delete();
                }

                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("a K:mm");
                nowDate = sdf.format(date);

                //클라단에서 이미지 보여주고 보내도록 나중에 추가해야됨
                //클라단에서 이미지 바로 올리는 부분은 앨범가져오기부분에 넣어둠
                //아래 db저장 부분도 나주엥 추가
//                    m_Adapter.add(mac+":"+ivuri.toString(),mac,nowDate) ;
//                    m_Adapter.notifyDataSetChanged();
                refresh(fileurl, mac ,nowDate);

                //message = filename;

                imgthread = new ImageThread(filename);
                imgthread.start();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                //여기에 시간추가해야됨..
                Log.d("시간 나오나?", nowDate);
                MessageRequest messageRequest = new MessageRequest(mac, rid, filename, nowDate, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Chat1.this);
                queue.add(messageRequest);

                break;
            }

            case PICK_FROM_ALBUM: {
                // 이후의 처리가 카메라와 같으므로 일단  break없이 진행합니다.
                // 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.
                mImageCaptureUri = data.getData();

            }

            case PICK_FROM_CAMERA: {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.

                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                intent.putExtra("outputX", 300);
                intent.putExtra("outputY", 300);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_CAMERA);

                break;
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        Log.d("URI변환", path);
        return Uri.parse(path);
    }

    private void storeCropImage(Bitmap bitmap, String filePath) {
        //smartwheel이라는 폴더에 이미지 저장
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SmartWheel";
        File directory_SmartWheel = new File(dirPath);

        if (!directory_SmartWheel.exists()) {
            directory_SmartWheel.mkdir();
        }

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));

            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //입장유저정보가져오기위한 겟데이터
    private class GetData2 extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Chat1.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //   mTextViewResult.setText(result);
            Log.d(TAG, "response  - " + result);

            if (result == null) {

                //   mTextViewResult.setText(errorString);
            } else {
                //여기에 리퀘스트 넣어야되나?
                //five frag에서 num정보 받고 인텐트로 여기로 넘겨주도록 하자
                //여까지온다
                mJsonString = result;
                showResult2();
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
                Log.d(TAG, "response code - " + responseStatusCode);


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

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void showResult2() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);


            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                dbname = item.getString(TAG_ID);
                proimg = item.getString(TAG_PROFILE);
                hashMap = new HashMap<>();

                hashMap.put(TAG_ID, dbname);
                hashMap.put(TAG_PROFILE, proimg);

                //리스트뷰 담을 어뎁터 만들고 선언해주자
                mArrayList.add(hashMap);

                refresh2(mArrayList);

            }

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

    private void refresh2(ArrayList<HashMap<String, String>> inputValue) {
        Log.d("널포인트체크!!!!!!!!!!", inputValue.toString());
        m_Adapter2.add(inputValue);
        m_Adapter2.notifyDataSetChanged();
        //m_ListView.setSelection(0);
    }



}
