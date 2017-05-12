package com.samir.andrew.orchestra.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by andre on 06-May-17.
 */

public class DBhelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "DBase";

    private static final String KEY = "key";
    private static final String BODY = "body";
    private static final String DELIVERED = "delivered";
    private static final String SEEN = "seen";
    private static final String SENDER = "sender";
    private static final String TIME = "time";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createNewTable(String tableName) {

        SQLiteDatabase db = this.getWritableDatabase();


        String CREATE_NEW_TABLE = "CREATE TABLE IF NOT EXISTS "
                + tableName + "( " + KEY + " TEXT PRIMARY KEY, " + BODY + " TEXT , "
                + DELIVERED + " INTEGER  , " + SEEN + " INTEGER  , " + SENDER
                + " INTEGER  ," + TIME + " TEXT )";

        db.execSQL(CREATE_NEW_TABLE);
    }

    public void deleteUnitTable(String tableNAme) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + tableNAme);

    }

    public boolean ADD_NEW_MSG(String key, String body, Integer delivered, Integer seen, Integer sender, String time, String tableName) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY, key);
        values.put(BODY, body);
        values.put(DELIVERED, delivered);
        values.put(SEEN, seen);
        values.put(SENDER, sender);
        values.put(TIME, time);

        // insert row
        long user_row = db.insert(tableName, null, values);
        if (user_row == -1)
            return false;
        else
            return true;

    }

    public Cursor getAllMSgs(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + tableName, null);
    }


}
