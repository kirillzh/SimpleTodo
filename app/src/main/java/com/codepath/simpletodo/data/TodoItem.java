package com.codepath.simpletodo.data;

import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * Created by Kirill Zhukov on 8/15/17.
 */

public class TodoItem {
  @NonNull
  private final String mId;

  @NonNull
  private String mTitle;

  public TodoItem(@NonNull String id, @NonNull String title) {
    mId = id;
    mTitle = title;
  }

  public TodoItem(@NonNull String title) {
    this(UUID.randomUUID().toString(), title);
  }

  @NonNull
  public String getId() {
    return mId;
  }

  @NonNull
  public String getTitle() {
    return mTitle;
  }

  public void setTitle(@NonNull String title) {
    mTitle = title;
  }

  @Override
  public String toString() {
    return getId() + ":" + getTitle();
  }
}
