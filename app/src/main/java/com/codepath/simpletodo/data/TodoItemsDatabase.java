package com.codepath.simpletodo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import static com.codepath.simpletodo.data.TodoItemsPersistenceContract.TodoItemEntry;

/**
 * Created by Kirill Zhukov on 8/22/17.
 */

public class TodoItemsDatabase {
  private static TodoItemsDatabase INSTANCE;

  private TodoItemsDbHelper mDbHelper;

  private TodoItemsDatabase(@NonNull Context context) {
    mDbHelper = new TodoItemsDbHelper(context);
  }

  public static TodoItemsDatabase getInstance(@NonNull Context context) {
    if (INSTANCE == null) {
      INSTANCE = new TodoItemsDatabase(context.getApplicationContext());
    }
    return INSTANCE;
  }

  public List<TodoItem> getTodoItems() {
    List<TodoItem> todoItems = new ArrayList<>();

    SQLiteDatabase db = mDbHelper.getReadableDatabase();
    Cursor c = db.rawQuery("SELECT * FROM " + TodoItemEntry.TABLE_NAME, null);
    try {
      if (c.moveToFirst()) {
        do {
          String id = c.getString(c.getColumnIndexOrThrow(TodoItemEntry.COLUMN_NAME_ID));
          String title = c.getString(c.getColumnIndexOrThrow(TodoItemEntry.COLUMN_NAME_TITLE));
          long dueDate = c.getLong(c.getColumnIndexOrThrow(TodoItemEntry.COLUMN_NAME_DUE_DATE));
          todoItems.add(new TodoItem(id, title, dueDate));
        } while (c.moveToNext());
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (c != null && !c.isClosed()) {
        c.close();
      }
    }
    db.close();

    return todoItems;
  }

  public void addTodoItem(@NonNull TodoItem todoItem) {
    ContentValues values = new ContentValues();
    values.put(TodoItemEntry.COLUMN_NAME_ID, todoItem.getId());
    values.put(TodoItemEntry.COLUMN_NAME_TITLE, todoItem.getTitle());
    values.put(TodoItemEntry.COLUMN_NAME_DUE_DATE, todoItem.getDueDate());

    SQLiteDatabase db = mDbHelper.getWritableDatabase();
    db.beginTransaction();
    try {
      db.insertOrThrow(TodoItemEntry.TABLE_NAME, null, values);
      db.setTransactionSuccessful();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      db.endTransaction();
    }
    db.close();
  }

  public void deleteTodoItem(@NonNull String todoItemId) {
    String selection = TodoItemEntry.COLUMN_NAME_ID + " =?";
    String[] selectionArgs = {todoItemId};

    SQLiteDatabase db = mDbHelper.getWritableDatabase();
    db.beginTransaction();
    try {
      db.delete(TodoItemEntry.TABLE_NAME, selection, selectionArgs);
      db.setTransactionSuccessful();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      db.endTransaction();
    }
    db.close();
  }

  public void updateTodoItem(@NonNull TodoItem todoItem) {
    String selection = TodoItemEntry.COLUMN_NAME_ID + " =?";
    String[] selectionArgs = {todoItem.getId()};

    ContentValues values = new ContentValues();
    values.put(TodoItemEntry.COLUMN_NAME_ID, todoItem.getId());
    values.put(TodoItemEntry.COLUMN_NAME_TITLE, todoItem.getTitle());
    values.put(TodoItemEntry.COLUMN_NAME_DUE_DATE, todoItem.getDueDate());

    SQLiteDatabase db = mDbHelper.getWritableDatabase();
    db.beginTransaction();
    try {
      db.update(TodoItemEntry.TABLE_NAME, values, selection, selectionArgs);
      db.setTransactionSuccessful();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      db.endTransaction();
    }
    db.close();
  }
}
