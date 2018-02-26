package com.mawaqaa.playermatch.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mawaqaa.playermatch.Constants.AppConstants;
import com.mawaqaa.playermatch.R;
import com.mawaqaa.playermatch.Utilities.PreferenceUtil;

/**
 * Created by HP on 10/9/2017.
 */


public class SplashScreenActivity extends AppCompatActivity implements View.OnClickListener {

    Button button_English, button_Arabic;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_layout);

        button_Arabic = (Button) findViewById(R.id.button_Arabic);
        button_English = (Button) findViewById(R.id.button_English);

        button_Arabic.setOnClickListener(this);
        button_English.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_Arabic:
                PreferenceUtil.setLanguage(this, AppConstants.PlayerMatch_Arabic);

                if (PreferenceUtil.isUserSignedIn(this)) {

                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                    finish();

                } else {

                    Intent i = new Intent(this, SignInActivity.class);
                    startActivity(i);
                    finish();
                }




                break;
            case R.id.button_English:

                PreferenceUtil.setLanguage(this, AppConstants.PlayerMatch_English);

                if (PreferenceUtil.isUserSignedIn(this)) {

                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                    finish();

                } else {

                    Intent i = new Intent(this, SignInActivity.class);
                    startActivity(i);
                    finish();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}