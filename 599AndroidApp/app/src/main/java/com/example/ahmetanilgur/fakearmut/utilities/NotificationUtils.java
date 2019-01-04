package com.example.ahmetanilgur.fakearmut.utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.ahmetanilgur.fakearmut.AcceptedActivity;
import com.example.ahmetanilgur.fakearmut.MainActivity;
import com.example.ahmetanilgur.fakearmut.R;

public class NotificationUtils {
    public static final int    WATER_REMINDER_NOTIFICATION_ID         = 1138;
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

        Intent acceptIntent = new Intent(context, ActionReceiver.class);
        acceptIntent.putExtra("action","accept");
        PendingIntent acceptPendingIntent = PendingIntent.getBroadcast(context, 1, acceptIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent ignoreIntent = new Intent(context, ActionReceiver.class);
        ignoreIntent.putExtra("action","ignore");
        PendingIntent ignorePendingIntent = PendingIntent.getBroadcast(context, 2, ignoreIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,WATER_REMINDER_NOTIFICATION_CHANNEL_ID);
        notificationBuilder
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_home_black_24dp)
                .setContentTitle("You have a new day request!")
                .setContentText("User "+name+" has sent you a day request!")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("User "+name+" has sent you a day request!"))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .addAction(R.drawable.ic_notifications_black_24dp, "Accept", acceptPendingIntent)
                .addAction(R.drawable.ic_notifications_black_24dp, "Ignore", ignorePendingIntent)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(WATER_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
    }
}
