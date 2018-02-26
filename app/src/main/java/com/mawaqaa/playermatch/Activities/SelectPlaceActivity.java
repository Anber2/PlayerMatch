package com.mawaqaa.playermatch.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.mawaqaa.playermatch.R;
import com.mawaqaa.playermatch.Utilities.PreferenceUtil;

/**
 * Created by HP on 12/20/2017.
 */

public class SelectPlaceActivity extends FragmentActivity implements PlaceSelectionListener {

    static String TAG = "SelectPlaceActivity";

    //google AUTOCOMPLETE places
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    PlaceAutocompleteFragment place_autocomplete_fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_place);

        //PlaceAutocompleteFragment
        place_autocomplete_fragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        AutocompleteFilter typeFilter =
                new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS).build();
        place_autocomplete_fragment.setFilter(typeFilter);

        place_autocomplete_fragment.setOnPlaceSelectedListener(this);


    }

    @Override
    public void onPlaceSelected(Place place) {
        Log.i(TAG, "Place: " + place.getName() + "LatLng: " + place.getLatLng());

        Toast.makeText(this, "Place: " + place.getName() + "LatLng: " + place.getLatLng(), Toast.LENGTH_SHORT).show();

        String str = ""+place.getLatLng();

        String[] latLng = str.split("\\(");

        String lat = latLng[0];
        String lng = latLng[1];

        Log.i(TAG, "lat: " + lat + "lng: " + lng);

        String[] lng2 = lng.split(",");

        String lat1 = latLng[0];
        String lng3 = latLng[1];


        Log.i(TAG, "split 2 : " + lat1 + " lng: " + lng3);

        String[] loc = lng3.split(",");

        String lat_ = loc[0];
        String lng_ = loc[1];

        Log.i("-----------split 3", "lat_: " + lat_ + " lng_ : " + lng_);


        PreferenceUtil.setGameLatitude(this, "" + lat_);
        PreferenceUtil.setGameLongitude(this, "" + lng_);

        PreferenceUtil.setGameAddress(this, ""+ place.getName());



    }

    @Override
    public void onError(Status status) {

    }
}
