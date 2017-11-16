package com.example.tanushree.newz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by tanushree on 19/09/17.
 */

// To control the CRUD operations.

public class NewzArticleDataSource
{
    private Context mContext;
    private NewzArticleSQLiteHelper mNewzArticleSQLiteHelper;

    public NewzArticleDataSource(Context context)
    {
        mContext = context;
        mNewzArticleSQLiteHelper = new NewzArticleSQLiteHelper(context);
    }

    private SQLiteDatabase open()
    {
        return mNewzArticleSQLiteHelper.getWritableDatabase();
    }

    private void close (SQLiteDatabase sqliteDatabase)
    {
        sqliteDatabase.close();
    }

    // To read from the database.

    public ArrayList<NewzItem> read()
    {
        SQLiteDatabase sqliteDatabase = open();

        Cursor cursor = sqliteDatabase.query(
                NewzArticleSQLiteHelper.TABLE_NAME,
                new String [] {NewzArticleSQLiteHelper.COL_HEADLINE,
                               NewzArticleSQLiteHelper.COL_ARTICLE},
                null, // selection
                null, // selection args
                null, // group by
                null, // having
                null); // order

        ArrayList<NewzItem> newzItems = new ArrayList<NewzItem>();

        int headlineIndex = cursor.getColumnIndex(NewzArticleSQLiteHelper.COL_HEADLINE);
        int articleIndex = cursor.getColumnIndex(NewzArticleSQLiteHelper.COL_ARTICLE);

        if(cursor.moveToFirst())
        {
            do{
                NewzItem newzItem = new NewzItem();
                try {
                    newzItem.setHeadline(cursor.getString(headlineIndex));
                    newzItem.setArticle(cursor.getString(articleIndex));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }

                Log.i("Database", "read called" + newzItem.getHeadline());

                newzItems.add(newzItem);

            }while (cursor.moveToNext());
        }

        // Log.d("arraylist size in read", "" + newzItems.size());

        cursor.close();
        close(sqliteDatabase);

        return newzItems;
    }

    // To write into the database.

    public void create(ArrayList<NewzItem> newzItemList)
    {
        SQLiteDatabase sqliteDatabase = open();
        sqliteDatabase.beginTransaction();
        //implementation details
        ContentValues contentValues = new ContentValues();
        long id;
        // Log.d("arraylist size create()", "" + newzItemList.size());

        for (int i=0;i<newzItemList.size();i++)
        {
            contentValues.put(NewzArticleSQLiteHelper.COL_ARTICLEID,
                    newzItemList.get(i).getArticleId());
            contentValues.put(NewzArticleSQLiteHelper.COL_HEADLINE,
                    newzItemList.get(i).getHeadline());
            contentValues.put(NewzArticleSQLiteHelper.COL_ARTICLE,
                    newzItemList.get(i).getArticle());

            // call the insert method.
            id = sqliteDatabase.insert(NewzArticleSQLiteHelper.TABLE_NAME, null, contentValues);
        }

        // Log.i("Database", "create called");

        sqliteDatabase.setTransactionSuccessful();
        sqliteDatabase.endTransaction();
        close(sqliteDatabase);
    }

    // To delete from the database.

    public int delete()
    {
        SQLiteDatabase sqliteDatabase = open();
        sqliteDatabase.beginTransaction();

        int count = sqliteDatabase.delete(NewzArticleSQLiteHelper.TABLE_NAME, "1", null);

        sqliteDatabase.setTransactionSuccessful();
        sqliteDatabase.endTransaction();
        close(sqliteDatabase);

        return count;
    }
}