package com.mawaqaa.playermatch.Constants;

import org.json.JSONArray;

/**
 * Created by HP on 10/9/2017.
 */

public class AppConstants {

    // Location values
    public static final String GAME_LATITUDE = "0.00";
    public static final String GAME_LONGITUDE = "0.00";
    public static final String GAME_ADDRESS = "0.00";

    // Player Match language
    public static final String PlayerMatch_language = "";
    public static final String PlayerMatch_Arabic = "1";
    public static final String PlayerMatch_English = "1";

    //User Id
    public static final String PLAYER_MATCH_USER_ID = "";
    //Base URL
    //*********Online
    public static final String BASE_APP_URL = "http://playermatch.mawaqaademo.com";
    //*********Local
    //  public static final String BASE_APP_URL = "http://192.168.100.196:8022";
    //*********api
    public static final String BASE_API_URL = BASE_APP_URL + "/api/";
    //GET API Characters
    public static final String GET_API_URL_CHARACTER_1 = "?";
    public static final String GET_API_URL_CHARACTER_2 = "=";
    public static final String GET_API_URL_CHARACTER_3 = "&&";
    //Sign up URL
    public static final String POST_API_SIGN_UP_URL = BASE_API_URL + "User/Register_New_Player";
    //Get Nationality URL
    public static final String NATIONALITY_URL = BASE_API_URL + "Nationality/GetNationality";
    //Get Sports I play For SingUp URL
    public static final String GET_SPORTS_I_PLAY_URL = BASE_API_URL + "Sport_Type/GetCategories";
    //Games Created List URL
    public static final String API_CALENDAR_GAMES_EVENTS_URL = BASE_API_URL + "Match/GetAllMatchList";
    //Games Created List URL
    public static final String GAMES_CREATED_LIST_URL = BASE_API_URL + "Match/GetUpcomingGameByUserID";
    //GetCreatedGame
    public static final String GetCreatedGame = BASE_API_URL + "Match/GetCreatedGame";
    // DeleteMatchById
    public static final String DeleteMatchById = BASE_API_URL + "Match/DeleteMatchById";
    // CancelPlayerFromMacth
    public static final String CancelPlayerFromMacth = BASE_API_URL + "Individuals/CancelPlayerFromMacth";
    //GetAllIndividualsByMatchId
    public static final String GetAllIndividualsByMatchId = BASE_API_URL + "Individuals/GetAllIndividualsByMatchId";


    //Games Joined List URL
    public static final String GAMES_JOINED_LIST_URL = BASE_API_URL + "Match/GetJoinedGame";
    //Games Pending List URL
    public static final String GAMES_PENDING_LIST_URL = BASE_API_URL + "Match/GetPendingGame";
    //I Want To Play URL
    public static final String I_WANT_TO_PLAY_URL = BASE_API_URL + "User/UserWantToPlay";

    //GetNotificationByUserID
    public static final String GET_NOTIFICATION_LIST_URL = BASE_API_URL + "Notifications/GetNotificationByUserID";
    //Change Password URL
    public static final String CHANGE_PASSWORD_URL = BASE_API_URL + "Account/ChangePassword";
    //Forget Password URL
    public static final String FORGET_PASSWORD_URL = BASE_API_URL + "Account/SendResetEmail";
    //Update User profile URL
    public static final String POST_API_UPDATE_PROFILE_URL = BASE_API_URL + "User/SavePalyer";
    //Get User Profile URL
    public static final String GET_API_GET_USER_PROFILE_URL = BASE_API_URL + "User/GetUserProfile";
    //Login URL
    public static final String LOG_IN_URL = BASE_API_URL + "Account/login";
    //Logout URL
    public static final String API_LOG_OUT_URL = BASE_API_URL + "Account/Logout";
    //Reset Email URL
    public static final String GET_API_RESET_EMAIL_URL = BASE_API_URL + "Account/SendResetEmail";
    //Change Password URL
    public static final String POST_API_CHANGE_PASSWORD_URL = BASE_API_URL + "Account/ChangePassword";
    //Add Device Token URL
    public static final String GET_API_ADD_DEVICE_TOKEN_URL = BASE_API_URL + "Account/AddDeviceToken";
    //Get About Us URL
    public static final String GET_API_ABOUT_US_URL = BASE_API_URL + "AboutUs/GetAboutUS";
    //Get FAQ URL
    public static final String FAQ_URL = BASE_API_URL + "FAQs/GetFAQList";
    //AddDeviceToken URL
    public static final String AddDeviceToken = BASE_API_URL + "Account/AddDeviceToken";
    //Get Contact Us URL
    public static final String GET_CONTACT_US_URL = BASE_API_URL + "ContactUs/GetContactUS";
    //Get Game Individuals URL
    public static final String GET_LIST_OF_INDIVIDUALS_URL = BASE_API_URL + "Individuals/GetIndividualslst";
    //FilterList URL
    public static final String FilterList = BASE_API_URL + "Match/FilterList";


