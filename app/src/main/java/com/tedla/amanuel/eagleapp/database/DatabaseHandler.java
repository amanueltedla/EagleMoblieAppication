package com.tedla.amanuel.eagleapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dVentus-hq on 6/23/2017.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "CourseNames"; // the name of our database
    private static final int DB_VERSION = 1; // the version of the database
    Context context;

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE VACANCY ("
                + "_id TEXT PRIMARY KEY, "
                + "CODE TEXT,"
                + "POSITION TEXT,"
                + "DESCRIPTION TEXT,"
                + "EXPERIENCE TEXT,"
                + "QUALIFICATIONS TEXT,"
                + "STATUS TEXT,"
                + "DUE_DATE TEXT,"
                + "SALARY TEXT,"
                + "NUMBER_REQUIRED TEXT,"
                + "CONTACT TEXT,"
                + "MOBILE TEXT,"
                + "EMAIL TEXT,"
                + "LEVEL TEXT,"
                + "SEEN INTEGER"
                + "CATEGORY TEXT);");
        db.execSQL("CREATE TABLE NEWS ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "HEADER TEXT,"
                + "DETAIL TEXT);");
        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public static void insertVacancy(SQLiteDatabase db,
                                     String id,
                                     String code,
                                     String position,
                                     String description,
                                     String experience,
                                     String qualification,
                                     String status,
                                     String dueDate,
                                     String salary,
                                     String numberReg,
                                     String contact,
                                     String mobile,
                                     String email,
                                     String level,
                                     int seen,
                                     String category){

        ContentValues MeterFileValues = new ContentValues();
        MeterFileValues.put("_id", id);
        MeterFileValues.put("CODE", code);
        MeterFileValues.put("POSITION", position);
        MeterFileValues.put("DESCRIPTION", description);
        MeterFileValues.put("EXPERIENCE", experience);
        MeterFileValues.put("QUALIFICATIONS", qualification);
        MeterFileValues.put("STATUS", status);
        MeterFileValues.put("DUE_DATE", dueDate);
        MeterFileValues.put("SALARY", salary);
        MeterFileValues.put("NUMBER_REQUIRED", numberReg);
        MeterFileValues.put("CONTACT", contact);
        MeterFileValues.put("MOBILE", mobile);
        MeterFileValues.put("EMAIL", email);
        MeterFileValues.put("LEVEL", level);
        MeterFileValues.put("SEEN", seen);
        MeterFileValues.put("CATEGORY", category);
        db.insert("METER_FILE", null,MeterFileValues);

    }


}
