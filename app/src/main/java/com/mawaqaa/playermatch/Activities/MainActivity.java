package com.mawaqaa.playermatch.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.mawaqaa.playermatch.Adapters.DrawerListViewAdapter;
import com.mawaqaa.playermatch.Constants.AppConstants;
import com.mawaqaa.playermatch.Data.DrawerListData;
import com.mawaqaa.playermatch.Fragments.AboutUsFragment;
import com.mawaqaa.playermatch.Fragments.CalenderFragment;
import com.mawaqaa.playermatch.Fragments.ContactUsFragment;
import com.mawaqaa.playermatch.Fragments.FAQFragment;
import com.mawaqaa.playermatch.Fragments.HomeFragment;
import com.mawaqaa.playermatch.Fragments.MyProfileFragment;
import com.mawaqaa.playermatch.Fragments.NotificationFragment;
import com.mawaqaa.playermatch.R;
import com.mawaqaa.playermatch.Utilities.DrawerUtils;
import com.mawaqaa.playermatch.Utilities.PreferenceUtil;
import com.mawaqaa.playermatch.Utilities.SharedPrefsUtils;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.mawaqaa.playermatch.Constants.AppConstants.User_ID;
import static com.mawaqaa.playermatch.Fragments.SignInSignUpFragment.FragTAG;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    //Layout contents
    public static ImageView imageView_toolBar_menu, imageView_toolBar_calender;
    public static TextView textView_toolBar_language;
    public static DrawerLayout mDrawerLayout;
    public static NavigationView mNavigationView;
    ImageView imageView_toolBar_logo, imageView_instagram;
    ListView mDrawerList;
    //DrawerList
    DrawerListViewAdapter mDrawerListAdpater;
    ArrayList<DrawerListData> mDrawerItems;
    public static View drawerView;
    public static RelativeLayout signout_menu, contact_us_menu, faq_menu, about_us_menu, notification_menu, my_profile_menu, home_menu, event_menu, sign_in_menu;


    //TAG
    String TAG = "MainActivity";
    Context context;
    //progress par
    ProgressDialog progressBar;

    //FragmentManager

    FragmentTransaction tx;

    public static void handleToolBarinSingIn() {

        if (FragTAG == "SignInSignUpFragment") {
            imageView_toolBar_calender.setVisibility(View.INVISIBLE);
            imageView_toolBar_menu.setVisibility(View.INVISIBLE);
            textView_toolBar_language.setVisibility(View.INVISIBLE);
        } else {
            imageView_toolBar_calender.setVisibility(View.VISIBLE);
            imageView_toolBar_menu.setVisibility(View.VISIBLE);
            textView_toolBar_language.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setDrawer(getApplicationContext());
        //setDrawerLayout();
        //loadThisFragment(new SignInSignUpFragment());
        //handleToolBarinSingIn();
        resetMenuItemColors();

        getDeviceRegistration();

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.fragment_container, new HomeFragment());
        // tx.addToBackStack(null);
        tx.commit();


    }

    private void initView() {

        //ImageView
        imageView_toolBar_menu = (ImageView) findViewById(R.id.imageView_toolBar_menu);
        imageView_toolBar_logo = (ImageView) findViewById(R.id.imageView_toolBar_logo);
        imageView_toolBar_calender = (ImageView) findViewById(R.id.imageView_toolBar_calender);
        imageView_instagram = (ImageView) findViewById(R.id.imageView_instagram);

        //TextView
        textView_toolBar_language = (TextView) findViewById(R.id.textView_toolBar_language);

        //DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //resetMenuItemColors
        //resetMenuItemColors();

        //ListView
        //mDrawerList = (ListView) findViewById(R.id.drawer_list);

        //on click
        imageView_toolBar_menu.setOnClickListener(this);
        imageView_toolBar_calender.setOnClickListener(this);
        imageView_toolBar_logo.setOnClickListener(this);
        textView_toolBar_language.setOnClickListener(this);


    }


    public  void setDrawer(Context context ) {
        LinearLayout LayoutContainer = (LinearLayout) findViewById(R.id.fragment_container);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) LayoutContainer.getLayoutParams();
        drawerView = mNavigationView.inflateHeaderView(R.layout.drawer_layout);
        LayoutContainer.setLayoutParams(params);
        params.setMargins(0, 0, 0, 0);

        home_menu = (RelativeLayout) drawerView.findViewById(R.id.home_menu);
        event_menu = (RelativeLayout) drawerView.findViewById(R.id.event_menu);
        my_profile_menu = (RelativeLayout) drawerView.findViewById(R.id.my_profile_menu);
        notification_menu = (RelativeLayout) drawerView.findViewById(R.id.notification_menu);
        about_us_menu = (RelativeLayout) drawerView.findViewById(R.id.about_us_menu);
        faq_menu = (RelativeLayout) drawerView.findViewById(R.id.faq_menu);
        contact_us_menu = (RelativeLayout) drawerView.findViewById(R.id.contact_us_menu);
        signout_menu = (RelativeLayout) drawerView.findViewById(R.id.signout_menu);
        sign_in_menu = (RelativeLayout) drawerView.findViewById(R.id.sign_in_menu);

        if (!PreferenceUtil.isUserSignedIn(this)) {
            drawerView.findViewById(R.id.my_profile_menu).setVisibility(View.GONE);
            drawerView.findViewById(R.id.signout_menu).setVisibility(View.GONE);


        } else {

            drawerView.findViewById(R.id.my_profile_menu).setVisibility(View.VISIBLE);
            drawerView.findViewById(R.id.signout_menu).setVisibility(View.VISIBLE);
            drawerView.findViewById(R.id.sign_in_menu).setVisibility(View.GONE);

        }

        home_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerUtils.closeDrawerVeiw(getApplicationContext(), mDrawerLayout);
                loadThisFragment(new HomeFragment());


            }
        });


        home_menu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                resetMenuItemColors();

                home_menu.setBackgroundResource(R.color.blue_menu);

                ImageView imageView = drawerView.findViewById(R.id.img_home_menu);
                TextView t1 = drawerView.findViewById(R.id.text1);


                ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(ContextCompat.getColor
                        (MainActivity.this, R.color.tab_background_unselected)));

                t1.setTextColor(Color.WHITE);

                return false;
            }
        });


        event_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceUtil.isUserSignedIn(MainActivity.this)) {
                    DrawerUtils.closeDrawerVeiw(getApplicationContext(), mDrawerLayout);
                    loadThisFragment(new CalenderFragment());
                } else {
                    Intent ii = new Intent(MainActivity.this, SignInActivity.class);
                    startActivity(ii);
                    overridePendingTransition(0, 0);
                }

            }
        });

        event_menu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                resetMenuItemColors();

                event_menu.setBackgroundResource(R.color.blue_menu);

                ImageView imageView = drawerView.findViewById(R.id.img_event_menu);
                TextView t1 = drawerView.findViewById(R.id.text2);


                ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(ContextCompat.getColor
                        (MainActivity.this, R.color.tab_background_unselected)));

                t1.setTextColor(Color.WHITE);

                return false;
            }
        });


        my_profile_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerUtils.closeDrawerVeiw(getApplicationContext(), mDrawerLayout);
                loadThisFragment(new MyProfileFragment());
            }
        });

        my_profile_menu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                resetMenuItemColors();

                my_profile_menu.setBackgroundResource(R.color.blue_menu);

                ImageView imageView = drawerView.findViewById(R.id.img_my_profile_menu);
                TextView t1 = drawerView.findViewById(R.id.text3);


                ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(ContextCompat.getColor
                        (MainActivity.this, R.color.tab_background_unselected)));

                t1.setTextColor(Color.WHITE);

                return false;
            }
        });

        notification_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceUtil.isUserSignedIn(MainActivity.this)) {
                    DrawerUtils.closeDrawerVeiw(getApplicationContext(), mDrawerLayout);
                    loadThisFragment(new NotificationFragment());
                } else {
                    Intent ii = new Intent(MainActivity.this, SignInActivity.class);
                    startActivity(ii);
                    overridePendingTransition(0, 0);
                }

            }
        });

        notification_menu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                resetMenuItemColors();

                notification_menu.setBackgroundResource(R.color.blue_menu);

                ImageView imageView = drawerView.findViewById(R.id.img_notification_menu);
                TextView t1 = drawerView.findViewById(R.id.text4);


                ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(ContextCompat.getColor
                        (MainActivity.this, R.color.tab_background_unselected)));

                t1.setTextColor(Color.WHITE);

                return false;
            }
        });

        about_us_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerUtils.closeDrawerVeiw(getApplicationContext(), mDrawerLayout);
                loadThisFragment(new AboutUsFragment());

            }
        });

        about_us_menu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                resetMenuItemColors();

                about_us_menu.setBackgroundResource(R.color.blue_menu);

                ImageView imageView = drawerView.findViewById(R.id.img_about_us_menu);
                TextView t1 = drawerView.findViewById(R.id.text5);


                ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(ContextCompat.getColor
                        (MainActivity.this, R.color.tab_background_unselected)));

                t1.setTextColor(Color.WHITE);

                return false;
            }
        });

        faq_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerUtils.closeDrawerVeiw(getApplicationContext(), mDrawerLayout);
                loadThisFragment(new FAQFragment());

            }
        });

        faq_menu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                resetMenuItemColors();

                faq_menu.setBackgroundResource(R.color.blue_menu);

                ImageView imageView = drawerView.findViewById(R.id.img_faq_menu);
                TextView t1 = drawerView.findViewById(R.id.text6);


                ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(ContextCompat.getColor
                        (MainActivity.this, R.color.tab_background_unselected)));

                t1.setTextColor(Color.WHITE);

                return false;
            }
        });

        contact_us_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerUtils.closeDrawerVeiw(getApplicationContext(), mDrawerLayout);
                loadThisFragment(new ContactUsFragment());

            }
        });

        contact_us_menu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                resetMenuItemColors();

                contact_us_menu.setBackgroundResource(R.color.blue_menu);

                ImageView imageView = drawerView.findViewById(R.id.img_contact_us_menu);
                TextView t1 = drawerView.findViewById(R.id.text7);


                ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(ContextCompat.getColor
                        (MainActivity.this, R.color.tab_background_unselected)));

                t1.setTextColor(Color.WHITE);

                return false;
            }
        });

        sign_in_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerUtils.closeDrawerVeiw(getApplicationContext(), mDrawerLayout);
                Intent ii = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(ii);
                overridePendingTransition(0, 0);

            }
        });

        sign_in_menu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                resetMenuItemColors();

                sign_in_menu.setBackgroundResource(R.color.blue_menu);

                ImageView imageView = drawerView.findViewById(R.id.img_sign_in_menu);
                TextView t1 = drawerView.findViewById(R.id.text8);


                ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(ContextCompat.getColor
                        (MainActivity.this, R.color.tab_background_unselected)));

                t1.setTextColor(Color.WHITE);

                return false;
            }
        });

        signout_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerUtils.closeDrawerVeiw(getApplicationContext(), mDrawerLayout);
                handleLogOut();

            }
        });

        signout_menu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                resetMenuItemColors();

                signout_menu.setBackgroundResource(R.color.blue_menu);

                ImageView imageView = drawerView.findViewById(R.id.img_signout_menu);
                TextView t1 = drawerView.findViewById(R.id.text9);


                ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(ContextCompat.getColor
                        (MainActivity.this, R.color.tab_background_unselected)));

                t1.setTextColor(Color.WHITE);

                return false;
            }
        });

    }

    private void resetMenuItemColors() {

        //home_menu reset color
        ImageView imageView = drawerView.findViewById(R.id.img_home_menu);
        TextView t1 = drawerView.findViewById(R.id.text1);

        home_menu.setBackgroundResource(0);

        ImageViewCompat.setImageTintList(imageView, null);

        t1.setTextColor(Color.parseColor("#7AC3D1"));

        //event_menu reset color
        ImageView imageView2 = drawerView.findViewById(R.id.img_event_menu);
        TextView t2 = drawerView.findViewById(R.id.text2);

        event_menu.setBackgroundResource(0);

        ImageViewCompat.setImageTintList(imageView2, null);

        t2.setTextColor(Color.parseColor("#7AC3D1"));

        //my_profile_menu reset color
        ImageView imageView3 = drawerView.findViewById(R.id.img_my_profile_menu);
        TextView t3 = drawerView.findViewById(R.id.text3);

        my_profile_menu.setBackgroundResource(0);

        ImageViewCompat.setImageTintList(imageView3, null);

        t3.setTextColor(Color.parseColor("#7AC3D1"));


        //notification_menu reset color
        ImageView imageView4 = drawerView.findViewById(R.id.img_notification_menu);
        TextView t4 = drawerView.findViewById(R.id.text4);

        notification_menu.setBackgroundResource(0);

        ImageViewCompat.setImageTintList(imageView4, null);

        t4.setTextColor(Color.parseColor("#7AC3D1"));

        //about_us_menu reset color
        ImageView imageView5 = drawerView.findViewById(R.id.img_about_us_menu);
        TextView t5 = drawerView.findViewById(R.id.text5);

        about_us_menu.setBackgroundResource(0);

        ImageViewCompat.setImageTintList(imageView5, null);

        t5.setTextColor(Color.parseColor("#7AC3D1"));


        //faq_menu reset color
        ImageView imageView6 = drawerView.findViewById(R.id.img_faq_menu);
        TextView t6 = drawerView.findViewById(R.id.text6);

        faq_menu.setBackgroundResource(0);

        ImageViewCompat.setImageTintList(imageView6, null);

        t6.setTextColor(Color.parseColor("#7AC3D1"));

        //contact_us_menu reset color
        ImageView imageView7 = drawerView.findViewById(R.id.img_contact_us_menu);
        TextView t7 = drawerView.findViewById(R.id.text7);

        contact_us_menu.setBackgroundResource(0);

        ImageViewCompat.setImageTintList(imageView7, null);

        t7.setTextColor(Color.parseColor("#7AC3D1"));


        //sign_in_menu reset color
        ImageView imageView8 = drawerView.findViewById(R.id.img_sign_in_menu);
        TextView t8 = drawerView.findViewById(R.id.text8);

        sign_in_menu.setBackgroundResource(0);

        ImageViewCompat.setImageTintList(imageView8, null);

        t8.setTextColor(Color.parseColor("#7AC3D1"));


        //signout_menu reset color
        ImageView imageView9 = drawerView.findViewById(R.id.img_signout_menu);
        TextView t9 = drawerView.findViewById(R.id.text9);

        signout_menu.setBackgroundResource(0);

        ImageViewCompat.setImageTintList(imageView9, null);

        t9.setTextColor(Color.parseColor("#7AC3D1"));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_toolBar_menu:
                DrawerUtils.openDrawerView(this, mDrawerLayout, imageView_toolBar_menu);
                break;

            case R.id.imageView_toolBar_calender:


                if (PreferenceUtil.isUserSignedIn(this)) {
                    DrawerUtils.closeDrawerVeiw(getApplicationContext(), mDrawerLayout);

                    loadThisFragment(new CalenderFragment());
                } else {
                    Intent i = new Intent(this, SignInActivity.class);
                    startActivity(i);
                    overridePendingTransition(0, 0);
                }

                break;

            case R.id.imageView_toolBar_logo:
                DrawerUtils.closeDrawerVeiw(getApplicationContext(), mDrawerLayout);

                loadThisFragment(new HomeFragment());

                break;


            case R.id.textView_toolBar_language:

                if (textView_toolBar_language.getText().toString().equals("ع")) {
                    textView_toolBar_language.setText("EN");
                } else if (textView_toolBar_language.getText().toString().equals("EN")) {
                    textView_toolBar_language.setText("ع");
                }

                break;

            default:
                break;
        }
    }

    public void loadThisFragment(Fragment fragment) {

      /*  FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();*/

        tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.fragment_container, fragment);
        tx.addToBackStack(null);
        tx.commit();


    }

    public void setDrawerLayout() {

        // mDrawerItemNames = getResources().getStringArray(R.array.more_items);

        mDrawerItems = new ArrayList<DrawerListData>();

        try {
            mDrawerItems.add(new DrawerListData(getString(R.string.home), R.drawable.ic_home));
            mDrawerItems.add(new DrawerListData(getString(R.string.event), R.drawable.ic_date));
            mDrawerItems.add(new DrawerListData(getString(R.string.drawer_profile), R.drawable.my_profile_menu));
            mDrawerItems.add(new DrawerListData(getString(R.string.notification), R.drawable.ic_noti));
            mDrawerItems.add(new DrawerListData(getString(R.string.about_us_title), R.drawable.menu_logo));
            mDrawerItems.add(new DrawerListData(getString(R.string.drawer_faqs), R.drawable.ic_faq));
            mDrawerItems.add(new DrawerListData(getString(R.string.drawer_contact_us), R.drawable.menu_contact));
            mDrawerItems.add(new DrawerListData(getString(R.string.drawer_log_out), R.drawable.signout));


        } catch (Exception e) {
            Log.e(TAG, "Exception in setDrawer Method");
            e.printStackTrace();
        }

        mDrawerList.setOnItemClickListener(this);

        mDrawerListAdpater = new DrawerListViewAdapter(getApplicationContext(),
                mDrawerItems);
        mDrawerList.setAdapter(mDrawerListAdpater);


    }

    private void handleShareApp() {

        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Player Match");
            String sAux = "\nLet me recommend you this application\n\n";
            sAux = sAux + "https://play.google.com/store \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            e.toString();
        }
    }

    private void handleLogOut() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this);
        alertDialogBuilder
                .setMessage("Are you sure you want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                sendLogOutReq();
                                // loadThisFragment(new SignInSignUpFragment());
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    private void sendLogOutReq() {
        progressBar = ProgressDialog.show(this, "", "Please Wait ...", true, false);

        new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.putOpt("User_Id", SharedPrefsUtils.getStringPreference(MainActivity.this, User_ID));
                    jsonObject.putOpt("Device_IMEI", getDeviceID());


                    sendLogOutStatusReq(AppConstants.API_LOG_OUT_URL, jsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.dismiss();

                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }


            }
        }).start();
    }

    private String sendLogOutStatusReq(String urlPost, final JSONObject jsonObject) {

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

                                    PreferenceUtil.setUserSignedIn(MainActivity.this, false);
                                    //loadThisFragment(new SignInSignUpFragment());
                                    Intent i = new Intent(MainActivity.this, SignInActivity.class);
                                    startActivity(i);
                                    MainActivity.this.finish();


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
                            Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();

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


    @Override
    public void onBackPressed() {


        try {

            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT); //CLOSE Nav Drawer!
            }


            int fragments = getSupportFragmentManager().getBackStackEntryCount();


            if (fragments == 0) {
                new AlertDialog.Builder(this)
                        .setMessage(getString(R.string.alert_dialog_are_you_sure_msg))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.alert_dialog_are_you_sure_msg_yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MainActivity.this.finish();
                            }
                        })
                        .setNegativeButton(getString(R.string.alert_dialog_are_you_sure_msg_no), null)
                        .show();
            } else

                super.onBackPressed();
        } catch (Exception xx) {
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                DrawerUtils.closeDrawerVeiw(getApplicationContext(), mDrawerLayout);
                loadThisFragment(new HomeFragment());

                break;
            case 1:


                if (PreferenceUtil.isUserSignedIn(this)) {
                    DrawerUtils.closeDrawerVeiw(getApplicationContext(), mDrawerLayout);
                    loadThisFragment(new CalenderFragment());
                } else {
                    Intent ii = new Intent(this, SignInActivity.class);
                    startActivity(ii);
                    overridePendingTransition(0, 0);
                }

                break;

            case 2:

                if (PreferenceUtil.isUserSignedIn(this)) {
                    DrawerUtils.closeDrawerVeiw(getApplicationContext(), mDrawerLayout);
                    loadThisFragment(new MyProfileFragment());

                    break;
                } else {
                    Intent ii = new Intent(this, SignInActivity.class);
                    startActivity(ii);
                    overridePendingTransition(0, 0);
                }


                break;

            case 3:

                if (PreferenceUtil.isUserSignedIn(this)) {
                    DrawerUtils.closeDrawerVeiw(getApplicationContext(), mDrawerLayout);
                    loadThisFragment(new NotificationFragment());
                } else {
                    Intent ii = new Intent(this, SignInActivity.class);
                    startActivity(ii);
                    overridePendingTransition(0, 0);
                }

                break;

            case 4:
                DrawerUtils.closeDrawerVeiw(getApplicationContext(), mDrawerLayout);
                loadThisFragment(new AboutUsFragment());

                break;
            case 5:
                DrawerUtils.closeDrawerVeiw(getApplicationContext(), mDrawerLayout);

                loadThisFragment(new FAQFragment());


                break;

            case 6:
                DrawerUtils.closeDrawerVeiw(getApplicationContext(), mDrawerLayout);

                loadThisFragment(new ContactUsFragment());

                break;
            case 7:

                //handleShareApp();

                if (PreferenceUtil.isUserSignedIn(this)) {
                    DrawerUtils.closeDrawerVeiw(getApplicationContext(), mDrawerLayout);
                    handleLogOut();
                } else {
                    Toast.makeText(this, "Your ara not logged in!", Toast.LENGTH_SHORT).show();
                }

                break;

            default:
                break;
        }
    }

    public String getDeviceID() throws Throwable {

        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);


        return deviceId;
    }

    private void getDeviceRegistration() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    JSONObject jsonObject = new JSONObject();


                    jsonObject.putOpt("User_Id", SharedPrefsUtils.getStringPreference(MainActivity.this, AppConstants.User_ID));
                    jsonObject.putOpt("IMEI", getDeviceID());
                    jsonObject.putOpt("Token", PreferenceUtil.getDeviceToken(MainActivity.this));
                    jsonObject.putOpt("Platform", 2);


                    postDeviceRegistrationData(AppConstants.AddDeviceToken, jsonObject);

                } catch (Exception xx) {
                    xx.toString();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }
        }).start();


    }

    private String postDeviceRegistrationData(String urlPost, final JSONObject jsonObject) {
        StringRequest stringRequest = null;
        final String[] resultConn = {""};
        String string_json = "";


        try {
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

            final String finalString_json = string_json;

            stringRequest = new StringRequest(Request.Method.POST, urlPost,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObjResp = new JSONObject(response);
                                if (jsonObjResp != null) {

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
                            Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();

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


}
