package org.geekhub.cherkassy.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import org.geekhub.cherkassy.objects.Info;

public class InfoTable {

        public static final String TABLE_ITEMS = "items";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_LOGOURL = "logourl";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_WEBSITEURL = "websiteurl";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_CATEGORY = "category";

        public static final String[] PROJECTION = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_ADDRESS,
                COLUMN_LOGOURL,
                COLUMN_PHONE,
                COLUMN_EMAIL,
                COLUMN_WEBSITEURL,
                COLUMN_LATITUDE,
                COLUMN_LONGITUDE,
                COLUMN_CATEGORY

        };

        private static final String DATABASE_CREATE = "CREATE TABLE "
                + TABLE_ITEMS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_ADDRESS + " TEXT NOT NULL, "
                + COLUMN_LOGOURL + " TEXT NOT NULL, "
                + COLUMN_PHONE + " TEXT NOT NULL, "
                + COLUMN_EMAIL + " TEXT NOT NULL, "
                + COLUMN_WEBSITEURL + " TEXT NOT NULL, "
                + COLUMN_LATITUDE + " REAL, "
                + COLUMN_LONGITUDE + " TEXT NOT NULL, "
                + COLUMN_CATEGORY + " TEXT NOT NULL " + ")";

        public static void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
            onCreate(db);
        }

    public static int saveArticleToDB(Context context, Info info)
    {
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CATEGORY,info.getCategory());
        cv.put(COLUMN_PHONE,info.getPhone());
        cv.put(COLUMN_LONGITUDE,info.getLongitude());
        cv.put(COLUMN_WEBSITEURL,info.getWebsiteURL());
        cv.put(COLUMN_LATITUDE,info.getLatitude());
        cv.put(COLUMN_ADDRESS,info.getAddress());
        cv.put(COLUMN_NAME,info.getName());
        cv.put(COLUMN_LOGOURL,info.getLogoURL());
        cv.put(COLUMN_EMAIL,info.getEmail());

        Uri uri = context.getContentResolver().insert(InfoContentProvider.CONTENT_URI, cv);
        String id = uri.getLastPathSegment();
        return Integer.valueOf(id);
    }

    }
