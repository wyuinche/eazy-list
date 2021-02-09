package com.blueshadow.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ListDatabaseHelper extends SQLiteOpenHelper {
    public static String NAME = "ToDoList.db";
    public static String TABLE_NAME = "ItemList";
    public static String[] COLUMN_NAMES = {"_id", "date", "memo", "done"};
    public static int VERSION = 1;

    public ListDatabaseHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDB = "create table if not exists " + TABLE_NAME + "("
                + " _id INTEGER PRIMARY KEY, "
                + " date INTEGER, "
                + " memo TEXT, "
                + " done INTEGER)";
        String createIndex = "create unique index if not exists date_index "
                + " on " + TABLE_NAME + "(date)";
        db.execSQL(createDB);
        db.execSQL(createIndex);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > 1) {
            db.execSQL("DROP TABLE IF EXISTS emp");
        }
    }

    public void onReset(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS emp");
        onCreate(db);
    }
}

