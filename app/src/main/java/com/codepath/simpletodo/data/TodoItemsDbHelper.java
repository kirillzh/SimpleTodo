package com.codepath.simpletodo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.codepath.simpletodo.data.TodoItemsPersistenceContract.TodoItemEntry;

/**
 * Created by Kirill Zhukov on 8/22/17.
 */

public class TodoItemsDbHelper extends SQLiteOpenHelper {
  private static final int VERSION_2 = 2;

  public static final int DATABASE_VERSION = VERSION_2;
  public static String DATABASE_NAME = "TodoItems.db";

  public TodoItemsDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    // TODO: make id and title columns NOT NULL
    db.execSQL("CREATE TABLE " + TodoItemEntry.TABLE_NAME + "( " +
        TodoItemEntry.COLUMN_NAME_ID + " TEXT," +
        TodoItemEntry.COLUMN_NAME_TITLE + " TEXT" +
        " )");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    if (newVersion > oldVersion) {
      // TODO: persist existing data
      db.execSQL("DROP TABLE IF EXISTS " + TodoItemEntry.TABLE_NAME);
      onCreate(db);
    }

    if (oldVersion < VERSION_2) {
      upgradeToVersion2(db);
    }
  }

  private void upgradeToVersion2(final SQLiteDatabase db) {
    db.execSQL("ALTER TABLE " + TodoItemEntry.TABLE_NAME + " ADD COLUMN " +
        TodoItemEntry.COLUMN_NAME_DUE_DATE + " INTEGER");
  }
}
