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
    private static final String DATABASE_NAME = "meterDorm";

    // Table Names
    private static final String TABLE_ROOM = "room";
    private static final String TABLE_METER = "meter";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // Room Table - column names
    private static final String KEY_DORM_ID = "dorm_id";
    private static final String KEY_CAPACITY = "capacity";
    private static final String KEY_TOILET = "toilet";
    private static final String KEY_ROOM_TYPE = "room_type";
    private static final String KEY_ROOM_STATUS = "room_status";

    // Meter Table - column names
    private static final String KEY_MONTHS = "months";
    private static final String KEY_TERMS = "terms";
    private static final String KEY_YEARS = "years";
    private static final String KEY_ROOM_ID = "room_id";
    private static final String KEY_METER_START = "meter_start";
    private static final String KEY_METER_END = "meter_end";
    private static final String KEY_PAY_TYPE = "pay_type";

    // Table Create Statements
    // Room table create statement
    private static final String CREATE_TABLE_ROOM = "CREATE TABLE "
            + TABLE_ROOM + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ROOM_ID + " TEXT," + KEY_DORM_ID
            + " INTEGER," + KEY_CAPACITY + " INTEGER," + KEY_TOILET
            + " INTEGER," + KEY_ROOM_TYPE + " TEXT," + KEY_ROOM_STATUS
            + " INTEGER" + ")";

    // Meter table create statement
    private static final String CREATE_TABLE_METER = "CREATE TABLE "
            + TABLE_METER + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ROOM_ID
            + " TEXT," + KEY_MONTHS + " INTEGER," + KEY_TERMS
            + " INTEGER," + KEY_YEARS + " TEXT," + KEY_METER_START + " TEXT," + KEY_METER_END
            + " TEXT," + KEY_PAY_TYPE + " INTEGER," + KEY_CREATED_AT + " DATETIME)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_ROOM);
        db.execSQL(CREATE_TABLE_METER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_METER);

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
}
