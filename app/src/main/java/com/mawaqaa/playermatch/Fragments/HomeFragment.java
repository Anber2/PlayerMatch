package com.mawaqaa.playermatch.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.mawaqaa.playermatch.Activities.MainActivity;
import com.mawaqaa.playermatch.Adapters.SportsListAdapter;
import com.mawaqaa.playermatch.Constants.AppConstants;
import com.mawaqaa.playermatch.Data.SportsListData;
import com.mawaqaa.playermatch.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.mawaqaa.playermatch.Fragments.SignInSignUpFragment.FragTAG;

/**
 * Created by HP on 10/9/2017.
 */

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static MainActivity myContext;
    String TAG = "HomeFragment";
    //Layout contents
    ListView listView_sportsList;
    //SportsLis
    SportsListAdapter sportsListAdapter;
    ArrayList<SportsListData> sportsListDataArrayList;
    //
    SportsListData sportsListData;
    ProgressDialog progressBar;

    public static String getDeviceID() throws Throwable {
       /* String deviceId;
        final TelephonyManager mTelephony = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephony.getDeviceId() != null) {
            deviceId = mTelephony.getDeviceId();
        } else {
            deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        }*/

        String deviceId = Settings.Secure.getString(myContext.getContentResolver(), Settings.Secure.ANDROID_ID);


        return deviceId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        initView(v);

        setSportsListData();
        FragTAG = TAG;
        //handleToolBarinSingIn();
        return v;
    }

    private void setSportsListData() {
        progressBar = ProgressDialog.show(getActivity(), "", "Please Wait ...", true, false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject registrationJsonObject = new JSONObject();
                    registrationJsonObject.putOpt("langID", 1);
                    getSportsIPlayListReq(AppConstants.GET_SPORTS_I_PLAY_URL, registrationJsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.dismiss();
                }
            }
        }).start();
    }

    private void initView(View v) {

        //listView
        listView_sportsList = v.findViewById(R.id.listView_sportsList);


        //on Click
        listView_sportsList.setOnItemClickListener(this);

    }

    private String getSportsIPlayListReq(String urlPost, final JSONObject jsonObject) {
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
                                Log.d(TAG, response);
                                if (jsonObj != null) {
                                    JSONArray jsonArray = jsonObj.getJSONArray("lstSportType");
                                    sportsListDataArrayList = new ArrayList<SportsListData>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        String sportId = jsonObject.getString("Id");
                                        String sportName = jsonObject.getString("Name");
                                        String sportImage = jsonObject.getString("ImagePath");
                                        Boolean isPostion = jsonObject.getBoolean("isPosition");
                                        String Icon = jsonObject.getString("Icon");
                                        JSONArray lstPostion = jsonObject.getJSONArray("lstPosition");

                                        sportsListData = new SportsListData(sportId, sportImage, sportName, isPostion, Icon, lstPostion);
                                        sportsListDataArrayList.add(sportsListData);
                                    }
                                    if (getActivity() != null) {
                                        sportsListAdapter = new SportsListAdapter(getActivity(), sportsListDataArrayList);
                                        listView_sportsList.setAdapter(sportsListAdapter);
                                    }
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
                        progressBar.dismiss();

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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AppConstants.sportId = sportsListDataArrayList.get(i).getSportID();
        AppConstants.sportName = sportsListDataArrayList.get(i).getSportName();

        AppConstants.sportImage = sportsListDataArrayList.get(i).getSportImage();
        AppConstants.isPostion = sportsListDataArrayList.get(i).getPostion();
        AppConstants.lstPostion = sportsListDataArrayList.get(i).getLstPostion();
        AppConstants.iconUrl = sportsListDataArrayList.get(i).getIcon();

        loadThisFragment(new SportsListDetailsFragment());
    }

    public void loadThisFragment(Fragment fragment) {

      /*  FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();*/


        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}