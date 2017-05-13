package com.samir.andrew.orchestra.FCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.samir.andrew.orchestra.Activities.Home;
import com.samir.andrew.orchestra.R;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


/**
 * @author ton1n8o - antoniocarlos.dev@gmail.com on 6/13/16.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyGcmListenerService";

    @Override
    public void onMessageReceived(RemoteMessage message) {

        //  FirebaseCrash.report(new Exception(message.getData().get("message")));

        // String image = message.getNotification().getIcon();

        // String sound = message.getNotification().getSound();
        String text = null;
        String title = null;
        String sound = null;
        String id = null;
        String project = null;
        String unit = null;

        if (message.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + message.getData());

            title = message.getData().get("title");
            text = message.getData().get("text");
            sound = message.getData().get("sound");
            id = message.getData().get("id");
            project = message.getData().get("project");
            unit = message.getData().get("unit");
        }

        // Check if message contains a notification payload.
   /*     if (message.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + message.getNotification().getBody());
            text = message.getNotification().getBody();
            title = message.getNotification().getTitle();
            sound = message.getNotification().getSound();


        }*/


        // this.sendNotification(new NotificationData(image, id, title, text, sound));

        if (Integer.parseInt(id) == 1)
            this.sendNotification(new NotificationData(null, Integer.parseInt(id), title, text, sound, project, unit));
        else
            this.sendNotification(new NotificationData(null, Integer.parseInt(id), title, text, sound));
    }

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param notificationData GCM message received.
     */
    private void sendNotification(NotificationData notificationData) {

        Intent intent = new Intent(this, Home.class);
        intent.putExtra(NotificationData.TEXT, notificationData.getTextMessage());

        intent.putExtra("fromNotification", notificationData.getId());

        if (notificationData.getId() == 1) {

            intent.putExtra("project", notificationData.getProject());
            intent.putExtra("unit", notificationData.getUnit());
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = null;
        try {

            notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(URLDecoder.decode(notificationData.getTitle(), "UTF-8"))
                    .setContentText(URLDecoder.decode(notificationData.getTextMessage(), "UTF-8"))
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (notificationBuilder != null) {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationData.getId(), notificationBuilder.build());
        } else {
            Log.d(TAG, "Não foi possível criar objeto notificationBuilder");
        }
    }
}
