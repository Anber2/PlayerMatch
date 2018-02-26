package com.mawaqaa.playermatch.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mawaqaa.playermatch.Constants.AppConstants;
import com.mawaqaa.playermatch.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.mawaqaa.playermatch.Fragments.HomeFragment.myContext;

/**
 * Created by HP on 10/12/2017.
 */

public class GameDetailsFragment extends Fragment  {
    String TAG = "GamesCreatedFragment";
    //layout
    TextView textView_orginizerName, textView_date, textView_time, textView_locationName, textView_gender, textView_ageRange, textView_playersAquired, textView_playersRequired, textView_locationMap;
    LinearLayout LayoutMap;
    ImageView imageView_locationMapArrow, imageView_sportDetails;
    //progress par
    ProgressDialog progressBar;
    //map
    GoogleMap mMap;
    SupportMapFragment mapFragment;
    String Longitude="00.00000";
    String Latitude= "00.00000";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_details, container, false);

        initView(v);

        getMatchDetails();

        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
       // mapFragment.getMapAsync(this);

        return v;
    }

    private void getMatchDetails() {
        progressBar = ProgressDialog.show(getActivity(), "", "Please Wait ...", true, false);


        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.putOpt("Id", AppConstants.matchId);
                    jsonObject.putOpt("langID", 1);


                    getMatchDetailsReq(AppConstants.GET_MATCH_DETAILS_URL, jsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.dismiss();
                }

            }


        }).start();
    }

    private void initView(View view) {

        //ImageView
        imageView_locationMapArrow = view.findViewById(R.id.imageView_locationMapArrow);
        imageView_sportDetails = view.findViewById(R.id.imageView_gameDetails);

        Picasso.with(myContext).load(AppConstants.iconUrl)
                .into(imageView_sportDetails);
        //TextView
        textView_orginizerName = view.findViewById(R.id.textView_orginizerName);
        textView_date = view.findViewById(R.id.textView_date);
        textView_time = view.findViewById(R.id.textView_time);
        textView_locationName = view.findViewById(R.id.textView_locationName);
        textView_ageRange = view.findViewById(R.id.textView_ageRange);
        textView_playersAquired = view.findViewById(R.id.textView_playersAquired);
        textView_gender = view.findViewById(R.id.textView_gender);
        textView_playersRequired = view.findViewById(R.id.textView_playersRequired);

    }

    private String getMatchDetailsReq(String urlPost, final JSONObject jsonObject) {

        StringRequest stringRequest = null;
        final String[] resultConn = {""};
        String string_json = "";

        try {
            RequestQueue queue = Volley.newRequestQueue(this.getActivity());

            final String finalString_json = string_json;

            stringRequest = new StringRequest(Request.Method.POST, urlPost,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObj = new JSONObject(response);

                                if (jsonObj != null) {

                                    JSONObject jsonObject1 = jsonObj.getJSONObject("oMatch");

                                    String LocationName = jsonObject1.getString("LocationName");
                                    String NumOfPlayers = jsonObject1.getString("NumOfPlayers");
                                    String Total_Players = jsonObject1.getString("Total_Players");
                                    String AgeRange = jsonObject1.getString("Age");
                                    String Name = jsonObject1.getString("Name");
                                    String Gender = jsonObject1.getString("Gender");
                                    String MatchDate = jsonObject1.getString("MatchDate");
                                    String MatchTime = jsonObject1.getString("MatchTime");
                                    Longitude = jsonObject1.getString("Longitude");
                                    Latitude = jsonObject1.getString("Latitude");


                                    textView_orginizerName.setText(Name);
                                    textView_date.setText(MatchDate);
                                    textView_time.setText(MatchTime);
                                    textView_locationName.setText(LocationName);
                                    textView_gender.setText(Gender);
                                    textView_ageRange.setText(AgeRange);
                                    textView_playersAquired.setText(NumOfPlayers);
                                    textView_playersRequired.setText(Total_Players);

                                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                                        @Override
                                        public void onMapReady(GoogleMap googleMap) {
                                            mMap = googleMap;

                                            // Add a marker in Sydney, Australia, and move the camera.


                                            // Add a marker in Sydney, Australia, and move the camera.
                                            LatLng sydney = new LatLng(Double.parseDouble(Latitude), Double.parseDouble(Longitude));
                                            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Kuwait"));
                                            // For zooming automatically to the location of the marker
                                            CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                        }
                                    });



                                }


                            } catch (Exception xx) {
                                Log.e(TAG, "   " + xx.toString());
                                xx.toString();
                                progressBar.dismiss();

                            }
                            progressBar.dismiss();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    String xx = error.toString();
                    progressBar.dismiss();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();

                        }
                    });

                }
            }) {


                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<String, String>();
                    try {

                        Iterator<?> keys = jsonObject.keys();

                        while (keys.hasNext()) {
                            String key = (String) keys.next();
                            String value = jsonObject.getString(key);
                            params.put(key, value);

                        }


                    } catch (Exception xx) {
                        xx.toString();
                    }
                    return params;
                }


                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    try {

                        String jsonString = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers));

                        return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));


                    } catch (UnsupportedEncodingException e) {
                        return Response.error(new ParseError(e));
                    }
                }


            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(stringRequest);


        } catch (Exception e) {
            e.toString();
            progressBar.dismiss();

            return e.toString();
        }

        return resultConn[0];

    }


    /*@Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.


        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(Double.parseDouble(Latitude), Double.parseDouble(Longitude));
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Kuwait"));
        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
*/

}
