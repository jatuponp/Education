package com.nkc.education.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nkc.education.model.Task;

import android.database.SQLException;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Jumpon-pc on 28/3/2559.
 */
public class TasksDataSource {

    private SQLiteDatabase db;
    private DatabaseHelper helper;
    private static TasksDataSource instance;

    private TasksDataSource(){

    }

    private TasksDataSource(Context context){
        helper = new DatabaseHelper(context);
    }

    /**
     * Call this to get access to the instance of TasksDataSource Singleton
     * @param context
     * @return instance of TasksDataSource
     */
    public static synchronized TasksDataSource getInstance(Context context){
        instance = new TasksDataSource(context);
        return instance;
    }

    private void open() throws SQLException{
        db = helper.getWritableDatabase();
    }

    private void close(){
        helper.close();
    }

    /*********************************************************************
     * Task																 *
     *********************************************************************/
    public Task getTask(int id){
        Date date = new Date();
        open();
        Cursor cursor = db.query(DatabaseHelper.TABLE_EXAM, new String[]{
                        DatabaseHelper.KEY_ID,
                        DatabaseHelper.KEY_COURSECODE,
                        DatabaseHelper.KEY_COURSENAMEENG,
                        DatabaseHelper.KEY_DUE_DATE
                },
                DatabaseHelper.KEY_ID + " = " + id,
                null,null,null,null,null
        );
        if(cursor.moveToFirst()){
            /*DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = iso8601Format.parse(cursor.getString(3));
            } catch (ParseException e) {
                Log.e("ERROR:", "Parsing ISO8601 datetime failed", e);
            }
            long dueDate = date.getTime();*/
            Log.d("getTask: ", String.valueOf(cursor.getInt(0)) + " " + cursor.getString(1) + " " + cursor.getString(3));

            Task task = new Task(
                    cursor.getInt(0),
                    cursor.getString(1) + " " + cursor.getString(2),
                    false,
                    System.currentTimeMillis(),
                    1
            );

            close();
            cursor.close();
            return task;
        }else {
            close();
            cursor.close();
            return null;
        }
    }

    public ArrayList<Task> getAllTasks(){
        ArrayList<Task> taskList = new ArrayList<Task>();

        //Select all Exam
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_EXAM + " WHERE "
                + DatabaseHelper.KEY_DATEMID + " != ''"
                + " ORDER BY date(" + DatabaseHelper.KEY_DATEMID + ") ASC";

        Date date = new Date();

        open();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                Calendar t = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                long beforOneHour = 0;
                long beforOneDay = 0;
                try {
                    Date dt = sdf.parse(cursor.getString(11));
                    t.setTime(dt);
                    t.add(Calendar.HOUR, -1);
                    beforOneHour = t.getTimeInMillis();

                    //
                    t.add(Calendar.DATE, -1);
                    t.set(Calendar.HOUR_OF_DAY, 20);
                    t.set(Calendar.MINUTE, 0);
                    beforOneDay = t.getTimeInMillis();
                } catch (ParseException e){
                    Log.e("ERROR:", e.toString());
                }

                //convert timeinmillis to date string
                Date resultdate = new Date(beforOneDay);
                Log.d("After beforOneDay:", sdf.format(resultdate));

                Date resultdate1 = new Date(beforOneHour);
                Log.d("After beforOneHour:", sdf.format(resultdate1));

                Task task = new Task(
                        cursor.getInt(0),
                        cursor.getString(3),
                        false,
                        beforOneHour,
                        1
                        );

                // adding task to list
                taskList.add(task);

                Task task1 = new Task(
                        cursor.getInt(0),
                        cursor.getString(3),
                        false,
                        beforOneDay,
                        1
                );

                // adding task to list
                taskList.add(task1);
            }while (cursor.moveToNext());
        }

        cursor.close();
        close();
        return taskList;
    }

    public int updateTask(Task task){
        open();

        ContentValues values = new ContentValues();

        int i = db.update(DatabaseHelper.TABLE_EXAM, values,
                DatabaseHelper.KEY_ID + " = ?", new String[] { String.valueOf(task.getID())});
        close();
        return i;
    }


}
