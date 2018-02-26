package com.mawaqaa.playermatch.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import com.mawaqaa.playermatch.Activities.PaymentWebViewActivity;
import com.mawaqaa.playermatch.Adapters.NotificationListAdapter;
import com.mawaqaa.playermatch.Constants.AppConstants;
import com.mawaqaa.playermatch.Data.NotificationData;
import com.mawaqaa.playermatch.R;
import com.mawaqaa.playermatch.Utilities.SharedPrefsUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.mawaqaa.playermatch.Constants.AppConstants.User_ID;

public class NotificationFragment extends Fragment {
    //myContext
    private static MainActivity myContext;
    //Tag
    String TAG = "AboutUsFragment";
    //layout
    ListView listView_notificationList;
    TextView textView_noNotifications;
    //Notification list
    NotificationData notificationData;
    ArrayList<NotificationData> notificationDataArrayList;
    NotificationListAdapter notificationListAdapter;
    //progress par
    ProgressDialog progressBar;
    //SharedPrefsUtils
    SharedPrefsUtils myPref;

    public static void acceptJoinMatch(final String notificationsId) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject registrationJsonObject = new JSONObject();
                    registrationJsonObject.putOpt("Notifications_Id", notificationsId);
                    registrationJsonObject.putOpt("Languages_Id", 1);


