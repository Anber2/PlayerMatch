package com.mawaqaa.playermatch.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
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
import com.mawaqaa.playermatch.Adapters.FAQListAdapter;
import com.mawaqaa.playermatch.Constants.AppConstants;
import com.mawaqaa.playermatch.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by HP on 10/16/2017.
 */

public class FAQFragment extends Fragment {

    String TAG = "FAQFragment";

    //layout
    ExpandableListView expListView;

    //FAQ list
    FAQListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, String> listDataChild;
    List<String> answerList;

    //progress par
    private ProgressDialog progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_f_a_q, container, false);
        SignInSignUpFragment.FragTAG = TAG;

        initView(v);
        prepareListData();

        return v;
    }

    private void initView(View v) {

        expListView = v.findViewById(R.id.lvExp);
    }

    private void prepareListData() {

         progressBar = ProgressDialog.show(getActivity(), "", "Please Wait ...", true, false);


        new Thread(new Runnable() {
            @Override
            public void run() {



                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.putOpt("langID", 1);


                    getFAQDataReq(AppConstants.FAQ_URL, jsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.dismiss();
                }
            }
        }).start();

    }

    private String getFAQDataReq(String urlPost, final JSONObject jsonObject) {

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
                                    listDataChild = new HashMap<>();
                                    listDataHeader = new ArrayList<String>();
                                    answerList = new ArrayList<String>();
                                    List<String> answerList2 = new ArrayList<String>();

                                    JSONArray jsonArray = jsonObj.getJSONArray("lstFaq");

                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        String fAQId = jsonObject.getString("Id");

                                        String Question = jsonObject.getString("Quetion");
                                        String answer = jsonObject.getString("Answer");

                                        listDataHeader.add(Question);

                                        answerList.add(answer);



                                    }

                                    for (int i = 0; i < listDataHeader.size(); i++) {
                                       answerList2.add(answerList.get(i));

                                        listDataChild.put(listDataHeader.get(i), answerList.get(i)  );

                                    }

                                    if (getActivity() != null) {
                                        listAdapter = new FAQListAdapter(getActivity(), listDataHeader, listDataChild);

                                        expListView.setAdapter(listAdapter);
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
