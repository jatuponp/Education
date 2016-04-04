package com.nkc.education;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InboxDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/thaisansneue-regular-webfont.ttf");
        Typeface typeFace1 = Typeface.createFromAsset(getAssets(), "fonts/thaisansneue-bold-webfont.ttf");
        TextView txtTitle = (TextView) findViewById(R.id._title);
        txtTitle.setText(String.valueOf(b.get("subject")));
        txtTitle.setTypeface(typeFace1);

        TextView txtTime = (TextView) findViewById(R.id.txtTimeFrom);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt = sdf.parse(String.valueOf(b.get("sendDate")));
            Locale locale = new Locale("th","TH");
            Locale.setDefault(locale);
            SimpleDateFormat sdf1 = new SimpleDateFormat("d MMM yyyy HH:mm", Locale.getDefault());
            txtTime.setText(sdf1.format(dt));
        }catch (ParseException e){
            txtTime.setText("");
        }
        txtTime.setTypeface(typeFace);

        TextView txtMsg = (TextView) findViewById(R.id.txtMsgFrom);
        txtMsg.setText(String.valueOf(b.get("body")));
        txtMsg.setTypeface(typeFace);


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
