package com.example.care;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

public class WelfareWorkerBottomBar extends AppCompatActivity
        implements OnMapReadyCallback {

    private static final String TAG = "WelfareWorkerBottomBar";

//    Fragment fragment_map;
//    Fragment fragment_list;
//    Fragment fragment_thing;
//    Fragment fragment_mypage;

    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private FusedLocationSource mLocationSource;
    private NaverMap mNaverMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welfare_worker_bottom_bar);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        MapFragment fragmentMap = (MapFragment) fragmentManager.findFragmentById(R.id.map);
        if (fragmentMap == null) {
            fragmentMap = MapFragment.newInstance();
        }

        transaction.replace(R.id.container, fragmentMap).commit();

        //getMapAsync를 호출하여 비동기로 OnMapReady 콜백 메서드 호출
        //OnMapReady에서 네이버 맵 객체를 받음
        fragmentMap.getMapAsync(this);

        //위치를 반환하는 구현체인 FusedLocationSource 생성
        mLocationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);


//        // fragment_map, fragment_list, fragment_thing, fragment_mypage 현재 사용 안함.
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_map).commit();
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.w_map:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_map).commit();
//                        return true;
//                    case R.id.w_list:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_list).commit();
//                        return true;
//                    case R.id.w_thing:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_thing).commit();
//                        return true;
//                    case R.id.w_mypage:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_mypage).commit();
//                        return true;
//                }
//                return false;
//            }
//        });
    }

    public void onMapReady(@NonNull NaverMap naverMap){
        Log.d(TAG, "onMapReady");

        //지도상에 마커 표시
        Marker marker = new Marker();
        marker.setPosition(new LatLng(37.5670135, 126.9783740));
        marker.setMap(naverMap);


        //naverMap 객체 받아서 naverMap객체에 위치 소스 지정
        mNaverMap = naverMap;
        mNaverMap.setLocationSource(mLocationSource);

        //사용자의 위치가 변경되면 좌표를 출력
        mNaverMap.addOnLocationChangeListener(location ->
                Log.d(TAG, location.getLatitude() + ", " + location.getLongitude()));

        //권한확인, 결과는 onRequestPermissionsResult 콜백 메서드 호출
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);

    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults){
        //request code와 권한획득 여부 확인
        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}