package com.mawaqaa.playermatch.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
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
import com.mawaqaa.playermatch.Adapters.SelectMatchListAdapter;
import com.mawaqaa.playermatch.Constants.AppConstants;
import com.mawaqaa.playermatch.Data.GameListData;
import com.mawaqaa.playermatch.R;
import com.mawaqaa.playermatch.Utilities.SharedPrefsUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.mawaqaa.playermatch.Adapters.ListOFIndividualsAdapter.IndividualId;
import static com.mawaqaa.playermatch.Adapters.SelectMatchListAdapter.selectedMatchId;
import static com.mawaqaa.playermatch.Constants.AppConstants.User_ID;

/**
 * Created by HP on 1/16/2018.
 */

public class MatchListActivity extends AppCompatActivity {

    //layOut
    ListView listView_selectMatchList;
    TextView textView_noMatch;
    Button button_invitePlayer_selectMatch;

    //Games
    SelectMatchListAdapter selectMatchListAdapter;
    ArrayList<GameListData> gameListDataArrayList;
    GameListData gameListData;
    //progress par
    ProgressDialog progressBar;


    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);

        initView();

        getGamesCreatedList();
    }


    private String getGamesCreatedListReq(String urlPost, final JSONObject jsonObject) {

        StringRequest stringRequest = null;
        final String[] resultConn = {""};
        String string_json = "";

        try {
            final RequestQueue queue = Volley.newRequestQueue(this);

            final String finalString_json = string_json;

            stringRequest = new StringRequest(Request.Method.POST, urlPost,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObj = new JSONObject(response);

                                if (jsonObj != null) {

                                    gameListDataArrayList = new ArrayList<GameListData>();

                                    JSONArray jsonArray = jsonObj.getJSONArray("lstMatches");

                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        String gameID = jsonObject.getString("Id");
                                        String sportName = jsonObject.getString("Name");
                                        String sportDate = jsonObject.getString("MatchDate");
                                        String sportLocationName = jsonObject.getString("LocationName");
                                        String daysToGo = jsonObject.getString("dayToDo");

                                        gameListData = new GameListData(gameID, sportName, sportDate, sportLocationName, daysToGo);
                                        gameListDataArrayList.add(gameListData);

                                    }
                                    if (this != null) {
                                        selectMatchListAdapter = new SelectMatchListAdapter(MatchListActivity.this, gameListDataArrayList);

                                        listView_selectMatchList.setAdapter(selectMatchListAdapter);
                                    }

                                }


                            } catch (Exception xx) {
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

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MatchListActivity.this, error.toString(), Toast.LENGTH_LONG).show();

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


    private void initView() {

        //listView
        listView_selectMatchList = findViewById(R.id.listView_selectMatchList);

        //TextView
        textView_noMatch = findViewById(R.id.textView_noMatch);

        //button
        button_invitePlayer_selectMatch = findViewById(R.id.button_invitePlayer_selectMatch);


        //on click
        button_invitePlayer_selectMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                invitePlayer();

            }
        });

    }

    private void invitePlayer() {
        progressBar = ProgressDialog.show(this, "", "Please Wait ...", true, false);

        new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.putOpt("IndividualId", IndividualId);
                    jsonObject.putOpt("MatchId", selectedMatchId);
                    jsonObject.putOpt("User_Id", SharedPrefsUtils.getStringPreference(MatchListActivity.this, User_ID));
                    jsonObject.putOpt("langID", 1);

                    invitePlayerReq(AppConstants.invitePlayer, jsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.dismiss();
                }
            }
        }).start();

    }


    private String invitePlayerReq(String urlPost, final JSONObject jsonObject) {

        StringRequest stringRequest = null;
        final String[] resultConn = {""};
        String string_json = "";

        try {
            final RequestQueue queue = Volley.newRequestQueue(this);

            final String finalString_json = string_json;

            stringRequest = new StringRequest(Request.Method.POST, urlPost,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObj = new JSONObject(response);

                                if (jsonObj != null) {

                                    Toast.makeText(MatchListActivity.this, jsonObj.getString("Message").toString(), Toast.LENGTH_SHORT).show();


                                    progressBar.dismiss();

                                }

                                progressBar.dismiss();

                            } catch (Exception xx) {
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

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MatchListActivity.this, error.toString(), Toast.LENGTH_LONG).show();

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


    private void getGamesCreatedList() {
        progressBar = ProgressDialog.show(this, "", "Please Wait ...", true, false);

        new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.putOpt("User_Id", SharedPrefsUtils.getStringPreference(MatchListActivity.this, User_ID));

                    jsonObject.putOpt("langID", 1);


                    getGamesCreatedListReq(AppConstants.GetCreatedGame, jsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.dismiss();
                }
            }
        }).start();
    }


}
