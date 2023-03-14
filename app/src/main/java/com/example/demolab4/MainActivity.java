package com.example.demolab4;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationRequest;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;

public class MainActivity extends AppCompatActivity {
    private com.google.android.gms.location.FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         //bài 1
        ////hihihihiaii


      fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ActivityResultLauncher<String[]> locationPermissionRequest =
                    registerForActivityResult(new ActivityResultContracts
                            .RequestMultiplePermissions(), result -> {
                        Boolean fineLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_FINE_LOCATION, false);
                        Boolean coarseLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_COARSE_LOCATION, false);



                        if (fineLocationGranted != null && fineLocationGranted) {

                            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            fusedLocationClient.getCurrentLocation(
                                    LocationRequest.QUALITY_BALANCED_POWER_ACCURACY,
                                    new CancellationToken() {
                                        @NonNull
                                        @Override
                                        public CancellationToken onCanceledRequested(
                                                @NonNull OnTokenCanceledListener
                                                        onTokenCanceledListener) {
                                            return null;
                                        }

                                        @Override
                                        public boolean isCancellationRequested() {
                                            return false;
                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    Toast.makeText(MainActivity.this,  location.getLatitude() + "  :" +
                                            location.getLongitude() , Toast.LENGTH_LONG).show();

                                }
                            });


                                } else if (coarseLocationGranted != null && coarseLocationGranted) {
                            fusedLocationClient.getLastLocation()
                                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                        @Override
                                        public void onSuccess(Location location) {
                                            Toast.makeText(MainActivity.this, " kinh độ: "+location.getLatitude() + " vĩ độ:" +
                                                    location.getLongitude() , Toast.LENGTH_LONG).show();
                                        }
                                    });
                                } else {
                                    Toast.makeText(this, "App cần cấp quyền mới có thế nhận gps ", Toast.LENGTH_SHORT).show();
                                }
                            }
                    );


// Before you perform the actual permission request, check whether your app
// already has the permissions, and whether your app needs to show a permission
// rationale dialog. For more details, see Request permissions.
            locationPermissionRequest.launch(new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION},999);
            }
            }
        // bài 2
        findViewById(R.id.btn).setOnClickListener(v -> {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo dulieu = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (wifi.isConnected()) {
                Toast.makeText(this, "đang dùng wifi", Toast.LENGTH_SHORT).show();
            }
            else if (dulieu.isConnected()) {
                Toast.makeText(this, "đang dùng 3g", Toast.LENGTH_SHORT).show();
            }
        });

        }


}

