package com.codepath.simpletodo;

/**
 * Created by Kirill Zhukov on 8/15/17.
 */

public class TodoItem {
  public String title;

  public TodoItem(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return title;
  }
}
