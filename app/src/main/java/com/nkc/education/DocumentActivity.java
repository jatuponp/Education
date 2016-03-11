package com.nkc.education;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import com.nkc.education.adapter.ExamListAdapter;
import com.nkc.education.helper.DatabaseHelper;
import com.nkc.education.helper.SessionManager;
import com.nkc.education.model.Document;
import com.nkc.education.model.Exam;

import java.util.ArrayList;
import java.util.List;

public class DocumentActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private SessionManager session;
    private List<Document> docList = new ArrayList<Document>();
    private ListView listView;
    private DocListAdapter adapter;
    Context context;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/thaisansneue-regular-webfont.ttf");
        Typeface typeFace1 = Typeface.createFromAsset(getAssets(), "fonts/thaisansneue-bold-webfont.ttf");
        TextView txtTitle = (TextView) findViewById(R.id._title);
        txtTitle.setTypeface(typeFace1);
        TextView txtDocList = (TextView) findViewById(R.id.txtDocList);
        TextView txtFullName = (TextView) findViewById(R.id.txtFullName);

        db = new DatabaseHelper(getApplicationContext());
        String fullName = db.getFullName();
        txtFullName.setText(fullName);
        txtFullName.setTypeface(typeFace);
        txtDocList.setTypeface(typeFace);

        listDoc();

    }

    public void listDoc(){
        listView = (ListView) findViewById(R.id.list_doc);
        adapter = new DocListAdapter(this, docList);
        listView.setAdapter(adapter);
        db = new DatabaseHelper(getApplicationContext());

        docList.clear();

        List<Document> row = db.getAllDoc();
        for (Document r : row){
            Document docs = new Document();
            docs.setFeeidname(r.getFeeidname());
            docs.setFeeidweb(r.getFeeidweb());
            docs.setRequestdate(r.getRequestdate());
            docs.setStatus(r.getStatus());

            docList.add(docs);
        }

        adapter.notifyDataSetChanged();

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