    //Create Match URL
    public static final String GET_API_CREATE_MATCH_URL = BASE_API_URL + "Match/CreateMatch";
    //Get Upcoming Game URL
    public static final String GET_LIST_OF_GAMES_URL = BASE_API_URL + "Match/GetUpcomingGameByUserID";

    //Get Upcoming Game URL
    public static final String GetUpcomingGame = BASE_API_URL + "Match/GetUpcomingGame";
    //Get Match By Id URL
    public static final String GET_MATCH_DETAILS_URL = BASE_API_URL + "Match/GetMatchById";
    //Getlocation
    public static final String Getlocation = BASE_API_URL + "Locations/Getlocation";
    //GetAgeRangeLst
    public static final String GetAgeRange = BASE_API_URL + "AgeRange/GetAgeRangeLst";

    //GetAgeRangeLst
    public static final String invitePlayer = BASE_API_URL + "Individuals/InvitePlayer";


    //Accept Join Match
    public static final String AcceptJoinMatch = BASE_API_URL + "Match/OrganizerAcceptJoinMatch";

    //Accept Join Match
    public static final String GenerateReferenceNo = BASE_API_URL + "Payment/GenerateReferenceNo";
    //PaymentRequest
    public static final String PaymentRequest = "http://tapapi.gotapnow.com/TapWebConnect/Tap/WebPay/PaymentRequest";


    //Reject Join Match
    public static final String RejectJoinMatch = BASE_API_URL + "Match/OrganizerRejectJoinMatch";
    //Join Match
    public static final String JoinMatch = BASE_API_URL + "Match/JoinMatch";

    //Get UserProfile
    public static final String GetUserProfile = BASE_API_URL + "User/GetUserProfile";
    //UpdateUserProfile
    public static final String UpdateUserProfile = BASE_API_URL + "User/SavePalyer";
    //ChangePassword
    public static final String ChangePassword = BASE_API_URL + "Account/ChangePassword";

    //Filter Games  URL
    public static final String API_FILTER_GAMES_URL = BASE_API_URL + "Match/GetGamesCalender";
    //Get Nationality Name URL
    public static final String GET_API_GET_NATIONALITY_NAME_URL = BASE_API_URL + "Nationality/GetNationalityName";
    //Get Sport Type By ID URL
    public static final String GET_API_GET_SPORT_TYPE_BY_ID_URL = BASE_API_URL + "Sport_Type/GetSportTypeByID";
    //Customer JSON VALUES
    public static final String ID = "id";
    public static final String LANGUAGE_ID = "Languages_Id";
    public static final String DEVICE_TOKEN = "DeviceToken";
    public static final String USER_SPORTS = "sportsIPlay";

    //is Signed In
    public static boolean PlayerMatch_isSignedIn = false;
    //BackStackEntryCount
    public static int backStackEntryCount = 0;
    public static String sportId;
    public static String sportImage;
    public static String sportName;
    public static Boolean isPostion;
    public static JSONArray lstPostion;
    public static String User_ID = "User_ID";
    public static String matchId = "matchId";

    //icon URL
    public static String iconUrl;

    //currentClass

    public static String currentClass = "";

}
