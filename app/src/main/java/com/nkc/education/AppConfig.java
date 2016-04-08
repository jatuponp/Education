package com.nkc.education;

/**
 * Created by Jumpon-pc on 30/9/2558.
 */
public class AppConfig {
    // Server user login url
    public static String URL_LOGIN = "https://it.nkc.kku.ac.th/backend/api/login";
    public static String URL_REGISTER = "https://it.nkc.kku.ac.th/frontend/edu/register";
    public static String URL_UNREGISTER = "https://it.nkc.kku.ac.th/frontend/edu/unregister";

    public static String URL_GETEXAM = "https://it.nkc.kku.ac.th/frontend/edu/getexam";
    public static String URL_GETDOC = "https://it.nkc.kku.ac.th/frontend/edu/getdoc";

    public static String URL_SETFEEDBACK = "https://it.nkc.kku.ac.th/frontend/edu/setfeedback";
    public static String URL_GETINBOX = "https://it.nkc.kku.ac.th/frontend/edu/getinbox";
    public static String URL_SETINBOX_READ = "https://it.nkc.kku.ac.th/frontend/edu/setinboxread";

    //For Notification Configuration
    public static final String VIBRATE_ON_ALARM = "vibrate_on_alarm";
    public static final String REMINDER_TIME = "reminder_time";
    public static final String ALARM_TIME = "alarm_time";

    public static final String DEFAULT_REMINDER_TIME = "6";
    public static final String DEFAULT_ALARM_TIME = "15";
    public static final String DEFAULT_HOUR_VALUE = "12";

}
