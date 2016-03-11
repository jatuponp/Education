package com.nkc.education;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.nkc.education.adapter.IconAdapter;
import com.nkc.education.gcm.QuickstartPreferences;
import com.nkc.education.gcm.RegistrationIntentService;
import com.nkc.education.helper.DatabaseHelper;
import com.nkc.education.helper.SQLiteHandler;
import com.nkc.education.helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = MainActivity.class.getSimpleName();
    private SQLiteHandler db;
    private DatabaseHelper db_education;
    private SessionManager session;
    private ProgressDialog pDialog;
    Context context;
    static final String[] iconApps = new String[]{
            "News", "Exam Schedule", "Documents Service", "Inbox", "Feedback", "About", "Log out"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Iconify.with(new FontAwesomeModule());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        context = getApplicationContext();
        session = new SessionManager(context);
        if (!session.isLoggedIn()) {
            logoutUser();
        }

        db = new SQLiteHandler(context);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        setSupportActionBar(toolbar);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/thaisansneue-regular-webfont.ttf");

        listView = (ListView) findViewById(R.id.list_view);

        listView.setAdapter(new IconAdapter(this, iconApps));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intnews = new Intent(MainActivity.this, NewsActivity.class);
                        startActivity(intnews);
                        break;
                    case 1:
                        Intent intexam = new Intent(MainActivity.this, ExamActivity.class);
                        startActivity(intexam);
                        break;
                    case 2:
                        Intent intdoc = new Intent(MainActivity.this, DocumentActivity.class);
                        startActivity(intdoc);
                        break;
                    case 4:
                        Intent intfeed = new Intent(MainActivity.this, FeedbackActivity.class);
                        startActivity(intfeed);
                        break;
                    case 5:
                        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intent);
                        break;
                    case 6:
                        AlertDialog();
                        break;
                    default:
                        break;
                }
            }
        });

        if (checkPlayServices()) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }

        if (isInternetConnection()) {
            HashMap<String, String> user = db.getUserDetails();
            String userid = "5634100715";//user.get("uid");
            syncExam(userid);
            syncDocument(userid);
        }
    }

    private boolean isInternetConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    private void AlertDialog() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_lock_power_off)
                .setTitle("Logout")
                .setMessage("Would you like to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logoutUser();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    private void syncExam(String userid) {
        pDialog.setMessage("Synchronize Database. Please wait...");
        showDialog();

        db_education = new DatabaseHelper(getApplicationContext());
        db_education.deleteAllExam();

        JsonArrayRequest examReq = new JsonArrayRequest(Request.Method.POST, AppConfig.URL_GETEXAM + "?userid=" + userid,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "Response: " + response.toString());

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                String Nox = obj.getString("NOX");
                                Integer Running = Integer.valueOf(obj.getString("Running"));
                                String Coursecode = obj.getString("COURSECODE");
                                String Section = obj.getString("SECTION");
                                String Studentid = obj.getString("STUDENTID");
                                String Studentcode = obj.getString("STUDENTCODE");
                                String Coursenameeng = obj.getString("COURSENAMEENG");
                                String DateMid = obj.getString("DateMid");
                                String TimeBegin = obj.getString("TimeBegin");
                                String TimeEnd = obj.getString("TimeEnd");
                                String Runcode = obj.getString("RunCode");
                                String chk = obj.getString("CHK");
                                String Classid = obj.getString("CLASSID");
                                String Codex = obj.getString("CODEX");
                                String Enroll148 = obj.getString("Enroll148_STUDENTID");
                                String Prefixname = obj.getString("PREFIXNAME");
                                String Studentname = obj.getString("STUDENTNAME");
                                String Studentsurname = obj.getString("STUDENTSURNAME");
                                String RoomID = obj.getString("RoomID");
                                String Number = obj.getString("Number");
                                String Programname = obj.getString("PROGRAMNAME");
                                String Studentyear = obj.getString("STUDENTYEAR");
                                String Acadyear = obj.getString("ACADYEAR");
                                String Semester = obj.getString("SEMESTER");
                                String Financestatus = obj.getString("FINANCESTATUS");
                                String Programabbeng = obj.getString("PROGRAMABBENG");
                                String ExamType = obj.getString("ExamType");
                                String ExamYear = obj.getString("ExamYear");
                                String ExamYearX = obj.getString("ExamYearX");
                                String BYTEDES = obj.getString("BYTEDES");
                                String Comment = obj.getString("Comment");

                                db_education.createExam(Nox, Running, Coursecode, Section, Studentid, Studentcode, Coursenameeng, DateMid,
                                        TimeBegin, TimeEnd, Runcode, chk, Classid, Codex, Enroll148, Prefixname, Studentname, Studentsurname,
                                        RoomID, Number, Programname, Studentyear, Acadyear, Semester, Financestatus, Programabbeng,
                                        ExamType, ExamYear, ExamYearX, BYTEDES, Comment);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        hideDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }

                }
        );

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(examReq);
        db_education.closeDB();
    }

    private void syncDocument(String userid) {
        pDialog.setMessage("Synchronize Documents Database. Please wait...");
        showDialog();
        db_education = new DatabaseHelper(getApplicationContext());
        db_education.deleteAllDoc();

        JsonArrayRequest docReq = new JsonArrayRequest(Request.Method.POST, AppConfig.URL_GETDOC + "?userid=" + userid,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        int j = 0;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Integer Autoid = Integer.valueOf(obj.getString("AUTOID"));
                                String Batchno = obj.getString("BATCHNO");
                                String Requestdate = obj.getString("REQUESTDATE");
                                String Studentcode = obj.getString("STUDENTCODE");
                                String Studentname = obj.getString("STUDENTNAME");
                                String Studentsurname = obj.getString("STUDENTSURNAME");
                                String Acadyear = obj.getString("ACADYEAR");
                                Integer Semester = Integer.valueOf(obj.getString("SEMESTER"));
                                String Feeid = obj.getString("FEEID");
                                String Feeidname = obj.getString("FEEIDNAME");
                                String Feeidweb = obj.getString("FEEIDWEB");
                                String Quantity = obj.getString("QUANTITY");
                                String Reason = obj.getString("REASON");
                                String Remark = obj.getString("REMARK");
                                String Status = obj.getString("STATUS");

                                db_education.createDocument(Autoid, Batchno, Requestdate, Studentcode, Studentname, Studentsurname,
                                        Acadyear, Semester, Feeid, Feeidname, Feeidweb, Quantity, Reason, Remark, Status);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        hideDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }

                }
        );

        MySingleton.getInstance(this).addToRequestQueue(docReq);
        db_education.closeDB();
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     */
    private void logoutUser() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            String token = sharedPreferences.getString(QuickstartPreferences.TOKEN_ID, null);
            // Fetching user details from sqlite
            HashMap<String, String> user = db.getUserDetails();
            // Add custom implementation, as needed.
            Map<String, String> params = new HashMap<String, String>();
            params.put("regId", token);
            params.put("userId", user.get("uid"));

            String serverUrl = AppConfig.URL_UNREGISTER;
            doPost(serverUrl, params);
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
        } catch (Exception ex) {
            Log.d("Except", "Failed to complete token refresh" + ex.getMessage());
        }

        session.setLogin(false);
        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private static void doPost(final String urlConn, final Map<String, String> params) {
        Thread background = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    StringBuilder bodyBuilder = new StringBuilder();
                    Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
                    // constructs the POST body using the parameters
                    while (iterator.hasNext()) {
                        Map.Entry<String, String> param = iterator.next();
                        bodyBuilder.append(param.getKey()).append('=')
                                .append(param.getValue());
                        if (iterator.hasNext()) {
                            bodyBuilder.append('&');
                        }
                    }
                    String body = bodyBuilder.toString();
                    Log.v("POST", "Posting '" + body + "' to " + urlConn);
                    byte[] bytes = body.getBytes();

                    URL url;
                    try {
                        url = new URL(urlConn);
                    } catch (MalformedURLException e) {
                        throw new IllegalArgumentException("invalid url: " + urlConn);
                    }
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setFixedLengthStreamingMode(bytes.length);
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                    con.connect();
                    // post the request
                    OutputStream out = con.getOutputStream();
                    out.write(bytes);
                    out.flush();
                    out.close();

                    int status = con.getResponseCode();
                    Log.e("status", String.valueOf(status));

                } catch (Throwable t) {
                    // just end the background thread
                    Log.i("Animation", "Thread  exception " + t);
                }
            }

        });
        // Start Thread
        background.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
