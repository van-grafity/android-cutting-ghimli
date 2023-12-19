package com.app.ivans.ghimli.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.ivans.ghimli.model.CuttingOrderRecordDetail;

import java.util.ArrayList;
import java.util.List;

public class CuttingOrderRecordDBHelper {
    private DBHelper dbHelper;

    public CuttingOrderRecordDBHelper(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void addCuttingOrderRecord(CuttingOrderRecordDetail record) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_CUTTING_ORDER_RECORD_ID, record.getCuttingOrderRecordId());
        values.put(DBHelper.COLUMN_FABRIC_ROLL, record.getFabricRoll());
        db.insert(DBHelper.TABLE_NAME, null, values);
        db.close();
    }

    public List<CuttingOrderRecordDetail> getAllCuttingOrderRecords() {
        List<CuttingOrderRecordDetail> records = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DBHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                CuttingOrderRecordDetail record = new CuttingOrderRecordDetail();
                int columnIndex = cursor.getColumnIndex(DBHelper.COLUMN_CUTTING_ORDER_RECORD_ID);
                if (columnIndex >= 0) {
                    int cuttingOrderRecordId = cursor.getInt(columnIndex);
                    record.setCuttingOrderRecordId(cuttingOrderRecordId);
                } else {
                    // Handle the case where the column doesn't exist
                }
                int fabricRollIndex = cursor.getColumnIndex(DBHelper.COLUMN_FABRIC_ROLL);
                if (fabricRollIndex >= 0) {
                    String fabricRoll = cursor.getString(fabricRollIndex);
                    record.setFabricRoll(fabricRoll);
                } else {
                    // Handle the case where the column doesn't exist
                }
                records.add(record);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return records;
    }
}
