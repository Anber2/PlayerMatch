package com.mawaqaa.playermatch.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.mawaqaa.playermatch.Activities.SignInActivity;
import com.mawaqaa.playermatch.Activities.SportsIPlayActivity;
import com.mawaqaa.playermatch.Adapters.AgeRangeListAdapter;
import com.mawaqaa.playermatch.Adapters.NationalityListAdapter;
import com.mawaqaa.playermatch.Adapters.SportsIPlayListAdapter;
import com.mawaqaa.playermatch.Constants.AppConstants;
import com.mawaqaa.playermatch.Data.AgeRangeListData;
import com.mawaqaa.playermatch.Data.NationalityListData;
import com.mawaqaa.playermatch.Data.SportsIPlayListData;
import com.mawaqaa.playermatch.R;

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
 * Created by HP on 10/10/2017.
 */

public class SignUpFragment extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //Layout contents
    public static EditText editText_signUp_name, editText_signUp_phone, editText_signUp_email, editText_signUp_pass, editText_signUp_confirmPass, editText_signUp_sportsIPlay;
    String TAG = "SignUpFragment";
    RadioButton radioButton_male, radioButton_femail, radioButton_coEd;
    RadioGroup RadioGroup_SignUp_Gender;
    Spinner spinner_ageRange, spinner_nationality;
    GridView graidView_sportsIPlay;
    Button button_singup_save;
    TextView textView_myProfile_addSport;
    //SportsIPlay
    SportsIPlayListAdapter sportsIPlayListAdapter;
    ArrayList<SportsIPlayListData> sportsIPlayListDataArrayList;
    SportsIPlayListData sportsIPlayListData;
    List<String> sportIPlayId;
    //AgeRange
    AgeRangeListAdapter ageRangeListAdapter;
    ArrayList<AgeRangeListData> ageRangeListDataArrayList;
    AgeRangeListData ageRangeListData;
    String ageRangeId;

    //Nationality
    NationalityListAdapter nationalityListAdapter;
    ArrayList<NationalityListData> nationalityListDataArrayList;
    NationalityListData nationalityListData;
    String nationalityId;
    //
    int indexGender;
    //
    private ProgressDialog progressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signup);


        initView();


        setSportsIPlayListData();

        setAgeRangeListData();
        setNationalityListData();


    }


   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_signup, container, false);
        initView(v);


        setSportsIPlayListData();

        setAgeRangeListData();
        setNationalityListData();


        SignInSignUpFragment.FragTAG = TAG;
        handleToolBarinSingIn();
        return v;
    }*/

    private void setNationalityListData() {
        progressBar = ProgressDialog.show(this, "", "Please Wait ...", true, false);


        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.putOpt("langID", 1);


                    getNationalityListReq(AppConstants.NATIONALITY_URL, jsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.dismiss();

                }
            }
        }).start();


    }

    private String getNationalityListReq(String urlPost, final JSONObject jsonObject) {
        StringRequest stringRequest = null;
        final String[] resultConn = {""};
        String string_json = "";
        try {
            RequestQueue queue = Volley.newRequestQueue(this);
            final String finalString_json = string_json;
            stringRequest = new StringRequest(Request.Method.POST, urlPost,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray object = new JSONArray(response);
                                Log.d(TAG, response);


                                if (object != null) {


                                    nationalityListDataArrayList = new ArrayList<NationalityListData>();

                                    nationalityListDataArrayList.add(new NationalityListData("-1", "Select Nationality"));
                                    for (int i = 0; i < object.length(); i++) {

                                        JSONObject jsonObject = object.getJSONObject(i);

                                        String nationalityId = jsonObject.getString("Id");
                                        String nationalityName = jsonObject.getString("Name");

                                        nationalityListData = new NationalityListData(nationalityId, nationalityName);
                                        nationalityListDataArrayList.add(nationalityListData);
                                    }
                                    if (this != null) {

                                        nationalityListAdapter = new NationalityListAdapter(SignUpFragment.this, nationalityListDataArrayList);
                                        spinner_nationality.setAdapter(nationalityListAdapter);
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

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignUpFragment.this, error.toString(), Toast.LENGTH_LONG).show();
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
            RequestQueue queue = Volley.newRequestQueue(SignUpFragment.this);
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
                                    if (SignUpFragment.this != null) {


                                        ageRangeListAdapter = new AgeRangeListAdapter(SignUpFragment.this, ageRangeListDataArrayList);

                                        spinner_ageRange.setAdapter(ageRangeListAdapter);
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

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignUpFragment.this, error.toString(), Toast.LENGTH_LONG).show();
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

    private void initView() {

        //editText
        editText_signUp_name = findViewById(R.id.editText_signUp_name);
        editText_signUp_phone = findViewById(R.id.editText_signUp_phone);
        editText_signUp_email = findViewById(R.id.editText_signUp_email);
        editText_signUp_pass = findViewById(R.id.editText_signUp_pass);
        editText_signUp_confirmPass = findViewById(R.id.editText_signUp_confirmPass);
        editText_signUp_sportsIPlay = findViewById(R.id.editText_signUp_sportsIPlay);
        //textView
        textView_myProfile_addSport = findViewById(R.id.textView_signUp_addSport);
        //radioButton
        radioButton_male = findViewById(R.id.radioButton_male);
        radioButton_femail = findViewById(R.id.radioButton_femail);
        RadioGroup_SignUp_Gender = findViewById(R.id.RadioGroup_SignUp_Gender);
        radioButton_coEd = findViewById(R.id.radioButton_coEd);
        //spinner
        spinner_ageRange = findViewById(R.id.spinner_ageRange);
        spinner_nationality = findViewById(R.id.spinner_nationality);

        //graidView
        graidView_sportsIPlay = findViewById(R.id.graidView_sportsIPlay);

        //button
        button_singup_save = findViewById(R.id.button_singup_save);

        //handle on click
        button_singup_save.setOnClickListener(this);
        spinner_nationality.setOnItemSelectedListener(this);

        textView_myProfile_addSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentClass = TAG;
                Intent ii = new Intent(SignUpFragment.this, SportsIPlayActivity.class);
                startActivity(ii);
                overridePendingTransition(0, 0);
            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button_singup_save:

                if (signUpAuthentication()) {
                    getSportsIPlay();
                    signUpUser();
                }
                break;
            default:
                break;
        }

    }

    private void signUpUser() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject registrationJsonObject = new JSONObject();

                    registrationJsonObject.putOpt("UserName", editText_signUp_name.getText().toString());
                    registrationJsonObject.putOpt("Name", editText_signUp_name.getText().toString());
                    registrationJsonObject.putOpt("Email", editText_signUp_email.getText().toString());
                    registrationJsonObject.putOpt("Password", editText_signUp_pass.getText().toString());
                    registrationJsonObject.putOpt("Gender", getCheckedGenderId());
                    registrationJsonObject.putOpt("Phone", editText_signUp_phone.getText().toString());
                    registrationJsonObject.putOpt("Phone", editText_signUp_phone.getText().toString());
                    registrationJsonObject.putOpt("Age", getAgeRangeIdSelected());
                    registrationJsonObject.putOpt("Nationality_Id", getNationalityIdSelected());
                    registrationJsonObject.putOpt("User_Type_Id", "2");
                    registrationJsonObject.putOpt("User_Sports", android.text.TextUtils.join(",", sportIPlayId));

                    registrPlayerReq(AppConstants.POST_API_SIGN_UP_URL, registrationJsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }).start();

    }

    private void getSportsIPlay() {


        sportIPlayId = new ArrayList<String>();
        int listPosition = sportsIPlayListDataArrayList.size();

        for (int t = 0; t < listPosition; t++) {
            if (status.get(t).equals(true)) {


                String sportId = sportsIPlayListDataArrayList.get(t).getSportID();

                sportIPlayId.add(sportId);

            }
        }


    }

    private String registrPlayerReq(String urlPost, final JSONObject jsonObject) {

        StringRequest stringRequest = null;
        final String[] resultConn = {""};
        String string_json = "";

        try {
            RequestQueue queue = Volley.newRequestQueue(this);

            final String finalString_json = string_json;

            stringRequest = new StringRequest(Request.Method.POST, urlPost,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObj = new JSONObject(response);

                                if (jsonObj != null) {

                                    String message = jsonObj.getString("Message");
                                    String isSuccess = jsonObj.getString("IsSuccess");


                                    Toast.makeText(SignUpFragment.this, message, Toast.LENGTH_SHORT).show();

                                    if (isSuccess.equalsIgnoreCase("true")) {
                                        startActivity(new Intent(SignUpFragment.this, SignInActivity.class));

                                        SignUpFragment.this.finish();

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
                            Toast.makeText(SignUpFragment.this, error.toString(), Toast.LENGTH_LONG).show();

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

    private String getSportsIPlayListReq(String urlPost, final JSONObject jsonObject) {

        StringRequest stringRequest = null;
        final String[] resultConn = {""};
        String string_json = "";

        try {
            RequestQueue queue = Volley.newRequestQueue(SignUpFragment.this);

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

                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        String sportId = jsonObject.getString("Id");
                                        String sportName = jsonObject.getString("Name");

                                        sportsIPlayListData = new SportsIPlayListData(sportId, sportName);
                                        sportsIPlayListDataArrayList.add(sportsIPlayListData);
                                    }

                                    if (SignUpFragment.this != null) {
                                        sportsIPlayListAdapter = new SportsIPlayListAdapter(SignUpFragment.this, sportsIPlayListDataArrayList);
                                        graidView_sportsIPlay.setAdapter(sportsIPlayListAdapter);
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
                            Toast.makeText(SignUpFragment.this, error.toString(), Toast.LENGTH_LONG).show();

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


    private String getCheckedGenderId() {

        int radioButtonID = RadioGroup_SignUp_Gender.getCheckedRadioButtonId();
        View radioButton = RadioGroup_SignUp_Gender.findViewById(radioButtonID);
        int idx = RadioGroup_SignUp_Gender.indexOfChild(radioButton);


      /*  RadioGroup_SignUp_Gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                indexGender = radioGroup.indexOfChild(radioButton);
            }
        });*/
        return "" + idx;
    }

    boolean signUpAuthentication() {

        if (editText_signUp_name.getText().toString().equals("")) {
            Toast.makeText(this, getString(R.string.signup_error_username_empty), Toast.LENGTH_LONG).show();
            editText_signUp_name.requestFocus();
            return false;
        }
        if (editText_signUp_phone.getText().toString().equals("")) {
            Toast.makeText(this, getString(R.string.signup_error_phone_empty), Toast.LENGTH_LONG).show();
            editText_signUp_phone.requestFocus();
            return false;
        }
        if (editText_signUp_email.getText().toString().equals("")
                || !android.util.Patterns.EMAIL_ADDRESS.matcher(editText_signUp_email.getText().toString()).matches()) {
            Toast.makeText(this, getString(R.string.signup_error_email_empty), Toast.LENGTH_LONG).show();
            editText_signUp_email.requestFocus();
            return false;
        }
        if (editText_signUp_pass.getText().toString().equals("")) {
            Toast.makeText(this, getString(R.string.signup_error_password_empty), Toast.LENGTH_LONG).show();
            editText_signUp_pass.requestFocus();
            return false;
        }
        if ( editText_signUp_confirmPass.getText().toString().length()  <= 0) {
            Toast.makeText(this, getString(R.string.signup_error_password_confirm_empty), Toast.LENGTH_LONG).show();
            editText_signUp_confirmPass.requestFocus();
            return false;
        }else
        if (!editText_signUp_confirmPass.getText().toString().equals(editText_signUp_pass.getText().toString())) {
            Toast.makeText(this, getString(R.string.signup_error_password_confirm_empty2), Toast.LENGTH_LONG).show();
            editText_signUp_confirmPass.requestFocus();
            return false;
        }
        if (nationalityListData.getNationalityID().equals("-1") || nationalityListData.getNationalityID().equals(null)) {
            Toast.makeText(this, getString(R.string.signup_error_nationality_empty), Toast.LENGTH_SHORT).show();
            spinner_nationality.requestFocus();
            return false;
        }
        if (!sportsIPlayAuthentication()) {
            Toast.makeText(this, getString(R.string.signup_error_sports_empty), Toast.LENGTH_SHORT).show();
            graidView_sportsIPlay.requestFocus();
            return false;
        }


        return true;
    }

    private boolean sportsIPlayAuthentication() {


        boolean isChecked = false;

        for (int t = 0; t < status.size(); t++) {

            if (status.get(t).equals(true)) {

                isChecked = true;

                break;

            }

        }


        return isChecked;

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (adapterView.getId()) {

            case R.id.spinner_nationality:

                getNationalityIdSelected();

                break;


            case R.id.spinner_ageRange:

                getAgeRangeIdSelected();

                break;

            default:
                break;
        }

    }

    private String getAgeRangeIdSelected() {

        int position = spinner_ageRange.getSelectedItemPosition();
        ageRangeListData = ageRangeListDataArrayList.get(position);
        ageRangeId = ageRangeListData.getAgeRangeID();

        return ageRangeId;
    }

    private String getNationalityIdSelected() {

        int position = spinner_nationality.getSelectedItemPosition();

        nationalityListData = nationalityListDataArrayList.get(position);

        nationalityId = nationalityListData.getNationalityID();

        return nationalityId;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
