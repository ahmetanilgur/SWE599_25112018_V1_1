package com.example.ahmetanilgur.fakearmut.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ahmetanilgur.fakearmut.data.UserContract.UserEntry;

import static android.content.ContentValues.TAG;

public class UserDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "userlist.db";
    public static final Integer DATABASE_VERSION = 2; // veritabanı schemasını degistirirken version yukselt > 2
    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_USERLIST_TABLE = "CREATE TABLE " +
            UserEntry.TABLE_NAME + " ("+
            UserEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                UserEntry.COLUMN_NAME + " TEXT NOT NULL UNIQUE,"+
                UserEntry.COLUMN_JOB + " TEXT NOT NULL,"+
                UserEntry.COLUMN_CITY + " TEXT NOT NULL,"+
                UserEntry.COLUMN_PRICE + " TEXT NOT NULL,"+
                UserEntry.COLUMN_ISEMPLOYER + " TEXT NOT NULL,"+
                UserEntry.COLUMN_MONDAY + " TEXT NOT NULL,"+
                UserEntry.COLUMN_TUESDAY + " TEXT NOT NULL,"+
                UserEntry.COLUMN_WEDNESDAY + " TEXT NOT NULL,"+
                UserEntry.COLUMN_THURSDAY + " TEXT NOT NULL,"+
                UserEntry.COLUMN_FRIDAY + " TEXT NOT NULL,"+
                UserEntry.COLUMN_SATURDAY + " TEXT NOT NULL,"+
                UserEntry.COLUMN_SUNDAY + " TEXT NOT NULL, "+
                "UNIQUE (" +UserEntry.COLUMN_NAME+ ") ON CONFLICT REPLACE)"+
                ";";
        db.execSQL(SQL_CREATE_USERLIST_TABLE);
        Log.d(TAG, "onCreate,db: DB yarattı. ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ UserEntry.TABLE_NAME);
        onCreate(db);
    }
}
