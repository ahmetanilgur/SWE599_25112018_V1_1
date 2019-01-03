package com.example.ahmetanilgur.fakearmut.utilities;

import android.content.Context;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class ReminderTasks {

    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";
    public static void executeTask(Context context, String action){ if (ACTION_DISMISS_NOTIFICATION.equals(action)){
        Log.d(TAG, "executeTask: tıklandı");
    }

    }
}
