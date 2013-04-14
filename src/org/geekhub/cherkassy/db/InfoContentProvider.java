package org.geekhub.cherkassy.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;


public class InfoContentProvider extends ContentProvider {

    // database
    private DatabaseHelper database;

    // Used for the UriMacher
    private static final int INFO = 1;
    private static final int INFO_ID = 2;
    private static final int IMAGE = 3;
    private static final int IMAGE_ID = 4;

    private static final String AUTHORITY = "org.geekhub.cherkassy.db";

    private static final String BASE_PATH = "INFOS";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_PATH);

    private static final String IMG_PATH = "IMAGES";
    public static final Uri IMG_URI = Uri.parse("content://" + AUTHORITY
            + "/" + IMG_PATH);


    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, INFO);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", INFO_ID);
        sURIMatcher.addURI(AUTHORITY, IMG_PATH, IMAGE);
        sURIMatcher.addURI(AUTHORITY, IMG_PATH, IMAGE_ID);
    }

    @Override
    public boolean onCreate() {
        database = new DatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // Check if the caller has requested a column which does not exists
        //checkColumns(projection);



        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case INFO:
                queryBuilder.setTables(InfoTable.TABLE_ITEMS);
                break;
            case INFO_ID:
                queryBuilder.setTables(InfoTable.TABLE_ITEMS);
                // Adding the ID to the original query
                queryBuilder.appendWhere(InfoTable.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                break;

            case IMAGE:
                queryBuilder.setTables(ImageTable.TABLE_IMAGES);
                break;
            case IMAGE_ID:
                queryBuilder.setTables(ImageTable.TABLE_IMAGES);
                // Adding the ID to the original query
                queryBuilder.appendWhere(ImageTable.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // Make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        Uri resURI = null;
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        long id = 0;
        switch (uriType) {
            case INFO:
                id = sqlDB.insert(InfoTable.TABLE_ITEMS, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return Uri.parse(BASE_PATH + "/" + id);
            case IMAGE:
                id = sqlDB.insert(ImageTable.TABLE_IMAGES, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return Uri.parse(IMG_PATH + "/" + id);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case INFO:
                rowsDeleted = sqlDB.delete(InfoTable.TABLE_ITEMS, selection,
                        selectionArgs);
                break;
            case INFO_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(InfoTable.TABLE_ITEMS,
                            InfoTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(InfoTable.TABLE_ITEMS,
                            InfoTable.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case INFO:
                rowsUpdated = sqlDB.update(InfoTable.TABLE_ITEMS,
                        values,
                        selection,
                        selectionArgs);
                break;
            case INFO_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(InfoTable.TABLE_ITEMS,
                            values,
                            InfoTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(InfoTable.TABLE_ITEMS,
                            values,
                            InfoTable.COLUMN_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkColumns(String[] projection) {
        String[] available = InfoTable.PROJECTION;

        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            // Check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }

}