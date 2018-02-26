package com.mawaqaa.playermatch.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
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
import com.mawaqaa.playermatch.Adapters.SportsIPlayListAdapter;
import com.mawaqaa.playermatch.Constants.AppConstants;
import com.mawaqaa.playermatch.Data.SportsIPlayListData;
import com.mawaqaa.playermatch.Fragments.MyProfileFragment;
import com.mawaqaa.playermatch.Fragments.SignUpFragment;
import com.mawaqaa.playermatch.R;
import com.mawaqaa.playermatch.Utilities.PreferenceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.mawaqaa.playermatch.Adapters.SportsIPlayListAdapter.status;
import static com.mawaqaa.playermatch.Constants.AppConstants.currentClass;

/**
 * Created by HP on 11/6/2017.
 */

public class SportsIPlayActivity extends Activity {

    static String TAG = "SportsIPlayActivity";

    //layout
    ListView graidView_myProfile_sportsIPlay;
    Button button_myProfile_save;
    //SportsIPlay
    SportsIPlayListAdapter sportsIPlayListAdapter;
    ArrayList<SportsIPlayListData> sportsIPlayListDataArrayList;
    SportsIPlayListData sportsIPlayListData;
    List<String> sportIPlayId;
    //ProgressDialog
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_i_play);

        initView();
        setSportsIPlayListData();

    }

    private void initView() {

        //graidView
        graidView_myProfile_sportsIPlay = findViewById(R.id.graidView_myProfile_sportsIPlay);

        //button
        button_myProfile_save = findViewById(R.id.button_myProfile_save);

        button_myProfile_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSportsIPlay();


            }
        });


    }

    private void getSportsIPlay() {


        sportIPlayId = new ArrayList<String>();
        int listPosition = sportsIPlayListDataArrayList.size();

        String sportName = "";

        for (int t = 0; t < listPosition; t++) {
            if (status.get(t).equals(true)) {


                String sportId = sportsIPlayListDataArrayList.get(t).getSportID();

                sportName = sportName + "  " + sportsIPlayListDataArrayList.get(t).getSportName();

                sportIPlayId.add(sportId);

            }
        }

        PreferenceUtil.setSportsIPlay(SportsIPlayActivity.this, "" + sportIPlayId);

        if(currentClass.equalsIgnoreCase("SignUpFragment")){
            SignUpFragment.editText_signUp_sportsIPlay.setText(sportName);
        }else
       if(currentClass.equalsIgnoreCase("MyProfileFragment")){
           MyProfileFragment.editText_myProfile_sportsIPlay.setText(sportName);
       }


       this.finish();
    }


    private void setSportsIPlayListData() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject registrationJsonObject = new JSONObject();


                    registrationJsonObject.putOpt("langID", 1);

                    getSportsIPlayListReq(AppConstants.GET_SPORTS_I_PLAY_URL, registrationJsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }).start();


    }

    private String getSportsIPlayListReq(String urlPost, final JSONObject jsonObject) {

        StringRequest stringRequest = null;
        final String[] resultConn = {""};
        String string_json = "";

        try {
            RequestQueue queue = Volley.newRequestQueue(SportsIPlayActivity.this);

            final String finalString_json = string_json;

            stringRequest = new StringRequest(Request.Method.POST, urlPost,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObj = new JSONObject(response);

                                if (jsonObj != null) {
                                    JSONArray jsonArray = jsonObj.getJSONArray("lstSportType");

                                    sportsIPlayListDataArrayList = new ArrayList<SportsIPlayListData>();
                                    status = new ArrayList<Boolean>();


                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        String sportId = jsonObject.getString("Id");
                                        String sportName = jsonObject.getString("Name");

                                        sportsIPlayListData = new SportsIPlayListData(sportId, sportName);
                                        sportsIPlayListDataArrayList.add(sportsIPlayListData);
                                    }

                                    if (SportsIPlayActivity.this != null) {
                                        sportsIPlayListAdapter = new SportsIPlayListAdapter(SportsIPlayActivity.this, sportsIPlayListDataArrayList);
                                        graidView_myProfile_sportsIPlay.setAdapter(sportsIPlayListAdapter);
                                    }

                                }


                            } catch (Exception xx) {
                                Log.e(TAG, "   " + xx.toString());
                                xx.toString();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    String xx = error.toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SportsIPlayActivity.this, error.toString(), Toast.LENGTH_LONG).show();

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
            progressBar.dismiss();
            e.toString();
            return e.toString();
        }

        return resultConn[0];

    }


}
