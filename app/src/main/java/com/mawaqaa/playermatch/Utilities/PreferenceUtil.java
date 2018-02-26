package com.mawaqaa.playermatch.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mawaqaa.playermatch.Constants.AppConstants;

/**
 * Created by HP on 10/9/2017.
 */

public class PreferenceUtil {

    private final static String PlayerMatch_LANGUAGE = "language";
    private final static String GAME_LAT = "ResLatitude";
    private final static String GAME_LONG = "ResLongitude";
    private final static String GAME_ADD = "AddressLine";
    private final static String USER_ID = "userId";
    private final static String DEVICE_TOKEN = "Token";
    private final static String SPORTS_I_PLAY = "Token";
    private final static String IS_USER_SIGNED_IN = "is_user_signed_in";
    public static SharedPreferences sp;

    /*User Id Preference Part*/
    public final static void setUserId(Context context, String lang) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(USER_ID, lang).apply();
    }

    public final static String getUserId(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(USER_ID, AppConstants.PLAYER_MATCH_USER_ID);
    }

    /*Language Preference Part*/
    public final static void setLanguage(Context context, String lang) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PlayerMatch_LANGUAGE, lang).apply();
    }

    public final static String getLanguage(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PlayerMatch_LANGUAGE, AppConstants.PlayerMatch_English);
    }

    /*Game Location Preference Part*/

    public final static void setGameLatitude(Context context, String lang) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(GAME_LAT, lang).apply();
    }

    public final static String getGameLatitude(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(GAME_LAT, AppConstants.GAME_LATITUDE);
    }

    public final static void setGameLongitude(Context context, String lang) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(GAME_LONG, lang).apply();
    }

    public final static String getGameLongitude(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(GAME_LONG, AppConstants.GAME_LONGITUDE);
    }
    /*Game Address Name Preference Part*/

    public final static void setGameAddress(Context context, String lang) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(GAME_ADD, lang).apply();
    }

    public final static String getGameAddress(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(GAME_ADD, AppConstants.GAME_ADDRESS);
    }
/*Device Token Preference Part*/

    public final static void setDeviceToken(Context context, String lang) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(DEVICE_TOKEN, lang).apply();
    }

    public final static String getDeviceToken(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(DEVICE_TOKEN, AppConstants.DEVICE_TOKEN);
    }

    /*Sports I play Preference Part*/

    public final static void setSportsIPlay(Context context, String lang) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(SPORTS_I_PLAY, lang).apply();
    }

    public final static String getSportsIPlay(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(SPORTS_I_PLAY, AppConstants.USER_SPORTS);
    }

        /*  Is User Signed In Preference Part*/


    public final static void setUserSignedIn(Context context, boolean signed_in) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(IS_USER_SIGNED_IN, signed_in).apply();

    }

    public final static boolean isUserSignedIn(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(IS_USER_SIGNED_IN, false);
    }
}
