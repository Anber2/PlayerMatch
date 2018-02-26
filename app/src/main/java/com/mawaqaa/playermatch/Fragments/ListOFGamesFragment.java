package com.mawaqaa.playermatch.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.mawaqaa.playermatch.Activities.SignInActivity;
import com.mawaqaa.playermatch.Adapters.ListOFGamesAdapter;
import com.mawaqaa.playermatch.Constants.AppConstants;
import com.mawaqaa.playermatch.Data.ListOFGamesListData;
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

/**
 * Created by HP on 10/17/2017.
 */

public class ListOFGamesFragment extends Fragment {

    public static ArrayList<ListOFGamesListData> listOFGamesListDataArrayList;
    public static JSONObject jsonObj;
    public static String message;
    static String TAG = "ListOFGamesFragment";
    //ListOFGames
    //layout
    static ListView listView_ListOfGames;
    static ListOFGamesAdapter listOFGamesAdapter;
    static ListOFGamesListData listOFGamesListData;
    private static MainActivity myContext;
    ImageButton floatingButton_creatGame;
    SharedPrefsUtils mypref;
    String User_ID;

    public static void yourDesiredMethod() {
        myContext.loadThisFragment(new GameDetailsFragment());
        Log.d(TAG, " yourDesiredMethod ");
    }

    public static void joinGame(final String listOFGamesId) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.putOpt("Match_Id", listOFGamesId);

                    jsonObject.putOpt("User_Id", SharedPrefsUtils.getStringPreference(myContext, AppConstants.User_ID));
                    jsonObject.putOpt("langID", 1);

                    joinGameReq(AppConstants.JoinMatch, jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }).start();


    }

    public static String joinGameReq(String urlPost, final JSONObject jsonObject) {
        StringRequest stringRequest = null;
        final String[] resultConn = {""};
        String string_json = "";
        try {
            RequestQueue queue = Volley.newRequestQueue(myContext);
            final String finalString_json = string_json;
            stringRequest = new StringRequest(Request.Method.POST, urlPost,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                if (jsonObj != null) {

                                    message = jsonObj.getString("Message");

                                    Toast.makeText(myContext, message, Toast.LENGTH_LONG).show();


                                }
                            } catch (Exception xx) {
                                xx.toString();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    String xx = error.toString();

                    myContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(myContext, error.toString(), Toast.LENGTH_LONG).show();
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

    public static void handleListOfGames(JSONArray jsonArray) {

        try {

            listOFGamesListDataArrayList = new ArrayList<ListOFGamesListData>();


            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String ListOFGamesId = jsonObject.getString("Id");
                String ListOFGamesLocationName = jsonObject.getString("LocationName");
                String NumOfPlayers = jsonObject.getString("NumOfPlayers");
                String Total_Players = jsonObject.getString("Total_Players");
                String Match_Date = jsonObject.getString("MatchDate");
                String Match_time = jsonObject.getString("MatchTime");


                listOFGamesListData = new ListOFGamesListData(ListOFGamesId, Match_Date, Match_time, ListOFGamesLocationName, NumOfPlayers, Total_Players);
                listOFGamesListDataArrayList.add(listOFGamesListData);
            }

            if (myContext != null) {
                listOFGamesAdapter = new ListOFGamesAdapter(myContext, listOFGamesListDataArrayList);
                listView_ListOfGames.setAdapter(listOFGamesAdapter);
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
        View v = inflater.inflate(R.layout.fragment_list_of_games, container, false);
        User_ID = mypref.getStringPreference(myContext, AppConstants.User_ID);
        initview(v);
        setListOfGames();
        return v;
    }

    private void setListOfGames() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.putOpt("sportID", AppConstants.sportId);
                    jsonObject.putOpt("User_Id", SharedPrefsUtils.getStringPreference(getActivity(), AppConstants.User_ID));
                    jsonObject.putOpt("langID", 1);

                    getListOfGamesReq(AppConstants.GetUpcomingGame, jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }).start();


    }

    private String getListOfGamesReq(String urlPost, final JSONObject jsonObject) {

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


                                    JSONArray jsonArray = jsonObj.getJSONArray("lstMatches");

                                    handleListOfGames(jsonArray);
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

    private void initview(View v) {

        listView_ListOfGames = v.findViewById(R.id.listView_ListOfGames);
        floatingButton_creatGame = v.findViewById(R.id.floatingButton_creatGame);

        floatingButton_creatGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (PreferenceUtil.isUserSignedIn(getActivity())) {
                    myContext.loadThisFragment(new CreateGameFragment());
                } else {
                    Intent ii = new Intent(getActivity(), SignInActivity.class);
                    startActivity(ii);
                    getActivity().overridePendingTransition(0, 0);
                }

                /*Intent i = new Intent(getActivity(), CreateGameFragment.class);
                startActivity(i);
                getActivity().overridePendingTransition(0, 0);*/
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        myContext = (MainActivity) activity;
        super.onAttach(activity);
    }

}