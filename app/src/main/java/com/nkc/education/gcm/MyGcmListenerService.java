package com.nkc.education.gcm;

import com.google.android.gms.gcm.GcmListenerService;
import com.nkc.education.AppConfig;
import com.nkc.education.MainActivity;
import com.nkc.education.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by Jumpon-pc on 8/10/2558.
 */
public class MyGcmListenerService extends GcmListenerService {
    private static final String TAG = "MyGcmListenerService";

    /**
         * Called when message is received.
         *
         * @param from SenderID of the sender.
         * @param data Data bundle containing message data as key/value pairs.
         *             For Set of keys use data.keySet().
         */
        // [START receive_message]
        @Override
        public void onMessageReceived(String from, Bundle data) {
            String message = data.getString("message");
            Log.d(TAG, "From: " + from);
            Log.d(TAG, "Message: " + message);

            if (from.startsWith("/topics/")) {
                // message received from some topic.
            } else {
                // normal downstream message.
            }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        sendNotification(message);
        // [END_EXCLUDE]
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        boolean vibrate = prefs.getBoolean(AppConfig.VIBRATE_ON_ALARM, true);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ic_launcher);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(bitmap)
                .setSmallIcon(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? R.drawable.ic_exam_name : R.drawable.ic_exam_name)
                .setContentTitle("NKC Education")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setDefaults(vibrate ? Notification.DEFAULT_ALL : Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)
                .setContentIntent(pendingIntent)
                .setColor(Color.argb(255, 21, 149, 196))
                .setPriority(Notification.PRIORITY_HIGH);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
