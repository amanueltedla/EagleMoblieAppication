package com.tedla.amanuel.eagleapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tedla.amanuel.eagleapp.model.JobCategoryModel;
import com.tedla.amanuel.eagleapp.model.NewsModel;
import com.tedla.amanuel.eagleapp.model.UserModel;
import com.tedla.amanuel.eagleapp.model.VacancyModel;

import java.util.ArrayList;
import java.util.List;

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
        db.execSQL("CREATE TABLE FREE_VACANCY ("
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
                + "SEEN INTEGER,"
                + "CATEGORY TEXT);");
        db.execSQL("CREATE TABLE PAID_VACANCY ("
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
                + "SEEN INTEGER,"
                + "CATEGORY TEXT);");
        db.execSQL("CREATE TABLE NEWS ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "HEADER TEXT,"
                + "DETAIL TEXT);");
        db.execSQL("CREATE TABLE USER ("
                + "USERNAME TEXT PRIMARY KEY, "
                + "PASSWORD TEXT);");

        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public static void insertUser(SQLiteDatabase db, UserModel userModel){
        ContentValues userInfoValues = new ContentValues();
        userInfoValues.put("USERNAME",userModel.getUser_name());
        userInfoValues.put("PASSWORD",userModel.getPassword());
        db.insert("USER", null, userInfoValues);
    }
    public static void insertNews(SQLiteDatabase db, List<NewsModel> newsmodels){
        for(NewsModel news:newsmodels) {
            ContentValues newsInfoValues = new ContentValues();
            newsInfoValues.put("HEADER", news.getTitle());
            newsInfoValues.put("DETAIL", news.getContent());
            db.insert("NEWS", null, newsInfoValues);
        }
    }
    public static UserModel getUser(SQLiteDatabase db){
        Cursor cursor = db.query ("USER",
                new String[] {"USERNAME","PASSWORD"},
               null,
                null,
                null, null,null);
        UserModel userModel = new UserModel();
        if(cursor.moveToFirst()){
            userModel.setUser_name(cursor.getString(0));
            userModel.setPassword(cursor.getString(1));
            return userModel;
        }
        else {
            return null;
        }

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
        MeterFileValues.put("SEEN", 0);
        MeterFileValues.put("CATEGORY", category);
        db.insert("VACANCY", null,MeterFileValues);
    }

    public static void insertFreeVacancy(SQLiteDatabase db,List<VacancyModel> vacancyModels){
        for(VacancyModel vm:vacancyModels) {
            ContentValues MeterFileValues = new ContentValues();
            MeterFileValues.put("_id", vm.get_id());
            MeterFileValues.put("CODE", vm.getCode());
            MeterFileValues.put("POSITION", vm.getPosition());
            MeterFileValues.put("DESCRIPTION", vm.getDescription());
            MeterFileValues.put("EXPERIENCE", vm.getExprience());
            MeterFileValues.put("QUALIFICATIONS", vm.getQualifications());
            MeterFileValues.put("STATUS", vm.getStatus());
            MeterFileValues.put("DUE_DATE", vm.getDue_date());
            MeterFileValues.put("SALARY", vm.getSalary());
            MeterFileValues.put("NUMBER_REQUIRED", vm.getNumber_required());
            MeterFileValues.put("CONTACT", vm.getContact());
            MeterFileValues.put("MOBILE", vm.getMobile());
            MeterFileValues.put("EMAIL", vm.getEmail());
            MeterFileValues.put("LEVEL", vm.getLevel());
            MeterFileValues.put("SEEN", vm.getSeen());
            MeterFileValues.put("CATEGORY", vm.getCategory());
            db.insert("FREE_VACANCY", null, MeterFileValues);
        }
    }

    public static void insertPaidVacancy(SQLiteDatabase db,List<VacancyModel> vacancyModels){
        for(VacancyModel vm:vacancyModels) {
            ContentValues MeterFileValues = new ContentValues();
            MeterFileValues.put("_id", vm.get_id());
            MeterFileValues.put("CODE", vm.getCode());
            MeterFileValues.put("POSITION", vm.getPosition());
            MeterFileValues.put("DESCRIPTION", vm.getDescription());
            MeterFileValues.put("EXPERIENCE", vm.getExprience());
            MeterFileValues.put("QUALIFICATIONS", vm.getQualifications());
            MeterFileValues.put("STATUS", vm.getStatus());
            MeterFileValues.put("DUE_DATE", vm.getDue_date());
            MeterFileValues.put("SALARY", vm.getSalary());
            MeterFileValues.put("NUMBER_REQUIRED", vm.getNumber_required());
            MeterFileValues.put("CONTACT", vm.getContact());
            MeterFileValues.put("MOBILE", vm.getMobile());
            MeterFileValues.put("EMAIL", vm.getEmail());
            MeterFileValues.put("LEVEL", vm.getLevel());
            MeterFileValues.put("SEEN", vm.getSeen());
            MeterFileValues.put("CATEGORY", vm.getCategory());
            db.insert("PAID_VACANCY", null, MeterFileValues);
        }
    }
   public List<NewsModel> getNews(SQLiteDatabase db){
       List<NewsModel> newsModels = new ArrayList<>();
       Cursor cursor = db.query ("NEWS",
               new String[] {"HEADER","DETAIL"},
               null,
               null,
               null, null,null);

       if(cursor.moveToFirst()){
           for (int k = 0; k < cursor.getCount(); k++) {
               NewsModel newsModel = new NewsModel();
               newsModel.setTitle(cursor.getString(0));
               newsModel.setContent(cursor.getString(1));
               newsModels.add(newsModel);
               cursor.moveToNext();
           }
       }
       return  newsModels;

   }
    public List<VacancyModel>  getFreeVacancy(SQLiteDatabase db) {
        Cursor cursor = db.query("FREE_VACANCY",
                new String[]{"_id"
                        , "CODE"
                        , "POSITION"
                        , "DESCRIPTION"
                        , "EXPERIENCE"
                        , "QUALIFICATIONS"
                        , "STATUS"
                        , "DUE_DATE"
                        , "SALARY"
                        , "NUMBER_REQUIRED"
                        , "CONTACT"
                        , "MOBILE"
                        , "EMAIL"
                        , "LEVEL"
                        , "SEEN"
                        , "CATEGORY"},
                null,
                null,
                null, null, null);
        List<VacancyModel> vacancyModels = new ArrayList<>();
        if (cursor.moveToFirst()) {
            for (int k = 0; k < cursor.getCount(); k++) {
                VacancyModel vacancyModel = new VacancyModel();
                vacancyModel.set_id(cursor.getString(0));
                vacancyModel.setCode(cursor.getString(1));
                vacancyModel.setPosition(cursor.getString(2));
                vacancyModel.setDescription(cursor.getString(3));
                vacancyModel.setExprience(cursor.getString(4));
                vacancyModel.setQualifications(cursor.getString(5));
                vacancyModel.setStatus(cursor.getString(6));
                vacancyModel.setDue_date(cursor.getString(7));
                vacancyModel.setSalary(cursor.getString(8));
                vacancyModel.setNumber_required(cursor.getString(9));
                vacancyModel.setContact(cursor.getString(10));
                vacancyModel.setMobile(cursor.getString(11));
                vacancyModel.setEmail(cursor.getString(12));
                vacancyModel.setLevel(cursor.getString(13));
                vacancyModel.setSeen(cursor.getInt(14));
                vacancyModel.setCategory(cursor.getString(15));
                vacancyModels.add(vacancyModel);
                cursor.moveToNext();
            }
        }
        return vacancyModels;
    }

    public List<VacancyModel>  getPaidVacancy(SQLiteDatabase db) {
        Cursor cursor = db.query("PAID_VACANCY",
                new String[]{"_id"
                        , "CODE"
                        , "POSITION"
                        , "DESCRIPTION"
                        , "EXPERIENCE"
                        , "QUALIFICATIONS"
                        , "STATUS"
                        , "DUE_DATE"
                        , "SALARY"
                        , "NUMBER_REQUIRED"
                        , "CONTACT"
                        , "MOBILE"
                        , "EMAIL"
                        , "LEVEL"
                        , "SEEN"
                        , "CATEGORY"},
                null,
                null,
                null, null, null);
        List<VacancyModel> vacancyModels = new ArrayList<>();
        if (cursor.moveToFirst()) {
            for (int k = 0; k < cursor.getCount(); k++) {
                VacancyModel vacancyModel = new VacancyModel();
                vacancyModel.set_id(cursor.getString(0));
                vacancyModel.setCode(cursor.getString(1));
                vacancyModel.setPosition(cursor.getString(2));
                vacancyModel.setDescription(cursor.getString(3));
                vacancyModel.setExprience(cursor.getString(4));
                vacancyModel.setQualifications(cursor.getString(5));
                vacancyModel.setStatus(cursor.getString(6));
                vacancyModel.setDue_date(cursor.getString(7));
                vacancyModel.setSalary(cursor.getString(8));
                vacancyModel.setNumber_required(cursor.getString(9));
                vacancyModel.setContact(cursor.getString(10));
                vacancyModel.setMobile(cursor.getString(11));
                vacancyModel.setEmail(cursor.getString(12));
                vacancyModel.setLevel(cursor.getString(13));
                vacancyModel.setSeen(cursor.getInt(14));
                vacancyModel.setCategory(cursor.getString(15));
                vacancyModels.add(vacancyModel);
                cursor.moveToNext();
            }
        }
        return vacancyModels;
    }

    public static void clearPaidVacancy(SQLiteDatabase db){
        db.execSQL("delete from "+ "PAID_VACANCY");
    }

    public static void clearFreeVacancy(SQLiteDatabase db){
        db.execSQL("delete from "+ "FREE_VACANCY");
    }
    public static void clearUser(SQLiteDatabase db){
        db.execSQL("delete from "+ "USER");
    }
    public static void clearNews(SQLiteDatabase db){
        db.execSQL("delete from "+ "NEWS");
    }


    public static void updateSeenFreeVacancy(SQLiteDatabase db, String vacancyID){
        ContentValues vacancyUpdate = new ContentValues();
        vacancyUpdate.put("SEEN",1);
        db.update("FREE_VACANCY",
                vacancyUpdate,
                "_id = ?",
                new String[]{vacancyID});
    }
    public static void  updateSeenPaidVacancy(SQLiteDatabase db, String vacancyID){
        ContentValues vacancyUpdate = new ContentValues();
        vacancyUpdate.put("SEEN",1);
        db.update("PAID_VACANCY",
                vacancyUpdate,
                "_id = ?",
                new String[] {vacancyID});
    }

}
