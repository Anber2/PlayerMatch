package com.mawaqaa.playermatch.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.mawaqaa.playermatch.R;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by HP on 10/19/2017.
 */

public class ForgetPasswordFragment extends AppCompatActivity implements View.OnClickListener {
    String TAG = "ForgetPasswordFragment";

    //layout
    Button button_forgetPass;
    EditText editText_email;
    //progress par
    ProgressDialog progressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_forget_password);


        initView( );



    }




    private void initView( ) {

        button_forgetPass =  findViewById(R.id.button_forgetPass);
        editText_email =  findViewById(R.id.editText_email);

        button_forgetPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {


        if (ForgetPasswordAuthentication()) {
            sendForgetPass();
        }
    }

    private void sendForgetPass() {
        progressBar = ProgressDialog.show(ForgetPasswordFragment.this, "", "Please Wait ...", true, false);


        new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.putOpt("Email", editText_email.getText().toString());


                    sendForgetPassReq(AppConstants.FORGET_PASSWORD_URL, jsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.dismiss();
                }
            }
        }).start();
    }


    private String sendForgetPassReq(String urlPost, final JSONObject jsonObject) {

        StringRequest stringRequest = null;
        final String[] resultConn = {""};
        String string_json = "";

        try {
            final RequestQueue queue = Volley.newRequestQueue(this );

            final String finalString_json = string_json;

            stringRequest = new StringRequest(Request.Method.POST, urlPost,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObj = new JSONObject(response);

                                if (jsonObj != null) {

                                    Toast.makeText(ForgetPasswordFragment.this, jsonObj.getString("Message"), Toast.LENGTH_SHORT).show();


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
                            Toast.makeText(ForgetPasswordFragment.this, error.toString(), Toast.LENGTH_LONG).show();

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

    private boolean ForgetPasswordAuthentication() {
        if (editText_email.getText().toString().equals("") ||
                !android.util.Patterns.EMAIL_ADDRESS.matcher(editText_email.getText().toString()).matches()) {
            Toast.makeText(ForgetPasswordFragment.this, getString(R.string.forget_password_empty), Toast.LENGTH_LONG).show();
            editText_email.requestFocus();
            return false;
        }

        return true;
    }

}
