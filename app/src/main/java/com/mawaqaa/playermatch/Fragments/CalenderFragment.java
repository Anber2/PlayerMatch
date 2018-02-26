package com.mawaqaa.playermatch.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.mawaqaa.playermatch.Activities.MainActivity;
import com.mawaqaa.playermatch.Adapters.GameListAdapter;
import com.mawaqaa.playermatch.Constants.AppConstants;
import com.mawaqaa.playermatch.Data.GameListData;
import com.mawaqaa.playermatch.R;
import com.mawaqaa.playermatch.Utilities.SharedPrefsUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import static com.mawaqaa.playermatch.Constants.AppConstants.User_ID;

/**
 * Created by HP on 10/11/2017.
 */

public class CalenderFragment extends Fragment implements View.OnClickListener {

    //isCreated
    public static boolean isCreatedGamesCalled = false;
    private static MainActivity myContext;
    String TAG = "CalenderFragment";
    //Calendar
    MaterialCalendarView calendarView;
    HashSet<CalendarDay> calendarGamesCreated, calendarGamesJoined, calendarGamesPending;
    //layout
    ListView listView_game_General;
    TextView imageView_pendingGames, imageView_joinedGames, imageView_createdGames;
    //Games
    GameListAdapter gameListAdapter;
    ArrayList<GameListData> gameListDataArrayList;
    GameListData gameListData;
    //progress par
    ProgressDialog progressBar;

    public static void yourDesiredMethod(int i) {
        myContext.loadThisFragment(new PlayersListFragment());


    }

    public static void yourDesiredMethod2(final String i) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.putOpt("MatchId", i);
                    jsonObject.putOpt("langID", 1);


