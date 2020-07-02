package com.example.samsung.picnic12;


import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/*  메인 액티비티 뷰페이저를 이용한 프레그먼트 구조
 프래그먼트 1: 미세먼지
 프래그먼트 2: 날씨
 프래그먼트 3: 채팅
 프래그먼트 4: 게시판
 */
public class MainActivity extends AppCompatActivity
{
    ViewPager vp;
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //뷰페이저 선언 및 레이아웃 선언

        vp = (ViewPager)findViewById(R.id.vp);
        ll = (LinearLayout)findViewById(R.id.ll);

        //프래그먼트 탭의 글씨 쓰는 부분
        TextView tab_first = (TextView)findViewById(R.id.tab_first);
        TextView tab_second = (TextView)findViewById(R.id.tab_second);
        TextView tab_third = (TextView)findViewById(R.id.tab_third);
        TextView tab_four = (TextView)findViewById(R.id.tab_four);
        TextView tab_five = (TextView)findViewById(R.id.tab_five);

        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        vp.setCurrentItem(0);
        // 탭 클릭시 작동할 리스너
        tab_first.setOnClickListener(movePageListener);
        tab_first.setTag(0);
        tab_second.setOnClickListener(movePageListener);
        tab_second.setTag(1);
        tab_third.setOnClickListener(movePageListener);
        tab_third.setTag(2);
        tab_four.setOnClickListener(movePageListener);
        tab_four.setTag(3);
        tab_five.setOnClickListener(movePageListener);
        tab_five.setTag(4);

        tab_first.setSelected(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);



// 뷰페이저로 스크롤 이동할때 포지션 값 계산
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                int i = 0;
                while(i<5)
                {
                    if(position==i)
                    {
                        ll.findViewWithTag(i).setSelected(true);
                    }
                    else
                    {
                        ll.findViewWithTag(i).setSelected(false);
                    }
                    i++;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
// 뷰페이저 클릭이동시 페이지 계산
    View.OnClickListener movePageListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int tag = (int) v.getTag();

            int i = 0;
            while(i<5)
            {
                if(tag==i)
                {
                    ll.findViewWithTag(i).setSelected(true);
                }
                else
                {
                    ll.findViewWithTag(i).setSelected(false);
                }
                i++;
            }

            vp.setCurrentItem(tag);
        }
    };

    private class pagerAdapter extends FragmentStatePagerAdapter
    {
        public pagerAdapter(android.support.v4.app.FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch(position)
            {
                case 0:
                    return new FirstFragment();
                case 1:
                    return new SecondFragment();
                case 2:
                    return new Fragment1();
                case 3:
                    return new FourFragment();
                case 4:
                    return new FiveFragment();
                default:
                    return null;
            }
        }
        @Override
        public int getCount()
        {
            return 5;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==444){

        }
        if(resultCode==333){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_actions, menu);

        return true;

    }
//상당의 탭의 메뉴에서 프로필, AR 촬영 선택하는 부분
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case  R.id.first:
                Intent prfileintent = new Intent(this,Myprofile.class);
                startActivity(prfileintent);

                break;
            case R.id.second:
                Intent prfileintent2 = new Intent(this,DrawAR.class);
                startActivity(prfileintent2);
        }

        return true;
    }
}


