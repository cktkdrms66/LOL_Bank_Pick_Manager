package com.example.lol_ban_pick_manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TeamDBhelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1 ;
    public static final String DBFILE_TEAM = "team.db" ;

    public TeamDBhelper(Context context){
        super(context, DBFILE_TEAM, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TeamDBCtrct.SQL_CREATE_TBL) ;
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db) ;
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // onUpgrade(db, oldVersion, newVersion);
    }
}
