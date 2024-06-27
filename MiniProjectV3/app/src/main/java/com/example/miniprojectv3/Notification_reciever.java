package com.example.miniprojectv3;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class Notification_reciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        NotificationManager notificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent repeating_intent = new Intent(context,MainActivity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100,repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"regular")
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.checkbox_on_background)
                .setContentTitle("Power Memory")
                .setContentText("Remember to check your TODOs and Activities")
                .setAutoCancel(true);

        notificationManager.notify(100,builder.build());

    }
}
