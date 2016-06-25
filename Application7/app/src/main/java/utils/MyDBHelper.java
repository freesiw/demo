package utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/6/25.
 */
public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "hotpoint.db";
    private static final int VERSION = 1;

    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1 = "create table if not exists coll (_id integer primary key autoincrement not null," +
                "title text (100)," +
                "desc text(200)," +
                "rcount text(10)," +
                "time text(20));";
        db.execSQL(sql1);
        String sql2 = "create table if not exists hist (_id integer primary key autoincrement not null," +
                "title text (100)," +
                "desc text(200)," +
                "rcount text(10)," +
                "time text(20));";
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
