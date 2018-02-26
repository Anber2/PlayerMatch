package com.mawaqaa.playermatch.Fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.mawaqaa.playermatch.Activities.MainActivity;
import com.mawaqaa.playermatch.Adapters.AgeRangeListAdapter;
import com.mawaqaa.playermatch.Adapters.LocationListAdapter;
import com.mawaqaa.playermatch.Adapters.ScheduleTimeListAdapter;
import com.mawaqaa.playermatch.Constants.AppConstants;
import com.mawaqaa.playermatch.Data.AgeRangeListData;
import com.mawaqaa.playermatch.Data.LocationListData;
import com.mawaqaa.playermatch.Data.PostionModel;
import com.mawaqaa.playermatch.Data.ScheduleTimeListData;
import com.mawaqaa.playermatch.R;
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

/**
 * Created by HP on 10/17/2017.
 */

public class SportsListDetailsFragment extends Fragment implements View.OnClickListener {

    private static MainActivity myContext;
    String TAG = "SportsListDetailsFragment";
    //layout
    ImageView imageView_sportDetails, imageView_filter;
    TextView textView_SportName, textView_filterPosition;
    LinearLayout LinearLayout_filter;
    EditText editText_Date, editText_Time;
    Spinner spinner_filterGender, spinner_filterAge, spinner_filterPosi;
    Button button_filterFind, button_filterClear;
    //Animation
    Animation animSlideDOwn, animSlideUp;
    //Calendar
    Calendar myCalendar, mcurrentTime;
    ArrayList<ScheduleTimeListData> scheduleTimeListDataArrayList;
    ScheduleTimeListAdapter scheduleTimeListAdapter;
    //Age Range
    AgeRangeListAdapter ageRangeListAdapter;
    ArrayList<AgeRangeListData> ageRangeListDataArrayList;
    AgeRangeListData ageRangeListData;
    String ageRangeId;
    //Position
    RequestQueue queue;
    LinearLayout linear_postion;
    SharedPrefsUtils myPref;
    String User_Id;
    Boolean isPostion;
    List<PostionModel> postionList = new ArrayList<PostionModel>();
    //Location
    ArrayList<LocationListData> locationListDataArrayList;
    LocationListAdapter locationListAdapter;
    LocationListData locationListData;
    String locationId;
    //progress par
    ProgressDialog progressBar;
    //Tabs
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sport_list_details, container, false);
        initView(v);


        setSportsTabs(v);
        setAgeRangeListData();
        setPositionOption();
        setGenderList();

        return v;
    }


    private void initView(View v) {
        //imageView
        imageView_sportDetails = v.findViewById(R.id.imageView_sportDetailsFilter);
        imageView_filter = v.findViewById(R.id.imageView_filter);
        //textView
        textView_SportName = v.findViewById(R.id.textView_SportName);
        textView_filterPosition = v.findViewById(R.id.textView_filterPosition);
        textView_SportName.setText(AppConstants.sportName);
        //EditText
        editText_Date = v.findViewById(R.id.editText_Date);
        editText_Time = v.findViewById(R.id.editText_Time);
        //Spinner
        spinner_filterGender = v.findViewById(R.id.spinner_filterGender);
        spinner_filterAge = v.findViewById(R.id.spinner_filterAge);
        spinner_filterPosi = v.findViewById(R.id.spinner_filterPosi);
        //Button
        button_filterClear = v.findViewById(R.id.button_filterClear);
        button_filterFind = v.findViewById(R.id.button_filterFind);

        //LinearLayout
        LinearLayout_filter = v.findViewById(R.id.LinearLayout_filter);
        //Animation
        animSlideDOwn = AnimationUtils.loadAnimation(getActivity(), R.anim.slidedown);
        animSlideUp = AnimationUtils.loadAnimation(getActivity(), R.anim.slideup);

        Picasso.with(myContext).load(AppConstants.iconUrl)
                .into(imageView_sportDetails);

        //on click handle
        imageView_filter.setOnClickListener(this);
        button_filterClear.setOnClickListener(this);
        button_filterFind.setOnClickListener(this);
        editText_Date.setOnClickListener(this);
        editText_Time.setOnClickListener(this);

    }


    private void setSportsTabs(View v) {

        viewPager = v.findViewById(R.id.viewpager_sportDetails);
        setupViewPager(viewPager);
        tabLayout = v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new ListOFGamesFragment(), "List of Games");
        adapter.addFragment(new ListOFIndividualsFragment(), "List of Individuals");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.imageView_filter:

                showHideFilter();

                break;

            case R.id.editText_Date:
                setDatePickerDialog();
                break;

            case R.id.editText_Time:
                setTimePickerDialog();
                break;
            case R.id.button_filterClear:
                resetFilter();
                break;


            case R.id.button_filterFind:
                filterList();
                if (filterAuthentication()) {


                }


                break;


            default:
                break;
        }


    }

    private boolean filterAuthentication() {

        if (editText_Date.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter date!", Toast.LENGTH_SHORT).show();
            editText_Date.requestFocus();
            return false;
        }
        if (editText_Time.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter time!", Toast.LENGTH_SHORT).show();
            editText_Time.requestFocus();
            return false;
        }
        return true;
    }

    private void filterList() {

        progressBar = ProgressDialog.show(getActivity(), "", "Please Wait ...", true, false);


        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.putOpt("User_Id", SharedPrefsUtils.getStringPreference(getActivity(), User_ID));
                    jsonObject.putOpt("sportID", AppConstants.sportId);
                    jsonObject.putOpt("langID", 1);

                    jsonObject.putOpt("Date", editText_Date.getText().toString());
                    jsonObject.putOpt("Time", editText_Time.getText().toString());
                    jsonObject.putOpt("AgeRange", getAgeRangeIdSelected());
                    jsonObject.putOpt("positionId", getSelectedPosition());
                    jsonObject.putOpt("gender", getGenderIdSelected());

                    postFilterData(AppConstants.FilterList, jsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.dismiss();


                }
            }
        }).start();


    }

    private String postFilterData(String urlPost, final JSONObject jsonObject) {
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

                                    JSONArray jsonArraylstMatches = object.getJSONArray("lstMatches");
                                    JSONArray jsonArraylstUserIndividuals = object.getJSONArray("lstUserIndividuals");


                                    if (jsonArraylstMatches.length() == 0) {
                                        Toast.makeText(getActivity(), "No Match Found!", Toast.LENGTH_SHORT).show();
                                        ListOFGamesFragment.listView_ListOfGames.setAdapter(null);

                                    } else {
                                        ListOFGamesFragment.handleListOfGames(jsonArraylstMatches);
                                    }

                                    if (jsonArraylstUserIndividuals.length() == 0) {
                                        Toast.makeText(getActivity(), "No Player Found!", Toast.LENGTH_SHORT).show();
                                        ListOFIndividualsFragment.listView_ListOfIndividuals.setAdapter(null);

                                    } else {
                                        ListOFIndividualsFragment.handleListOFIndividuals(jsonArraylstUserIndividuals);
                                    }


                                }
                                progressBar.dismiss();


                            } catch (Exception xx) {
                                Log.e(TAG, "   " + xx.toString());
                                xx.toString();
                                progressBar.dismiss();

                            }
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
            progressBar.dismiss();

        } catch (Exception e) {
            progressBar.dismiss();

            e.toString();
            return e.toString();

        }
        return resultConn[0];
    }


    private String getSelectedPosition() {

        String positionId = "0";

        if (postionList.size() != 0) {
            int position = spinner_filterPosi.getSelectedItemPosition();

            positionId = postionList.get(position).getPositionId();
        }


        return positionId;

    }

    private String getAgeRangeIdSelected() {

        int position = spinner_filterAge.getSelectedItemPosition();
        ageRangeListData = ageRangeListDataArrayList.get(position);
        ageRangeId = ageRangeListData.getAgeRangeID();

        return ageRangeId;
    }

    private String getGenderIdSelected() {

        //Used same location data class for time saving

        int position = spinner_filterGender.getSelectedItemPosition();
        locationListData = locationListDataArrayList.get(position);
        locationId = locationListData.getLocationId();

        return locationId;
    }


    private void resetFilter() {
        editText_Date.setText("");
        editText_Time.setText("");

        spinner_filterGender.setAdapter(null);
        spinner_filterAge.setAdapter(null);
        spinner_filterPosi.setAdapter(null);

        setAgeRangeListData();
        setPositionOption();
        setGenderList();
    }

    private void showHideFilter() {
        if (LinearLayout_filter.getVisibility() == View.GONE) {
            LinearLayout_filter.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.GONE);

        } else {
            LinearLayout_filter.setVisibility(View.GONE);
            // LinearLayout_filter.setAnimation(animSlideUp);
            tabLayout.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onAttach(Activity activity) {
        myContext = (MainActivity) activity;
        super.onAttach(activity);
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

                editText_Time.setText(selected_Hour + ":" + selected_Minute + " " + am_pm);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        editText_Date.setText(sdf.format(myCalendar.getTime()));
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
                                    ageRangeListDataArrayList.add(new AgeRangeListData("0", ""));


                                    for (int i = 0; i < object.length(); i++) {

                                        JSONObject jsonObject = object.getJSONObject(i);

                                        String ageId = jsonObject.getString("Id");
                                        String ageValue = jsonObject.getString("Value");

                                        ageRangeListData = new AgeRangeListData(ageId, ageValue);
                                        ageRangeListDataArrayList.add(ageRangeListData);
                                    }
                                    if (getActivity() != null) {


                                        ageRangeListAdapter = new AgeRangeListAdapter(getActivity(), ageRangeListDataArrayList);

                                        spinner_filterAge.setAdapter(ageRangeListAdapter);

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

    private void setPositionOption() {

        isPostion = AppConstants.isPostion;

        if (isPostion) {
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
            spinner_filterPosi.setAdapter(spinnerArrayAdapter);
        } else {
            spinner_filterPosi.setVisibility(View.INVISIBLE);
            textView_filterPosition.setVisibility(View.INVISIBLE);
        }
    }

    private void setGenderList() {

        locationListDataArrayList = new ArrayList<LocationListData>();

        locationListDataArrayList.add(new LocationListData("-1", ""));

        locationListDataArrayList.add(new LocationListData("1", getString(R.string.signup_male)));
        locationListDataArrayList.add(new LocationListData("2", getString(R.string.signup_femail)));
        locationListDataArrayList.add(new LocationListData("3", getString(R.string.create_gender_non)));

        locationListAdapter = new LocationListAdapter(getActivity(), locationListDataArrayList);
        spinner_filterGender.setAdapter(locationListAdapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
