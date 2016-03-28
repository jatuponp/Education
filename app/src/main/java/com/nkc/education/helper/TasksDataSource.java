package com.nkc.education.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nkc.education.model.Task;

import android.database.SQLException;
import java.util.ArrayList;

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
        open();
        Cursor cursor = db.query(DatabaseHelper.TABLE_EXAM, new String[]{
                        DatabaseHelper.KEY_COURSECODE,
                        DatabaseHelper.KEY_COURSENAMEENG
                },
                DatabaseHelper.KEY_ID + " = " + id,
                null,null,null,null,null
        );
        if(cursor.moveToFirst()){
            Task task = new Task();

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
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_EXAM  + " ORDER BY date(" + DatabaseHelper.KEY_DATEMID + ") ASC";

        open();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                Task task = new Task();

                // adding task to list
                taskList.add(task);
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
