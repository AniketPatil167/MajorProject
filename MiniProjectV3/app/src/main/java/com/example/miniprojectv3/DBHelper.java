package com.example.miniprojectv3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "miniProjectDB.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "activity_log";
    public static final String TABLE_NAME2 = "todo_list";
    public static final String TABLE_NAME3 = "med_remind";
    public static final String TABLE_NAME4 = "emergency_contacts";
    public static final String ACTIVITY_TITLE = "activity_title";
    public static final String ACTIVITY_TIME  = "activity_time";
    public static final String ACTIVITY_DATE = "activity_date";
    public static final String TODO_ID = "todo_id";
    public static final String TODO_TITLE = "todo_title";
    public static final String TODO_DESC = "todo_desc";
    public static final String DUE_DATE = "due_date";
    public static final String MED_TITLE = "med_title";
    public static final String MED_TIME = "med_time";
    public static final String CONTACT1 = "contact1";
//    public static final String CONTACT2 = "contact2";
//    public static final String CONTACT3 = "contact3";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "("
                + ACTIVITY_TITLE + " TEXT NOT NULL,"
                + ACTIVITY_DATE + " TEXT NOT NULL,"
                + ACTIVITY_TIME + " TEXT NOT NULL);";
        String query_todo = "CREATE TABLE " + TABLE_NAME2 + "("
                + TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TODO_TITLE + " TEXT NOT NULL,"
                + TODO_DESC + " TEXT NOT NULL,"
                + DUE_DATE + " TEXT NOT NULL);";
        String query_med = "CREATE TABLE " + TABLE_NAME3 + "("
                + MED_TITLE + " TEXT NOT NULL,"
                + MED_TIME + " TEXT NOT NULL);";
        String query_contacts = "CREATE TABLE " + TABLE_NAME4 + "("
                + CONTACT1 + " TEXT NOT NULL);";
        db.execSQL(query);
        db.execSQL(query_todo);
        db.execSQL(query_med);
        db.execSQL(query_contacts);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME4);
        onCreate(db);
    }


    //ACTIVITY LOG SECTION CODE
    public void addActivity(String activityName, String activityDate, String activityTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ACTIVITY_TITLE, activityName);
        cv.put(ACTIVITY_DATE, activityDate);
        cv.put(ACTIVITY_TIME, activityTime);

        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1) {
            Toast.makeText(context, "Failed to add data in database", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, "Successfully added activity", Toast.LENGTH_LONG).show();
        }
    }

    public Cursor getActivityData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //TODO LIST SECTION CODE
    public void addTodo(String todo_title, String todo_desc, String due_date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TODO_TITLE, todo_title);
        cv.put(TODO_DESC, todo_desc);
        cv.put(DUE_DATE, due_date);

        long result = db.insert(TABLE_NAME2, null, cv);
        if(result == -1) {
            Toast.makeText(context, "FAILED TO ADD TODO ITEM IN DATABASE", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, "SUCCESSFULLY ADDED TO TODO LIST", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateTodo(String row_id, String todo_title, String todo_desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TODO_TITLE, todo_title);
        cv.put(TODO_DESC, todo_desc);

        long result = db.update(TABLE_NAME2, cv, "todo_id=?", new String[] {row_id});
        if(result == -1) {
            Toast.makeText(context, "FAILED TO UPDATE DATA", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "SUCCESSFULLY UPDATED DATA", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneTodo(String todo_title) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME2, "todo_title=?", new String[] {todo_title});

        if(result == -1) {
            Toast.makeText(context, "FAILED TO DELETE TODO", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "SUCCESSFULLY DELETED TODO", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readTodoData() {
        String query = "SELECT * FROM " + TABLE_NAME2;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //MEDICINE REMINDER SECTION
    public void addMedicine(String med_title, String med_time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(MED_TITLE, med_title);
        cv.put(MED_TIME, med_time);

        long result = db.insert(TABLE_NAME3, null, cv);
        if(result == -1) {
            Toast.makeText(context, "FAILED TO ADD MEDICINE IN DATABASE", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, "SUCCESSFULLY ADDED MEDICINE REMINDER", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteMed(String medTitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME3, "med_title=?", new String[] {medTitle});

        if(result == -1) {
            Toast.makeText(context, "FAILED TO DELETE MEDICINE", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "SUCESSFULLY DELETED MEDICINE", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getMedData() {
        String query = "SELECT * FROM " + TABLE_NAME3;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    //EMERGENCY CONTACTS SECTION
    public void addContacts(String contact1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CONTACT1, contact1);

        long result = db.insert(TABLE_NAME4, null, cv);
        if(result == -1) {
            Toast.makeText(context, "FAILED TO ADD MEDICINE IN DATABASE", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, "SUCCESSFULLY ADDED MEDICINE REMINDER", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteContacts(String contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME4, "contact=?", new String[] {contact});

        if(result == -1) {
            Toast.makeText(context, "FAILED TO DELETE CONTACT", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "SUCESSFULLY DELETED CONTACT", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getContacts() {
        String query = "SELECT * FROM " + TABLE_NAME4;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

}
