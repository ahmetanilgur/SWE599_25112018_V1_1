package com.example.ahmetanilgur.fakearmut.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class UserContract {
    public static final String CONTENT_AUTHORITY = "com.example.ahmetanilgur.fakearmut";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_USER = "user";

    public static final class UserEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();
        public static final String TABLE_NAME = "userList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_JOB = "job";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_ISEMPLOYER = "isEmployer";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_MONDAY = "monday";
        public static final String COLUMN_TUESDAY = "tuesday";
        public static final String COLUMN_WEDNESDAY = "wednesday";
        public static final String COLUMN_THURSDAY = "thursday";
        public static final String COLUMN_FRIDAY = "friday";
        public static final String COLUMN_SATURDAY = "saturday";
        public static final String COLUMN_SUNDAY = "sunday";

        public static Uri buildUserUriWithId(long id) {
            return CONTENT_URI.buildUpon()
                .appendPath(Long.toString(id)).build();
        }

    }
}