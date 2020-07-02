package com.example.samsung.picnic12;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by samsung on 2018-06-19.
 */

public class Myprofile extends AppCompatActivity implements View.OnClickListener {

    ImageView profileimage = null;

    TextView idtext = null;
    TextView nametext = null;

    Button imgbtn;
    Button editbtn;

    String myid,imgname,id,name;

    String mJsonString;
    private static final String TAG_JSON = "webnautes";
    private static final String TAG_IMAGE = "proimg";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";


    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int CROP_FROM_FACECAMERA = 3;

    private Uri mImageCaptureUri;

    Uri ivuri;

    String absoultePath;
    String filePath, filename,fileurl;

    String upLoadServerUri = null;
    int serverResponseCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileimage = (ImageView)findViewById(R.id.profileimage);
        idtext = (TextView) findViewById(R.id.id);
        nametext = (TextView) findViewById(R.id.name);
        imgbtn = (Button)findViewById(R.id.imgbtn);
        editbtn = (Button)findViewById(R.id.editbtn);

        imgbtn.setOnClickListener(this);
        editbtn.setOnClickListener(this);

        SharedPreferences idsdf = getSharedPreferences("id", Activity.MODE_PRIVATE);
        myid = idsdf.getString("id", ""); //id넘어오도록

        upLoadServerUri = "http://whgksthf702.vps.phps.kr/UploadToServer.php";//서버컴퓨터의 ip주소

        Myprofile.GetData task = new Myprofile.GetData();
        //num 조건 동일한거 가져오도록 설정해주기
        //우선 num.을 넘겨줘야겠네
        task.execute("http://whgksthf702.vps.phps.kr/profileimage.php", myid);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.imgbtn:
                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent faceintent = new Intent(Myprofile.this,facecamera.class);
                        startActivityForResult(faceintent, CROP_FROM_FACECAMERA);
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

                    }
                };

                new android.app.AlertDialog.Builder(v.getContext())
                        .setTitle("업로드할 이미지 선택")
                        .setPositiveButton("사진촬영", cameraListener)
                        .setNeutralButton("앨범선택", albumListener)
                        .setNegativeButton("취소", cancelListener)
                        .show();

                break;
            case  R.id.editbtn:

                new Thread(new Runnable() {

                    public void run() {
                        Log.d("프로필절대경로",absoultePath);
                        uploadFile(absoultePath);
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        };
                        ProfileRequest profileRequest = new ProfileRequest(myid,filename, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(Myprofile.this);
                        queue.add(profileRequest);
                    }
                }).start();

                finish();
                break;
        }
    }

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Myprofile.this,
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
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            // num 값 받아옴
            String userid = params[1];

            HashMap<String, String> nummap;

            nummap = new HashMap<>();

            nummap.put("userid", userid);


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
                stringBuffer.append("test").append("=").append(userid);

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

    private void showResult() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);


            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                imgname = item.getString(TAG_IMAGE);
                name = item.getString(TAG_NAME);

                nametext.setText(name);
                idtext.setText(myid);

                if(imgname.contains(".jpg")) {
                    Picasso.with(Myprofile.this)
                            .load("http://whgksthf702.vps.phps.kr/uploads/"+imgname)
                            .into(profileimage);
                }
                else {
                    profileimage.setImageResource(R.drawable.normalprofile);
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
                    profileimage.setImageURI(ivuri);
                    storeCropImage(photo, filePath + filename);
                    absoultePath = filePath+filename;

                    fileurl = ivuri.toString();

                    Log.d("프로필사진url값",fileurl);
                    Log.d("프로필사진url값",absoultePath);

                }

                // 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) {
                    f.delete();
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

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
            case CROP_FROM_FACECAMERA: {
                String faceuri = data.getStringExtra("result");

                String[] filenamelist=faceuri.split("/");
                filename = filenamelist[5];
                absoultePath = faceuri;

                Log.d("프로필액티비티얼굴사진경로",filename);
                Uri myUri = Uri.parse(faceuri);
                Log.d("프로필액티비티얼굴사진경로",myUri.toString());
                profileimage.setImageURI(myUri);

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

    public int uploadFile(String sourceFileUri) {


        final String fileName = sourceFileUri;


        HttpURLConnection conn = null;

        DataOutputStream dos = null;

        String lineEnd = "\r\n";

        String twoHyphens = "--";

        String boundary = "*****";

        int bytesRead, bytesAvailable, bufferSize;

        byte[] buffer;

        int maxBufferSize = 1 * 1024 * 1024;

        File sourceFile = new File(sourceFileUri);


        if (!sourceFile.isFile()) {



            runOnUiThread(new Runnable() {

                public void run() {


                }

            });


            return 0;


        } else

        {

            try {


                // open a URL connection to the Servlet

                FileInputStream fileInputStream = new FileInputStream(sourceFile);

                URL url = new URL(upLoadServerUri);


                // Open a HTTP  connection to  the URL

                conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true); // Allow Inputs

                conn.setDoOutput(true); // Allow Outputs

                conn.setUseCaches(false); // Don't use a Cached Copy

                conn.setRequestMethod("POST");

                conn.setRequestProperty("Connection", "Keep-Alive");

                conn.setRequestProperty("ENCTYPE", "multipart/form-data");

                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                conn.setRequestProperty("uploaded_file", fileName);


                dos = new DataOutputStream(conn.getOutputStream());


                dos.writeBytes(twoHyphens + boundary + lineEnd);

                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""

                        + fileName + "\"" + lineEnd);


                dos.writeBytes(lineEnd);


                // create a buffer of  maximum size

                bytesAvailable = fileInputStream.available();


                bufferSize = Math.min(bytesAvailable, maxBufferSize);

                buffer = new byte[bufferSize];


                // read file and write it into form...

                bytesRead = fileInputStream.read(buffer, 0, bufferSize);


                while (bytesRead > 0) {


                    dos.write(buffer, 0, bufferSize);

                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);

                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);


                }


                // send multipart form data necesssary after file data...

                dos.writeBytes(lineEnd);

                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);


                // Responses from the server (code and message)

                serverResponseCode = conn.getResponseCode();

                String serverResponseMessage = conn.getResponseMessage();


                Log.i("uploadFile", "HTTP Response is : "

                        + serverResponseMessage + ": " + serverResponseCode);


                if (serverResponseCode == 200) {


                    runOnUiThread(new Runnable() {

                        public void run() {


                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"

                                    + fileName;


                            Toast.makeText(Myprofile.this, "File Upload Complete.",

                                    Toast.LENGTH_SHORT).show();

                        }

                    });

                }


                //close the streams //

                fileInputStream.close();

                dos.flush();

                dos.close();


            } catch (MalformedURLException ex) {

                ex.printStackTrace();


                runOnUiThread(new Runnable() {

                    public void run() {


                        Toast.makeText(Myprofile.this, "MalformedURLException",

                                Toast.LENGTH_SHORT).show();

                    }

                });


                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);

            } catch (Exception e) {

                e.printStackTrace();


                runOnUiThread(new Runnable() {

                    public void run() {


                        Toast.makeText(Myprofile.this, "Got Exception : see logcat ",

                                Toast.LENGTH_SHORT).show();

                    }

                });

                Log.e("Upload server Exception", "Exception : "

                        + e.getMessage(), e);

            }

            return serverResponseCode;


        } // End else block

    }


}
