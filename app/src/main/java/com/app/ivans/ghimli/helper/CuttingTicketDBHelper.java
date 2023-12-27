package com.app.ivans.ghimli.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.ivans.ghimli.model.CuttingTicket;

import java.util.ArrayList;
import java.util.List;

public class CuttingTicketDBHelper {
    private DBHelper dbHelper;

    public CuttingTicketDBHelper(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void addCuttingTicket(CuttingTicket record) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_CUTTING_TICKET_ID, record.getId());
        values.put(DBHelper.COLUMN_SERIAL_NUMBER, record.getSerialNumber());
        db.insert(DBHelper.TABLE_NAME, null, values);
        db.close();
    }

    public List<CuttingTicket> getAllCuttingTickets() {
        List<CuttingTicket> records = new ArrayList<>();
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
                CuttingTicket record = new CuttingTicket();
                int columnIndex = cursor.getColumnIndex(DBHelper.COLUMN_CUTTING_TICKET_ID);
                if (columnIndex >= 0) {
                    int cuttingTicketId = cursor.getInt(columnIndex);
                    record.setId(cuttingTicketId);
                } else {
                    // Handle the case where the column doesn't exist
                }
                int serialNumberIndex = cursor.getColumnIndex(DBHelper.COLUMN_SERIAL_NUMBER);
                if (serialNumberIndex >= 0) {
                    String serialNumber = cursor.getString(serialNumberIndex);
                    record.setSerialNumber(serialNumber);
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
    
    public void deleteCuttingTickets() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TABLE_NAME, null, null);
        db.close();
    }

    public void deleteCuttingTicket(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TABLE_NAME, DBHelper.COLUMN_CUTTING_TICKET_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}
