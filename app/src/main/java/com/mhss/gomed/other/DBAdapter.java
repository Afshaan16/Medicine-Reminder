package com.mhss.gomed.other;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBAdapter {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "GoMed";

    final Context mContext;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    public DBAdapter(Context context) {
        mContext = context;
        dbHelper = new DatabaseHelper(mContext);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(NotesTable.CREATE_TABLE_TIME_TABLE);
                Log.d("Q", NotesTable.CREATE_TABLE_TIME_TABLE);
                db.execSQL(ReminderTable.CREATE_TABLE_REMINDER);
                Log.d("Q", ReminderTable.CREATE_TABLE_REMINDER);
                db.execSQL(ReminderTimeTable.CREATE_TABLE_REMINDER_TIME);
                Log.d("Q", ReminderTimeTable.CREATE_TABLE_REMINDER_TIME);
                db.execSQL(ReportTable.CREATE_TABLE_REPORT_TABLE);
                Log.d("Q", ReportTable.CREATE_TABLE_REPORT_TABLE);
            } catch (Exception ex) {
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + NotesTable.TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + ReminderTable.TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + ReminderTimeTable.TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + ReportTable.TABLE);
            onCreate(db);
        }
    }

    public DBAdapter open() {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }


    public int insertNotes(NotesTable customer) {
        ContentValues values = new ContentValues();
        values.put(NotesTable.KEY_Notes, customer.getNotes());
        values.put(NotesTable.KEY_DateTime, customer.getDatetime());
        return (int) db.insert(NotesTable.TABLE, null, values);
    }

    public int insertReport(ReportTable customer) {
        ContentValues values = new ContentValues();
        //values.put(ReportTable.KEY_ReportId, customer.getReportId());
        values.put(ReportTable.KEY_Med_Name, customer.getMedName());
        values.put(ReportTable.KEY_DateTime, customer.getDatetime());
        values.put(ReportTable.KEY_Status, customer.getStatus());
        return (int) db.insert(ReportTable.TABLE, null, values);
    }


    public boolean updateNotes(String id, String name, String datetime) {
        ContentValues values = new ContentValues();
        values.put(NotesTable.KEY_Notes, name);
        values.put(NotesTable.KEY_DateTime, datetime);
        int idReturn = (int) db.update(NotesTable.TABLE, values, NotesTable.KEY_NotesId + "=" + id, null);
        if (idReturn > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor getNotes() {
        String countQuery = "SELECT  * FROM " + NotesTable.TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null) {
            return cursor;
        } else {
            return null;
        }
    }

    public Cursor getReport() {
        String countQuery = "SELECT  * FROM " + ReportTable.TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null) {
            return cursor;
        } else {
            return null;
        }
    }


    public int deleteNotes(String id) {
        Log.d("Delete", "TT");
        return (int) db.delete(NotesTable.TABLE, NotesTable.KEY_NotesId + "='" + id + "'", null);
    }

    public int insertReminder(ReminderTable customer) {
        ContentValues values = new ContentValues();
        values.put(ReminderTable.KEY_ReminderName, customer.getReminderName());
        values.put(ReminderTable.KEY_ReminderType, customer.getReminderType());
        values.put(ReminderTable.KEY_ReminderUnit, customer.getReminderUnit());
        values.put(ReminderTable.KEY_OneTimeDose, customer.getOneTimeDose());
        values.put(ReminderTable.KEY_TotalDose, customer.getTotalDose());
        values.put(ReminderTable.KEY_RemainingDose, customer.getRemainingDose());
        values.put(ReminderTable.KEY_DoseCount, customer.getDoseCount());
        values.put(ReminderTable.KEY_RemainingDose, customer.getRemainingDose());
        values.put(ReminderTable.KEY_ReminderStartDate, customer.getStartDate());
        values.put(ReminderTable.KEY_ReminderEndDate, customer.getEndDate());
        values.put(ReminderTable.KEY_ReminderReq, customer.getIsReq());
        return (int) db.insert(ReminderTable.TABLE, null, values);
    }

    public Cursor getReminder() {
        String countQuery = "SELECT  * FROM " + ReminderTable.TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null) {
            return cursor;
        } else {
            return null;
        }
    }

    public Cursor getReminder(String id) {
        String countQuery = "SELECT  * FROM " + ReminderTable.TABLE + " where " + ReminderTimeTable.KEY_ReminderId + " = " + id;
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null) {
            return cursor;
        } else {
            return null;
        }
    }

    public int deleteReminder(String id) {
        Log.d("Delete", "TT");
        return (int) db.delete(ReminderTable.TABLE, ReminderTable.KEY_ReminderId + "='" + id + "'", null);
    }

    public int getPilsRemainder(String id) {
        String countQuery = "SELECT  * FROM " + ReminderTable.TABLE + " where " + ReminderTable.KEY_ReminderId + " = " + id;
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    return Integer.parseInt(cursor.getString(cursor.getColumnIndex(ReminderTable.KEY_RemainingDose)));
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public boolean updatePills(String name , String id, int flag) {
        int rem = getPilsRemainder(id);
        if (rem > 0) {
            String status = "Not Taken";
            if(flag == 1) {
                rem = rem - 1;
                ContentValues values = new ContentValues();
                values.put(ReminderTable.KEY_RemainingDose, rem + "");
                int idReturn = (int) db.update(ReminderTable.TABLE, values, ReminderTable.KEY_ReminderId + "=" + id, null);
                status = "Taken";
            }
            ReportTable rep = new ReportTable();
            rep.setMedName(name);
            rep.setDatetime(Utils.getDatetime());
            rep.setStatus(status);
            insertReport(rep);
        }
        return true;
    }


    public int insertReminderTime(ReminderTimeTable customer) {
        ContentValues values = new ContentValues();
        values.put(ReminderTimeTable.KEY_ReminderId, customer.getReminderId());
        values.put(ReminderTimeTable.KEY_ReminderTime, customer.getReminderTime());
        return (int) db.insert(ReminderTimeTable.TABLE, null, values);
    }

    public Cursor getReminderTime(String id) {
        String countQuery = "SELECT  * FROM " + ReminderTimeTable.TABLE + " where " + ReminderTimeTable.KEY_ReminderId + " = " + id;
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null) {
            return cursor;
        } else {
            return null;
        }
    }

    public int deleteReminderTime(String id) {
        Log.d("Delete", "TT");
        return (int) db.delete(ReminderTimeTable.TABLE, ReminderTimeTable.KEY_ReminderId + "='" + id + "'", null);
    }


}
