package com.baozou.toptab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TopTabView topTabView;

    private TopTabView.TopViewAdapter adapter;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        topTabView = (TopTabView) findViewById(R.id.toptabview);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return new Fragment1();
                } else if (position == 1) {
                    return new Fragment2();
                } else {
                    return new Fragment3();
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
        adapter = new TopTabView.TopViewAdapter() {
            @Override
            public View getView(int position) {
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.top_child_view, topTabView, false);
                TextView textView = (TextView) view.findViewById(R.id.tab1);
                if (position == 0) {
                    textView.setText("tab1");
                } else if (position == 1) {
                    textView.setText("tab2");
                } else if (position == 2) {
                    textView.setText("tab3");
                }
                return view;
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public void onClick(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public View getIndexView() {
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.top_child_view_index,topTabView,false);
//                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.top_child_view_index, null);
                return view;
            }
        };
        topTabView.setViewPager(viewPager);
        topTabView.setAdapter(adapter);
    }
}