                    deleteGameReq(AppConstants.API_CALENDAR_GAMES_EVENTS_URL, jsonObject);

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).start();

    }

    public static String deleteGameReq(String urlPost, final JSONObject jsonObject) {

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myContext = (MainActivity) this.getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calender, container, false);

        initView(v);
        SignInSignUpFragment.FragTAG = TAG;
        //handleToolBarinSingIn();

        getCalenderGameEvents();

        //getGamesCreatedList();

        return v;
    }

    private void getCalenderGameEvents() {

        progressBar = ProgressDialog.show(getActivity(), "", "Please Wait ...", true, false);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.putOpt("User_Id", SharedPrefsUtils.getStringPreference(getActivity(), User_ID));
                    jsonObject.putOpt("langID", 1);


                    getCalenderGamesEventsReq(AppConstants.API_CALENDAR_GAMES_EVENTS_URL, jsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.dismiss();

                }
            }
        }).start();

    }

    private String getCalenderGamesEventsReq(String urlPost, final JSONObject jsonObject) {

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

                                    calendarGamesCreated = new HashSet<CalendarDay>();
                                    calendarGamesJoined = new HashSet<CalendarDay>();
                                    calendarGamesPending = new HashSet<CalendarDay>();

                                    CalendarDay calendarDay = new CalendarDay();

                                    JSONArray gamesCreatedJsonArray = jsonObj.getJSONArray("lstCreatedMatches");

                                    for (int i = 0; i < gamesCreatedJsonArray.length(); i++) {

                                        JSONObject jsonObject = gamesCreatedJsonArray.getJSONObject(i);
                                        int gamesCreatedDay = jsonObject.getInt("day");
                                        int gamesCreatedMonth = jsonObject.getInt("month");
                                        int gamesCreatedYear = jsonObject.getInt("year");

                                        calendarDay = new CalendarDay(gamesCreatedYear, gamesCreatedMonth - 1, gamesCreatedDay);

                                        calendarGamesCreated.add(calendarDay);
                                    }


                                    JSONArray gamesJoinedJsonArray = jsonObj.getJSONArray("lstJoinedMatches");

                                    for (int i = 0; i < gamesJoinedJsonArray.length(); i++) {

                                        JSONObject jsonObject = gamesJoinedJsonArray.getJSONObject(i);
                                        int gamesCreatedDay = jsonObject.getInt("day");
                                        int gamesCreatedMonth = jsonObject.getInt("month");
                                        int gamesCreatedYear = jsonObject.getInt("year");

                                        calendarGamesJoined.add(new CalendarDay(gamesCreatedYear, gamesCreatedMonth - 1, gamesCreatedDay));

                                    }

                                    JSONArray gamesPendingJsonArray = jsonObj.getJSONArray("lstPendingMatches");

                                    for (int i = 0; i < gamesPendingJsonArray.length(); i++) {

                                        JSONObject jsonObject = gamesPendingJsonArray.getJSONObject(i);
                                        int gamesCreatedDay = jsonObject.getInt("day");
                                        int gamesCreatedMonth = jsonObject.getInt("month");
                                        int gamesCreatedYear = jsonObject.getInt("year");

                                        calendarGamesPending.add(new CalendarDay(gamesCreatedYear, gamesCreatedMonth - 1, gamesCreatedDay));

                                    }


                                    setCalendarEvent();

                                    getGamesCreatedList();

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

    private void setCalendarEvent() {

        Calendar instance = Calendar.getInstance();

        calendarView.setSelectedDate(instance.getTime());


        calendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return calendarGamesCreated.contains(day);
            }

            @Override
            public void decorate(DayViewFacade view) {
                // view.addSpan(new DotSpan(10, color));
                //view.setSelectionDrawable(getResources().getDrawable(R.drawable.radio2x));
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.calender_cercle_yallo));
            }
        });

        calendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return calendarGamesJoined.contains(day);
            }

            @Override
            public void decorate(DayViewFacade view) {
                //view.addSpan(new DotSpan(10, color));
                //view.setSelectionDrawable(getResources().getDrawable(R.drawable.radio2x));
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.calender_cercle_gray));
            }
        });

        calendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return calendarGamesPending.contains(day);
            }

            @Override
            public void decorate(DayViewFacade view) {
                // view.addSpan(new DotSpan(10, color));
                //view.setSelectionDrawable(getResources().getDrawable(R.drawable.radio2x));
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.calender_cercle_blue));
            }
        });

        progressBar.dismiss();

    }

    private void initView(View v) {

        //calender
        calendarView = v.findViewById(R.id.calendarView);
        //listView
        listView_game_General = v.findViewById(R.id.listView_game_General);
        //imageView
        imageView_pendingGames = v.findViewById(R.id.imageView_pendingGames);
        imageView_joinedGames = v.findViewById(R.id.imageView_joinedGames);
        imageView_createdGames = v.findViewById(R.id.imageView_createdGames);

        //on click
        imageView_pendingGames.setOnClickListener(this);
        imageView_joinedGames.setOnClickListener(this);
        imageView_createdGames.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.imageView_createdGames:

                //Toast.makeText(getActivity(), "Games Pending list is being called", Toast.LENGTH_SHORT).show();
                getGamesCreatedList();

                break;

            case R.id.imageView_joinedGames:
                getGamesJoinedList();

                break;

            case R.id.imageView_pendingGames:

                getGamesPendingList();

                break;

            default:
                break;
        }

    }

    private void getGamesCreatedList() {

        isCreatedGamesCalled = true;


        progressBar = ProgressDialog.show(getActivity(), "", "Please Wait ...", true, false);

        new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.putOpt("User_Id", SharedPrefsUtils.getStringPreference(getActivity(), User_ID));

                    jsonObject.putOpt("langID", 1);


                    getGamesCreatedListReq(AppConstants.GetCreatedGame, jsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.dismiss();
                }
            }
        }).start();
    }

    private void getGamesJoinedList() {
        isCreatedGamesCalled = false;


        progressBar = ProgressDialog.show(getActivity(), "", "Please Wait ...", true, false);

        new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.putOpt("User_Id", SharedPrefsUtils.getStringPreference(getActivity(), User_ID));


                    getGamesJoinedListReq(AppConstants.GAMES_JOINED_LIST_URL, jsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.dismiss();
                }
            }
        }).start();
    }

    private void getGamesPendingList() {

        isCreatedGamesCalled = false;


        progressBar = ProgressDialog.show(getActivity(), "", "Please Wait ...", true, false);

        new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.putOpt("User_Id", SharedPrefsUtils.getStringPreference(getActivity(), User_ID));


                    getGamesPendingListReq(AppConstants.GAMES_PENDING_LIST_URL, jsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.dismiss();
                }
            }
        }).start();
    }

    private String getGamesCreatedListReq(String urlPost, final JSONObject jsonObject) {

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
                                    if (getActivity() != null) {
                                        gameListAdapter = new GameListAdapter(myContext, gameListDataArrayList);

                                        listView_game_General.setAdapter(gameListAdapter);
                                        progressBar.dismiss();

                                    }
                                    progressBar.dismiss();

                                }
                                progressBar.dismiss();


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
            progressBar.dismiss();

            e.toString();
            return e.toString();
        }

        return resultConn[0];

    }

    private String getGamesJoinedListReq(String urlPost, final JSONObject jsonObject) {

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

                                    gameListDataArrayList = new ArrayList<GameListData>();

                                    JSONArray jsonArray = jsonObj.getJSONArray("lstMatches");

                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        String gameID = jsonObject.getString("Id");
                                        String sportName = jsonObject.getString("Name");
                                        String sportDate = jsonObject.getString("MatchDate");
                                        String sportLocationName = jsonObject.getString("LocationName");
                                        String DaysToGo = jsonObject.getString("dayToDo");

                                        gameListData = new GameListData(gameID, sportName, sportDate, sportLocationName, DaysToGo);
                                        gameListDataArrayList.add(gameListData);

                                    }
                                    if (getActivity() != null) {
                                        gameListAdapter = new GameListAdapter(myContext, gameListDataArrayList);

                                        listView_game_General.setAdapter(gameListAdapter);
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

    private String getGamesPendingListReq(String urlPost, final JSONObject jsonObject) {

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

                                    gameListDataArrayList = new ArrayList<GameListData>();

                                    JSONArray jsonArray = jsonObj.getJSONArray("lstMatches");

                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        String gameID = jsonObject.getString("Id");
                                        String sportName = jsonObject.getString("Name");
                                        String sportDate = jsonObject.getString("MatchDate");
                                        String sportLocationName = jsonObject.getString("LocationName");
                                        String DaysToGo = jsonObject.getString("dayToDo");

                                        gameListData = new GameListData(gameID, sportName, sportDate, sportLocationName, DaysToGo);
                                        gameListDataArrayList.add(gameListData);

                                    }
                                    if (getActivity() != null) {
                                        gameListAdapter = new GameListAdapter(myContext, gameListDataArrayList);

                                        listView_game_General.setAdapter(gameListAdapter);
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
