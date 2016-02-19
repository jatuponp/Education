package com.nkc.education.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jumpon-pc on 4/11/2558.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    //Logcat tag
    private static final String LOG = DatabaseHelper.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Education";

    // Table Names
    private static final String TABLE_EXAM = "exam";
    private static final String TABLE_DOCUMENT = "document";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_STUDENTID = "studentid";
    private static final String KEY_STUDENTCODE = "studentcode";
    private static final String KEY_STUDENTNAME = "Studentname";
    private static final String KEY_STUDENTSURNAME = "Studentsurname";
    private static final String KEY_ACADYEAR = "Acadyear";
    private static final String KEY_SEMESTER = "Semester";

    // Exam Table - column names
    private static final String KEY_NOX = "NOX";
    private static final String KEY_RUNNING = "running";
    private static final String KEY_COURSECODE = "coursecode";
    private static final String KEY_SECTION = "section";
    private static final String KEY_COURSENAMEENG = "coursenameeng";
    private static final String KEY_DATEMID = "DateMid";
    private static final String KEY_TIMEBEGIN = "TimeBegin";
    private static final String KEY_TIMEEND = "TimeEnd";
    private static final String KEY_RUNCODE = "runcode";
    private static final String KEY_CHK = "chk";
    private static final String KEY_CLASSID = "Classid";
    private static final String KEY_CODEX = "Codex";
    private static final String KEY_Enroll148_STUDENTID = "Enroll148";
    private static final String KEY_PREFIXNAME = "Prefixname";
    private static final String KEY_RoomID = "RoomID";
    private static final String KEY_Number = "Number";
    private static final String KEY_PROGRAMNAME = "Programname";
    private static final String KEY_STUDENTYEAR = "Studentyear";
    private static final String KEY_FINANCESTATUS = "Financestatus";
    private static final String KEY_PROGRAMABBENG = "Programabbeng";
    private static final String KEY_ExamType = "ExamType";
    private static final String KEY_ExamYear = "ExamYear";
    private static final String KEY_ExamYearX = "ExamYearX";
    private static final String KEY_BYTEDES = "Bytedes";
    private static final String KEY_Comment = "Comment";

    // document Table - column names
    private static final String KEY_AUTOID = "Autoid";
    private static final String KEY_BATCHNO = "Batchno";
    private static final String KEY_REQUESTDATE = "Requestdate";
    private static final String KEY_FEEID = "Feeid";
    private static final String KEY_FEEIDNAME = "Feeidname";
    private static final String KEY_FEEIDWEB = "Feeidweb";
    private static final String KEY_QUANTITY = "Quantity";
    private static final String KEY_REASON = "Reason";
    private static final String KEY_REMARK = "Remark";
    private static final String KEY_STATUS = "Status";

    // Table Create Statements
    // Room table create statement
    private static final String CREATE_TABLE_EXAM = "CREATE TABLE "
            + TABLE_EXAM + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NOX + " TEXT," + KEY_RUNNING
            + " INTEGER," + KEY_COURSECODE + " TEXT," + KEY_SECTION
            + " TEXT," + KEY_STUDENTID + " TEXT," + KEY_STUDENTCODE + " TEXT," + KEY_COURSENAMEENG
            + " TEXT," + KEY_DATEMID + " DATE," + KEY_TIMEBEGIN + " TEXT," + KEY_TIMEEND + " TEXT," + KEY_RUNCODE
            + " TEXT," + KEY_CHK + " TEXT," + KEY_CLASSID + " TEXT," + KEY_CODEX
            + " TEXT," + KEY_Enroll148_STUDENTID + " TEXT," + KEY_PREFIXNAME + " TEXT," + KEY_STUDENTNAME
            + " TEXT," + KEY_STUDENTSURNAME + " TEXT," + KEY_RoomID + " TEXT," + KEY_Number
            + " TEXT," + KEY_PROGRAMNAME + " TEXT," + KEY_STUDENTYEAR + " TEXT," + KEY_ACADYEAR
            + " TEXT," + KEY_SEMESTER + " TEXT," + KEY_FINANCESTATUS + " TEXT," + KEY_PROGRAMABBENG
            + " TEXT," + KEY_ExamType + " TEXT," + KEY_ExamYear
            + " TEXT," + KEY_ExamYearX + " TEXT," + KEY_BYTEDES + " TEXT," + KEY_Comment
            + " TEXT," + KEY_CREATED_AT + " DATETIME)";

    // Meter table create statement
    private static final String CREATE_TABLE_DOCUMENT = "CREATE TABLE "
            + TABLE_DOCUMENT + "(" + KEY_AUTOID + " INTEGER PRIMARY KEY," + KEY_BATCHNO
            + " TEXT," + KEY_REQUESTDATE + " DATE," + KEY_STUDENTCODE
            + " TEXT," + KEY_STUDENTNAME + " TEXT," + KEY_STUDENTSURNAME + " TEXT," + KEY_ACADYEAR
            + " TEXT," + KEY_SEMESTER + " INTEGER," + KEY_FEEID + " TEXT," + KEY_FEEIDNAME
            + " TEXT," + KEY_FEEIDWEB + " TEXT," + KEY_QUANTITY + " TEXT," + KEY_REASON
            + " TEXT," + KEY_REMARK + " TEXT," + KEY_STATUS
            + " TEXT," + KEY_CREATED_AT + " DATETIME)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_EXAM);
        db.execSQL(CREATE_TABLE_DOCUMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCUMENT);

        // create new tables
        onCreate(db);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /**
     * get datetime
     */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    // ------------------------ "Exam" table methods ----------------//
    public void createExam(String Nox, Integer Running, String Coursecode, String Section, String Studentid, String Studentcode, String Coursenameeng, String DateMid,
                           String TimeBegin, String TimeEnd, String Runcode, String chk, String Classid, String Codex, String Enroll148, String Prefixname, String Studentname, String Studentsurname,
                           String RoomID, String Number, String Programname, String Studentyear, String Acadyear, String Semester, String Financestatus, String Programabbeng,
                           String ExamType, String ExamYear, String ExamYearX, String BYTEDES, String Comment) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOX, Nox);
        values.put(KEY_RUNNING, Running);
        values.put(KEY_COURSECODE, Coursecode);
        values.put(KEY_SECTION, Section);
        values.put(KEY_STUDENTID, Studentid);
        values.put(KEY_STUDENTCODE, Studentcode);
        values.put(KEY_COURSENAMEENG, Coursenameeng);
        values.put(KEY_DATEMID, DateMid);
        values.put(KEY_TIMEBEGIN, TimeBegin);
        values.put(KEY_TIMEEND, TimeEnd);
        values.put(KEY_RUNCODE, Runcode);
        values.put(KEY_CHK, chk);
        values.put(KEY_CLASSID, Classid);
        values.put(KEY_CODEX, Codex);
        values.put(KEY_Enroll148_STUDENTID, Enroll148);
        values.put(KEY_PREFIXNAME, Prefixname);
        values.put(KEY_STUDENTNAME, Studentname);
        values.put(KEY_STUDENTSURNAME, Studentsurname);
        values.put(KEY_RoomID, RoomID);
        values.put(KEY_Number, Number);
        values.put(KEY_PROGRAMNAME, Programname);
        values.put(KEY_STUDENTYEAR, Studentyear);
        values.put(KEY_ACADYEAR, Acadyear);
        values.put(KEY_SEMESTER, Semester);
        values.put(KEY_FINANCESTATUS, Financestatus);
        values.put(KEY_PROGRAMABBENG, Programabbeng);
        values.put(KEY_ExamType, ExamType);
        values.put(KEY_ExamYear, ExamYear);
        values.put(KEY_ExamYearX, ExamYearX);
        values.put(KEY_BYTEDES, BYTEDES);
        values.put(KEY_Comment, Comment);
        values.put(KEY_CREATED_AT, getDateTime());

        // Inserting Row
        long id = db.insert(TABLE_EXAM, null, values);

        Log.d(LOG, "New EXAM inserted into sqlite: " + id);
    }

    /**
     * Deleting a all Exam
     */
    public void deleteAllExam() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_EXAM);
    }


    // ------------------------ "Document" table methods ----------------//
    public void createDocument(Integer Autoid, String Batchno, String Requestdate, String Studentcode, String Studentname, String Studentsurname,
                               String Acadyear, Integer Semester, String Feeid, String Feeidname, String Feeidweb, String Quantity, String Reason,
                               String Remark, String Status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_AUTOID, Autoid);
        values.put(KEY_BATCHNO, Batchno);
        values.put(KEY_REQUESTDATE, Requestdate);
        values.put(KEY_STUDENTCODE, Studentcode);
        values.put(KEY_STUDENTNAME, Studentname);
        values.put(KEY_STUDENTSURNAME, Studentsurname);
        values.put(KEY_ACADYEAR, Acadyear);
        values.put(KEY_SEMESTER, Semester);
        values.put(KEY_FEEID, Feeid);
        values.put(KEY_FEEIDNAME, Feeidname);
        values.put(KEY_FEEIDWEB, Feeidweb);
        values.put(KEY_QUANTITY, Quantity);
        values.put(KEY_REASON, Reason);
        values.put(KEY_REMARK, Remark);
        values.put(KEY_STATUS, Status);
        values.put(KEY_CREATED_AT, getDateTime());

        // Inserting Row
        long id = db.insert(TABLE_DOCUMENT, null, values);

        Log.d(LOG, "New Document inserted into sqlite: " + id);
    }

    /**
     * Deleting a all Documents
     */
    public void deleteAllDoc() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_DOCUMENT);
    }
}
