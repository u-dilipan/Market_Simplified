package com.example.market_simplified;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {
    DBHelper dbHelper;

    public DBAdapter(Context context){
        dbHelper = new DBHelper(context);
    }

    public String getComment(int id)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+ DBHelper.COMMENTS + " FROM " +
                DBHelper.TABLE_NAME + " WHERE " + DBHelper.ID + " = " + id + ";", null);
        assert cursor != null;
        cursor.moveToFirst();
        String comments = "";
        while (!cursor.isAfterLast())
        {
            comments = cursor.getString(cursor.getColumnIndex(DBHelper.COMMENTS));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return comments;
    }

    public void insertComment(int id, String comment)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COMMENTS, comment);
        String[] whereArgs = { Integer.toString(id) };
        int inserted = db.update(DBHelper.TABLE_NAME , contentValues, DBHelper.ID + "=?", whereArgs);
        if(inserted == 0) {
            contentValues.put(DBHelper.ID, id);
            db.insert(DBHelper.TABLE_NAME, null , contentValues);
        }
        db.close();
    }

    static class DBHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "MS";
        private static final String TABLE_NAME = "user";
        private static final int DATABASE_Version = 1;
        private static final String ID = "id";
        private static final String COMMENTS = "comment";
        private static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME +
                " (" + ID +" INTEGER PRIMARY KEY," + COMMENTS + " VARCHAR(255));";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+ TABLE_NAME;

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try
            {
                db.execSQL(CREATE_TABLE);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1)
        {
            try
            {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
