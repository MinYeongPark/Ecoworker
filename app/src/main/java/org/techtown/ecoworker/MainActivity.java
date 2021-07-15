package org.techtown.ecoworker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import org.techtown.ecoworker.alarm.fragment_alarm;
import org.techtown.ecoworker.change.fragment_change;
import org.techtown.ecoworker.intro.fragment_intro;
import org.techtown.ecoworker.stat.fragment_stat;

public class MainActivity extends AppCompatActivity {

    fragment_intro fragment1;
    fragment_change fragment2;
    fragment_alarm fragment3;
    fragment_stat fragment4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment1 = new fragment_intro();
        fragment2 = new fragment_change();
        fragment3 = new fragment_alarm();
        fragment4 = new fragment_stat();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("소개"));
        tabs.addTab(tabs.newTab().setText("변환"));
        tabs.addTab(tabs.newTab().setText("알람"));
        tabs.addTab(tabs.newTab().setText("통계"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition(); //몇번째 탭 선택했는지

                if (position == 0) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
                } else if (position == 1) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment2).commit();
                } else if (position == 2) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment3).commit();
                } else if (position == 3) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment4).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}