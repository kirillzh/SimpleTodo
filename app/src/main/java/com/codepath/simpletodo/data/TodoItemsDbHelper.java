package com.codepath.simpletodo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.codepath.simpletodo.data.TodoItemsPersistenceContract.TodoItemEntry;

/**
 * Created by Kirill Zhukov on 8/22/17.
 */

public class TodoItemsDbHelper extends SQLiteOpenHelper {
  public static final int DATABASE_VERSION = 1;
  public static String DATABASE_NAME = "TodoItems.db";

  public TodoItemsDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE " + TodoItemEntry.TABLE_NAME + "( " +
        TodoItemEntry.COLUMN_NAME_ID + " TEXT," +
        TodoItemEntry.COLUMN_NAME_TITLE + " TEXT" +
        " )");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    db.execSQL("DROP TABLE IF EXISTS " + TodoItemEntry.TABLE_NAME);
    onCreate(db);
  }
}
