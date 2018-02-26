package com.mawaqaa.playermatch.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
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
import com.mawaqaa.playermatch.Constants.AppConstants;
import com.mawaqaa.playermatch.Fragments.ForgetPasswordFragment;
import com.mawaqaa.playermatch.Fragments.SignUpFragment;
import com.mawaqaa.playermatch.R;
import com.mawaqaa.playermatch.Utilities.PreferenceUtil;
import com.mawaqaa.playermatch.Utilities.SharedPrefsUtils;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.mawaqaa.playermatch.Constants.AppConstants.PlayerMatch_isSignedIn;

/**
 * Created by HP on 1/8/2018.
 */

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    public static String FragTAG;
    String TAG = "SignInSignUpFragment";
    MainActivity mainActivity;
    //layout
    EditText editText_userName, editText_Passwrd;
    Button button_singIn;
    TextView textView_signUp, textView_continueAsGuest, textView_forgetPass;
    SharedPrefsUtils myPref;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signin_signup);


        initView();
        FragTAG = TAG;
        //handleToolBarinSingIn();


    }

    private void initView() {
        // editText
        editText_userName = findViewById(R.id.editText_userName);
        editText_Passwrd = findViewById(R.id.editText_Passwrd);

        //textView
        textView_signUp = findViewById(R.id.textView_signUp);
        textView_continueAsGuest = findViewById(R.id.textView_continueAsGuest);
        textView_forgetPass = findViewById(R.id.textView_forgetPass);

        SpannableString content = new SpannableString(getString(R.string.signin_up_signin_forger_pass));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView_forgetPass.setText(content);

        //button
        button_singIn = findViewById(R.id.button_singIn);

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
                startActivity(new Intent(SignInActivity.this, SignUpFragment.class));

                break;

            case R.id.textView_continueAsGuest:
                myPref.setStringPreference(SignInActivity.this, AppConstants.User_ID, "0");
                startActivity(new Intent(SignInActivity.this, MainActivity.class));

                break;
            case R.id.textView_forgetPass:
                startActivity(new Intent(SignInActivity.this, ForgetPasswordFragment.class));
                break;
            default:
                break;
        }
    }

    private boolean signInAuthentication() {

        if (editText_userName.getText().toString().equals("")) {
            Toast.makeText(this, getString(R.string.signin_up_username_empty), Toast.LENGTH_LONG).show();
            editText_userName.requestFocus();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(editText_userName.getText().toString()).matches()) {
            Toast.makeText(this, getString(R.string.signin_up_valid), Toast.LENGTH_LONG).show();
            editText_userName.requestFocus();
            return false;
        }

        if (editText_Passwrd.getText().toString().equals("")) {
            Toast.makeText(this, getString(R.string.signin_up_pass_empty), Toast.LENGTH_LONG).show();
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


                    signInJsonObject.putOpt("Email", editText_userName.getText().toString());
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
                                    String IsSuccess = jsonObj.getString("IsSuccess");
                                    String userId = jsonObj.getString("Id");

                                    Toast.makeText(SignInActivity.this, message, Toast.LENGTH_SHORT).show();

                                    if (!IsSuccess.equalsIgnoreCase("false")) {

                                        PlayerMatch_isSignedIn = true;

                                        PreferenceUtil.setUserSignedIn(SignInActivity.this, true);
                                        myPref.setStringPreference(SignInActivity.this, AppConstants.User_ID, userId);
                                        String User_Id = myPref.getStringPreference(SignInActivity.this, AppConstants.User_ID);
                                        Log.d(TAG, User_Id);
                                        PreferenceUtil.setUserId(SignInActivity.this, userId);

                                        //loadThisFragment(new HomeFragment());

                                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                        SignInActivity.this.finish();
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
                            Toast.makeText(SignInActivity.this, error.toString(), Toast.LENGTH_LONG).show();
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


    }


}
