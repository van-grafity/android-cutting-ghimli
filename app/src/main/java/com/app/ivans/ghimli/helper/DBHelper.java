package com.app.ivans.ghimli.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cutting_order_db";
    private static final int DATABASE_VERSION = 1;

    // Nama tabel dan kolom
    public static final String TABLE_NAME = "cutting_order_records";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CUTTING_ORDER_RECORD_ID = "cutting_order_record_id";
    public static final String COLUMN_FABRIC_ROLL = "fabric_roll";

    // Query untuk membuat tabel
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CUTTING_ORDER_RECORD_ID + " INTEGER, " +
                    COLUMN_FABRIC_ROLL + " TEXT" +
                    ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
