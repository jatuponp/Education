package com.nkc.education;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.nkc.education.adapter.DocListAdapter;
import com.nkc.education.adapter.InboxListAdapter;
import com.nkc.education.helper.DatabaseHelper;
import com.nkc.education.helper.SQLiteHandler;
import com.nkc.education.helper.SessionManager;
import com.nkc.education.model.Document;
import com.nkc.education.model.Inboxs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InboxActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ProgressDialog pDialog;
    private DatabaseHelper db;
    private SQLiteHandler udb;
    private SessionManager session;
    private List<Inboxs> inboxList = new ArrayList<Inboxs>();
    private ListView listView;
    private InboxListAdapter adapter;
    Context context;
    SharedPreferences sharedPreferences;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String TAG = InboxActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        listView = (ListView) findViewById(R.id.list_inbox);
        adapter = new InboxListAdapter(this, inboxList);
        listView.setAdapter(adapter);

        Typeface typeFace1 = Typeface.createFromAsset(getAssets(), "fonts/thaisansneue-bold-webfont.ttf");
        TextView txtTitle = (TextView) findViewById(R.id._title);
        txtTitle.setTypeface(typeFace1);


        swipeRefreshLayout.setOnRefreshListener(this);
        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        listInbox();
                                    }
                                }
        );
    }

    public void listInbox() {
        db = new DatabaseHelper(getApplicationContext());
        udb = new SQLiteHandler(getApplicationContext());
        // showing refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);

        HashMap<String, String> user = udb.getUserDetails();
        String userid = user.get("uid");

        JsonArrayRequest inboxReq = new JsonArrayRequest(Request.Method.POST, AppConfig.URL_GETINBOX + "?userid=" + userid,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        if (response.length() > 0) {
                            db.deleteAllInbox();
                        }
                        int j = 0;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Integer MIds = obj.getInt("MIds");
                                String MForm = obj.getString("MForm");
                                String MTo = obj.getString("MTo");
                                String MSubject = obj.getString("MSubject");
                                String MBody = obj.getString("MBody");
                                String MRead = obj.getString("MRead");
                                String MReadDate = obj.getString("MReadDate");
                                String MSendate = obj.getString("MSendate");

                                db.createInbox(MIds, MForm, MTo, MSubject, MBody, MRead, MReadDate, MSendate);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        // stopping swipe refresh
                        //swipeRefreshLayout.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);
                    }

                }
        );

        MySingleton.getInstance(this).addToRequestQueue(inboxReq);

        inboxList.clear();

        List<Inboxs> row = db.getAllInbox();
        for (Inboxs r : row) {
            Inboxs inboxs = new Inboxs();
            inboxs.setId(r.getId());
            inboxs.setMIds(r.getMIds());
            inboxs.setMSubject(r.getMSubject());
            inboxs.setMSendDate(r.getMSendate());
            inboxs.setMBody(r.getMBody());
            inboxs.setMRead(r.getMRead());
            inboxList.add(inboxs);
        }

        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Inboxs inboxs = inboxList.get(position);
                Intent intent = new Intent(InboxActivity.this, InboxDetailActivity.class);
                intent.putExtra("id", inboxs.getId());
                intent.putExtra("mids", inboxs.getMIds());
                intent.putExtra("subject", inboxs.getMSubject());
                intent.putExtra("body", inboxs.getMBody());
                intent.putExtra("sendDate", inboxs.getMSendate());
                db.setRead(inboxs.getMIds());
                setRead(inboxs.getMIds());

                startActivity(intent);
            }
        });

        // stopping swipe refresh
        swipeRefreshLayout.setRefreshing(false);

    }

    public void setRead(Integer ids){
        JsonArrayRequest inboxReq = new JsonArrayRequest(Request.Method.POST, AppConfig.URL_SETINBOX_READ + "?id=" + ids                ,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);
                    }

                }
        );

        MySingleton.getInstance(this).addToRequestQueue(inboxReq);
    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        listInbox();
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
