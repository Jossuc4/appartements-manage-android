package com.app.appli.Database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

import java.util.Objects;

public class Database extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    public Database(Context ctx, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(ctx, name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Voitures(" +
                "id INTERGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                "marque VARCHAR(50) NOT NULL,"+
                "prix INTEGER NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Voiture");
        onCreate(db);
    }

    public void createDb(Context ctx){
        try{
            db = new Database(ctx, "voitures.db", null, 1).getWritableDatabase();
        }catch(Exception e){
            Log.i("DATABASE_ERROR", Objects.requireNonNull(e.getMessage()));
        }
    }

    public void closeDb(Context cxt){
        db.close();
    }
}
