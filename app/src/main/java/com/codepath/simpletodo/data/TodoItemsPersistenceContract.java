package com.codepath.simpletodo.data;

import android.provider.BaseColumns;

/**
 * Created by Kirill Zhukov on 8/22/17.
 */

public class TodoItemsPersistenceContract {
  private TodoItemsPersistenceContract() {
  }

  public static abstract class TodoItemEntry implements BaseColumns {
    public static final String TABLE_NAME = "todo_items";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_TITLE = "title";
  }
}
