package com.example.ahmetanilgur.fakearmut.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import org.jetbrains.annotations.Nullable;

import static android.content.ContentValues.TAG;

public class UserProvider extends ContentProvider {
    private UserDBHelper mOpenHelper;
    public static final int CODE_USER = 100;
    public static final int CODE_USER_WITH_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {

        /*
         * All paths added to the UriMatcher have a corresponding code to return when a match is
         * found. The code passed into the constructor of UriMatcher here represents the code to
         * return for the root URI. It's common to use NO_MATCH as the code for this case.
         */
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = UserContract.CONTENT_AUTHORITY;

        /*
         * For each type of URI you want to add, create a corresponding code. Preferably, these are
         * constant fields in your class so that you can use them throughout the class and you no
         * they aren't going to change. In Todo, we use CODE_TODO or CODE_TODO_WITH_ID.
         */

        /* This URI is content://com.example.todo/todo/ */
        matcher.addURI(authority, UserContract.UserEntry.TABLE_NAME, CODE_USER);

        /*
         * This URI would look something like content://com.example.todo/todo/1
         * The "/#" signifies to the UriMatcher that if TABLE_NAME is followed by ANY number,
         * that it should return the CODE_TODO_WITH_ID code
         */
        matcher.addURI(authority, UserContract.UserEntry.TABLE_NAME + "/#", CODE_USER_WITH_ID);

        return matcher;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
                long _id = db.insert(UserContract.UserEntry.TABLE_NAME, null, values);
                Log.d(TAG, "insert olayı: "+_id);
                if (_id != -1) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                Log.d(TAG, "insert: returnden once: "+_id);
                return UserContract.UserEntry.buildUserUriWithId(_id);

        }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        mOpenHelper =new UserDBHelper(getContext());
        return mOpenHelper != null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        Cursor cursor;

                String _ID = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{_ID};
                cursor = mOpenHelper.getReadableDatabase().query(
                        /* Table we are going to query */
                        UserContract.UserEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        sortOrder);
        Log.d(TAG, "query: query eyledi"+ cursor.getCount());

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
