package com.example.samsung.picnic12;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

//import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by samsung on 2018-04-22.
 * 미세먼지 정보 프레그먼트
 * 공공데이터 포털에서 각 정보를 받아아고 사용자는 원하는 지역 선택해서 확인
 */

public class FirstFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener
{
    Button getBtn,getNearStation;
    static EditText where;
    static Spinner sido,station;	//스피너
    static String sidolist[]={"서울","부산","대전","대구","광주","울산","경기","강원","충북","충남","경북","경남","전북","전남","제주"};
    static String stationlist[];	//측정소목록(이건 api로 가져올꺼라 몇개인지 모른다)
    static ArrayAdapter<String> spinnerSido,spinnerStation;	//spinner에 붙일 array adapter
    static TextView date,so2value,covalue,o3value,no2value,pm10value,khaivalue,so2grade,cograde,o3grade,no2grade,pm10grade,khaigrade,pm25value,pm25grade;
    static TextView so2name,coname,o3name,no2name,pm10name,khainame,pm25name;
    static TextView seoulgrade,jejugrade,jnamgrade,jbukgrade,gwangjugrade,gyeongnamgrade,gyeongbuggrade,ulsangrade,daegugrade,busangrade,chungnamgrade,chungbuggrade,sejonggrade,daejeongrade,yeongdonggrade,yeongseograde,gyeong_gibugbugrade,gyeong_ginambugrade,incheongrade;
    static TextView seoulname,jejuname,jnamname,jbukname,gwangjuname,gyeongnamname,gyeongbugname,ulsanname,daeguname,busanname,chungnamname,chungbugname,sejongname,daejeonname,yeongdongname,yeongseoname,gyeong_gibugbuname,gyeong_ginambuname,incheonname;
    static ImageView seoulimage,jejuimage,jnamimage,jbukimage,gwangjuimage,gyeongnamimage,gyeongbugimage,ulsanimage,daeguimage,busanimage,chungnamimage,chungbugimage,sejongimage,daejeonimage,yeongdongimage,yeongseoimage,gyeong_gibugbuimage,gyeong_ginambuimage,incheonimage;
    static TextView seoulgrade2,jejugrade2,jnamgrade2,jbukgrade2,gwangjugrade2,gyeongnamgrade2,gyeongbuggrade2,ulsangrade2,daegugrade2,busangrade2,chungnamgrade2,chungbuggrade2,sejonggrade2,daejeongrade2,yeongdonggrade2,yeongseograde2,gyeong_gibugbugrade2,gyeong_ginambugrade2,incheongrade2;
    static TextView seoulname2,jejuname2,jnamname2,jbukname2,gwangjuname2,gyeongnamname2,gyeongbugname2,ulsanname2,daeguname2,busanname2,chungnamname2,chungbugname2,sejongname2,daejeonname2,yeongdongname2,yeongseoname2,gyeong_gibugbuname2,gyeong_ginambuname2,incheonname2;
    static ImageView seoulimage2,jejuimage2,jnamimage2,jbukimage2,gwangjuimage2,gyeongnamimage2,gyeongbugimage2,ulsanimage2,daeguimage2,busanimage2,chungnamimage2,chungbugimage2,sejongimage2,daejeonimage2,yeongdongimage2,yeongseoimage2,gyeong_gibugbuimage2,gyeong_ginambuimage2,incheonimage2;
    static ImageView so2image,coimage,o3image,no2image,pm10image,khaiimage,pm25image;
    static int stationCnt=0;
    static String seoulstate,jejustate,jnamstate,jbukstate,gwangjustate,gyeongnamstate,gyeongbugstate,ulsanstate,daegustate,busanstate,chungnamstate,chungbugstate,sejongstate,daejeonstate,yeongdongstate,yeongseostate,gyeong_gibugbustate,gyeong_ginambustate,incheonstate;
    static String seoulstate2,jejustate2,jnamstate2,jbukstate2,gwangjustate2,gyeongnamstate2,gyeongbugstate2,ulsanstate2,daegustate2,busanstate2,chungnamstate2,chungbugstate2,sejongstate2,daejeonstate2,yeongdongstate2,yeongseostate2,gyeong_gibugbustate2,gyeong_ginambustate2,incheonstate2;
    static Context mContext;	//static에서 context를 쓰기위해

    public FirstFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ScrollView layout = (ScrollView) inflater.inflate(R.layout.fragment_first, container, false);

        mContext=getActivity();	//static에서 context를 쓰기위해~
      //  totalcnt=(TextView)layout.findViewById(R.id.totalcnt);
        date=(TextView)layout.findViewById(R.id.date);
        so2value=(TextView)layout.findViewById(R.id.so2value);
        covalue=(TextView)layout.findViewById(R.id.covalue);
        o3value=(TextView)layout.findViewById(R.id.o3value);
        no2value=(TextView)layout.findViewById(R.id.no2value);
        pm10value=(TextView)layout.findViewById(R.id.pm10value);
        khaivalue=(TextView)layout.findViewById(R.id.khaivalue);
        so2grade=(TextView)layout.findViewById(R.id.so2grade);
        cograde=(TextView)layout.findViewById(R.id.cograde);
        o3grade=(TextView)layout.findViewById(R.id.o3grade);
        no2grade=(TextView)layout.findViewById(R.id.no2grade);
        pm10grade=(TextView)layout.findViewById(R.id.pm10grade);
        khaigrade=(TextView)layout.findViewById(R.id.khaigrade);
        pm25grade=(TextView)layout.findViewById(R.id.pm25grade);
        pm25value=(TextView)layout.findViewById(R.id.pm25value);


        so2name = (TextView)layout.findViewById(R.id.so2name);
        coname = (TextView)layout.findViewById(R.id.coname);
        no2name = (TextView)layout.findViewById(R.id.no2name);
        pm10name = (TextView)layout.findViewById(R.id.pm10name);
        khainame = (TextView)layout.findViewById(R.id.khainame);
        o3name = (TextView)layout.findViewById(R.id.o3name);
        pm25name = (TextView)layout.findViewById(R.id.pm25name) ;


        so2image = (ImageView)layout.findViewById(R.id.so2image);
        coimage = (ImageView)layout.findViewById(R.id.coimage);
        no2image = (ImageView)layout.findViewById(R.id.no2image);
        pm10image = (ImageView)layout.findViewById(R.id.pm10image);
        khaiimage = (ImageView)layout.findViewById(R.id.khaiimage);
        o3image = (ImageView)layout.findViewById(R.id.o3image);
        pm25image = (ImageView)layout.findViewById(R.id.pm25image);

        seoulimage = (ImageView)layout.findViewById(R.id.seoulimage);
        jejuimage = (ImageView)layout.findViewById(R.id.jejuimage);
        jnamimage = (ImageView)layout.findViewById(R.id.jnamimage);
        jbukimage = (ImageView)layout.findViewById(R.id.jbukimage);
        gwangjuimage = (ImageView)layout.findViewById(R.id.gwangjuimage);
        gyeongnamimage = (ImageView)layout.findViewById(R.id. gyeongnamimage);
        gyeongbugimage = (ImageView)layout.findViewById(R.id.gyeongbukimage);
       ulsanimage = (ImageView)layout.findViewById(R.id.ulsanimage);
        daeguimage= (ImageView)layout.findViewById(R.id.daeguimage);
        busanimage = (ImageView)layout.findViewById(R.id.busanimage);
       chungnamimage = (ImageView)layout.findViewById(R.id.chungnamimage);
        chungbugimage = (ImageView)layout.findViewById(R.id.chungbukimage);
       sejongimage = (ImageView)layout.findViewById(R.id.sejongimage);
        daejeonimage= (ImageView)layout.findViewById(R.id.daejeonimage);
        yeongdongimage = (ImageView)layout.findViewById(R.id.yeongdongimage);
        yeongseoimage = (ImageView)layout.findViewById(R.id.yeongseoimage);
        gyeong_ginambuimage = (ImageView)layout.findViewById(R.id.gyeong_ginambuimage);
        gyeong_gibugbuimage = (ImageView)layout.findViewById(R.id.gyeong_gibugbuimage);
        incheonimage = (ImageView)layout.findViewById(R.id.incheonimage);

