package com.nailonline.client.fcm;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nailonline.client.MainActivity;
import com.nailonline.client.R;
import com.nailonline.client.SplashActivity;
import com.nailonline.client.entity.JobState;
import com.nailonline.client.order.JobListActivity;
import com.nailonline.client.order.OrderTabActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by Olga Riabkova on 15.11.2016.
 * Cheese
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            sendNotification(remoteMessage.getData().toString());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            sendNotification(remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.


    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        // из сообщения вычленяем jobId
        // передаем его в активити, чтобы в фрагменте на нем отобразить фокус
        // {stateId=9, jobId=94}

        int stateId = 0;
        int jobId = 0;
        long startDate = 0;
        try {
            JSONObject jsonObject = new JSONObject(messageBody);
            stateId = jsonObject.getInt("stateId");
            jobId = jsonObject.getInt("jobId");
            startDate = jsonObject.getLong("startDate");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        stackBuilder.addNextIntent(mainActivityIntent);
        Intent ordersIntent = new Intent(this, OrderTabActivity.class);
        stackBuilder.addNextIntent(ordersIntent);
        Intent jobListIntent = new Intent(this, JobListActivity.class);
        jobListIntent.putExtra(JobListActivity.JOB_KEY, jobId);
        stackBuilder.addNextIntent(jobListIntent);
        Intent splashIntent = new Intent(this, SplashActivity.class);
        splashIntent.putExtra(SplashActivity.FROM_PUSH, true);
        stackBuilder.addNextIntent(splashIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

/*
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(Constants.INTENT_JOB_ID, jobId);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
*/
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        StringBuilder sbText = new StringBuilder();
        sbText.append("Запись к мастеру на ");
        sbText.append(dateFormat.format(startDate * 1000));
        sbText.append(" ");
        sbText.append(JobState.getById(stateId).getText().toLowerCase());

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("NailOnline")
                .setSmallIcon(R.drawable.ic_notif)
                .setColor(ContextCompat.getColor(MyFirebaseMessagingService.this, R.color.colorAccent))
                .setContentText(sbText.toString())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(sbText.toString()))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
