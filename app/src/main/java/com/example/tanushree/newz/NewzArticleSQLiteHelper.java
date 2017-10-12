package com.example.tanushree.newz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tanushree on 19/09/17.
 */

// A helper class to manage database creation and version management.

public class NewzArticleSQLiteHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "articleDatabase.db";
    private static final int DATABASE_VERSION =1;
    // Article Table
    public static final String TABLE_NAME = "articles";
    public static final String COL_ARTICLEID = "articleId";
    public static final String COL_HEADLINE = "headline";
    public static final String COL_ARTICLE = "article";

    public NewzArticleSQLiteHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // to create the database schema for the first time.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                COL_ARTICLEID + " INTEGER, " +
                                COL_HEADLINE + " TEXT, " +
                                COL_ARTICLE + " TEXT)"
                                );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