        seoulimage2 = (ImageView)layout.findViewById(R.id.seoulimage2);
        jejuimage2 = (ImageView)layout.findViewById(R.id.jejuimage2);
        jnamimage2 = (ImageView)layout.findViewById(R.id.jnamimage2);
        jbukimage2 = (ImageView)layout.findViewById(R.id.jbukimage2);
        gwangjuimage2 = (ImageView)layout.findViewById(R.id.gwangjuimage2);
        gyeongnamimage2 = (ImageView)layout.findViewById(R.id. gyeongnamimage2);
        gyeongbugimage2 = (ImageView)layout.findViewById(R.id.gyeongbukimage2);
        ulsanimage2 = (ImageView)layout.findViewById(R.id.ulsanimage2);
        daeguimage2= (ImageView)layout.findViewById(R.id.daeguimage2);
        busanimage2 = (ImageView)layout.findViewById(R.id.busanimage2);
        chungnamimage2 = (ImageView)layout.findViewById(R.id.chungnamimage2);
        chungbugimage2 = (ImageView)layout.findViewById(R.id.chungbukimage2);
        sejongimage2 = (ImageView)layout.findViewById(R.id.sejongimage2);
        daejeonimage2= (ImageView)layout.findViewById(R.id.daejeonimage2);
        yeongdongimage2 = (ImageView)layout.findViewById(R.id.yeongdongimage2);
        yeongseoimage2 = (ImageView)layout.findViewById(R.id.yeongseoimage2);
        gyeong_ginambuimage2 = (ImageView)layout.findViewById(R.id.gyeong_ginambuimage2);
        gyeong_gibugbuimage2 = (ImageView)layout.findViewById(R.id.gyeong_gibugbuimage2);
        incheonimage2 = (ImageView)layout.findViewById(R.id.incheonimage2);

        seoulname = (TextView)layout.findViewById(R.id.seoulname);
        jejuname = (TextView)layout.findViewById(R.id.jejuname);
        jnamname = (TextView)layout.findViewById(R.id.jnamname);
        jbukname = (TextView)layout.findViewById(R.id.jbukname);
        gwangjuname = (TextView)layout.findViewById(R.id.gwangjuname);
        gyeongnamname = (TextView)layout.findViewById(R.id. gyeongnamname);
        gyeongbugname = (TextView)layout.findViewById(R.id.gyeongbukname);
        ulsanname = (TextView)layout.findViewById(R.id.ulsanname);
        daeguname= (TextView)layout.findViewById(R.id.daeguname);
        busanname = (TextView)layout.findViewById(R.id.busanname);
        chungnamname = (TextView)layout.findViewById(R.id.chungnamname);
        chungbugname = (TextView)layout.findViewById(R.id.chungbukname);
        sejongname = (TextView)layout.findViewById(R.id.sejongname);
        daejeonname= (TextView)layout.findViewById(R.id.daejeonname);
        yeongdongname = (TextView)layout.findViewById(R.id.yeongdongname);
        yeongseoname = (TextView)layout.findViewById(R.id.yeongseoname);
        gyeong_ginambuname = (TextView)layout.findViewById(R.id.gyeong_ginambuname);
        gyeong_gibugbuname = (TextView)layout.findViewById(R.id.gyeong_gibugbuname);
        incheonname = (TextView)layout.findViewById(R.id.incheonname);

        seoulgrade = (TextView)layout.findViewById(R.id.seoulgrade);
        jejugrade = (TextView)layout.findViewById(R.id.jejugrade);
        jnamgrade = (TextView)layout.findViewById(R.id.jnamgrade);
        jbukgrade = (TextView)layout.findViewById(R.id.jbukgrade);
        gwangjugrade = (TextView)layout.findViewById(R.id.gwangjugrade);
        gyeongnamgrade = (TextView)layout.findViewById(R.id. gyeongnamgrade);
        gyeongbuggrade = (TextView)layout.findViewById(R.id.gyeongbukgrade);
        ulsangrade = (TextView)layout.findViewById(R.id.ulsangrade);
        daegugrade= (TextView)layout.findViewById(R.id.daegugrade);
        busangrade = (TextView)layout.findViewById(R.id.busangrade);
        chungnamgrade = (TextView)layout.findViewById(R.id.chungnamgrade);
        chungbuggrade = (TextView)layout.findViewById(R.id.chungbukgrade);
        sejonggrade = (TextView)layout.findViewById(R.id.sejonggrade);
        daejeongrade= (TextView)layout.findViewById(R.id.daejeongrade);
        yeongdonggrade = (TextView)layout.findViewById(R.id.yeongdonggrade);
        yeongseograde = (TextView)layout.findViewById(R.id.yeongseograde);
        gyeong_ginambugrade = (TextView)layout.findViewById(R.id.gyeong_ginambugrade);
        gyeong_gibugbugrade = (TextView)layout.findViewById(R.id.gyeong_gibugbugrade);
        incheongrade = (TextView)layout.findViewById(R.id.incheongrade);

        seoulname2 = (TextView)layout.findViewById(R.id.seoulname2);
        jejuname2 = (TextView)layout.findViewById(R.id.jejuname2);
        jnamname2 = (TextView)layout.findViewById(R.id.jnamname2);
        jbukname2 = (TextView)layout.findViewById(R.id.jbukname2);
        gwangjuname2 = (TextView)layout.findViewById(R.id.gwangjuname2);
        gyeongnamname2 = (TextView)layout.findViewById(R.id. gyeongnamname2);
        gyeongbugname2 = (TextView)layout.findViewById(R.id.gyeongbukname2);
        ulsanname2 = (TextView)layout.findViewById(R.id.ulsanname2);
        daeguname2= (TextView)layout.findViewById(R.id.daeguname2);
        busanname2 = (TextView)layout.findViewById(R.id.busanname2);
        chungnamname2 = (TextView)layout.findViewById(R.id.chungnamname2);
        chungbugname2 = (TextView)layout.findViewById(R.id.chungbukname2);
        sejongname2 = (TextView)layout.findViewById(R.id.sejongname2);
        daejeonname2= (TextView)layout.findViewById(R.id.daejeonname2);
        yeongdongname2 = (TextView)layout.findViewById(R.id.yeongdongname2);
        yeongseoname2 = (TextView)layout.findViewById(R.id.yeongseoname2);
        gyeong_ginambuname2 = (TextView)layout.findViewById(R.id.gyeong_ginambuname2);
        gyeong_gibugbuname2 = (TextView)layout.findViewById(R.id.gyeong_gibugbuname2);
        incheonname2 = (TextView)layout.findViewById(R.id.incheonname2);

        seoulgrade2 = (TextView)layout.findViewById(R.id.seoulgrade2);
        jejugrade2 = (TextView)layout.findViewById(R.id.jejugrade2);
        jnamgrade2 = (TextView)layout.findViewById(R.id.jnamgrade2);
        jbukgrade2 = (TextView)layout.findViewById(R.id.jbukgrade2);
        gwangjugrade2 = (TextView)layout.findViewById(R.id.gwangjugrade2);
        gyeongnamgrade2 = (TextView)layout.findViewById(R.id. gyeongnamgrade2);
        gyeongbuggrade2 = (TextView)layout.findViewById(R.id.gyeongbukgrade2);
        ulsangrade2 = (TextView)layout.findViewById(R.id.ulsangrade2);
        daegugrade2= (TextView)layout.findViewById(R.id.daegugrade2);
        busangrade2 = (TextView)layout.findViewById(R.id.busangrade2);
        chungnamgrade2 = (TextView)layout.findViewById(R.id.chungnamgrade2);
        chungbuggrade2 = (TextView)layout.findViewById(R.id.chungbukgrade2);
        sejonggrade2 = (TextView)layout.findViewById(R.id.sejonggrade2);
        daejeongrade2= (TextView)layout.findViewById(R.id.daejeongrade2);
        yeongdonggrade2 = (TextView)layout.findViewById(R.id.yeongdonggrade2);
        yeongseograde2 = (TextView)layout.findViewById(R.id.yeongseograde2);
        gyeong_ginambugrade2 = (TextView)layout.findViewById(R.id.gyeong_ginambugrade2);
        gyeong_gibugbugrade2 = (TextView)layout.findViewById(R.id.gyeong_gibugbugrade2);
        incheongrade2 = (TextView)layout.findViewById(R.id.incheongrade2);


        where=(EditText)layout.findViewById(R.id.where);

        getBtn=(Button) layout.findViewById(R.id.getBtn);		//대기정보버튼 객체생성

        sido=(Spinner)layout.findViewById(R.id.sido);	//시도 스피너
        station=(Spinner)layout.findViewById(R.id.station);	//측정소 스피너
        sido.setOnItemSelectedListener(this);	//스피너 선택할때 작동시킬 리스너등록
        station.setOnItemSelectedListener(this);


        getBtn.setOnClickListener(this);	//대기정보 가져오는 버튼 리스너
        spinnerSido=new ArrayAdapter<>(getActivity(), R.layout.spinner_text,sidolist);	//array adapter에 시도 리스트를 넣어줌
        sido.setAdapter(spinnerSido);	//스피너에 adapter를 연결


        getTomorrowDust();

