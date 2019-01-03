package com.example.ahmetanilgur.fakearmut.utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.ahmetanilgur.fakearmut.MainActivity;
import com.example.ahmetanilgur.fakearmut.R;

public class NotificationUtils {
    private static final int    WATER_REMINDER_NOTIFICATION_ID         = 1138;
    private static final int    WATER_REMINDER_PENDING_INTENT_ID       = 3417;
    private static final String WATER_REMINDER_NOTIFICATION_CHANNEL_ID = "reminder_notification_channel";

    public static void remindUserBecauseCharging(Context context, String name) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    WATER_REMINDER_NOTIFICATION_CHANNEL_ID,
                    "Primary",
                    NotificationManager.IMPORTANCE_HIGH);

            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,WATER_REMINDER_NOTIFICATION_CHANNEL_ID);
        notificationBuilder
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_home_black_24dp)
                .setContentTitle("You have a new day request!")
                .setContentText("User "+name+" has sent you a day request!")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("User "+name+" has sent you a day request!"))
                .setDefaults(Notification.DEFAULT_VIBRATE)
               // .addAction
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(WATER_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
    }
}
