package com.mawaqaa.playermatch.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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
import com.mawaqaa.playermatch.Activities.SportsIPlayActivity;
import com.mawaqaa.playermatch.Adapters.AgeRangeListAdapter;
import com.mawaqaa.playermatch.Adapters.SportsIPlayListAdapter;
import com.mawaqaa.playermatch.Constants.AppConstants;
import com.mawaqaa.playermatch.Data.AgeRangeListData;
import com.mawaqaa.playermatch.Data.SportsIPlayListData;
import com.mawaqaa.playermatch.R;
import com.mawaqaa.playermatch.Utilities.PreferenceUtil;
import com.mawaqaa.playermatch.Utilities.SharedPrefsUtils;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.mawaqaa.playermatch.Constants.AppConstants.User_ID;
import static com.mawaqaa.playermatch.Constants.AppConstants.currentClass;

/**
 * Created by HP on 10/15/2017.
 */

public class MyProfileFragment extends Fragment implements View.OnClickListener {

    String TAG = "MyProfileFragment";
    //Layout contents
    TextView textView_edit, textView_myProfile_addSport, textView_myProfile_chagePass;
    EditText editText_myProfile_name, editText_myProfile_phone, editText_myProfile_age, editText_myProfile_nationality;
    public static EditText editText_myProfile_sportsIPlay;
    //progress par
    ProgressDialog progressBar;
    //SportsIPlay
    SportsIPlayListAdapter sportsIPlayListAdapter;
    ArrayList<SportsIPlayListData> sportsIPlayListDataArrayList;
    //AgeRange
    AgeRangeListAdapter ageRangeListAdapter;
    ArrayList<AgeRangeListData> ageRangeListDataArrayList;
    //Dialog
    Dialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_profile, container, false);

        initView(v);
        //setSportsIPlayListData();

        SignInSignUpFragment.FragTAG = TAG;
        //handleToolBarinSingIn();
        getUserProfile();


        return v;
    }

    private void getUserProfile() {

        progressBar = ProgressDialog.show(getActivity(), "", "Please Wait ...", true, false);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.putOpt("User_Id", SharedPrefsUtils.getStringPreference(getActivity(), User_ID));
                    jsonObject.putOpt("langID", 1);


                    getUserProfileReq(AppConstants.GetUserProfile, jsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.dismiss();

                }
            }
        }).start();

    }

    private String getUserProfileReq(String urlPost, final JSONObject jsonObject) {

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
                                Log.d(TAG, response.toString());
                                JSONObject jsonObj = new JSONObject(response);

                                if (jsonObj != null) {

                                    JSONObject jsonObject1 = jsonObj.getJSONObject("data");



                                    editText_myProfile_name.setText(jsonObject1.getString("Name"));
                                    editText_myProfile_phone.setText(jsonObject1.getString("Phone"));

                                    editText_myProfile_age.setText(jsonObject1.getString("Age"));
                                    editText_myProfile_nationality.setText(jsonObject1.getString("NationalityName"));
                                    editText_myProfile_sportsIPlay.setText(jsonObject1.getString("sportTypeString"));

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


    private void initView(View v) {

        //textView
        textView_edit = v.findViewById(R.id.textView_edit);
        textView_myProfile_addSport = v.findViewById(R.id.textView_myProfile_addSport);
        textView_myProfile_chagePass = v.findViewById(R.id.textView_myProfile_chagePass);

        //editText
        editText_myProfile_name = v.findViewById(R.id.editText_myProfile_name);
        editText_myProfile_phone = v.findViewById(R.id.editText_myProfile_phone);
        editText_myProfile_age = v.findViewById(R.id.editText_myProfile_age);
        editText_myProfile_nationality = v.findViewById(R.id.editText_myProfile_nationality);
        editText_myProfile_sportsIPlay = v.findViewById(R.id.editText_myProfile_sportsIPlay);

        //editText setEnabled
        editText_myProfile_name.setEnabled(false);
        editText_myProfile_phone.setEnabled(false);
        editText_myProfile_age.setEnabled(false);
        editText_myProfile_nationality.setEnabled(false);
        editText_myProfile_sportsIPlay.setEnabled(false);

        //assign click listener
        textView_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleEditTextEnabled();
            }
        });

        textView_myProfile_chagePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleChangePassword();
            }
        });

        textView_myProfile_addSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentClass =TAG;
                Intent ii = new Intent(getActivity(), SportsIPlayActivity.class);
                startActivity(ii);
                getActivity().overridePendingTransition(0, 0);

            }
        });


    }

    private void handleEditTextEnabled() {

        if (textView_edit.getText().toString().equalsIgnoreCase("edit")) {

            textView_edit.setText("Save");
            editText_myProfile_name.setEnabled(true);
            editText_myProfile_phone.setEnabled(true);
            editText_myProfile_age.setEnabled(true);
            editText_myProfile_nationality.setEnabled(true);
            editText_myProfile_sportsIPlay.setEnabled(true);


        } else if (textView_edit.getText().toString().equalsIgnoreCase("save")) {

            textView_edit.setText("Edit");
            editText_myProfile_name.setEnabled(false);
            editText_myProfile_phone.setEnabled(false);
            editText_myProfile_age.setEnabled(false);
            editText_myProfile_nationality.setEnabled(false);
            editText_myProfile_sportsIPlay.setEnabled(false);

            updateUserProfile();
        }
    }

    private void handleChangePassword() {

        dialog = new Dialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.change_password_dialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final EditText oldPassword = (EditText) dialog.findViewById(R.id.editText_oldPass);
        final EditText newPassword = (EditText) dialog.findViewById(R.id.editText_newPass);
        final EditText newPassConfirm = (EditText) dialog.findViewById(R.id.editText_confirmNewPass);
        Button cancel = (Button) dialog.findViewById(R.id.button_changePass_cancel);


        Button submit = (Button) dialog.findViewById(R.id.button_changePassSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oldPassword.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter old password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (newPassword.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter new password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!newPassConfirm.getText().toString().equalsIgnoreCase(newPassword.getText().toString())) {
                    Toast.makeText(getActivity(), "New password does not match confirmed password", Toast.LENGTH_SHORT).show();
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject obj = new JSONObject();
                            obj.putOpt("User_Id", SharedPrefsUtils.getStringPreference(getActivity(), User_ID));
                            obj.putOpt("CurrentPassword", oldPassword.getText().toString());
                            obj.putOpt("newPassConfirm", newPassConfirm.getText().toString());

                            postChangePasswordData(AppConstants.ChangePassword, obj);
                        } catch (Exception xx) {
                        }
                    }
                }).start();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void postChangePasswordData(String urlPost, final JSONObject jsonObject) {

        final RequestQueue queue;
        StringRequest stringRequest = null;

        try {


            queue = Volley.newRequestQueue(getActivity());

            stringRequest = new StringRequest(Request.Method.POST, urlPost,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                if (response != null) {


                                    JSONObject result = new JSONObject(response);
                                    if (result.get("IsSuccess").equals("true")) {
                                        Toast.makeText(getActivity(), result.get("Message").toString(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();

                                    } else {
                                        Toast.makeText(getActivity(), result.get("Message").toString(), Toast.LENGTH_SHORT).show();

                                    }

                                }


                            } catch (Exception xx) {
                                xx.toString();
                            }


                        }

                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(final VolleyError error) {
                    String xx = error.toString();
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();


                }


            })


            {


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
                    8000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(stringRequest);


        } catch (Exception e) {
            e.toString();
        }


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            default:
                break;

        }
    }

    private void updateUserProfile() {

        progressBar = ProgressDialog.show(getActivity(), "", "Please Wait ...", true, false);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.putOpt("Id", SharedPrefsUtils.getStringPreference(getActivity(), User_ID));
                    jsonObject.putOpt("Name", editText_myProfile_name.getText().toString());
                    jsonObject.putOpt("UserName", "");
                    jsonObject.putOpt("Password", "");
                    jsonObject.putOpt("Email", "");
                    jsonObject.putOpt("Gender", "");
                    jsonObject.putOpt("Phone", editText_myProfile_phone.getText().toString());
                    jsonObject.putOpt("Age", editText_myProfile_age.getText().toString());
                    jsonObject.putOpt("Nationality_Id", "");
                    jsonObject.putOpt("User_Sports", PreferenceUtil.getSportsIPlay(getActivity()));

                    updateUserProfileReq(AppConstants.UpdateUserProfile, jsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.dismiss();

                }
            }
        }).start();

    }

    private String updateUserProfileReq(String urlPost, final JSONObject jsonObject) {

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
                                Log.d(TAG, response.toString());
                                JSONObject jsonObj = new JSONObject(response);

                                if (jsonObj != null) {

                                    JSONObject result = new JSONObject(response);
                                    if (result.get("IsSuccess").equals("true")) {
                                        Toast.makeText(getActivity(), result.get("Message").toString(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();

                                    } else {
                                        Toast.makeText(getActivity(), result.get("Message").toString(), Toast.LENGTH_SHORT).show();

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
