package com.mawaqaa.playermatch.Fragments;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
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
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.mawaqaa.playermatch.Activities.SelectPlaceActivity;
import com.mawaqaa.playermatch.Adapters.AgeRangeListAdapter;
import com.mawaqaa.playermatch.Adapters.LocationListAdapter;
import com.mawaqaa.playermatch.Adapters.ScheduleTimeListAdapter;
import com.mawaqaa.playermatch.Constants.AppConstants;
import com.mawaqaa.playermatch.Data.AgeRangeListData;
import com.mawaqaa.playermatch.Data.LocationListData;
import com.mawaqaa.playermatch.Data.ScheduleTimeListData;
import com.mawaqaa.playermatch.R;
import com.mawaqaa.playermatch.Utilities.PreferenceUtil;
import com.mawaqaa.playermatch.Utilities.SharedPrefsUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.mawaqaa.playermatch.Constants.AppConstants.User_ID;
import static com.mawaqaa.playermatch.Fragments.HomeFragment.myContext;

/**
 * Created by HP on 10/17/2017.
 */

public class CreateGameFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    static String TAG = "ListOFGamesFragment";

    //Layout
    EditText editText_CreateGame_date, editText_CreateGame_selectTime, editText_CreateGame_playersRequired, editText_CreateGame_totalPlayers, editText_CreateGame_title;
    Spinner spinner_CreateGame_schedule, spinner_CreateGame_ageRanges, spinner_CreatGamelocation;
    RadioButton radioButton_CreateGame_male, radioButton_CreateGame_femail, radioButton_CreateGame_noGender;
    RadioGroup radioGroup_gendor;
    Button button_CreateGame;
    ImageView imageView_creatGame;
    //calender
    Calendar myCalendar, mcurrentTime;

    //Schedule
    ScheduleTimeListAdapter scheduleTimeListAdapter;
    ArrayList<ScheduleTimeListData> scheduleTimeListDataArrayList;

    //Age Range
    AgeRangeListAdapter ageRangeListAdapter;
    ArrayList<AgeRangeListData> ageRangeListDataArrayList;
    AgeRangeListData ageRangeListData;
    String ageRangeId;

    //Location
    ArrayList<LocationListData> locationListDataArrayList;
    LocationListAdapter locationListAdapter;
    LocationListData locationListData;
    String locationId;

    //google AUTOCOMPLETE places
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    PlaceAutocompleteFragment place_autocomplete_fragment;
    //progress par
    ProgressDialog progressBar;

    //Values
    String Date, Time;
    int indexGender;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_game, container, false);
        initView(v);
        //setListOfGames();

        setScheduleTimeSpinner();
        setAgeRangeListData();
        getLocation();

        //setLocationsList();
        //callPlaceAutocompleteActivityIntent();
        return v;
    }

    private void getLocation() {
        progressBar = ProgressDialog.show(getActivity(), "", "Please Wait ...", true, false);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject jsonObject = new JSONObject();


                    getLocationReq(AppConstants.Getlocation, jsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.dismiss();

                }
            }
        }).start();
    }

    private String getLocationReq(String urlPost, final JSONObject jsonObject) {
        StringRequest stringRequest = null;
        final String[] resultConn = {""};
        String string_json = "";
        try {
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            final String finalString_json = string_json;
            stringRequest = new StringRequest(Request.Method.POST, urlPost,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                Log.d(TAG, response);
                                if (jsonArray != null) {

                                    locationListDataArrayList = new ArrayList<LocationListData>();
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        String Id = jsonObject.getString("Id");
                                        String Name = jsonObject.getString("Name");

                                        locationListData = new LocationListData(Id, Name);
                                        locationListDataArrayList.add(locationListData);
                                    }

                                    if (this != null) {

                                        locationListAdapter = new LocationListAdapter(getActivity(), locationListDataArrayList);
                                        spinner_CreatGamelocation.setAdapter(locationListAdapter);

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
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.editText_CreateGame_date:

                setDatePickerDialog();

                break;

            case R.id.editText_CreateGame_selectTime:

                setTimePickerDialog();

                break;

            case R.id.button_CreateGame:

                if (createGameAuthentication()) {
                    createGameInitiate();
                }

                break;

            default:
                break;


        }
    }

    private boolean createGameAuthentication() {


        if (editText_CreateGame_title.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter title!", Toast.LENGTH_SHORT).show();
            editText_CreateGame_title.requestFocus();
            return false;
        }

        if (editText_CreateGame_date.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter date!", Toast.LENGTH_SHORT).show();
            editText_CreateGame_date.requestFocus();
            return false;
        }
        if (editText_CreateGame_selectTime.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter time!", Toast.LENGTH_SHORT).show();
            editText_CreateGame_selectTime.requestFocus();
            return false;
        }
        if (editText_CreateGame_playersRequired.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter players required!", Toast.LENGTH_SHORT).show();
            editText_CreateGame_playersRequired.requestFocus();
            return false;
        }
        if (editText_CreateGame_totalPlayers.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter total players!", Toast.LENGTH_SHORT).show();
            editText_CreateGame_totalPlayers.requestFocus();
            return false;
        }


        return true;
    }

    private void createGameInitiate() {
        progressBar = ProgressDialog.show(getActivity(), "", "Please Wait ...", true, false);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.putOpt("User_Id", SharedPrefsUtils.getStringPreference(getActivity(), User_ID));
                    jsonObject.putOpt("MatchDate", editText_CreateGame_date.getText().toString());
                    jsonObject.putOpt("MatchTime", editText_CreateGame_selectTime.getText().toString());
                    jsonObject.putOpt("Schedule", getSelectedSchedule());
                    jsonObject.putOpt("Age", getAgeRangeIdSelected());
                    jsonObject.putOpt("Location_Id", locationId);
                    jsonObject.putOpt("Longitude", PreferenceUtil.getGameLongitude(getActivity()));
                    jsonObject.putOpt("Latitude", PreferenceUtil.getGameLatitude(getActivity()));
                    jsonObject.putOpt("LocationName", PreferenceUtil.getGameAddress(getActivity()));
                    jsonObject.putOpt("Gender", getCheckedGenderId());
                    jsonObject.putOpt("Sport_Type_Id", AppConstants.sportId);
                    jsonObject.putOpt("NumOfPlayers", editText_CreateGame_totalPlayers.getText().toString());
                    jsonObject.putOpt("Total_Players", editText_CreateGame_playersRequired.getText().toString());
                    jsonObject.putOpt("Name", editText_CreateGame_title.getText().toString());
                    jsonObject.putOpt("Schedule", getSelectedSchedule());


                    createGameReq(AppConstants.GET_API_CREATE_MATCH_URL, jsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.dismiss();

                }
            }
        }).start();
    }

    private String createGameReq(String urlPost, final JSONObject jsonObject) {
        StringRequest stringRequest = null;
        final String[] resultConn = {""};
        String string_json = "";
        try {
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            final String finalString_json = string_json;
            stringRequest = new StringRequest(Request.Method.POST, urlPost,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject object = new JSONObject(response);
                                Log.d(TAG, response);
                                if (object != null) {

                                    Toast.makeText(getActivity(), object.getString("Message"), Toast.LENGTH_SHORT).show();

                                    String isSuccess = object.getString("IsSuccess");


                                    Toast.makeText(getActivity(), object.getString("Message"), Toast.LENGTH_SHORT).show();

                                    if (isSuccess.equalsIgnoreCase("true")) {

                                        progressBar.dismiss();

                                         loadThisFragment(new SportsListDetailsFragment());

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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (adapterView.getId()) {

            case R.id.spinner_CreatGamelocation:

                getSelectedLocation();

                break;

            case R.id.spinner_CreateGame_ageRanges:
                getAgeRangeIdSelected();

                break;


            case R.id.spinner_CreateGame_schedule:
                getSelectedSchedule();

                break;

            default:
                break;
        }

    }

    private String getAgeRangeIdSelected() {

        int position = spinner_CreateGame_ageRanges.getSelectedItemPosition();
        ageRangeListData = ageRangeListDataArrayList.get(position);
        ageRangeId = ageRangeListData.getAgeRangeID();

        return ageRangeId;
    }

    private String getSelectedLocation() {

        int locationPos = spinner_CreatGamelocation.getSelectedItemPosition();
        locationId = locationListDataArrayList.get(locationPos).getLocationId();

        if (locationId.equals("-1")) {
            Intent i = new Intent(getActivity(), SelectPlaceActivity.class);
            startActivity(i);
            getActivity().overridePendingTransition(0, 0);
        }

        return locationId;
    }

    private String getSelectedSchedule() {

        int position = spinner_CreateGame_schedule.getSelectedItemPosition();

        String scheduleId = scheduleTimeListDataArrayList.get(position).getTimeId();

        return scheduleId;
    }

    private boolean checkValue() {
        if (editText_CreateGame_date.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            editText_CreateGame_date.requestFocus();
            return false;
        }
        if (!validateTime()) {
            return false;
        }
        //sendPlayerJoiningData();
        return true;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";

        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);


        editText_CreateGame_date.setText(sdf.format(myCalendar.getTime()));
    }

    private void setTimePickerDialog() {
        mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String am_pm = (selectedHour < 12) ? "AM" : "PM";
                String selected_Hour = "" + selectedHour;
                String selected_Minute = "" + selectedMinute;

                if (selectedHour < 10) {
                    selected_Hour = "0" + selectedHour;
                }
                if (selectedMinute < 10) {
                    selected_Minute = "0" + selectedMinute;
                }
                if (selectedHour == 00) {
                    selected_Hour = "" + 12;
                }


                editText_CreateGame_selectTime.setText(selected_Hour + ":" + selected_Minute + " " + am_pm);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void setDatePickerDialog() {
        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }

        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void setAgeRangeListData() {


        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.putOpt("langID", 1);


                    getAgeRangeListReq(AppConstants.GetAgeRange, jsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.dismiss();

                }
            }
        }).start();


    }

    private String getAgeRangeListReq(String urlPost, final JSONObject jsonObject) {
        StringRequest stringRequest = null;
        final String[] resultConn = {""};
        String string_json = "";
        try {
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            final String finalString_json = string_json;
            stringRequest = new StringRequest(Request.Method.POST, urlPost,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray object = new JSONArray(response);
                                Log.d(TAG, response);


                                if (object != null) {


                                    ageRangeListDataArrayList = new ArrayList<AgeRangeListData>();


                                    for (int i = 0; i < object.length(); i++) {

                                        JSONObject jsonObject = object.getJSONObject(i);

                                        String ageId = jsonObject.getString("Id");
                                        String ageValue = jsonObject.getString("Value");

                                        ageRangeListData = new AgeRangeListData(ageId, ageValue);
                                        ageRangeListDataArrayList.add(ageRangeListData);
                                    }
                                    if (getActivity() != null) {


                                        ageRangeListAdapter = new AgeRangeListAdapter(getActivity(), ageRangeListDataArrayList);

                                        spinner_CreateGame_ageRanges.setAdapter(ageRangeListAdapter);
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

    private void setScheduleTimeSpinner() {

        scheduleTimeListDataArrayList = new ArrayList<ScheduleTimeListData>();

        scheduleTimeListDataArrayList.add(new ScheduleTimeListData("0", "Time 1"));
        scheduleTimeListDataArrayList.add(new ScheduleTimeListData("1", "Time 2"));
        scheduleTimeListDataArrayList.add(new ScheduleTimeListData("2", "Time 3"));
        scheduleTimeListDataArrayList.add(new ScheduleTimeListData("3", "Time 4"));
        scheduleTimeListDataArrayList.add(new ScheduleTimeListData("4", "Time 5"));

        scheduleTimeListAdapter = new ScheduleTimeListAdapter(getActivity(), scheduleTimeListDataArrayList);
        spinner_CreateGame_schedule.setAdapter(scheduleTimeListAdapter);
    }

    private void initView(View view) {

        //editText
        editText_CreateGame_date = view.findViewById(R.id.editText_CreateGame_date);
        editText_CreateGame_selectTime = view.findViewById(R.id.editText_CreateGame_selectTime);
        editText_CreateGame_playersRequired = view.findViewById(R.id.editText_CreateGame_playersRequired);
        editText_CreateGame_totalPlayers = view.findViewById(R.id.editText_CreateGame_totalPlayers);
        editText_CreateGame_title = view.findViewById(R.id.editText_CreateGame_title);

        //Spinner
        spinner_CreateGame_schedule = view.findViewById(R.id.spinner_CreateGame_schedule);
        spinner_CreateGame_ageRanges = view.findViewById(R.id.spinner_CreateGame_ageRanges);
        spinner_CreatGamelocation = view.findViewById(R.id.spinner_CreatGamelocation);

        //RadioButton
        radioButton_CreateGame_male = view.findViewById(R.id.radioButton_CreateGame_male);
        radioButton_CreateGame_femail = view.findViewById(R.id.radioButton_CreateGame_femail);
        radioButton_CreateGame_noGender = view.findViewById(R.id.radioButton_CreateGame_noGender);
        //radioGroup
        radioGroup_gendor = view.findViewById(R.id.radioGroup_gendor);
        //Button
        button_CreateGame = view.findViewById(R.id.button_CreateGame);

        //ImageView
        imageView_creatGame = view.findViewById(R.id.imageView_creatGame);

        Picasso.with(myContext).load(AppConstants.iconUrl)
                .into(imageView_creatGame);

        //on Click handle
        editText_CreateGame_date.setOnClickListener(this);
        editText_CreateGame_selectTime.setOnClickListener(this);
        button_CreateGame.setOnClickListener(this);
        spinner_CreatGamelocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int locationPos = spinner_CreatGamelocation.getSelectedItemPosition();
                locationId = locationListDataArrayList.get(locationPos).getLocationId();

                if (locationId.equals("-1")) {
                    Intent m = new Intent(getActivity(), SelectPlaceActivity.class);
                    startActivity(m);
                    getActivity().overridePendingTransition(0, 0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private boolean validateTime() {
        Time = editText_CreateGame_date.getText().toString();
        if (Time.isEmpty()) {
            Toast.makeText(getActivity(), "Please select Time ", Toast.LENGTH_SHORT).show();
            return false;
        } else {
        }
        return true;
    }

    private boolean validateDate() {
        Date = editText_CreateGame_date.getText().toString();
        if (Date.isEmpty()) {
            Toast.makeText(getActivity(), "Please select date ", Toast.LENGTH_SHORT).show();
            return false;
        } else {
        }
        return true;
    }

    private String getCheckedGenderId() {

        int radioButtonID = radioGroup_gendor.getCheckedRadioButtonId();
        View radioButton = radioGroup_gendor.findViewById(radioButtonID);
        int idx = radioGroup_gendor.indexOfChild(radioButton);

        return "" + idx;
    }

    public void loadThisFragment(Fragment fragment) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
