package com.nkc.education;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupActionBar();
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/thaisansneue-regular-webfont.ttf");

        TextView txt2 = (TextView) findViewById(R.id.textView2);
        txt2.setTypeface(typeFace);

        TextView txt3 = (TextView) findViewById(R.id.textView3);
        txt3.setTypeface(typeFace);

        TextView txt4 = (TextView) findViewById(R.id.textView4);
        txt4.setTypeface(typeFace);

        TextView txt5 = (TextView) findViewById(R.id.textView5);
        txt5.setTypeface(typeFace);

        TextView txt6 = (TextView) findViewById(R.id.textView6);
        txt6.setTypeface(typeFace);

        TextView txt7 = (TextView) findViewById(R.id.textView7);
        txt7.setTypeface(typeFace);

        TextView txt8 = (TextView) findViewById(R.id.textView8);
        txt8.setTypeface(typeFace);
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
