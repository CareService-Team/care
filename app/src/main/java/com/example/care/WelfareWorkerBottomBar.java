package com.example.care;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WelfareWorkerBottomBar extends AppCompatActivity {

    Fragment fragment_map;
    Fragment fragment_list;
    Fragment fragment_thing;
    Fragment fragment_mypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welfare_worker_bottom_bar);

        fragment_map = new FragmentMap();
        fragment_list = new FragmentList();
        fragment_thing = new FragmentThing();
        fragment_mypage = new FragmentMypage();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_map).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.w_map:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_map).commit();
                        return true;
                    case R.id.w_list:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_list).commit();
                        return true;
                    case R.id.w_thing:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_thing).commit();
                        return true;
                    case R.id.w_mypage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_mypage).commit();
                        return true;
                }
                return false;
            }
        });
    }
}