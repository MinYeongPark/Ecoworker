package org.techtown.ecoworker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Dialog;
import android.app.FragmentTransaction;

import android.content.Intent;


import android.content.Intent;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.techtown.ecoworker.alarm.fragment_alarm;
import org.techtown.ecoworker.change.fragment_change;
import org.techtown.ecoworker.intro.fragment_intro;

import org.techtown.ecoworker.mypage.MypageActivity;
import org.techtown.ecoworker.login.LoginActivity;
import org.techtown.ecoworker.stat.fragment_stat;

public class MainActivity extends AppCompatActivity {

    fragment_intro fragment1;
    fragment_change fragment2;
    fragment_alarm fragment3;
    fragment_stat fragment4;
    ImageButton profile;
    Dialog intro_dialog2;

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

        profile = (ImageButton) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, MypageActivity.class);
                startActivity(intent);
            }
        });

        intro_dialog2 = new Dialog(MainActivity.this);
        intro_dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        intro_dialog2.setContentView(R.layout.intro_dialog2);
        showintro_dialog();


    }

    public void showintro_dialog(){
        intro_dialog2.show();
        ImageView imageViewclose = intro_dialog2.findViewById(R.id.imageViewclose);

        imageViewclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intro_dialog2.dismiss();
                Toast.makeText(MainActivity.this, "안내사항 닫기", Toast.LENGTH_SHORT).show();
            }
        });



    }
}