package com.example.samsung.picnic12;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by samsung on 2018-05-16.
 */

public class RoomAdapter extends BaseAdapter {
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
    private ArrayList<ListContents> m_List;

    public RoomAdapter() {
        m_List = new ArrayList<ListContents>();
    }

    // 외부에서 아이템 추가 요청 시 사용
    // 즉 채팅 추가될때 사용되는 부분
    public void add(ArrayList<HashMap<String, String>> _inputValue) {

        m_List.add(new ListContents(_inputValue));
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
        TextView        text    = null;
        //고정시키는거?
        CustomHolder    holder  = null;
        LinearLayout    layout  = null;
        //그 작대기 표시되는거임 크게 중요 x  지워야지.. 아니면 현재 날짜 == 아니면 생기게하던가.
        View            viewRight = null;
        View            viewLeft = null;

        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_roomitem, parent, false);

            // 뷰 새로 생길때
            layout    = (LinearLayout) convertView.findViewById(R.id.layout);
            text    = (TextView) convertView.findViewById(R.id.text);


            // 홀더 생성 및 Tag로 등록
            // holder. 이것들은 홀더 메소드에서 선언된것들
            holder = new CustomHolder();
            holder.m_TextView   = text;
            holder.layout = layout;
            convertView.setTag(holder);
        }
        else {
            holder  = (CustomHolder) convertView.getTag();
            text    = holder.m_TextView;
  //          layout  = holder.layout;

        }
//        Log.d("id체크",m_List.get(position).myid);
//        Log.d("roomname체크",m_List.get(position).roomname);
        // Text 등록, 이거 같이해서 아이디로 표시가능하게하자
     //   Log.d("어뎁터로 넘어오냐",m_List.get(position).inputValue.get(position).toString());
        for(int i=0; i<m_List.size();i++) {
          // Log.d("어뎁터로 넘어오냐",m_List.get(position).inputValue.get(position).get("title"));
            text.setText(m_List.get(position).inputValue.get(position).get("title"));
        }

        //이건 냅둬도될듯?? 레이아웃에 텍스트뷰 추가시켜서 쓰면 그게 간단할듯
//        if( m_List.get(position).type == 0 ) {
//            text.setBackgroundResource(R.drawable.inbox2);
//            layout.setGravity(Gravity.LEFT);
//            viewRight.setVisibility(View.GONE);
//            viewLeft.setVisibility(View.GONE);
//        }else if(m_List.get(position).type == 1){
//            text.setBackgroundResource(R.drawable.outbox2);
//            layout.setGravity(Gravity.RIGHT);
//            viewRight.setVisibility(View.GONE);
//            viewLeft.setVisibility(View.GONE);
//        }else if(m_List.get(position).type == 2){
//            text.setBackgroundResource(R.drawable.datebg);
//            layout.setGravity(Gravity.CENTER);
//            viewRight.setVisibility(View.VISIBLE);
//            viewLeft.setVisibility(View.VISIBLE);
//        }
        // 리스트 아이템을 터치 했을 때 이벤트 발생
//        convertView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // 터치 시 해당 아이템 이름 출력
//                Toast.makeText(context, "리스트 클릭 : "+m_List.get(pos), Toast.LENGTH_SHORT).show();
//            }
//        });

//        // 리스트 아이템을 길게 터치 했을때 이벤트 발생
//        convertView.setOnLongClickListener(new OnLongClickListener() {
//
//            @Override
//            public boolean onLongClick(View v) {
//                // 터치 시 해당 아이템 이름 출력
//                Toast.makeText(context, "리스트 롱 클릭 : "+m_List.get(pos), Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });

        return convertView;
    }

    private class CustomHolder {
      
        TextView    m_TextView;
        LinearLayout layout;

    }
}
