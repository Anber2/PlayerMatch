package com.mawaqaa.playermatch.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import com.mawaqaa.playermatch.Activities.MatchListActivity;
import com.mawaqaa.playermatch.Activities.SignInActivity;
import com.mawaqaa.playermatch.Adapters.ListOFIndividualsAdapter;
import com.mawaqaa.playermatch.Constants.AppConstants;
import com.mawaqaa.playermatch.Data.ListOFIndividualsData;
import com.mawaqaa.playermatch.R;
import com.mawaqaa.playermatch.Utilities.PreferenceUtil;
import com.mawaqaa.playermatch.Utilities.SharedPrefsUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.mawaqaa.playermatch.Constants.AppConstants.User_ID;

/**
 * Created by HP on 10/17/2017.
 */

public class ListOFIndividualsFragment extends Fragment {

    //layout
    public static ListView listView_ListOfIndividuals;
    //ListOFIndividuals
    public static ListOFIndividualsAdapter listOFIndividualsAdapter;
    public static ArrayList<ListOFIndividualsData> listOFIndividualsDataArrayList;
    public static ListOFIndividualsData listOFIndividualsData;
    public static JSONObject jsonObj;
    private static MainActivity myContext;
    String TAG = "ListOFIndividualsFrag";
    ImageButton floatingButton_iWantToPlay;
    //progress par
    ProgressDialog progressBar;
    SharedPrefsUtils myPref;
    String User_Id;

    public static void yourDesiredMethod(int i) {
        myContext.loadThisFragment(new GameDetailsFragment());


    }

    public static void yourDesiredMethod2(int i) {
        Intent ii = new Intent(myContext, MatchListActivity.class);
        myContext.startActivity(ii);
        myContext.overridePendingTransition(0, 0);


    }

    public static void handleListOFIndividuals(JSONArray jsonArray) {

        try {
            listOFIndividualsDataArrayList = new ArrayList<ListOFIndividualsData>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String IndividualId = jsonObject.getString("Id");
                String IndividualName = jsonObject.getString("Name");
                String IndividualPhone = jsonObject.getString("Phone");
                String IndividualAgeRange = jsonObject.getString("AgeRangeValue");
                String IndividualDate = jsonObject.getString("Date");
                String IndividualTime = jsonObject.getString("time");


                listOFIndividualsData = new ListOFIndividualsData(IndividualId, "", IndividualName, IndividualAgeRange, IndividualPhone, IndividualDate, IndividualTime);
                listOFIndividualsDataArrayList.add(listOFIndividualsData);
            }

            if (myContext != null) {
                listOFIndividualsAdapter = new ListOFIndividualsAdapter(myContext, listOFIndividualsDataArrayList);
                listView_ListOfIndividuals.setAdapter(listOFIndividualsAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myContext = (MainActivity) this.getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_of_individuals, container, false);
        User_Id = myPref.getStringPreference(myContext, AppConstants.User_ID);
        initView(v);

        setListOFIndividuals();

        return v;
    }

    private void setListOFIndividuals() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.putOpt("User_Id", SharedPrefsUtils.getStringPreference(getActivity(), User_ID));
                    jsonObject.putOpt("sportID", AppConstants.sportId);
                    jsonObject.putOpt("langID", 1);

                    getListOfIndividualsReq(AppConstants.GET_LIST_OF_INDIVIDUALS_URL, jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }).start();
    }

    private String getListOfIndividualsReq(String urlPost, final JSONObject jsonObject) {

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
                                jsonObj = new JSONObject(response);

                                if (jsonObj != null) {

                                    JSONArray jsonArray = jsonObj.getJSONArray("lstUserIndividuals");

                                    handleListOFIndividuals(jsonArray);


                                }


                            } catch (Exception xx) {
                                xx.toString();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    String xx = error.toString();
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
            return e.toString();
        }
        return resultConn[0];
    }

    private void initView(View v) {

        listView_ListOfIndividuals = v.findViewById(R.id.listView_ListOfIndividuals);
        floatingButton_iWantToPlay = v.findViewById(R.id.floatingButton_iWantToPlay);

        floatingButton_iWantToPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (PreferenceUtil.isUserSignedIn(getActivity())) {
                    loadThisFragment(new WantToPlayFragment());
                } else {
                    Intent ii = new Intent(getActivity(), SignInActivity.class);
                    startActivity(ii);
                    getActivity().overridePendingTransition(0, 0);
                }


            }
        });

    }

    private void sendIWantToPlayRequest() {
        progressBar = ProgressDialog.show(getActivity(), "", "Please Wait ...", true, false);

        new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.putOpt("Id", 3);
                    jsonObject.putOpt("Status", 4);


                    sendIWantToPlayRequestReq(AppConstants.I_WANT_TO_PLAY_URL, jsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.dismiss();
                }
            }
        }).start();

    }

    private String sendIWantToPlayRequestReq(String urlPost, final JSONObject jsonObject) {

        StringRequest stringRequest = null;
        final String[] resultConn = {""};
        String string_json = "";

        try {
            final RequestQueue queue = Volley.newRequestQueue(this.getActivity());

            final String finalString_json = string_json;

            stringRequest = new StringRequest(Request.Method.POST, urlPost,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObj = new JSONObject(response);

                                if (jsonObj != null) {

                                    Toast.makeText(getActivity(), jsonObj.getString("Message").toString(), Toast.LENGTH_SHORT).show();

                                }


                            } catch (Exception xx) {
                                Log.e(TAG, "" + xx.toString());
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
            progressBar.dismiss();

            e.toString();
            return e.toString();
        }

        return resultConn[0];

    }


    @Override
    public void onAttach(Activity activity) {
        myContext = (MainActivity) activity;
        super.onAttach(activity);
    }

    public void loadThisFragment(Fragment fragment) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }


}
