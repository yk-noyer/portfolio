package com.example.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    static final private String DBNAME = "sample.sqlite";
    static final private int VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (db != null) {
            db.execSQL("CREATE TABLE books(" + "isbn TEXT PRIMARY KEY,title TEXT,price INTEGER)");
//            db.execSQL("INSERT INTO books(isbn,title,price)"+
//                    "VALUES('978-4-7620-2197-8','児童サービス論','1800')");

            String[] isbns = {"978-4-7620-2197-8", "978-4-8204-1224-3",
                    "978-4-623-07631-4", "978-4-7620-2889-2",
                    "978-4-7620-2192-3", "978-4-478-11219-9"};

            String[] titles = {"児童サービス論", "図書館員のための生涯学習論",
                    "よくわかる生涯学習", "情報資源組織論",
                    "図書館情報技術論", "14歳からのプログラミング"};

            int[] prices = {1800, 1900, 2500, 1900, 1800, 2200};

            db.beginTransaction();
            try {
                SQLiteStatement sql = db.compileStatement(
                        "INSERT INTO books(isbn,title,price)VALUES(?,?,?)");

                for (int i = 0; i < isbns.length; i++) {
                    sql.bindString(1, isbns[i]);
                    sql.bindString(2, titles[i]);
                    sql.bindLong(3, prices[i]);
                    sql.executeInsert();
                }

                db.setTransactionSuccessful();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }
        }
    }

    //データベースをバージョンアップした際にテーブル再作成
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS books");
            onCreate(db);
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}

