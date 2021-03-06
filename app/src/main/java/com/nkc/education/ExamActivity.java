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

import com.nkc.education.adapter.ExamListAdapter;
import com.nkc.education.helper.DatabaseHelper;
import com.nkc.education.helper.SessionManager;
import com.nkc.education.model.Exam;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ExamActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private SessionManager session;
    private List<Exam> examList = new ArrayList<Exam>();
    private ListView listView;
    private ExamListAdapter adapter;
    Context context;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/thaisansneue-regular-webfont.ttf");
        Typeface typeFace1 = Typeface.createFromAsset(getAssets(), "fonts/thaisansneue-bold-webfont.ttf");
        TextView txtTitleExam = (TextView) findViewById(R.id.titleExam);
        txtTitleExam.setTypeface(typeFace1);
        TextView txtExamYearX = (TextView) findViewById(R.id.txtExamYearX);
        TextView txtFullName = (TextView) findViewById(R.id.txtFullName);

        db = new DatabaseHelper(getApplicationContext());
        String yearX = db.getExamYearX();
        String fullName = db.getFullName();
        txtExamYearX.setText(yearX);
        txtFullName.setText(fullName);
        txtExamYearX.setTypeface(typeFace);
        txtFullName.setTypeface(typeFace);

        listExam();
    }

    public void listExam(){
        listView = (ListView) findViewById(R.id.list_exam);
        adapter = new ExamListAdapter(this, examList);
        listView.setAdapter(adapter);
        db = new DatabaseHelper(getApplicationContext());

        examList.clear();

        List<Exam> row = db.getAllExam();
        for (Exam r : row){
            Exam exam = new Exam();
            exam.setCoursecode(r.getCoursecode());
            exam.setSection(r.getSection());
            exam.setCoursenameeng(r.getCoursenameeng());
            exam.setDateMid(r.getDateMid());
            exam.setTimeBegin(r.getTimeBegin());
            exam.setTimeEnd(r.getTimeEnd());
            exam.setRoomID(r.getRoomID());
            exam.setRunning(r.getRunning());

            examList.add(exam);
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
