package com.nkc.education;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.nkc.education.adapter.IconAdapter;
import com.nkc.education.helper.SQLiteHandler;
import com.nkc.education.helper.SessionManager;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    private SQLiteHandler db;
    private SessionManager session;
    Context context;
    static final String[] iconApps = new String[]{
            "News", "Exam Schedule", "Documents Service",  "Inbox", "Links", "Feedback", "About us", "Log out"};

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

        setSupportActionBar(toolbar);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/thaisansneue-regular-webfont.ttf");

        listView = (ListView) findViewById(R.id.list_view);

        listView.setAdapter(new IconAdapter(this, iconApps));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                switch(position){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 7:
                        logoutUser();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     */
    private void logoutUser() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            //String token = sharedPreferences.getString(QuickstartPreferences.TOKEN_ID, null);
            // Fetching user details from sqlite
            HashMap<String, String> user = db.getUserDetails();
            // Add custom implementation, as needed.
            Map<String, String> params = new HashMap<String, String>();
            //params.put("regId", token);
            params.put("userId", user.get("uid"));

            //String serverUrl = AppConfig.URL_UNREGISTER;
            //doPost(serverUrl, params);
            //sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
