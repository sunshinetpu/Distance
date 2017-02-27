package com.sunshinetpu.distance;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{
    private Marker mMarker1, mMarker2;
    private TextView mTvDistance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvDistance = (TextView) findViewById(R.id.text_view_distance);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng position = marker.getPosition();
                marker.setTitle(getLocationTitle(position));
                marker.showInfoWindow();
                updateDistance();
            }
        });

        LatLng hanoi = new LatLng(21.004975, 105.841577);
        mMarker1 = googleMap.addMarker(createMarkerOptions(hanoi));

        LatLng russia = new LatLng(61.398292, 98.102852);
        mMarker2 = googleMap.addMarker(createMarkerOptions(russia));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(hanoi));
        updateDistance();
    }

    private MarkerOptions createMarkerOptions(LatLng location){
        return new MarkerOptions().position(location).title(getLocationTitle(location)).draggable(true);
    }

    private String getLocationTitle(LatLng location){
        return location.latitude + ", " + location.longitude;
    }

    private void updateDistance(){
        if(mMarker1 != null && mMarker2 != null){
            Point firstPoint = new Point(mMarker1.getPosition().latitude,mMarker1.getPosition().longitude);
            Point secondPoint = new Point(mMarker2.getPosition().latitude,mMarker2.getPosition().longitude);
            String meters = String.format(Locale.getDefault(),"%.2f",Point.getDistanceBetween2Points(firstPoint,secondPoint)) + " m";
            mTvDistance.setText(meters);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


}
