package org.geekhub.cherkassy.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import org.geekhub.cherkassy.objects.Info;

public class ImageTable {

    public static final String TABLE_IMAGES = "images";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEMID = "itemid";
    public static final String COLUMN_URL = "url";


    public static final String[] PROJECTION = {
            COLUMN_ID,
            COLUMN_ITEMID,
            COLUMN_URL
    };

    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_IMAGES + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_URL + " TEXT NOT NULL ,"
            + " FOREIGN KEY ("+COLUMN_ITEMID+") REFERENCES "+InfoTable.TABLE_ITEMS+" ("+InfoTable.COLUMN_ID+"), " + ")";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
        onCreate(db);
    }

    public static void saveToDB(Context context, Info info)
    {
        ContentValues cv = new ContentValues();

        context.getContentResolver().insert(InfoContentProvider.CONTENT_URI, cv);

    }

}