                    acceptJoinMatchReq(AppConstants.AcceptJoinMatch, registrationJsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public static void rejectJoinMatch(final String notificationsId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject registrationJsonObject = new JSONObject();
                    registrationJsonObject.putOpt("Notifications_Id", notificationsId);
                    registrationJsonObject.putOpt("Languages_Id", 1);
                    registrationJsonObject.putOpt("rejectReason", "");


                    rejectJoinMatchReq(AppConstants.RejectJoinMatch, registrationJsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public static String rejectJoinMatchReq(String urlPost, final JSONObject jsonObject) {
        StringRequest stringRequest = null;
        final String[] resultConn = {""};
        String string_json = "";
        try {
            RequestQueue queue = Volley.newRequestQueue(myContext);
            final String finalString_json = string_json;
            stringRequest = new StringRequest(Request.Method.POST, urlPost,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                if (jsonObj != null) {

                                    Toast.makeText(myContext, jsonObj.getString("Message"), Toast.LENGTH_SHORT).show();


                                }
                            } catch (Exception xx) {
                                xx.toString();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    String xx = error.toString();

                    myContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(myContext, error.toString(), Toast.LENGTH_LONG).show();
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

    public static String acceptJoinMatchReq(String urlPost, final JSONObject jsonObject) {
        StringRequest stringRequest = null;
        final String[] resultConn = {""};
        String string_json = "";
        try {
            RequestQueue queue = Volley.newRequestQueue(myContext);
            final String finalString_json = string_json;
            stringRequest = new StringRequest(Request.Method.POST, urlPost,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                if (jsonObj != null) {


                                    Toast.makeText(myContext, jsonObj.getString("Message"), Toast.LENGTH_SHORT).show();


                                }
                            } catch (Exception xx) {
                                xx.toString();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    String xx = error.toString();

                    myContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(myContext, error.toString(), Toast.LENGTH_LONG).show();
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

    public static void PayJoinMatch(final String notificationId, final String matchId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject registrationJsonObject = new JSONObject();
                    registrationJsonObject.putOpt("UserId", SharedPrefsUtils.getStringPreference(myContext, User_ID));
                    registrationJsonObject.putOpt("MatchId", matchId);
                    registrationJsonObject.putOpt("NotificationID", notificationId);
                    registrationJsonObject.putOpt("langID", 1);

                    PayJoinMatchReq(AppConstants.GenerateReferenceNo, registrationJsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static String PayJoinMatchReq(String urlPost, final JSONObject jsonObject) {
        StringRequest stringRequest = null;
        final String[] resultConn = {""};
        String string_json = "";
        try {
            RequestQueue queue = Volley.newRequestQueue(myContext);
            final String finalString_json = string_json;
            stringRequest = new StringRequest(Request.Method.POST, urlPost,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                if (jsonObj != null) {

                                    if (jsonObj.getString("IsSuccess").equalsIgnoreCase("true")) {

                                        JSONObject jsonObject1 = jsonObj.getJSONObject("oApi_ReturnUserDetails");

                                        sendPaymentRequest(jsonObject1.getString("Email"), jsonObject1.getString("Gender"),
                                                jsonObject1.getString("Phone"), jsonObject1.getString("Name")
                                                , jsonObject1.getString("Nationality"), jsonObject1.getString("ReferenceNo")
                                                , jsonObject1.getString("MatchName"), jsonObject1.getString("price"));
                                    } else {
                                        Toast.makeText(myContext, jsonObj.getString("Message"), Toast.LENGTH_SHORT).show();
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

                    myContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(myContext, error.toString(), Toast.LENGTH_LONG).show();
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

    private static void sendPaymentRequest(final String Email, final String Gender, final String Phone, final String Name, final String Nationality, final String ReferenceNo, final String MatchName, final String price) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    // CustomerDCJsonOb
                    JSONObject CustomerDCJsonObject = new JSONObject();

                    CustomerDCJsonObject.put("Email", Email);
                    CustomerDCJsonObject.put("Floor", "");
                    CustomerDCJsonObject.put("Gender", Gender);
                    CustomerDCJsonObject.put("ID", "");
                    CustomerDCJsonObject.put("Mobile", Phone);
                    CustomerDCJsonObject.put("Name", Name);
                    CustomerDCJsonObject.put("Nationality", Nationality);
                    CustomerDCJsonObject.put("Street", "");
                    CustomerDCJsonObject.put("Area", "");
                    CustomerDCJsonObject.put("CivilID", "");
                    CustomerDCJsonObject.put("Building", "");
                    CustomerDCJsonObject.put("Apartment", "");
                    CustomerDCJsonObject.put("DOB", "");

                    // lstProductDCJsonArray


                    JSONArray lstProductDCJsonArray = new JSONArray();

                    JSONObject lstProductDCJsonObject = new JSONObject();

                    lstProductDCJsonObject.put("CurrencyCode", "KWD");
                    lstProductDCJsonObject.put("ImgUrl", "http://2e0e4e551211ba98fa70-d81ddca05536e7c590811927217ea7a4.r4.cf3.rackcdn.com/catalog/product/cache/1/image/700x700/17f82f742ffe127f42dca9de82fb58b1/g/r/green_apple_fragrance.jpg");
                    lstProductDCJsonObject.put("Quantity", "1");
                    lstProductDCJsonObject.put("TotalPrice", price);
                    lstProductDCJsonObject.put("UnitDesc", MatchName);
                    lstProductDCJsonObject.put("UnitID", MatchName);
                    lstProductDCJsonObject.put("UnitName", MatchName);
                    lstProductDCJsonObject.put("UnitPrice", price);
                    lstProductDCJsonObject.put("VndID", "");

                    lstProductDCJsonArray.put(lstProductDCJsonObject);

                    // lstGateWayDCJsonArray

                    JSONArray lstGateWayDCJsonArray = new JSONArray();

                    JSONObject lstGateWayDCJsonObject = new JSONObject();

                    lstGateWayDCJsonObject.put("Name", "ALL");

                    lstGateWayDCJsonArray.put(lstGateWayDCJsonObject);


                    //  MerMastDCJsonObject
                    JSONObject MerMastDCJsonObject = new JSONObject();

                    MerMastDCJsonObject.put("AutoReturn", "Y");
                    MerMastDCJsonObject.put("ErrorURL", "https://www.facebook.com");
                    MerMastDCJsonObject.put("HashString", "829125d3b28a5e22fb80ad0069e8da33569c7591d4efb061cbdf7c1b7352dd2d");
                    MerMastDCJsonObject.put("LangCode", "EN");
                    MerMastDCJsonObject.put("MerchantID", "1014");
                    MerMastDCJsonObject.put("Password", "test");
                    MerMastDCJsonObject.put("PostURL", "https://www.facebook.com");
                    MerMastDCJsonObject.put("ReferenceID", ReferenceNo);
                    MerMastDCJsonObject.put("ReturnURL", "https://www.facebook.com");
                    MerMastDCJsonObject.put("UserName", "test");


                    //  Add all to main jsonObject

                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("CustomerDC", CustomerDCJsonObject);

                    jsonObject.put("lstProductDC", lstProductDCJsonArray);

                    jsonObject.put("lstGateWayDC", lstGateWayDCJsonArray);

                    jsonObject.put("MerMastDC", MerMastDCJsonObject);


                    postPaymentRequestData(AppConstants.PaymentRequest, jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private static String postPaymentRequestData(String urlPost, final JSONObject jsonObject) {
        StringRequest stringRequest = null;
        final String[] resultConn = {""};
        String string_json = "";
        try {
            RequestQueue queue = Volley.newRequestQueue(myContext);
            final String finalString_json = string_json;
            stringRequest = new StringRequest(Request.Method.POST, urlPost,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                if (jsonObj != null) {

                                    //Toast.makeText(myContext, jsonObj.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();


                                    Intent i = new Intent(myContext, PaymentWebViewActivity.class);
                                    myContext.startActivity(i);
                                    myContext.overridePendingTransition(0, 0);

                                }
                            } catch (Exception xx) {
                                xx.toString();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    String xx = error.toString();

                    myContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(myContext, error.toString(), Toast.LENGTH_LONG).show();
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myContext = (MainActivity) this.getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.notification_fragment, container, false);
        initView(v);

        getNotificationList();

        return v;
    }

    private void getNotificationList() {

        progressBar = ProgressDialog.show(getActivity(), "", "Please Wait ...", true, false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject registrationJsonObject = new JSONObject();
                    registrationJsonObject.putOpt("User_Id", SharedPrefsUtils.getStringPreference(getActivity(), User_ID));
                    registrationJsonObject.putOpt("Languages_Id", 1);

                    getNotificationListReq(AppConstants.GET_NOTIFICATION_LIST_URL, registrationJsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.dismiss();
                }
            }
        }).start();
    }

    private String getNotificationListReq(String urlPost, final JSONObject jsonObject) {
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
                                Log.d(TAG, response);
                                if (jsonObj != null) {
                                    JSONArray jsonArray = jsonObj.getJSONArray("LstApi_Notifications");
                                    notificationDataArrayList = new ArrayList<NotificationData>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        String Id = jsonObject.getString("Id");

                                        String Title = jsonObject.getString("Title");
                                        String PostDate = jsonObject.getString("PostDate");
                                        String Description = jsonObject.getString("Description");

                                        String NotificationType = jsonObject.getString("NotificationType");
                                        String MatchId = jsonObject.getString("MatchId");

                                        notificationData = new NotificationData(Id, Title, PostDate, Description, NotificationType, MatchId);
                                        notificationDataArrayList.add(notificationData);

                                    }

                                    if (getActivity() != null) {

                                        notificationListAdapter = new NotificationListAdapter(getActivity(), notificationDataArrayList);

                                        listView_notificationList.setAdapter(notificationListAdapter);

                                    } else {
                                        textView_noNotifications.setVisibility(View.VISIBLE);
                                        listView_notificationList.setVisibility(View.GONE);
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

    private void initView(View v) {

        //listView
        listView_notificationList = v.findViewById(R.id.listView_notificationList);

        //textView
        textView_noNotifications = v.findViewById(R.id.textView_noNotifications);

    }

}