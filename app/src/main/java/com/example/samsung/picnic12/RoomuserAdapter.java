package com.example.samsung.picnic12;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by samsung on 2018-06-26.
 */

public class RoomuserAdapter extends BaseAdapter {
    String name;

    public class ListContents{

        ArrayList<HashMap<String, String>> inputValue;

        //메세지 선언인가봄
        //  String roomname;
        //  String id;
        // 내껀지 다른사람껀지 날짜인지 타입 1;내꺼 2: 다른사람 3: 날짜
        int type;

        ListContents(ArrayList<HashMap<String, String>> _inputValue)
        {
            //생성자
            this.inputValue = _inputValue;
        }
    }

    //아이템 담을 리스트
    private ArrayList<RoomuserAdapter.ListContents> m_List;

    public RoomuserAdapter() {
        m_List = new ArrayList<RoomuserAdapter.ListContents>();
    }

    // 외부에서 아이템 추가 요청 시 사용
    // 즉 채팅 추가될때 사용되는 부분
    public void add(ArrayList<HashMap<String, String>> _inputValue) {

        m_List.add(new RoomuserAdapter.ListContents(_inputValue));
    }

    //이건 필요없겠다.
    // 외부에서 아이템 삭제 요청 시 사용
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

    // 보여지는 부분 처리
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //위치값
        final int pos = position;
        final Context context = parent.getContext();

        //글씨 써지는 부분
        TextView idview    = null;
        ImageView imgview = null;
        //고정시키는거?
        RoomuserAdapter.CustomHolder holder  = null;
        LinearLayout layout  = null;
        //그 작대기 표시되는거임 크게 중요 x  지워야지.. 아니면 현재 날짜 == 아니면 생기게하던가.
        View            viewRight = null;
        View            viewLeft = null;

        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.navigation_roomuseritem, parent, false);

            // 뷰 새로 생길때
            layout    = (LinearLayout) convertView.findViewById(R.id.layout);
            idview    = (TextView) convertView.findViewById(R.id.userid);
            imgview = (ImageView)convertView.findViewById(R.id.proimg);


            // 홀더 생성 및 Tag로 등록
            // holder. 이것들은 홀더 메소드에서 선언된것들
            holder = new RoomuserAdapter.CustomHolder();
            holder.m_TextView   = idview;
            holder.m_ImgView = imgview;
            holder.layout = layout;
            convertView.setTag(holder);
        }
        else {
            holder  = (RoomuserAdapter.CustomHolder) convertView.getTag();
           idview    = holder.m_TextView;
           imgview = holder.m_ImgView;
            //          layout  = holder.layout;

        }

        for(int i=0; i<m_List.size();i++) {
            Log.d("룸유저어뎁터로 넘어오냐",m_List.get(position).inputValue.get(position).get("id"));
            idview.setText(m_List.get(position).inputValue.get(position).get("id"));
            Picasso.with(context)
                    .load("http://whgksthf702.vps.phps.kr/uploads/"+m_List.get(position).inputValue.get(position).get("proimg"))
                    .into(imgview);

        }

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context,m_List.get(pos).inputValue.get(pos).get("id"), Toast.LENGTH_SHORT).show();
//
//            }
//        });


        return convertView;
    }


    private class CustomHolder {

        TextView    m_TextView;
        ImageView m_ImgView;
        LinearLayout layout;


    }
}
