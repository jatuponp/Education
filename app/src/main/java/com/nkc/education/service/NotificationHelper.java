/*
 * NotificationHelper.java
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

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateFormat;

import com.nkc.education.AppConfig;
import com.nkc.education.ExamActivity;
import com.nkc.education.R;
import com.nkc.education.model.Exam;
import com.nkc.education.model.Task;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Creates Notifications using NotificationCompat to allow for
 * comparability though different API levels
 *
 * @author Dhimitraq Jorgji
 */
public class NotificationHelper {
    /**
     * Basic Text Notification for Task Butler, using NotificationCompat
     *
     * @param context

     */
    public void sendBasicNotification(Context context, Task task) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean vibrate = prefs.getBoolean(AppConfig.VIBRATE_ON_ALARM, true);
        int alarmInterval;
        int alarmUnits;

        if (task.hasFinalDateDue()) {
            alarmInterval = Integer.parseInt(prefs.getString(AppConfig.ALARM_TIME, AppConfig.DEFAULT_ALARM_TIME));
            alarmUnits = Calendar.MINUTE;
        } else {
            alarmInterval = Integer.parseInt(prefs.getString(AppConfig.REMINDER_TIME, AppConfig.DEFAULT_REMINDER_TIME));
            alarmUnits = Calendar.HOUR_OF_DAY;
        }

        Calendar next_reminder = GregorianCalendar.getInstance();
        next_reminder.add(alarmUnits, alarmInterval);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setAutoCancel(true)
                .setContentIntent(getPendingIntent(context, task.getID()))
                .setContentInfo(Task.PRIORITY_LABELS[task.getPriority()])
                .setContentTitle(task.getName())
                .setContentText(task.getDetail())
                .setDefaults(vibrate ? Notification.DEFAULT_ALL : Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)
                .setSmallIcon(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? R.drawable.ic_exam_name : R.drawable.ic_exam_name)
                .setLargeIcon(bitmap)
                .setTicker(task.getName())
                .setPriority(Notification.PRIORITY_MAX)
                .setColor(Color.argb(255,21,149,196))
                .setWhen(System.currentTimeMillis());

        @SuppressWarnings("deprecation")
        Notification notification = builder.getNotification();
        NotificationManager notificationManager = getNotificationManager(context);
        notificationManager.notify(task.getID(), notification);
    }

    /**
     * Basic Text Notification with Ongoing flag enabled for Task Butler, using NotificationCompat
     *
     * @param context
     * @param id      id of task, call task.getID() and pass it to this parameter
     * @deprecated Use sendBasicNotification for all notifications
     */
    public void sendPersistentNotification(Context context, Task task) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentText(task.getNotes())
                .setContentTitle(task.getName())
                .setSmallIcon(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                        R.drawable.ic_exam_name : R.drawable.ic_exam_name)
                .setAutoCancel(true)
                .setContentIntent(getPendingIntent(context, task.getID()))
                .setWhen(System.currentTimeMillis())
                .setOngoing(true)
                .setDefaults(Notification.DEFAULT_ALL);

        Notification notification = builder.getNotification();
        NotificationManager notificationManager = getNotificationManager(context);
        notificationManager.notify(task.getID(), notification);
    }

    //get a PendingIntent
    PendingIntent getPendingIntent(Context context, int id) {
        Intent intent = new Intent(context, ExamActivity.class)
                .putExtra(Task.EXTRA_TASK_ID, id);
        return PendingIntent.getActivity(context, id, intent, 0);
    }

    //get a NotificationManager
    NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * Cancels an existing notification, if user modified the task. Make the
     * actual call from TaskAlarm.cancelNotification(Context, int)
     *
     * @param context
     * @param taskID
     */
    public void cancelNotification(Context context, int taskID) {
        NotificationManager notificationManager = getNotificationManager(context);
        notificationManager.cancel(taskID);
    }

}
