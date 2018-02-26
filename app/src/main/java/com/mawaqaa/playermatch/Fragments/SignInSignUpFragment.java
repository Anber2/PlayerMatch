package com.mawaqaa.playermatch.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.mawaqaa.playermatch.Activities.MainActivity;
import com.mawaqaa.playermatch.Constants.AppConstants;
import com.mawaqaa.playermatch.R;
import com.mawaqaa.playermatch.Utilities.PreferenceUtil;
import com.mawaqaa.playermatch.Utilities.SharedPrefsUtils;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.mawaqaa.playermatch.Activities.MainActivity.handleToolBarinSingIn;
import static com.mawaqaa.playermatch.Constants.AppConstants.PlayerMatch_isSignedIn;
import static com.mawaqaa.playermatch.Constants.AppConstants.User_ID;

/**
 * Created by HP on 10/10/2017.
 */

public class SignInSignUpFragment extends Fragment implements View.OnClickListener {
    public static String FragTAG;
    String TAG = "SignInSignUpFragment";
    MainActivity mainActivity;
    //layout
    EditText editText_userName, editText_Passwrd;
    Button button_singIn;
    TextView textView_signUp, textView_continueAsGuest, textView_forgetPass;
    SharedPrefsUtils myPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_signin_signup, container, false);
        initView(v);
        FragTAG = TAG;
        handleToolBarinSingIn();

        return v;
    }

    private void initView(View v) {
        // editText
        editText_userName = v.findViewById(R.id.editText_userName);
        editText_Passwrd = v.findViewById(R.id.editText_Passwrd);

        //textView
        textView_signUp = v.findViewById(R.id.textView_signUp);
        textView_continueAsGuest = v.findViewById(R.id.textView_continueAsGuest);
        textView_forgetPass = v.findViewById(R.id.textView_forgetPass);

        //button
        button_singIn = v.findViewById(R.id.button_singIn);

        // on click
        button_singIn.setOnClickListener(this);
        textView_signUp.setOnClickListener(this);
        textView_signUp.setOnClickListener(this);
        textView_continueAsGuest.setOnClickListener(this);
        textView_forgetPass.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_singIn:
                if (signInAuthentication()) {
                    handleLogIn();
                }
                break;

            case R.id.textView_signUp:

                break;

            case R.id.textView_continueAsGuest:
                myPref.setStringPreference(mainActivity, AppConstants.User_ID, SharedPrefsUtils.getStringPreference(getActivity(),User_ID));
                loadThisFragment(new HomeFragment());
                break;
            case R.id.textView_forgetPass:
                 break;
            default:
                break;
        }
    }

    private boolean signInAuthentication() {

        if (editText_userName.getText().toString().equals("")
               /* || !android.util.Patterns.EMAIL_ADDRESS.matcher(editText_userName.getText().toString()).matches()*/) {
            Toast.makeText(getActivity(), getString(R.string.signin_up_username_empty), Toast.LENGTH_LONG).show();
            editText_userName.requestFocus();
            return false;
        }

        if (editText_Passwrd.getText().toString().equals("")) {
            Toast.makeText(getActivity(), getString(R.string.signin_up_pass_empty), Toast.LENGTH_LONG).show();
            editText_Passwrd.requestFocus();
            return false;
        }
        return true;
    }


    private void handleLogIn() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject signInJsonObject = new JSONObject();


                    signInJsonObject.putOpt("UserName", editText_userName.getText().toString());
                    signInJsonObject.putOpt("Password", editText_Passwrd.getText().toString());
                    signInJsonObject.putOpt("langID", 1);


                    logInReq(AppConstants.LOG_IN_URL, signInJsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }).start();
    }

    private String logInReq(String urlPost, final JSONObject jsonObject) {

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

                                    String message = jsonObj.getString("Message");
                                    String IsSuccess = jsonObj.getString("IsSuccess");
                                    String userId = jsonObj.getString("Id");

                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                                    if (!IsSuccess.equalsIgnoreCase("false")) {

                                        PlayerMatch_isSignedIn = true;
                                        myPref.setStringPreference(mainActivity, AppConstants.User_ID, SharedPrefsUtils.getStringPreference(getActivity(),User_ID));
                                        String User_Id = myPref.getStringPreference(mainActivity,  AppConstants.User_ID);
                                        Log.d(TAG,User_Id);
                                        PreferenceUtil.setUserId(getActivity(), userId);

                                        loadThisFragment(new HomeFragment());
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

    @Override
    public void onAttach(Activity activity) {
        mainActivity = (MainActivity) activity;
        super.onAttach(activity);
    }
}
