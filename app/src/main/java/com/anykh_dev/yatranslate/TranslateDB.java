package com.anykh_dev.yatranslate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TranslateDB {

    private final String DB_NAME = "translation DB";
    private final int DB_VERS = 1;

    //Таблица истории
    private static String HIS_TABLE = "history";
    final static String HIS_TEXT_COLNAME = "text";
    final static String HIS_TRANSLATION_COLNAME = "translation";
    final String CREATE_HIS_TABLE = "create table " + HIS_TABLE + " ("
            + "_id integer primary key autoincrement, "
            + HIS_TEXT_COLNAME + " text, "
            + HIS_TRANSLATION_COLNAME + " text);";

    //Таблица избранного
    private final String FAVS_TABLE = "favorites";
    final static String FAVS_TEXT_COLNAME = "text";
    final static String FAVS_TRANSLATION_COLNAME = "translation";
    final String CREATE_FAVS_TABLE = "create table " + FAVS_TABLE + " ("
            + "_id integer primary key autoincrement, "
            + FAVS_TEXT_COLNAME + " text, "
            + FAVS_TRANSLATION_COLNAME + " text);";

    private Context mCtx;
    TranslateDBHelper tDBh;
    SQLiteDatabase db;
    ContentValues cv = new ContentValues();

    TranslateDB(Context ctx) {
        mCtx = ctx;
    }

    public void addToHistory(String text, String translation){
        dbOpen();
        cv.clear();
        cv.put(HIS_TEXT_COLNAME, text);
        cv.put(HIS_TRANSLATION_COLNAME, translation);
        db.insert(HIS_TABLE, null, cv);
        dbClose();
    }

    public void addToFavorites(String text, String translation){
        dbOpen();
        cv.clear();
        cv.put(FAVS_TEXT_COLNAME, text);
        cv.put(FAVS_TRANSLATION_COLNAME, translation);
        db.insert(FAVS_TABLE, null, cv);
        dbClose();
    }

    public Cursor getHisData(){
        return db.query(HIS_TABLE, null, null, null, null, null, "_id desc");
    }

    public Cursor getFavsData(){
        return db.query(FAVS_TABLE, null, null, null, null, null, null);
    }

    public void dbOpen() {
        tDBh = new TranslateDBHelper(mCtx, DB_NAME, null, DB_VERS);
        db = tDBh.getWritableDatabase();
    }

    public void dbClose() {
        if (tDBh != null)
            tDBh.close();
    }


    private class TranslateDBHelper extends SQLiteOpenHelper {

        TranslateDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
            super(context, name, factory,version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_HIS_TABLE);
            db.execSQL(CREATE_FAVS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}