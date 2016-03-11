package com.nkc.education;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nkc.education.helper.SQLiteHandler;
import com.nkc.education.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {
    private static final String TAG = FeedbackActivity.class.getSimpleName();
    private SQLiteHandler db;
    private Button btnSubmit;
    private EditText etTitle;
    private EditText etSuggest;
    private ProgressDialog pDialog;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        Typeface typeFace1 = Typeface.createFromAsset(getAssets(), "fonts/thaisansneue-bold-webfont.ttf");
        TextView txtTitle = (TextView) findViewById(R.id._title);
        txtTitle.setTypeface(typeFace1);

        Typeface typeFace= Typeface.createFromAsset(getAssets(), "fonts/thaisansneue-regular-webfont.ttf");

        if (isInternetConnection()) {

            etTitle = (EditText) findViewById(R.id.feedTitle);
            etSuggest = (EditText) findViewById(R.id.feedSuggest);
            btnSubmit = (Button) findViewById(R.id.btnSubmit);

            //set font
            etTitle.setTypeface(typeFace);
            etSuggest.setTypeface(typeFace);
            btnSubmit.setTypeface(typeFace);

            //Progress dialog
            pDialog = new ProgressDialog(this);
            pDialog.setCancelable(false);

            db = new SQLiteHandler(getApplicationContext());

            // Submit button Click Event
            btnSubmit.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    String title = etTitle.getText().toString().trim();
                    String suggest = etSuggest.getText().toString().trim();

                    // Check for empty data in the form
                    if (!title.isEmpty() && !suggest.isEmpty()) {
                        HashMap<String, String> user = db.getUserDetails();
                        String userid = user.get("uid");
                        sendFeedback(userid,title, suggest);
                    } else {
                        // Prompt user to enter credentials
                        Toast.makeText(getApplicationContext(),
                                "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_LONG)
                                .show();
                    }
                }

            });
        } else {
            showAlertNoNet();
        }
    }

    private void sendFeedback(final String uid,final String title, final String suggest) {
        // Tag used to cancel the request
        String tag_string_req = "req_feedback";
        pDialog.setMessage("send feedback ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SETFEEDBACK, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Feedback Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully send feedback
                        String msg = jObj.getString("msg");
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                        // Launch main activity
                        Intent intent = new Intent(FeedbackActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid", uid);
                params.put("title", title);
                params.put("suggest", suggest);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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

    public void showAlertNoNet() {
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);

        alertDlg.setMessage("ต้องทำการเชื่อมต่ออินเตอร์เน็ตก่อน...")
                .setTitle("แจ้งการใช้งาน")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("ตั้งค่า",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // TODO Auto-generated method stub
                                Intent gpsOptionsIntent = new Intent(
                                        android.provider.Settings.ACTION_SETTINGS);
                                startActivityForResult(gpsOptionsIntent, 0);
                            }
                        });
        alertDlg.setNegativeButton("ยกเลิก",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog alert = alertDlg.create();
        alert.show();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
