package com.mawaqaa.playermatch.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.mawaqaa.playermatch.R;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by HP on 10/16/2017.
 */

public class AboutUsFragment extends Fragment {
    String TAG = "AboutUsFragment";

    //layout
    TextView textView_aboutUS_details;
    //progress par
    ProgressDialog progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about_us, container, false);

        initview(v);
        SignInSignUpFragment.FragTAG = TAG;
       // handleToolBarinSingIn();

        getAboutUsData();

        return v;
    }

    private void getAboutUsData() {

        progressBar = ProgressDialog.show(getActivity(), "", "Please Wait ...", true, false);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.putOpt("langID", 1);


                    getAboutUsDataReq(AppConstants.GET_API_ABOUT_US_URL, jsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.dismiss();

                }
            }
        }).start();
    }


    private String getAboutUsDataReq(String urlPost, final JSONObject jsonObject) {

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

                                    String Summary = jsonObj.getString("Summary");
                                    String aboutUSDetails = jsonObj.getString("Details");

                                    //textView_title.setText(Html.fromHtml(Summary));

                                    textView_aboutUS_details.setText(Html.fromHtml(aboutUSDetails));


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


    private void initview(View v) {

        //textView
        textView_aboutUS_details = v.findViewById(R.id.textView_aboutUS_details);
        //textView_title = v.findViewById(R.id.textView_title);

    }

}
