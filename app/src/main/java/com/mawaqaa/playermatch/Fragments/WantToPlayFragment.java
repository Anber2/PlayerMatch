package com.mawaqaa.playermatch.Fragments;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.mawaqaa.playermatch.Activities.SelectPlaceActivity;
import com.mawaqaa.playermatch.Adapters.AgeRangeListAdapter;
import com.mawaqaa.playermatch.Adapters.LocationListAdapter;
import com.mawaqaa.playermatch.Adapters.ScheduleTimeListAdapter;
import com.mawaqaa.playermatch.Constants.AppConstants;
import com.mawaqaa.playermatch.Data.AgeRangeListData;
import com.mawaqaa.playermatch.Data.LocatioModel;
import com.mawaqaa.playermatch.Data.LocationListData;
import com.mawaqaa.playermatch.Data.PostionModel;
import com.mawaqaa.playermatch.Data.ScheduleTimeListData;
import com.mawaqaa.playermatch.R;
import com.mawaqaa.playermatch.Utilities.PreferenceUtil;
import com.mawaqaa.playermatch.Utilities.SharedPrefsUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.mawaqaa.playermatch.Constants.AppConstants.User_ID;
import static com.mawaqaa.playermatch.Fragments.HomeFragment.myContext;

public class WantToPlayFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    //Tag
    static String TAG = "WantToPlayFragment";
    //layout
    EditText editText_date, editText_selectTime;
    Spinner spinner_schedule, spinner_ageRanges, spinner_Location, spinner_postion;
    Button play;
    ImageView imageView_sportDetails;
    //Age Range
    AgeRangeListAdapter ageRangeListAdapter;
    ArrayList<AgeRangeListData> ageRangeListDataArrayList;
    AgeRangeListData ageRangeListData;
    String ageRangeId;
    //progress par
    ProgressDialog progressBar;
    //Location
    ArrayList<LocationListData> locationListDataArrayList;
    LocationListAdapter locationListAdapter;
    LocationListData locationListData;
    String locationId;
    //
    Calendar myCalendar, mcurrentTime;
    ArrayList<ScheduleTimeListData> scheduleTimeListDataArrayList;
    ScheduleTimeListAdapter scheduleTimeListAdapter;

    RequestQueue queue;
    List<LocatioModel> LocationList = new ArrayList<LocatioModel>();
    LinearLayout linear_postion;
    SharedPrefsUtils myPref;
    String User_Id;
    Boolean isPostion;
    List<PostionModel> postionList = new ArrayList<PostionModel>();

    String Date, Time, Schedule, PositionId, AgeRange, Location_Id, Longitude, Latitude, LocationName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.want_to_play_fragment, container, false);
        //User_Id = myPref.getStringPreference(myContext, AppConstants.User_ID);
        defineView(v);
        setAgeRangeListData();
        setScheduleTimeSpinner();
        getLocation();
        setPositionOption();

        return v;
    }

    private void setPositionOption() {

        isPostion = AppConstants.isPostion;

        if (isPostion) {
            linear_postion.setVisibility(View.VISIBLE);
            postionList.clear();
            JSONArray jArray = AppConstants.lstPostion;
            String name_postion[] = new String[jArray.length()];
            for (int i = 0; i < jArray.length(); i++) {
                try {
                    JSONObject jo = jArray.getJSONObject(i);
                    name_postion[i] = jo.getString("positionName");
                    postionList.add(new PostionModel(jo.getString("positionId"), jo.getString("positionName")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, name_postion);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_postion.setAdapter(spinnerArrayAdapter);
        } else {
            linear_postion.setVisibility(View.GONE);
        }
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
                                        spinner_Location.setAdapter(locationListAdapter);

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
                            progressBar.dismiss();

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

    private void defineView(View v) {

        imageView_sportDetails = v.findViewById(R.id.imageView_sportDetails);

        Picasso.with(myContext).load(AppConstants.iconUrl)
                .into(imageView_sportDetails);

        //EditText
        editText_date = (EditText) v.findViewById(R.id.editText_date);
        editText_selectTime = (EditText) v.findViewById(R.id.editText_selectTime);
        //Spinner
        spinner_schedule = (Spinner) v.findViewById(R.id.spinner_schedule);
        spinner_ageRanges = (Spinner) v.findViewById(R.id.spinner_ageRanges);
        spinner_Location = (Spinner) v.findViewById(R.id.spinner_location);
        spinner_postion = v.findViewById(R.id.spinner_postion);
        //Button
        play = (Button) v.findViewById(R.id.play);
        //LinerLayout
        linear_postion = v.findViewById(R.id.linear_postion);


        editText_date.setOnClickListener(this);
        editText_selectTime.setOnClickListener(this);
        play.setOnClickListener(this);
        spinner_Location.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (adapterView.getId()) {

            case R.id.spinner_location:

                getSelectedLocation();

                break;

            case R.id.spinner_ageRange:
                getAgeRangeIdSelected();

                break;

            case R.id.spinner_postion:
                getSelectedPosition();

                break;
            case R.id.spinner_schedule:
                getSelectedSchedule();

                break;

            default:
                break;
        }

    }

    private String getSelectedSchedule() {

        int position = spinner_schedule.getSelectedItemPosition();

        String scheduleId = scheduleTimeListDataArrayList.get(position).getTimeId();

        return scheduleId;
    }

    private String getSelectedPosition() {

        String positionId = "0";

        if (postionList.size() != 0) {
            int position = spinner_postion.getSelectedItemPosition();

            positionId = postionList.get(position).getPositionId();
        }


        return positionId;

    }

    private String getAgeRangeIdSelected() {

        int position = spinner_ageRanges.getSelectedItemPosition();
        ageRangeListData = ageRangeListDataArrayList.get(position);
        ageRangeId = ageRangeListData.getAgeRangeID();

        return ageRangeId;
    }

    private String getSelectedLocation() {

        int locationPos = spinner_Location.getSelectedItemPosition();
        locationId = locationListDataArrayList.get(locationPos).getLocationId();

        if (locationId.equals("-1")) {
            Intent i = new Intent(getActivity(), SelectPlaceActivity.class);
            startActivity(i);
            getActivity().overridePendingTransition(0, 0);
        }

        return locationId;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editText_date:
                setDatePickerDialog();
                break;

            case R.id.editText_selectTime:
                setTimePickerDialog();
                break;

            case R.id.play:
                checkValue();
                break;
        }
    }

    private void checkValue() {
        if (!validateDate()) {
            return;
        }
        if (!validateTime()) {
            return;
        }
        sendPlayerJoiningData();
    }

    private void sendPlayerJoiningData() {

        progressBar = ProgressDialog.show(getActivity(), "", "Please Wait ...", true, false);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.putOpt("UserID", SharedPrefsUtils.getStringPreference(getActivity(), User_ID));
                    jsonObject.putOpt("Date", Date);
                    jsonObject.putOpt("Time", Time);
                    jsonObject.putOpt("Schedule", getSelectedSchedule());
                    jsonObject.putOpt("PositionId", getSelectedPosition());
                    jsonObject.putOpt("AgeRange", getAgeRangeIdSelected());
                    jsonObject.putOpt("Location_Id", getSelectedLocation());
                    jsonObject.putOpt("Longitude", PreferenceUtil.getGameLongitude(getActivity()));
                    jsonObject.putOpt("Latitude", PreferenceUtil.getGameLatitude(getActivity()));
                    jsonObject.putOpt("LocationName", PreferenceUtil.getGameAddress(getActivity()));


                    sendPlayerJoiningReq(AppConstants.I_WANT_TO_PLAY_URL, jsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.dismiss();

                }
            }
        }).start();
    }

    private String sendPlayerJoiningReq(String urlPost, final JSONObject jsonObject) {
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


    private boolean validateTime() {
        Time = editText_selectTime.getText().toString();
        if (Time.isEmpty()) {
            Toast.makeText(getActivity(), "Please select Time ", Toast.LENGTH_SHORT).show();
            return false;
        } else {
        }
        return true;
    }

    private boolean validateDate() {
        Date = editText_date.getText().toString();
        if (Date.isEmpty()) {
            Toast.makeText(getActivity(), "Please select date ", Toast.LENGTH_SHORT).show();
            return false;
        } else {
        }
        return true;
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

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        editText_date.setText(sdf.format(myCalendar.getTime()));
    }

    private void setTimePickerDialog() {
        mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                String am_pm = (selectedHour < 12) ? "AM" : "PM";
                String selected_Hour  = ""+selectedHour;
                String selected_Minute  = ""+selectedMinute;

                if(selectedHour < 10) {
                    selected_Hour = "0" + selectedHour;
                }
                if(selectedMinute < 10) {
                    selected_Minute = "0" + selectedMinute;
                }
                if(selectedHour == 00) {
                    selected_Hour = "" + 12;
                }

                editText_selectTime.setText(selected_Hour + ":" + selected_Minute + " " + am_pm);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void setScheduleTimeSpinner() {
        scheduleTimeListDataArrayList = new ArrayList<ScheduleTimeListData>();
        scheduleTimeListDataArrayList.add(new ScheduleTimeListData("0", "Time 1"));
        scheduleTimeListDataArrayList.add(new ScheduleTimeListData("1", "Time 2"));
        scheduleTimeListDataArrayList.add(new ScheduleTimeListData("2", "Time 3"));
        scheduleTimeListDataArrayList.add(new ScheduleTimeListData("3", "Time 4"));
        scheduleTimeListDataArrayList.add(new ScheduleTimeListData("4", "Time 5"));

        scheduleTimeListAdapter = new ScheduleTimeListAdapter(getActivity(), scheduleTimeListDataArrayList);
        spinner_schedule.setAdapter(scheduleTimeListAdapter);
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

                                        spinner_ageRanges.setAdapter(ageRangeListAdapter);

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

    public void loadThisFragment(Fragment fragment) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}