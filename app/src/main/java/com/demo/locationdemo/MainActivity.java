package com.demo.locationdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private Activity mActivity;
    private FusedLocationProviderClient client;
    private Button button;
    private TextView tvLongitude;
    private TextView tvLatitude;
    private String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;
        button = findViewById(R.id.btn);
        tvLongitude = findViewById(R.id.tv_longitude);
        tvLatitude = findViewById(R.id.tv_latitude);
        if (ActivityCompat.checkSelfPermission(mActivity, permissions[0]) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(mActivity, permissions[1]) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, permissions, 20002);
        } else {
            client = LocationServices.getFusedLocationProviderClient(mActivity);
        }
    }

    public void getLastLocation(View view) {
        if (ActivityCompat.checkSelfPermission(mActivity, permissions[0]) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(mActivity, permissions[1]) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, permissions, 20002);
        } else {
            if (client == null) {
                client = LocationServices.getFusedLocationProviderClient(mActivity);
            }
            client.getLastLocation().addOnSuccessListener(mActivity, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Log.d("Longitude", String.valueOf(location.getLongitude()));
                        Log.d("Latitude", String.valueOf(location.getLatitude()));
                        tvLongitude.setText(String.format("Longitude:%s", location.getLongitude()));
                        tvLatitude.setText(String.format("Latitude:%s", location.getLatitude()));
                    } else {
                        Log.e("Error", "location == null");
                        Toast.makeText(mActivity, "location == null", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}