        return layout;


    }
    // 기상청에서 내일 정보 받아서 뿌려주는 스레드
    public static void getTomorrowDust(){
        GetTomorrowDustThread.active=true;
        GetTomorrowDustThread getweatherthread=new GetTomorrowDustThread(false);		//스레드생성(UI 스레드사용시 system 뻗는다)
        getweatherthread.start();	//스레드 시작
    }

    public static void FindTomorrowDust(String getCnt,String[] sDate,String[] sinformData,String[] sinformGrade){	//대기정보 가져온 결과값
        stationCnt=0;	//측정개수정보(여기선 1개만 가져온다
        stationCnt=Integer.parseInt(getCnt);
        if(stationCnt==0) {	//만약 측정정보가 없다면
            seoulgrade.setText("");
        }else{	//측정정보있으면

            String test[] = sinformGrade[1].split(","); // 1일뒤, 2일뒤는[2]하면됨
            String test2[] = sinformGrade[2].split(","); // 1일뒤, 2일뒤는[2]하면됨

            int idx = test[0].indexOf(":");
            int idx2 = test2[0].indexOf(":");

            seoulstate = test[0].substring(idx+1); //:뒤의내용
            jejustate = test[0].substring(idx+1);
            jnamstate = test[0].substring(idx+1);
            jbukstate = test[0].substring(idx+1);
            gwangjustate = test[0].substring(idx+1);
            gyeongnamstate = test[0].substring(idx+1);
            gyeongbugstate = test[0].substring(idx+1);
            ulsanstate = test[0].substring(idx+1);
            daegustate= test[0].substring(idx+1);
            busanstate = test[0].substring(idx+1);
            chungnamstate = test[0].substring(idx+1);
            chungbugstate = test[0].substring(idx+1);
            sejongstate = test[0].substring(idx+1);
            daejeonstate= test[0].substring(idx+1);
            yeongdongstate = test[0].substring(idx+1);
            yeongseostate = test[0].substring(idx+1);
            gyeong_ginambustate = test[0].substring(idx+1);
            gyeong_gibugbustate = test[0].substring(idx+1);
            incheonstate = test[0].substring(idx+1);

            seoulstate2 = test2[0].substring(idx2+1); //:뒤의내용
            jejustate2 = test2[0].substring(idx2+1);
            jnamstate2 = test2[0].substring(idx2+1);
            jbukstate2 = test2[0].substring(idx2+1);
            gwangjustate2 = test2[0].substring(idx2+1);
            gyeongnamstate2 = test2[0].substring(idx2+1);
            gyeongbugstate2 = test2[0].substring(idx2+1);
            ulsanstate2 = test2[0].substring(idx2+1);
            daegustate2= test2[0].substring(idx2+1);
            busanstate2 = test2[0].substring(idx2+1);
            chungnamstate2 = test2[0].substring(idx2+1);
            chungbugstate2 = test2[0].substring(idx2+1);
            sejongstate2 = test2[0].substring(idx2+1);
            daejeonstate2= test2[0].substring(idx2+1);
            yeongdongstate2 = test2[0].substring(idx2+1);
            yeongseostate2 = test2[0].substring(idx2+1);
            gyeong_ginambustate2 = test2[0].substring(idx2+1);
            gyeong_gibugbustate2 = test2[0].substring(idx2+1);
            incheonstate2 = test2[0].substring(idx2+1);


            seoulgrade.setText(seoulstate); //서울 0 제주 1 .....
            jejugrade.setText(jejustate);
            jnamgrade.setText(jnamstate);
            jbukgrade.setText(jbukstate);
            gwangjugrade.setText(gwangjustate);
            gyeongnamgrade.setText(gyeongnamstate);
            gyeongbuggrade.setText(gyeongbugstate);
            ulsangrade.setText(ulsanstate);
            daegugrade.setText(daegustate);
            busangrade.setText(busanstate);
            chungnamgrade.setText(chungnamstate);
            chungbuggrade.setText(chungbugstate);
            sejonggrade.setText(sejongstate);
            daejeongrade.setText(daejeonstate);
            yeongdonggrade.setText(yeongdongstate);
            yeongseograde.setText(yeongseostate);
            gyeong_ginambugrade.setText(gyeong_ginambustate);
            gyeong_gibugbugrade.setText(gyeong_gibugbustate);
            incheongrade.setText(incheonstate);

            seoulgrade2.setText(seoulstate2); //서울 0 제주 1 .....
            jejugrade2.setText(jejustate2);
            jnamgrade2.setText(jnamstate2);
            jbukgrade2.setText(jbukstate2);
            gwangjugrade2.setText(gwangjustate2);
            gyeongnamgrade2.setText(gyeongnamstate2);
            gyeongbuggrade2.setText(gyeongbugstate2);
            ulsangrade2.setText(ulsanstate2);
            daegugrade2.setText(daegustate2);
            busangrade2.setText(busanstate2);
            chungnamgrade2.setText(chungnamstate2);
            chungbuggrade2.setText(chungbugstate2);
            sejonggrade2.setText(sejongstate2);
            daejeongrade2.setText(daejeonstate2);
            yeongdonggrade2.setText(yeongdongstate2);
            yeongseograde2.setText(yeongseostate2);
            gyeong_ginambugrade2.setText(gyeong_ginambustate2);
            gyeong_gibugbugrade2.setText(gyeong_gibugbustate2);
            incheongrade2.setText(incheonstate2);

            Log.d("여기까지오나??",seoulstate);
            switch (seoulstate){
                case " 좋음":{
                    seoulimage.setImageResource(R.drawable.good);
                    seoulgrade.setBackgroundResource(R.drawable.blue);
                    seoulname.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    seoulimage.setImageResource(R.drawable.normal);
                    seoulgrade.setBackgroundResource(R.drawable.green);
                    seoulname.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    seoulimage.setImageResource(R.drawable.bad);
                    seoulgrade.setBackgroundResource(R.drawable.orange);
                    seoulname.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    seoulimage.setImageResource(R.drawable.badx2);
                    seoulgrade.setBackgroundResource(R.drawable.red);
                    seoulname.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    seoulimage.setImageResource(R.drawable.zero);
                    seoulname.setBackgroundResource(R.drawable.white);
                    seoulname.setBackgroundResource(R.drawable.white);
                    break;
                }
            }

            switch (jejustate){
                case " 좋음":{
                    jejuimage.setImageResource(R.drawable.good);
                    jejugrade.setBackgroundResource(R.drawable.blue);
                    jejuname.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    jejuimage.setImageResource(R.drawable.normal);
                    jejugrade.setBackgroundResource(R.drawable.green);
                    jejuname.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    jejuimage.setImageResource(R.drawable.bad);
                    jejugrade.setBackgroundResource(R.drawable.orange);
                    jejuname.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    jejuimage.setImageResource(R.drawable.badx2);
                    jejugrade.setBackgroundResource(R.drawable.red);
                    jejuname.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    jejuimage.setImageResource(R.drawable.zero);
                    jejugrade.setBackgroundResource(R.drawable.white);
                    jejuname.setBackgroundResource(R.drawable.white);
                    break;
                }
            }

            switch (jnamstate){
                case " 좋음":{
                    jnamimage.setImageResource(R.drawable.good);
                    jnamgrade.setBackgroundResource(R.drawable.blue);
                    jnamname.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    jnamimage.setImageResource(R.drawable.normal);
                    jnamgrade.setBackgroundResource(R.drawable.green);
                    jnamname.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    jnamimage.setImageResource(R.drawable.bad);
                    jnamgrade.setBackgroundResource(R.drawable.orange);
                    jnamname.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    jnamimage.setImageResource(R.drawable.badx2);
                    jnamgrade.setBackgroundResource(R.drawable.red);
                    jnamname.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    jnamimage.setImageResource(R.drawable.zero);
                    jnamgrade.setBackgroundResource(R.drawable.white);
                    jnamname.setBackgroundResource(R.drawable.white);
                    break;
                }
            }

            switch (jbukstate){
                case " 좋음":{
                    jbukimage.setImageResource(R.drawable.good);
                    jbukgrade.setBackgroundResource(R.drawable.blue);
                    jbukname.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    jbukimage.setImageResource(R.drawable.normal);
                    jbukgrade.setBackgroundResource(R.drawable.green);
                    jbukname.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    jbukimage.setImageResource(R.drawable.bad);
                    jbukgrade.setBackgroundResource(R.drawable.orange);
                    jbukname.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    jbukimage.setImageResource(R.drawable.badx2);
                    jbukgrade.setBackgroundResource(R.drawable.red);
                    jbukname.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    jbukimage.setImageResource(R.drawable.zero);
                    jbukgrade.setBackgroundResource(R.drawable.white);
                    jbukname.setBackgroundResource(R.drawable.white);
                    break;
                }
            }

            switch (gwangjustate){
                case " 좋음":{
                    gwangjuimage.setImageResource(R.drawable.good);
                    gwangjugrade.setBackgroundResource(R.drawable.blue);
                    gwangjuname.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    gwangjuimage.setImageResource(R.drawable.normal);
                    gwangjugrade.setBackgroundResource(R.drawable.green);
                    gwangjuname.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    gwangjuimage.setImageResource(R.drawable.bad);
                    gwangjugrade.setBackgroundResource(R.drawable.orange);
                    gwangjuname.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    gwangjuimage.setImageResource(R.drawable.badx2);
                    gwangjugrade.setBackgroundResource(R.drawable.red);
                    gwangjuname.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    gwangjuimage.setImageResource(R.drawable.zero);
                    gwangjugrade.setBackgroundResource(R.drawable.white);
                    gwangjuname.setBackgroundResource(R.drawable.white);
                    break;
                }
            }

            switch (gyeongnamstate){
                case " 좋음":{
                    gyeongnamimage.setImageResource(R.drawable.good);
                    gyeongnamgrade.setBackgroundResource(R.drawable.blue);
                    gyeongnamname.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    gyeongnamimage.setImageResource(R.drawable.normal);
                    gyeongnamgrade.setBackgroundResource(R.drawable.green);
                    gyeongnamname.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    gyeongnamimage.setImageResource(R.drawable.bad);
                    gyeongnamgrade.setBackgroundResource(R.drawable.orange);
                    gyeongnamname.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    gyeongnamimage.setImageResource(R.drawable.badx2);
                    gyeongnamgrade.setBackgroundResource(R.drawable.red);
                    gyeongnamname.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    gyeongnamimage.setImageResource(R.drawable.zero);
                    gyeongnamgrade.setBackgroundResource(R.drawable.white);
                    gyeongnamname.setBackgroundResource(R.drawable.white);
                    break;
                }
            }

            switch (gyeongbugstate){
                case " 좋음":{
                    gyeongbugimage.setImageResource(R.drawable.good);
                    gyeongbuggrade.setBackgroundResource(R.drawable.blue);
                    gyeongbugname.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    gyeongbugimage.setImageResource(R.drawable.normal);
                    gyeongbuggrade.setBackgroundResource(R.drawable.green);
                    gyeongbugname.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    gyeongbugimage.setImageResource(R.drawable.bad);
                    gyeongbuggrade.setBackgroundResource(R.drawable.orange);
                    gyeongbugname.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    gyeongbugimage.setImageResource(R.drawable.badx2);
                    gyeongbuggrade.setBackgroundResource(R.drawable.red);
                    gyeongbugname.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    gyeongbugimage.setImageResource(R.drawable.zero);
                    gyeongbuggrade.setBackgroundResource(R.drawable.white);
                    gyeongbugname.setBackgroundResource(R.drawable.white);
                    break;
                }
            }

            switch (ulsanstate){
                case " 좋음":{
                    ulsanimage.setImageResource(R.drawable.good);
                    ulsangrade.setBackgroundResource(R.drawable.blue);
                    ulsanname.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    ulsanimage.setImageResource(R.drawable.normal);
                    ulsangrade.setBackgroundResource(R.drawable.green);
                    ulsanname.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    ulsanimage.setImageResource(R.drawable.bad);
                    ulsangrade.setBackgroundResource(R.drawable.orange);
                    ulsanname.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    ulsanimage.setImageResource(R.drawable.badx2);
                    ulsangrade.setBackgroundResource(R.drawable.red);
                    ulsanname.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    ulsanimage.setImageResource(R.drawable.zero);
                    ulsangrade.setBackgroundResource(R.drawable.white);
                    ulsanname.setBackgroundResource(R.drawable.white);
                    break;
                }
            }

            switch (daegustate){
                case " 좋음":{
                    daeguimage.setImageResource(R.drawable.good);
                    daegugrade.setBackgroundResource(R.drawable.blue);
                    daeguname.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    daeguimage.setImageResource(R.drawable.normal);
                    daegugrade.setBackgroundResource(R.drawable.green);
                    daeguname.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    daeguimage.setImageResource(R.drawable.bad);
                    daegugrade.setBackgroundResource(R.drawable.orange);
                    daeguname.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    daeguimage.setImageResource(R.drawable.badx2);
                    daegugrade.setBackgroundResource(R.drawable.red);
                    daeguname.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    daeguimage.setImageResource(R.drawable.zero);
                    daegugrade.setBackgroundResource(R.drawable.white);
                    daeguname.setBackgroundResource(R.drawable.white);
                    break;
                }
            }

            switch (busanstate){
                case " 좋음":{
                    busanimage.setImageResource(R.drawable.good);
                    busangrade.setBackgroundResource(R.drawable.blue);
                    busanname.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    busanimage.setImageResource(R.drawable.normal);
                    busangrade.setBackgroundResource(R.drawable.green);
                    busanname.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    busanimage.setImageResource(R.drawable.bad);
                    busangrade.setBackgroundResource(R.drawable.orange);
                    busanname.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    busanimage.setImageResource(R.drawable.badx2);
                    busangrade.setBackgroundResource(R.drawable.red);
                    busanname.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    busanimage.setImageResource(R.drawable.zero);
                    busangrade.setBackgroundResource(R.drawable.white);
                    busanname.setBackgroundResource(R.drawable.white);
                    break;
                }
            }

            switch (chungnamstate){
                case " 좋음":{
                    chungnamimage.setImageResource(R.drawable.good);
                    chungnamgrade.setBackgroundResource(R.drawable.blue);
                    chungnamname.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    chungnamimage.setImageResource(R.drawable.normal);
                    chungnamgrade.setBackgroundResource(R.drawable.green);
                    chungnamname.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    chungnamimage.setImageResource(R.drawable.bad);
                    chungnamgrade.setBackgroundResource(R.drawable.orange);
                    chungnamname.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    chungnamimage.setImageResource(R.drawable.badx2);
                    chungnamgrade.setBackgroundResource(R.drawable.red);
                    chungnamname.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    chungnamimage.setImageResource(R.drawable.zero);
                    chungnamgrade.setBackgroundResource(R.drawable.white);
                    chungnamname.setBackgroundResource(R.drawable.white);
                    break;
                }
            }

            switch (chungbugstate){
                case " 좋음":{
                    chungbugimage.setImageResource(R.drawable.good);
                    chungbuggrade.setBackgroundResource(R.drawable.blue);
                    chungbugname.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    chungbugimage.setImageResource(R.drawable.normal);
                    chungbuggrade.setBackgroundResource(R.drawable.green);
                    chungbugname.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    chungbugimage.setImageResource(R.drawable.bad);
                    chungbuggrade.setBackgroundResource(R.drawable.orange);
                    chungbugname.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    chungbugimage.setImageResource(R.drawable.badx2);
                    chungbuggrade.setBackgroundResource(R.drawable.red);
                    chungbugname.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    chungbugimage.setImageResource(R.drawable.zero);
                    chungbuggrade.setBackgroundResource(R.drawable.white);
                    chungbugname.setBackgroundResource(R.drawable.white);
                    break;
                }
            }

            switch (sejongstate){
                case " 좋음":{
                    sejongimage.setImageResource(R.drawable.good);
                    sejonggrade.setBackgroundResource(R.drawable.blue);
                    sejongname.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    sejongimage.setImageResource(R.drawable.normal);
                    sejonggrade.setBackgroundResource(R.drawable.green);
                    sejongname.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    sejongimage.setImageResource(R.drawable.bad);
                    sejonggrade.setBackgroundResource(R.drawable.orange);
                    sejongname.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    sejongimage.setImageResource(R.drawable.badx2);
                    sejonggrade.setBackgroundResource(R.drawable.red);
                    sejongname.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    sejongimage.setImageResource(R.drawable.zero);
                    sejonggrade.setBackgroundResource(R.drawable.white);
                    sejongname.setBackgroundResource(R.drawable.white);
                    break;
                }
            }

            switch (daejeonstate){
                case " 좋음":{
                    daejeonimage.setImageResource(R.drawable.good);
                    daejeongrade.setBackgroundResource(R.drawable.blue);
                    daejeonname.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    daejeonimage.setImageResource(R.drawable.normal);
                    daejeongrade.setBackgroundResource(R.drawable.green);
                    daejeonname.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    daejeonimage.setImageResource(R.drawable.bad);
                    daejeongrade.setBackgroundResource(R.drawable.orange);
                    daejeonname.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    daejeonimage.setImageResource(R.drawable.badx2);
                    daejeongrade.setBackgroundResource(R.drawable.red);
                    daejeonname.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    daejeonimage.setImageResource(R.drawable.zero);
                    daejeongrade.setBackgroundResource(R.drawable.white);
                    daejeonname.setBackgroundResource(R.drawable.white);
                    break;
                }
            }

            switch (yeongdongstate){
                case " 좋음":{
                    yeongdongimage.setImageResource(R.drawable.good);
                    yeongdonggrade.setBackgroundResource(R.drawable.blue);
                    yeongdongname.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    yeongdongimage.setImageResource(R.drawable.normal);
                    yeongdonggrade.setBackgroundResource(R.drawable.green);
                    yeongdongname.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    yeongdongimage.setImageResource(R.drawable.bad);
                    yeongdonggrade.setBackgroundResource(R.drawable.orange);
                    yeongdongname.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    yeongdongimage.setImageResource(R.drawable.badx2);
                    yeongdonggrade.setBackgroundResource(R.drawable.red);
                    yeongdongname.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    yeongdongimage.setImageResource(R.drawable.zero);
                    yeongdonggrade.setBackgroundResource(R.drawable.white);
                    yeongdongname.setBackgroundResource(R.drawable.white);
                    break;
                }
            }

            switch (yeongseostate){
                case " 좋음":{
                    yeongseoimage.setImageResource(R.drawable.good);
                    yeongseograde.setBackgroundResource(R.drawable.blue);
                    yeongseoname.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    yeongseoimage.setImageResource(R.drawable.normal);
                    yeongseograde.setBackgroundResource(R.drawable.green);
                    yeongseoname.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    yeongseoimage.setImageResource(R.drawable.bad);
                    yeongseograde.setBackgroundResource(R.drawable.orange);
                    yeongseoname.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    yeongseoimage.setImageResource(R.drawable.badx2);
                    yeongseograde.setBackgroundResource(R.drawable.red);
                    yeongseoname.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    yeongseoimage.setImageResource(R.drawable.zero);
                    yeongseograde.setBackgroundResource(R.drawable.white);
                    yeongseoname.setBackgroundResource(R.drawable.white);
                    break;
                }
            }

            switch (gyeong_gibugbustate){
                case " 좋음":{
                    gyeong_gibugbuimage.setImageResource(R.drawable.good);
                    gyeong_gibugbugrade.setBackgroundResource(R.drawable.blue);
                    gyeong_gibugbuname.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    gyeong_gibugbuimage.setImageResource(R.drawable.normal);
                    gyeong_gibugbugrade.setBackgroundResource(R.drawable.green);
                    gyeong_gibugbuname.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    gyeong_gibugbuimage.setImageResource(R.drawable.bad);
                    gyeong_gibugbugrade.setBackgroundResource(R.drawable.orange);
                    gyeong_gibugbuname.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    gyeong_gibugbuimage.setImageResource(R.drawable.badx2);
                    gyeong_gibugbugrade.setBackgroundResource(R.drawable.red);
                    gyeong_gibugbuname.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    gyeong_gibugbuimage.setImageResource(R.drawable.zero);
                    gyeong_gibugbugrade.setBackgroundResource(R.drawable.white);
                    gyeong_gibugbuname.setBackgroundResource(R.drawable.white);
                    break;
                }
            }

            switch (gyeong_ginambustate){
                case " 좋음":{
                    gyeong_ginambuimage.setImageResource(R.drawable.good);
                    gyeong_ginambugrade.setBackgroundResource(R.drawable.blue);
                    gyeong_ginambuname.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    gyeong_ginambuimage.setImageResource(R.drawable.normal);
                    gyeong_ginambugrade.setBackgroundResource(R.drawable.green);
                    gyeong_ginambuname.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    gyeong_ginambuimage.setImageResource(R.drawable.bad);
                    gyeong_ginambugrade.setBackgroundResource(R.drawable.orange);
                    gyeong_ginambuname.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    gyeong_ginambuimage.setImageResource(R.drawable.badx2);
                    gyeong_ginambugrade.setBackgroundResource(R.drawable.red);
                    gyeong_ginambuname.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    gyeong_ginambuimage.setImageResource(R.drawable.zero);
                    gyeong_ginambugrade.setBackgroundResource(R.drawable.white);
                    gyeong_ginambuname.setBackgroundResource(R.drawable.white);
                    break;
                }
            }

            switch (incheonstate){
                case " 좋음":{
                    incheonimage.setImageResource(R.drawable.good);
                    incheongrade.setBackgroundResource(R.drawable.blue);
                    incheonname.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    incheonimage.setImageResource(R.drawable.normal);
                    incheongrade.setBackgroundResource(R.drawable.green);
                    incheonname.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    incheonimage.setImageResource(R.drawable.bad);
                    incheongrade.setBackgroundResource(R.drawable.orange);
                    incheonname.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    incheonimage.setImageResource(R.drawable.badx2);
                    incheongrade.setBackgroundResource(R.drawable.red);
                    incheonname.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    incheonimage.setImageResource(R.drawable.zero);
                    incheongrade.setBackgroundResource(R.drawable.white);
                    incheonname.setBackgroundResource(R.drawable.white);
                    break;
                }
            }
            switch (seoulstate2){
                case " 좋음":{
                    seoulimage2.setImageResource(R.drawable.good);
                    seoulgrade2.setBackgroundResource(R.drawable.blue);
                    seoulname2.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    seoulimage2.setImageResource(R.drawable.normal);
                    seoulgrade2.setBackgroundResource(R.drawable.green);
                    seoulname2.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    seoulimage2.setImageResource(R.drawable.bad);
                    seoulgrade2.setBackgroundResource(R.drawable.orange);
                    seoulname2.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    seoulimage2.setImageResource(R.drawable.badx2);
                    seoulgrade2.setBackgroundResource(R.drawable.red);
                    seoulname2.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    seoulimage2.setImageResource(R.drawable.zero);
                    seoulgrade2.setBackgroundResource(R.drawable.black);
                    seoulname2.setBackgroundResource(R.drawable.black);
                    break;
                }
            }

            switch (jejustate2){
                case " 좋음":{
                    jejuimage2.setImageResource(R.drawable.good);
                    jejugrade2.setBackgroundResource(R.drawable.blue);
                    jejuname2.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    jejuimage2.setImageResource(R.drawable.normal);
                    jejugrade2.setBackgroundResource(R.drawable.green);
                    jejuname2.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    jejuimage2.setImageResource(R.drawable.bad);
                    jejugrade2.setBackgroundResource(R.drawable.orange);
                    jejuname2.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    jejuimage2.setImageResource(R.drawable.badx2);
                    jejugrade2.setBackgroundResource(R.drawable.red);
                    jejuname2.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    jejuimage2.setImageResource(R.drawable.zero);
                    jejugrade2.setBackgroundResource(R.drawable.black);
                    jejuname2.setBackgroundResource(R.drawable.black);
                    break;
                }
            }

            switch (jnamstate2){
                case " 좋음":{
                    jnamimage2.setImageResource(R.drawable.good);
                    jnamgrade2.setBackgroundResource(R.drawable.blue);
                    jnamname2.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    jnamimage2.setImageResource(R.drawable.normal);
                    jnamgrade2.setBackgroundResource(R.drawable.green);
                    jnamname2.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    jnamimage2.setImageResource(R.drawable.bad);
                    jnamgrade2.setBackgroundResource(R.drawable.orange);
                    jnamname2.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    jnamimage2.setImageResource(R.drawable.badx2);
                    jnamgrade2.setBackgroundResource(R.drawable.red);
                    jnamname2.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    jnamimage2.setImageResource(R.drawable.zero);
                    jnamgrade2.setBackgroundResource(R.drawable.black);
                    jnamname2.setBackgroundResource(R.drawable.black);
                    break;
                }
            }

            switch (jbukstate2){
                case " 좋음":{
                    jbukimage2.setImageResource(R.drawable.good);
                    jbukgrade2.setBackgroundResource(R.drawable.blue);
                    jbukname2.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    jbukimage2.setImageResource(R.drawable.normal);
                    jbukgrade2.setBackgroundResource(R.drawable.green);
                    jbukname2.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    jbukimage2.setImageResource(R.drawable.bad);
                    jbukgrade2.setBackgroundResource(R.drawable.orange);
                    jbukname2.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    jbukimage2.setImageResource(R.drawable.badx2);
                    jbukgrade2.setBackgroundResource(R.drawable.red);
                    jbukname2.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    jbukimage2.setImageResource(R.drawable.zero);
                    jbukgrade2.setBackgroundResource(R.drawable.black);
                    jbukname2.setBackgroundResource(R.drawable.black);
                    break;
                }
            }

            switch (gwangjustate2){
                case " 좋음":{
                    gwangjuimage2.setImageResource(R.drawable.good);
                    gwangjugrade2.setBackgroundResource(R.drawable.blue);
                    gwangjuname2.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    gwangjuimage2.setImageResource(R.drawable.normal);
                    gwangjugrade2.setBackgroundResource(R.drawable.green);
                    gwangjuname2.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    gwangjuimage2.setImageResource(R.drawable.bad);
                    gwangjugrade2.setBackgroundResource(R.drawable.orange);
                    gwangjuname2.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    gwangjuimage2.setImageResource(R.drawable.badx2);
                    gwangjugrade2.setBackgroundResource(R.drawable.red);
                    gwangjuname2.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    gwangjuimage2.setImageResource(R.drawable.zero);
                    gwangjugrade2.setBackgroundResource(R.drawable.black);
                    gwangjuname2.setBackgroundResource(R.drawable.black);
                    break;
                }
            }

            switch (gyeongnamstate2){
                case " 좋음":{
                    gyeongnamimage2.setImageResource(R.drawable.good);
                    gyeongnamgrade2.setBackgroundResource(R.drawable.blue);
                    gyeongnamname2.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    gyeongnamimage2.setImageResource(R.drawable.normal);
                    gyeongnamgrade2.setBackgroundResource(R.drawable.green);
                    gyeongnamname2.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    gyeongnamimage2.setImageResource(R.drawable.bad);
                    gyeongnamgrade2.setBackgroundResource(R.drawable.orange);
                    gyeongnamname2.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    gyeongnamimage2.setImageResource(R.drawable.badx2);
                    gyeongnamgrade2.setBackgroundResource(R.drawable.red);
                    gyeongnamname2.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    gyeongnamimage2.setImageResource(R.drawable.zero);
                    gyeongnamgrade2.setBackgroundResource(R.drawable.black);
                    gyeongnamname2.setBackgroundResource(R.drawable.black);
                    break;
                }
            }

            switch (gyeongbugstate2){
                case " 좋음":{
                    gyeongbugimage2.setImageResource(R.drawable.good);
                    gyeongbuggrade2.setBackgroundResource(R.drawable.blue);
                    gyeongbugname2.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    gyeongbugimage2.setImageResource(R.drawable.normal);
                    gyeongbuggrade2.setBackgroundResource(R.drawable.green);
                    gyeongbugname2.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    gyeongbugimage2.setImageResource(R.drawable.bad);
                    gyeongbuggrade2.setBackgroundResource(R.drawable.orange);
                    gyeongbugname2.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    gyeongbugimage2.setImageResource(R.drawable.badx2);
                    gyeongbuggrade2.setBackgroundResource(R.drawable.red);
                    gyeongbugname2.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    gyeongbugimage2.setImageResource(R.drawable.zero);
                    gyeongbuggrade2.setBackgroundResource(R.drawable.black);
                    gyeongbugname2.setBackgroundResource(R.drawable.black);
                    break;
                }
            }

            switch (ulsanstate2){
                case " 좋음":{
                    ulsanimage2.setImageResource(R.drawable.good);
                    ulsangrade2.setBackgroundResource(R.drawable.blue);
                    ulsanname2.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    ulsanimage2.setImageResource(R.drawable.normal);
                    ulsangrade2.setBackgroundResource(R.drawable.green);
                    ulsanname2.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    ulsanimage2.setImageResource(R.drawable.bad);
                    ulsangrade2.setBackgroundResource(R.drawable.orange);
                    ulsanname2.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    ulsanimage2.setImageResource(R.drawable.badx2);
                    ulsangrade2.setBackgroundResource(R.drawable.red);
                    ulsanname2.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    ulsanimage2.setImageResource(R.drawable.zero);
                    ulsangrade2.setBackgroundResource(R.drawable.black);
                    ulsanname2.setBackgroundResource(R.drawable.black);
                    break;
                }
            }

            switch (daegustate2){
                case " 좋음":{
                    daeguimage2.setImageResource(R.drawable.good);
                    daegugrade2.setBackgroundResource(R.drawable.blue);
                    daeguname2.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    daeguimage2.setImageResource(R.drawable.normal);
                    daegugrade2.setBackgroundResource(R.drawable.green);
                    daeguname2.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    daeguimage2.setImageResource(R.drawable.bad);
                    daegugrade2.setBackgroundResource(R.drawable.orange);
                    daeguname2.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    daeguimage2.setImageResource(R.drawable.badx2);
                    daegugrade2.setBackgroundResource(R.drawable.red);
                    daeguname2.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    daeguimage2.setImageResource(R.drawable.zero);
                    daegugrade2.setBackgroundResource(R.drawable.black);
                    daeguname2.setBackgroundResource(R.drawable.black);
                    break;
                }
            }

            switch (busanstate2){
                case " 좋음":{
                    busanimage2.setImageResource(R.drawable.good);
                    busangrade2.setBackgroundResource(R.drawable.blue);
                    busanname2.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    busanimage2.setImageResource(R.drawable.normal);
                    busangrade2.setBackgroundResource(R.drawable.green);
                    busanname2.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    busanimage2.setImageResource(R.drawable.bad);
                    busangrade2.setBackgroundResource(R.drawable.orange);
                    busanname2.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    busanimage2.setImageResource(R.drawable.badx2);
                    busangrade2.setBackgroundResource(R.drawable.red);
                    busanname2.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    busanimage2.setImageResource(R.drawable.zero);
                    busangrade2.setBackgroundResource(R.drawable.black);
                    busanname2.setBackgroundResource(R.drawable.black);
                    break;
                }
            }

            switch (chungnamstate2){
                case " 좋음":{
                    chungnamimage2.setImageResource(R.drawable.good);
                    chungnamgrade2.setBackgroundResource(R.drawable.blue);
                    chungnamname2.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    chungnamimage2.setImageResource(R.drawable.normal);
                    chungnamgrade2.setBackgroundResource(R.drawable.green);
                    chungnamname2.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    chungnamimage2.setImageResource(R.drawable.bad);
                    chungnamgrade2.setBackgroundResource(R.drawable.orange);
                    chungnamname2.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    chungnamimage2.setImageResource(R.drawable.badx2);
                    chungnamgrade2.setBackgroundResource(R.drawable.red);
                    chungnamname2.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    chungnamimage2.setImageResource(R.drawable.zero);
                    chungnamgrade2.setBackgroundResource(R.drawable.black);
                    chungnamname2.setBackgroundResource(R.drawable.black);
                    break;
                }
            }

            switch (chungbugstate2){
                case " 좋음":{
                    chungbugimage2.setImageResource(R.drawable.good);
                    chungbuggrade2.setBackgroundResource(R.drawable.blue);
                    chungbugname2.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    chungbugimage2.setImageResource(R.drawable.normal);
                    chungbuggrade2.setBackgroundResource(R.drawable.green);
                    chungbugname2.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    chungbugimage2.setImageResource(R.drawable.bad);
                    chungbuggrade2.setBackgroundResource(R.drawable.orange);
                    chungbugname2.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    chungbugimage2.setImageResource(R.drawable.badx2);
                    chungbuggrade2.setBackgroundResource(R.drawable.red);
                    chungbugname2.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    chungbugimage2.setImageResource(R.drawable.zero);
                    chungbuggrade2.setBackgroundResource(R.drawable.black);
                    chungbugname2.setBackgroundResource(R.drawable.black);
                    break;
                }
            }

            switch (sejongstate2){
                case " 좋음":{
                    sejongimage2.setImageResource(R.drawable.good);
                    sejonggrade2.setBackgroundResource(R.drawable.blue);
                    sejongname2.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    sejongimage2.setImageResource(R.drawable.normal);
                    sejonggrade2.setBackgroundResource(R.drawable.green);
                    sejongname2.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    sejongimage2.setImageResource(R.drawable.bad);
                    sejonggrade2.setBackgroundResource(R.drawable.orange);
                    sejongname2.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    sejongimage2.setImageResource(R.drawable.badx2);
                    sejonggrade2.setBackgroundResource(R.drawable.red);
                    sejongname2.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    sejongimage2.setImageResource(R.drawable.zero);
                    sejonggrade2.setBackgroundResource(R.drawable.black);
                    sejongname2.setBackgroundResource(R.drawable.black);
                    break;
                }
            }

            switch (daejeonstate2){
                case " 좋음":{
                    daejeonimage2.setImageResource(R.drawable.good);
                    daejeongrade2.setBackgroundResource(R.drawable.blue);
                    daejeonname2.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    daejeonimage2.setImageResource(R.drawable.normal);
                    daejeongrade2.setBackgroundResource(R.drawable.green);
                    daejeonname2.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    daejeonimage2.setImageResource(R.drawable.bad);
                    daejeongrade2.setBackgroundResource(R.drawable.orange);
                    daejeonname2.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    daejeonimage2.setImageResource(R.drawable.badx2);
                    daejeongrade2.setBackgroundResource(R.drawable.red);
                    daejeonname2.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    daejeonimage2.setImageResource(R.drawable.zero);
                    daejeongrade2.setBackgroundResource(R.drawable.black);
                    daejeonname2.setBackgroundResource(R.drawable.black);
                    break;
                }
            }

            switch (yeongdongstate2){
                case " 좋음":{
                    yeongdongimage2.setImageResource(R.drawable.good);
                    yeongdonggrade2.setBackgroundResource(R.drawable.blue);
                    yeongdongname2.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    yeongdongimage2.setImageResource(R.drawable.normal);
                    yeongdonggrade2.setBackgroundResource(R.drawable.green);
                    yeongdongname2.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    yeongdongimage2.setImageResource(R.drawable.bad);
                    yeongdonggrade2.setBackgroundResource(R.drawable.orange);
                    yeongdongname2.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    yeongdongimage2.setImageResource(R.drawable.badx2);
                    yeongdonggrade2.setBackgroundResource(R.drawable.red);
                    yeongdongname2.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    yeongdongimage2.setImageResource(R.drawable.zero);
                    yeongdonggrade2.setBackgroundResource(R.drawable.black);
                    yeongdongname2.setBackgroundResource(R.drawable.black);
                    break;
                }
            }

            switch (yeongseostate2){
                case " 좋음":{
                    yeongseoimage2.setImageResource(R.drawable.good);
                    yeongseograde2.setBackgroundResource(R.drawable.blue);
                    yeongseoname2.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    yeongseoimage2.setImageResource(R.drawable.normal);
                    yeongseograde2.setBackgroundResource(R.drawable.green);
                    yeongseoname2.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    yeongseoimage2.setImageResource(R.drawable.bad);
                    yeongseograde2.setBackgroundResource(R.drawable.orange);
                    yeongseoname2.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    yeongseoimage2.setImageResource(R.drawable.badx2);
                    yeongseograde2.setBackgroundResource(R.drawable.red);
                    yeongseoname2.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    yeongseoimage2.setImageResource(R.drawable.zero);
                    yeongseograde2.setBackgroundResource(R.drawable.black);
                    yeongseoname2.setBackgroundResource(R.drawable.black);
                    break;
                }
            }

            switch (gyeong_gibugbustate2){
                case " 좋음":{
                    gyeong_gibugbuimage2.setImageResource(R.drawable.good);
                    gyeong_gibugbugrade2.setBackgroundResource(R.drawable.blue);
                    gyeong_gibugbuname2.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    gyeong_gibugbuimage2.setImageResource(R.drawable.normal);
                    gyeong_gibugbugrade2.setBackgroundResource(R.drawable.green);
                    gyeong_gibugbuname2.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    gyeong_gibugbuimage2.setImageResource(R.drawable.bad);
                    gyeong_gibugbugrade2.setBackgroundResource(R.drawable.orange);
                    gyeong_gibugbuname2.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    gyeong_gibugbuimage2.setImageResource(R.drawable.badx2);
                    gyeong_gibugbugrade2.setBackgroundResource(R.drawable.red);
                    gyeong_gibugbuname2.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    gyeong_gibugbuimage2.setImageResource(R.drawable.zero);
                    gyeong_gibugbugrade2.setBackgroundResource(R.drawable.black);
                    gyeong_gibugbuname2.setBackgroundResource(R.drawable.black);
                    break;
                }
            }

            switch (gyeong_ginambustate2){
                case " 좋음":{
                    gyeong_ginambuimage2.setImageResource(R.drawable.good);
                    gyeong_ginambugrade2.setBackgroundResource(R.drawable.blue);
                    gyeong_ginambuname2.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    gyeong_ginambuimage2.setImageResource(R.drawable.normal);
                    gyeong_ginambugrade2.setBackgroundResource(R.drawable.green);
                    gyeong_ginambuname2.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    gyeong_ginambuimage2.setImageResource(R.drawable.bad);
                    gyeong_ginambugrade2.setBackgroundResource(R.drawable.orange);
                    gyeong_ginambuname2.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    gyeong_ginambuimage2.setImageResource(R.drawable.badx2);
                    gyeong_ginambugrade2.setBackgroundResource(R.drawable.red);
                    gyeong_ginambuname2.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    gyeong_ginambuimage2.setImageResource(R.drawable.zero);
                    gyeong_ginambugrade2.setBackgroundResource(R.drawable.black);
                    gyeong_ginambuname2.setBackgroundResource(R.drawable.black);
                    break;
                }
            }

            switch (incheonstate2){
                case " 좋음":{
                    incheonimage2.setImageResource(R.drawable.good);
                    incheongrade2.setBackgroundResource(R.drawable.blue);
                    incheonname2.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case " 보통":{
                    Log.d("여기까지오나??","여기까지오나??");
                    incheonimage2.setImageResource(R.drawable.normal);
                    incheongrade2.setBackgroundResource(R.drawable.green);
                    incheonname2.setBackgroundResource(R.drawable.green);
                    break;
                }
                case " 나쁨":{
                    incheonimage2.setImageResource(R.drawable.bad);
                    incheongrade2.setBackgroundResource(R.drawable.orange);
                    incheonname2.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case " 매우나쁨":{
                    incheonimage2.setImageResource(R.drawable.badx2);
                    incheongrade2.setBackgroundResource(R.drawable.red);
                    incheonname2.setBackgroundResource(R.drawable.red);
                    break;
                }
                case " 예보없음":{
                    incheonimage2.setImageResource(R.drawable.zero);
                    incheongrade2.setBackgroundResource(R.drawable.black);
                    incheonname2.setBackgroundResource(R.drawable.black);
                    break;
                }
            }

        }



        GetTomorrowDustThread.active=false;
        GetTomorrowDustThread.interrupted();
    }
    // 현재 대기정보를 가져오는 스레드
    public static void getFindDust(String name){

        GetFindDustThread.active=true;
        GetFindDustThread getweatherthread=new GetFindDustThread(false,name);		//스레드생성(UI 스레드사용시 system 뻗는다)
        getweatherthread.start();	//스레드 시작

    }


    public static void  FindDustThreadResponse(String getCnt,String[] sDate,String[] sSo2Value,String[] sCoValue,String[] sO3Value,String[] sNo2Value,String[] sPm10Value,String[] sKhaiValue,String[] sKhaiGrade,String[] sSo2Grade,String[] sNo2Grade,String[] sCoGrade,String[] sO3Grade,String[] sPm10Grade,String[] sPm25Value, String[] sPm25Grade){	//대기정보 가져온 결과값
        stationCnt=0;	//측정개수정보(여기선 1개만 가져온다
        stationCnt=Integer.parseInt(getCnt);

      //  Log.w("stationcnt", String.valueOf(stationCnt));

        if(stationCnt==0) {	//만약 측정정보가 없다면
           // totalcnt.setText("측정소 정보가 없거나 측정정보가 없습니다.");
         //   date.setText("");	//
            so2value.setText("");
            covalue.setText("");
            no2value.setText("");
            o3value.setText("");
            pm10value.setText("");
            khaivalue.setText("");
            khaigrade.setText("");
            so2grade.setText("");
            no2grade.setText("");
            cograde.setText("");
            o3grade.setText("");
            no2grade.setText("");
            pm10grade.setText("");
            pm25grade.setText("");
            pm25value.setText("");
        }else{	//측정정보있으면
           // totalcnt.setText(sDate[0] + "에 대기정보가 업데이트 되었습니다.");

         //   date.setText(sDate[0]);	//
            so2value.setText(sSo2Value[0]+"ppm");
            covalue.setText(sCoValue[0]+"ppm");
            no2value.setText(sNo2Value[0]+"ppm");
            o3value.setText(sO3Value[0]+"ppm");
            pm10value.setText(sPm10Value[0]+"μg/m³");
            pm25value.setText(sPm25Value[0]+"μg/m³");
            khaivalue.setText(sKhaiValue[0]);
            khaigrade.setText(transGrade(sKhaiGrade[0]));
            so2grade.setText(transGrade(sSo2Grade[0]));
            no2grade.setText(transGrade(sNo2Grade[0]));
            cograde.setText(transGrade(sCoGrade[0]));
            o3grade.setText(transGrade(sO3Grade[0]));
            no2grade.setText(transGrade(sNo2Grade[0]));
            pm10grade.setText(transGrade(sPm10Grade[0]));
            pm25grade.setText(transGrade(sPm25Grade[0]));


            switch (pm25grade.getText().toString()){
                case "좋음":{
                    pm25image.setImageResource(R.drawable.good);
                    pm25grade.setBackgroundResource(R.drawable.blue);
                    pm25value.setBackgroundResource(R.drawable.blue);
                    pm25name.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case "보통":{
                    pm25image.setImageResource(R.drawable.normal);
                    pm25grade.setBackgroundResource(R.drawable.green);
                    pm25value.setBackgroundResource(R.drawable.green);
                    pm25name.setBackgroundResource(R.drawable.green);
                    break;
                }
                case "나쁨":{
                    pm25image.setImageResource(R.drawable.bad);
                    pm25grade.setBackgroundResource(R.drawable.orange);
                    pm25value.setBackgroundResource(R.drawable.orange);
                    pm25name.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case "매우나쁨":{
                    pm25image.setImageResource(R.drawable.badx2);
                    pm25grade.setBackgroundResource(R.drawable.red);
                    pm25value.setBackgroundResource(R.drawable.red);
                    pm25name.setBackgroundResource(R.drawable.red);
                    break;
                }
            }


            switch (so2grade.getText().toString()){
                case "좋음":{
                    so2image.setImageResource(R.drawable.good);
                    so2grade.setBackgroundResource(R.drawable.blue);
                    so2value.setBackgroundResource(R.drawable.blue);
                    so2name.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case "보통":{
                    so2image.setImageResource(R.drawable.normal);
                    so2grade.setBackgroundResource(R.drawable.green);
                    so2value.setBackgroundResource(R.drawable.green);
                    so2name.setBackgroundResource(R.drawable.green);
                    break;
                }
                case "나쁨":{
                    so2image.setImageResource(R.drawable.bad);
                    so2grade.setBackgroundResource(R.drawable.orange);
                    so2value.setBackgroundResource(R.drawable.orange);
                    so2name.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case "매우나쁨":{
                    so2image.setImageResource(R.drawable.badx2);
                    so2grade.setBackgroundResource(R.drawable.red);
                    so2value.setBackgroundResource(R.drawable.red);
                    so2name.setBackgroundResource(R.drawable.red);
                    break;
                }
            }

            switch (cograde.getText().toString()){
                case "좋음":{
                    coimage.setImageResource(R.drawable.good);
                    cograde.setBackgroundResource(R.drawable.blue);
                    covalue.setBackgroundResource(R.drawable.blue);
                    coname.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case "보통":{
                    coimage.setImageResource(R.drawable.normal);
                    cograde.setBackgroundResource(R.drawable.green);
                    covalue.setBackgroundResource(R.drawable.green);
                    coname.setBackgroundResource(R.drawable.green);
                    break;
                }
                case "나쁨":{
                    coimage.setImageResource(R.drawable.bad);
                    cograde.setBackgroundResource(R.drawable.orange);
                    covalue.setBackgroundResource(R.drawable.orange);
                    coname.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case "매우나쁨":{
                    coimage.setImageResource(R.drawable.badx2);
                    cograde.setBackgroundResource(R.drawable.red);
                    covalue.setBackgroundResource(R.drawable.red);
                    coname.setBackgroundResource(R.drawable.red);
                    break;
                }
            }

            switch (no2grade.getText().toString()){
                case "좋음":{
                    no2image.setImageResource(R.drawable.good);
                    no2grade.setBackgroundResource(R.drawable.blue);
                    no2value.setBackgroundResource(R.drawable.blue);
                    no2name.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case "보통":{
                    no2image.setImageResource(R.drawable.normal);
                    no2grade.setBackgroundResource(R.drawable.green);
                    no2value.setBackgroundResource(R.drawable.green);
                    no2name.setBackgroundResource(R.drawable.green);
                    break;
                }
                case "나쁨":{
                    no2image.setImageResource(R.drawable.bad);
                    no2grade.setBackgroundResource(R.drawable.orange);
                    no2value.setBackgroundResource(R.drawable.orange);
                    no2name.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case "매우나쁨":{
                    no2image.setImageResource(R.drawable.badx2);
                    no2grade.setBackgroundResource(R.drawable.red);
                    no2value.setBackgroundResource(R.drawable.red);
                    no2name.setBackgroundResource(R.drawable.red);
                    break;
                }
            }

            switch (o3grade.getText().toString()){
                case "좋음":{
                    o3image.setImageResource(R.drawable.good);
                    o3grade.setBackgroundResource(R.drawable.blue);
                    o3value.setBackgroundResource(R.drawable.blue);
                    o3name.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case "보통":{
                    o3image.setImageResource(R.drawable.normal);
                    o3grade.setBackgroundResource(R.drawable.green);
                    o3value.setBackgroundResource(R.drawable.green);
                    o3name.setBackgroundResource(R.drawable.green);
                    break;
                }
                case "나쁨":{
                    o3image.setImageResource(R.drawable.bad);
                    o3grade.setBackgroundResource(R.drawable.orange);
                    o3value.setBackgroundResource(R.drawable.orange);
                    o3name.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case "매우나쁨":{
                    o3image.setImageResource(R.drawable.badx2);
                    o3grade.setBackgroundResource(R.drawable.red);
                    o3value.setBackgroundResource(R.drawable.red);
                    o3name.setBackgroundResource(R.drawable.red);
                    break;
                }
            }

            switch (pm10grade.getText().toString()){
                case "좋음":{
                    pm10image.setImageResource(R.drawable.good);
                    pm10grade.setBackgroundResource(R.drawable.blue);
                    pm10value.setBackgroundResource(R.drawable.blue);
                    pm10name.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case "보통":{
                    pm10image.setImageResource(R.drawable.normal);
                    pm10grade.setBackgroundResource(R.drawable.green);
                    pm10value.setBackgroundResource(R.drawable.green);
                    pm10name.setBackgroundResource(R.drawable.green);
                    break;
                }
                case "나쁨":{
                    pm10image.setImageResource(R.drawable.bad);
                    pm10grade.setBackgroundResource(R.drawable.orange);
                    pm10value.setBackgroundResource(R.drawable.orange);
                    pm10name.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case "매우나쁨":{
                    pm10image.setImageResource(R.drawable.badx2);
                    pm10grade.setBackgroundResource(R.drawable.red);
                    pm10value.setBackgroundResource(R.drawable.red);
                    pm10name.setBackgroundResource(R.drawable.red);
                    break;
                }
            }

            switch (khaigrade.getText().toString()){
                case "좋음":{
                    khaiimage.setImageResource(R.drawable.good);
                    khaigrade.setBackgroundResource(R.drawable.blue);
                    khaivalue.setBackgroundResource(R.drawable.blue);
                    khainame.setBackgroundResource(R.drawable.blue);
                    break;
                }
                case "보통":{
                    khaiimage.setImageResource(R.drawable.normal);
                    khaigrade.setBackgroundResource(R.drawable.green);
                    khaivalue.setBackgroundResource(R.drawable.green);
                    khainame.setBackgroundResource(R.drawable.green);
                    break;
                }
                case "나쁨":{
                    khaiimage.setImageResource(R.drawable.bad);
                    khaigrade.setBackgroundResource(R.drawable.orange);
                    khaivalue.setBackgroundResource(R.drawable.orange);
                    khainame.setBackgroundResource(R.drawable.orange);
                    break;
                }
                case "매우나쁨":{
                    khaiimage.setImageResource(R.drawable.badx2);
                    khaigrade.setBackgroundResource(R.drawable.red);
                    khaivalue.setBackgroundResource(R.drawable.red);
                    khainame.setBackgroundResource(R.drawable.red);
                    break;
                }
            }

//            if(so2grade.getText().toString().equals("좋음")){
//                so2image.setImageResource(R.drawable.good);
//                so2grade.setBackgroundResource(R.drawable.blue);
//                so2value.setBackgroundResource(R.drawable.blue);
//                so2name.setBackgroundResource(R.drawable.blue);
//            }
//            else if(so2grade.getText().toString().equals("보통")){
//                so2image.setImageResource(R.drawable.normal);
//                so2grade.setBackgroundResource(R.drawable.green);
//                so2value.setBackgroundResource(R.drawable.green);
//                so2name.setBackgroundResource(R.drawable.green);
//            }
//            else if(so2grade.getText().toString().equals("나쁨")){
//                so2image.setImageResource(R.drawable.bad);
//                so2grade.setBackgroundResource(R.drawable.orange);
//                so2value.setBackgroundResource(R.drawable.orange);
//                so2name.setBackgroundResource(R.drawable.orange);
//            }
//            else if(so2grade.getText().toString().equals("매우나쁨")){
//                so2image.setImageResource(R.drawable.badx2);
//                so2grade.setBackgroundResource(R.drawable.red);
//                so2value.setBackgroundResource(R.drawable.red);
//                so2name.setBackgroundResource(R.drawable.red);
//            }


        }

        GetFindDustThread.active=false;
        GetFindDustThread.interrupted();
    }
    public static void getStationList(String name){	//이건 측정소 정보가져올 스레드

        GetStationListThread.active=true;
        GetStationListThread getstationthread=new GetStationListThread(false,name);		//스레드생성(UI 스레드사용시 system 뻗는다)
        getstationthread.start();	//스레드 시작

    }

    public static void  StationListThreadResponse(String cnt,String[] sStation){	//측정소 정보를 가져온 결과
        stationCnt=0; //cnt 개수만큼 stationlist배열을 늘리고 sStion에 담긴것을 넣는다, sStation 자체가 null
        stationCnt=Integer.parseInt(cnt);
        stationlist=new String[stationCnt];
        for(int i=0;i<stationCnt;i++){
         //   Log.d("stationCnt i check",Integer.toString(i));
          //  Log.d("stationCnt check",Integer.toString(stationCnt));
          //  Log.d("sStationlist thread",sStation[i]);
            stationlist[i]=sStation[i];
        //    Log.d("stationlist thread",stationlist[i]);
//			Log.e("station",cnt);
//			Log.e("station",sStation[i]); // 널포인트
//			Log.e("station",stationlist[i]);
        }

     //   Log.d("stationlistcheck",stationlist.toString());
     //   Log.d("stationlistcheck",stationlist[0]);
        spinnerStation=new ArrayAdapter<>(mContext,R.layout.spinner_text,stationlist);
        station.setAdapter(spinnerStation);



        GetFindDustThread.active=false;
        GetFindDustThread.interrupted();



    }


    static public String transGrade(String intGrade){
        String trans=null;
        switch (intGrade){
            case "1":
                trans="좋음";
                break;
            case "2":
                trans="보통";
                break;
            case "3":
                trans="나쁨";
                break;
            case "4":
                trans="매우나쁨";
                break;
            default:
                break;

        }
        return trans;
    }

    /**
     * 버튼에 대한 처리
     */

    public void onClick(View v) {

        switch(v.getId()){

            case R.id.getBtn:	//대기정보 가져오는 버튼
                String stationName;
                stationName=where.getText().toString();
                getFindDust(stationName);

                break;

            default:
                break;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch(parent.getId()){

            case R.id.sido:		//시도 변경 스피너

                getStationList(sidolist[position]); // 시도 바뀔때 해당 측정소 보이도록하는거


                break;
            case R.id.station:	//측정소 변경 스피너
                try{
                    Log.e("station name232", stationlist[position]);

                }catch (Exception e){
                    Log.e("exception",""+e);
                }

                where.setText(stationlist[position]);	//측정소이름을 바로 입력해 준다.

                break;


            default:
                break;

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }










}
