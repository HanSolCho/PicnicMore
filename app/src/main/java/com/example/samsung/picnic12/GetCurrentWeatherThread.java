package com.example.samsung.picnic12;

import android.os.Handler;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by samsung on 2018-05-01.
 */

public class GetCurrentWeatherThread extends Thread {	//기상청 연결을 위한 스레드
    static public boolean active=false;
    //파서용 변수
    int data=0;			//이건 파싱해서 array로 넣을때 번지
    public boolean isreceiver;
    String sTotalCount,gridx,gridy;	//결과수
    String[] sCategory,sobsrValue;	//예보시간,날짜,온도,풍향,습도,날씨
    boolean bTotalCount,bCategory,bobsrValue;	//여긴 저장을 위한 플래그들
    boolean tResponse;	//이건 text로 뿌리기위한 플래그
    Handler handler;	//날씨저장 핸들러
    String Servicekey="serviceKey=uyp1INufhWtDzTKo0v%2BtGgDEaQbhIj%2B9kiJWRaQVHfHX9ojxLEdAtvktLXoQIT85QaOqTK9XwpIP%2Bk3V2hvZDw%3D%3D";
    String getInfo="http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/";
    String getCurrentWeather="ForecastGrib?";
    String searchDate="base_date=";
    String searchTime="base_time=";
    String infoCnt="numOfRows=10";
    String pagesize="pageSize=10";
    String pageNo="pageNo=1";
    String startpage="startPage=1";
    String type="_type=xml";


    long now = System.currentTimeMillis();
    Date date = new Date(now);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String nowDate = sdf.format(date);
    SimpleDateFormat sdf2 = new SimpleDateFormat("HH");
    String nowHour = sdf2.format(date);
    SimpleDateFormat sdf3 = new SimpleDateFormat("mm");
    String nowMin = sdf3.format(date);
//현재시간 넣고


    public GetCurrentWeatherThread(boolean receiver, String x, String y){


        handler=new Handler();
        isreceiver=receiver;
        //dongName=dong;
        Log.d("좌표좌표",x);
        gridx=x;
        gridy=y;

        bTotalCount=bCategory=bobsrValue=false;	//부울상수는 false로 초기화해주자
    }
    public void run(){

        if(active){
            try{

                sCategory = new String[100];
                sobsrValue = new String[100];


                data=0;

                int intmin = Integer.parseInt(nowMin);
                int inthour = Integer.parseInt(nowHour);
                if(intmin<20){
                    intmin = 40;
                    nowMin = Integer.toString(intmin);
                    inthour = inthour-1;
                    if(inthour<10) {
                        nowHour = "0"+ Integer.toString(inthour);
                    }
                    else{
                        nowHour = Integer.toString(inthour);
                    }

                }

                XmlPullParserFactory factory= XmlPullParserFactory.newInstance();	//이곳이 풀파서를 사용하게 하는곳
                factory.setNamespaceAware(true);									//이름에 공백도 인식
                XmlPullParser xpp=factory.newPullParser();							//풀파서 xpp라는 객체 생성
                String dustUrl=getInfo+getCurrentWeather+Servicekey+"&"+searchDate+nowDate+"&"+searchTime+nowHour+nowMin+"&nx="+gridx+"&ny="+gridy+"&"+infoCnt+"&"+pagesize+"&"+pageNo+"&"+startpage+"&"+type;
                Log.w("스레드가 받은 ", dustUrl);
                URL url=new URL(dustUrl);		//URL객체생성
                InputStream is=url.openStream();	//연결할 url을 inputstream에 넣어 연결을 하게된다.
                xpp.setInput(is,"UTF-8");			//이렇게 하면 연결이 된다. 포맷형식은 utf-8로

                int eventType=xpp.getEventType();	//풀파서에서 태그정보를 가져온다.

                while(eventType!= XmlPullParser.END_DOCUMENT){	//문서의 끝이 아닐때

                    switch(eventType){
                        case XmlPullParser.START_TAG:	//'<'시작태그를 만났을때

                            if(xpp.getName().equals("category")){	//측정일
                                bCategory=true;

                            }if(xpp.getName().equals("obsrValue")){	//측정일
                            bobsrValue=true;

                        }if(xpp.getName().equals("totalCount")){	//결과 수
                            bTotalCount=true;

                        }

                            break;

                        case XmlPullParser.TEXT:	//텍스트를 만났을때
                            //앞서 시작태그에서 얻을정보를 만나면 플래그를 true로 했는데 여기서 플래그를 보고
                            //변수에 정보를 넣어준 후엔 플래그를 false로~
                            if(bCategory){				//동네이름
                                sCategory[data]=xpp.getText();
                                bCategory=false;
                            }if(bobsrValue){				//동네이름
                            sobsrValue[data]=xpp.getText();
                            bobsrValue=false;
                        }if(bTotalCount){
                            sTotalCount=xpp.getText();
                            bTotalCount=false;
                        }
                            break;

                        case XmlPullParser.END_TAG:		//'</' 엔드태그를 만나면 (이부분이 중요)

                            if(xpp.getName().equals("response")){	//태그가 끝나느 시점의 태그이름이 item이면(이건 거의 문서의 끝
                                tResponse=true;						//따라서 이때 모든 정보를 화면에 뿌려주면 된다.
                                view_text();					//뿌려주는 곳~
                            }if(xpp.getName().equals("item")){	//item 예보시각기준 예보정보가 하나씩이다.
                            data++;							//즉 item == 예보 개수 그러므로 이때 array를 증가해주자
                        }
                            break;
                    }
                    eventType=xpp.next();	//이건 다음 이벤트로~
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }



    }

    /**
     * 이 부분이 뿌려주는곳
     * 뿌리는건 핸들러가~
     * @author Ans
     */
    private void view_text(){

        handler.post(new Runnable() {	//기본 핸들러니깐 handler.post하면됨

            @Override
            public void run() {

                active=false;
//                for(int i=0; i<data; i++) {
//                    Log.d("카테고리테스트", sReh[i]);
//                    Log.d("카테고리값테스트", sSky[i]);
//                }

                if(tResponse){		//문서를 다 읽었다
                    tResponse=false;
                    data=0;		//

                    for(int i=0; i<data; i++) {
                        Log.d("카테고리테스트", sCategory[i]);
                        Log.d("카테고리값테스트", sobsrValue[i]);
                    }

                    SecondFragment.FindCurrentWeatherThreadResponse(sTotalCount,sCategory,sobsrValue);



                }


            }
        });
    }
}
