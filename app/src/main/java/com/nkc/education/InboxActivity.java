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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.nkc.education.adapter.DocListAdapter;
import com.nkc.education.adapter.InboxListAdapter;
import com.nkc.education.helper.DatabaseHelper;
import com.nkc.education.helper.SessionManager;
import com.nkc.education.model.Document;
import com.nkc.education.model.Inboxs;

import java.util.ArrayList;
import java.util.List;

public class InboxActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    private DatabaseHelper db;
    private SessionManager session;
    private List<Inboxs> inboxList = new ArrayList<Inboxs>();
    private ListView listView;
    private InboxListAdapter adapter;
    Context context;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        Typeface typeFace1 = Typeface.createFromAsset(getAssets(), "fonts/thaisansneue-bold-webfont.ttf");
        TextView txtTitle = (TextView) findViewById(R.id._title);
        txtTitle.setTypeface(typeFace1);

        listInbox();
    }

    public void listInbox(){
        listView = (ListView) findViewById(R.id.list_inbox);
        adapter = new InboxListAdapter(this, inboxList);
        listView.setAdapter(adapter);
        db = new DatabaseHelper(getApplicationContext());

        inboxList.clear();

        List<Inboxs> row = db.getAllInbox();
        for (Inboxs r : row){
            Inboxs inboxs = new Inboxs();
            inboxs.setMSubject(r.getMSubject());
            inboxs.setMSendDate(r.getMSendate());
            inboxs.setMBody(r.getMBody());

            inboxList.add(inboxs);
        }

        adapter.notifyDataSetChanged();

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
