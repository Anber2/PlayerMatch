package com.mawaqaa.playermatch.Activities;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mawaqaa.playermatch.R;
import com.mawaqaa.playermatch.Utilities.PreferenceUtil;

import java.util.List;
import java.util.Locale;


/**
 * Created by HP on 10/18/2017.
 */

public class SelectLocationActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener {
    static String TAG = "SelectLocationActivity";

    //Layout
    Button button_selectLocation_ok;


    //
    String gameAddress;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_selectLocation);
        mapFragment.getMapAsync(this);

        button_selectLocation_ok = findViewById(R.id.button_selectLocation_ok);
        button_selectLocation_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SelectLocationActivity.this.finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng sydney = new LatLng(29.362703, 47.965167);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(7).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        LatLng gameAdd = new LatLng(Double.parseDouble(PreferenceUtil.getGameLatitude(this)), Double.parseDouble(PreferenceUtil.getGameLongitude(this)));


        mMap.addMarker(new MarkerOptions().position(gameAdd).title(PreferenceUtil.getGameAddress(this)));

        mMap.setOnMapLongClickListener(this);

        mMap.setOnMapClickListener(this);

    }


    @Override
    public void onMapLongClick(LatLng point) {


        try {
            Geocoder geo = new Geocoder(SelectLocationActivity.this, Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(point.latitude, point.longitude, 1);

            if (addresses.size() > 0) {

                  gameAddress = addresses.get(0).getFeatureName()
                        + ", " + addresses.get(0).getLocality()
                        + ", " + addresses.get(0).getAdminArea()
                        + ", " + addresses.get(0).getCountryName();

                Toast.makeText(SelectLocationActivity.this,
                        gameAddress
                        , Toast.LENGTH_SHORT).show();

                PreferenceUtil.setGameLatitude(SelectLocationActivity.this, "" + point.latitude);
                PreferenceUtil.setGameLongitude(SelectLocationActivity.this, "" + point.longitude);

                PreferenceUtil.setGameAddress(SelectLocationActivity.this, gameAddress);


                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(point.latitude, point.longitude)).title(gameAddress);
                mMap.clear();

                mMap.addMarker(marker);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(SelectLocationActivity.this, "Long click to select location", Toast.LENGTH_SHORT).show();


    }
}
