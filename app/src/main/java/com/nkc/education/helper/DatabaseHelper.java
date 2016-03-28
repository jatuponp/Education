package com.nkc.education.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.nkc.education.model.Document;
import com.nkc.education.model.Exam;
import com.nkc.education.model.Inboxs;

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
    public static final String TABLE_EXAM = "exam";
    public static final String TABLE_DOCUMENT = "document";
    public static final String TABLE_INBOX = "inbox";

    // Common column names
    public static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_STUDENTID = "studentid";
    private static final String KEY_STUDENTCODE = "studentcode";
    private static final String KEY_STUDENTNAME = "Studentname";
    private static final String KEY_STUDENTSURNAME = "Studentsurname";
    private static final String KEY_ACADYEAR = "Acadyear";
    private static final String KEY_SEMESTER = "Semester";

    // Exam Table - column names
    public static final String KEY_NOX = "NOX";
    public static final String KEY_RUNNING = "running";
    public static final String KEY_COURSECODE = "coursecode";
    public static final String KEY_SECTION = "section";
    public static final String KEY_COURSENAMEENG = "coursenameeng";
    public static final String KEY_DATEMID = "DateMid";
    public static final String KEY_TIMEBEGIN = "TimeBegin";
    public static final String KEY_TIMEEND = "TimeEnd";
    public static final String KEY_RUNCODE = "runcode";
    public static final String KEY_CHK = "chk";
    public static final String KEY_CLASSID = "Classid";
    public static final String KEY_CODEX = "Codex";
    public static final String KEY_Enroll148_STUDENTID = "Enroll148";
    public static final String KEY_PREFIXNAME = "Prefixname";
    public static final String KEY_RoomID = "RoomID";
    public static final String KEY_Number = "Number";
    public static final String KEY_PROGRAMNAME = "Programname";
    public static final String KEY_STUDENTYEAR = "Studentyear";
    public static final String KEY_FINANCESTATUS = "Financestatus";
    public static final String KEY_PROGRAMABBENG = "Programabbeng";
    public static final String KEY_ExamType = "ExamType";
    public static final String KEY_ExamYear = "ExamYear";
    public static final String KEY_ExamYearX = "ExamYearX";
    public static final String KEY_BYTEDES = "Bytedes";
    public static final String KEY_Comment = "Comment";

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

    // Inbox Table - column names
    private static final String KEY_MFORM = "MForm";
    private static final String KEY_MTO = "MTo";
    private static final String KEY_MSUBJECT = "MSubject";
    private static final String KEY_MBODY = "MBody";
    private static final String KEY_MREAD = "MRead";
    private static final String KEY_MREADDATE = "MReadDate";
    private static final String KEY_MSENDDATE = "MSendDate";

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

    // Document table create statement
    private static final String CREATE_TABLE_DOCUMENT = "CREATE TABLE "
            + TABLE_DOCUMENT + "(" + KEY_AUTOID + " INTEGER PRIMARY KEY," + KEY_BATCHNO
            + " TEXT," + KEY_REQUESTDATE + " DATE," + KEY_STUDENTCODE
            + " TEXT," + KEY_STUDENTNAME + " TEXT," + KEY_STUDENTSURNAME + " TEXT," + KEY_ACADYEAR
            + " TEXT," + KEY_SEMESTER + " INTEGER," + KEY_FEEID + " TEXT," + KEY_FEEIDNAME
            + " TEXT," + KEY_FEEIDWEB + " TEXT," + KEY_QUANTITY + " TEXT," + KEY_REASON
            + " TEXT," + KEY_REMARK + " TEXT," + KEY_STATUS
            + " TEXT," + KEY_CREATED_AT + " DATETIME)";

    // Inbox table create statement
    private static final String CREATE_TABLE_INBOX = "CREATE TABLE "
            + TABLE_INBOX + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MFORM
            + " TEXT," + KEY_MTO + " TEXT," + KEY_MSUBJECT
            + " TEXT," + KEY_MBODY + " TEXT," + KEY_MREAD + " TEXT," + KEY_MREADDATE
            + " DATETIME," + KEY_MSENDDATE + " DATETIME)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_EXAM);
        db.execSQL(CREATE_TABLE_DOCUMENT);
        db.execSQL(CREATE_TABLE_INBOX);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCUMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INBOX);

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

        if(!DateMid.equals("")) {
            String[] Date1 = DateMid.split("/");
            String months = "0" + Date1[1];
            String days = "0" + Date1[0];
            values.put(KEY_DATEMID, Date1[2] + "-" + months.substring(months.length() - 2, months.length()) + "-" + days.substring(days.length() - 2, days.length()));
        }
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

    // getting all examtable
    public List<Exam> getAllExam() {
        ArrayList<Exam> exams = new ArrayList<Exam>();
        String selectQuery = "SELECT * FROM " + TABLE_EXAM + " ORDER BY date(" + KEY_DATEMID + ") ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        //Log.i("selectQuery", selectQuery);

        if (c.moveToFirst()) {
            do {
                Exam e = new Exam();
                e.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                e.setCoursecode(c.getString(c.getColumnIndex(KEY_COURSECODE)));
                e.setSection(c.getString(c.getColumnIndex(KEY_SECTION)));
                e.setCoursenameeng(c.getString(c.getColumnIndex(KEY_COURSENAMEENG)));
                e.setDateMid(c.getString(c.getColumnIndex(KEY_DATEMID)));
                e.setTimeBegin(c.getString(c.getColumnIndex(KEY_TIMEBEGIN)));
                e.setTimeEnd(c.getString(c.getColumnIndex(KEY_TIMEEND)));
                e.setRoomID(c.getString(c.getColumnIndex(KEY_RoomID)));
                e.setRunning(Integer.valueOf(c.getString(c.getColumnIndex(KEY_RUNNING))));

                exams.add(e);
            } while (c.moveToNext());
        }
        this.closeDB();
        return exams;
    }

    public String getFullName(){
        String FullName = "";
        String query = "SELECT * FROM " + TABLE_EXAM + " LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            FullName = cursor.getString(cursor.getColumnIndex(KEY_STUDENTCODE)) + ": " + cursor.getString(cursor.getColumnIndex(KEY_PREFIXNAME)) + cursor.getString(cursor.getColumnIndex(KEY_STUDENTNAME)) + " " +cursor.getString(cursor.getColumnIndex(KEY_STUDENTSURNAME));
        }

        cursor.close();
        return FullName;
    }

    public String getExamYearX(){
        String ExamYearX = "";
        String query = "SELECT * FROM " + TABLE_EXAM + " LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            ExamYearX = "ตารางสอบ" + cursor.getString(cursor.getColumnIndex(KEY_ExamYearX));
        }

        cursor.close();
        return ExamYearX;
    }

    /**
     * Deleting a all Exam
     */
    public void deleteAllExam() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EXAM);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXAM);
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

    // getting all Document
    public List<Document> getAllDoc() {
        ArrayList<Document> docs = new ArrayList<Document>();
        String selectQuery = "SELECT * FROM " + TABLE_DOCUMENT + " ORDER BY " + KEY_FEEID + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        Log.i("selectQuery", selectQuery);

        if (c.moveToFirst()) {
            do {
                Document d = new Document();
                d.setFeeidname(c.getString(c.getColumnIndex(KEY_FEEIDNAME)));
                d.setFeeidweb(c.getString(c.getColumnIndex(KEY_FEEIDWEB)));
                d.setRequestdate(c.getString(c.getColumnIndex(KEY_REQUESTDATE)));
                d.setStatus(c.getString(c.getColumnIndex(KEY_STATUS)));

                docs.add(d);
            } while (c.moveToNext());
        }
        this.closeDB();
        return docs;
    }

    /**
     * Deleting a all Documents
     */
    public void deleteAllDoc() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_DOCUMENT);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCUMENT);
    }

    // ------------------------ "Inbox" table methods ----------------//
    public void createInbox(String MForm, String MTo, String MSubject, String MBody, String MRead, String MReadDate, String MSendate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MFORM, MForm);
        values.put(KEY_MTO, MTo);
        values.put(KEY_MSUBJECT, MSubject);
        values.put(KEY_MBODY, MBody);
        values.put(KEY_MREAD, MRead);
        values.put(KEY_MREADDATE, MReadDate);
        values.put(KEY_MSENDDATE, MSendate);

        // Inserting Row
        long id = db.insert(TABLE_INBOX, null, values);

        Log.d(LOG, "New Inbox inserted into sqlite: " + id);
    }

    // getting all Document
    public List<Inboxs> getAllInbox() {
        ArrayList<Inboxs> inboxs = new ArrayList<Inboxs>();
        String selectQuery = "SELECT * FROM " + TABLE_INBOX + " ORDER BY " + KEY_ID + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        Log.i("selectQuery", selectQuery);

        if (c.moveToFirst()) {
            do {
                Inboxs d = new Inboxs();
                d.setMForm(c.getString(c.getColumnIndex(KEY_MFORM)));
                d.setMTo(c.getString(c.getColumnIndex(KEY_MTO)));
                d.setMSubject(c.getString(c.getColumnIndex(KEY_MSUBJECT)));
                d.setMBody(c.getString(c.getColumnIndex(KEY_MBODY)));
                d.setMRead(c.getString(c.getColumnIndex(KEY_MREAD)));
                d.setMReadDate(c.getString(c.getColumnIndex(KEY_MREADDATE)));
                d.setMSendDate(c.getString(c.getColumnIndex(KEY_MSENDDATE)));

                inboxs.add(d);
            } while (c.moveToNext());
        }
        this.closeDB();
        return inboxs;
    }

    /**
     * Deleting a all Documents
     */
    public void deleteAllInbox() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_INBOX);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_INBOX);
    }
}
