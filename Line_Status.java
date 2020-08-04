package com.example.inline_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.example.inline_app.R.layout.activity_line__status;

public class Line_Status extends AppCompatActivity {

    private TextView mShowPlace;
    private int mPlace = Integer.parseInt(String.valueOf(mShowPlace));
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_line__status);
        mShowPlace = (TextView) findViewById(R.id.tv_line_status);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (savedInstanceState != null) {
            String place = savedInstanceState.getString("place");
            if (mShowPlace != null)
                mShowPlace.setText(place);
        }
        /*
        *
        *   Queue code
        *
         */
        if (mPlace == 0) {
            mShowPlace.setText("0");
            createNotificationChannel();
            deliverNotification(Line_Status.this);
        }
    }



    public void createNotificationChannel() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "It's your turn", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("You are up, please go to the desk");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void deliverNotification(Context context) {
        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_wait_over)
                .setContentTitle("It's Your Turn!")
                .setContentText("Please see yourself to the desk")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}