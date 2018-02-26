package com.mawaqaa.playermatch.Utilities;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;

/**
 * Created by HP on 10/9/2017.
 */

public class DrawerUtils {

    protected final static String TAG = "DrawerUtils";

    public static final void openDrawerView(Context context,
                                            DrawerLayout mDrawerLayout, ImageView btnMore) {


        try {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {

                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception in openDrawer Method");
            e.printStackTrace();
        }
    }

    public static final void closeDrawerVeiw(Context context,
                                             DrawerLayout mDrawerLayout) {
        try {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } catch (Exception e) {
            Log.e(TAG, "Exception in closeDrawer Method");
            e.printStackTrace();
        }
    }

}
