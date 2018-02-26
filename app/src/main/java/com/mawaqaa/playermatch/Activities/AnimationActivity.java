package com.mawaqaa.playermatch.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.mawaqaa.playermatch.R;

/**
 * Created by HP on 2/1/2018.
 */

public class AnimationActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_layout);

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                Intent i = new Intent(AnimationActivity.this, SplashScreenActivity.class);
                startActivity(i);

                finish();
            }
        }, 5000);

    }


}
