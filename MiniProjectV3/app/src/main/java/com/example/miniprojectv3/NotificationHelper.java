package com.example.miniprojectv3;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";
    private NotificationManager mManager;
    DBHelper dbHelper;


    //dbHelper = new DBHelper(this);
    //Cursor cursor = dbHelper.getMedData();





    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }



    public NotificationCompat.Builder getChannelNotification() {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("Reminder!")
                .setContentText(getTitle())
                .setSmallIcon(R.drawable.ic_alarm);
        return builder;

    }

    private CharSequence getTitle() {
        dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getMedData();
        CharSequence text = "Time to take your Medicine";
        if(cursor.getCount()==0){
            return null;
        }
        else {
        while (cursor.moveToNext()) {
            text = "Its time to have your Medicine: ".concat(cursor.getString(0));
        }
        return text;
    }
    }


}

