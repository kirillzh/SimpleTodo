package com.codepath.simpletodo.data;

/**
 * Created by Kirill Zhukov on 8/15/17.
 */

public class TodoItem {
  private String mTitle;

  public TodoItem(String title) {
    mTitle = title;
  }

  public String getTitle() {
    return mTitle;
  }

  @Override
  public String toString() {
    return getTitle();
  }
}
