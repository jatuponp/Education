/*
 * TasksBatlerService.java
 * 
 * Copyright 2012 Jonathan Hasenzahl, James Celona, Dhimitraq Jorgji
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nkc.education.service;

import android.content.Intent;
import android.util.Log;

import com.nkc.education.helper.TasksDataSource;
import com.nkc.education.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * An IntentService that takes care of setting up alarms for Task Butler
 * to remind the user of upcoming events
 *
 * @author Dhimitraq Jorgji
 */
public class TaskExamService extends WakefulIntentService {

    public TaskExamService() {
        super("TaskButlerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        TasksDataSource db = TasksDataSource.getInstance(this); //get access to the instance of TasksDataSource
        TaskAlarm alarm = new TaskAlarm();


        List<Task> tasks = db.getAllTasks(); //Get a list of all the tasks there
        for (Task task : tasks) {
            //Cancel existing alarm
            alarm.cancelAlarm(this, task.getID());

            //Procrastinator and Reminder alarm
//			if(task.isPastDue()){
//				alarm.setReminder(this, task.getID());
//			}
//
//			//handle repeat alarms
//			if(task.isRepeating() && task.isCompleted()){
//				task = alarm.setRepeatingAlarm(this, task.getID());
//			}

            //regular alarms
            //Log.i("Task: ", String.valueOf(task.isCompleted()) + " - " + task.getDateDue() + " >= " + System.currentTimeMillis());
            if (!task.isCompleted() && (task.getDateDue() >= System.currentTimeMillis())) {
                //Log.i("Task in set alarm: ", String.valueOf(task.isCompleted()) + " - " + task.getDateDue());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date resultdate = new Date(task.getDateDue());
                Log.d("Notification Date Due:", task.getID() + " : " + sdf.format(resultdate));
                alarm.setAlarm(this, task);
            }
        }
        super.onHandleIntent(intent);
    }
}