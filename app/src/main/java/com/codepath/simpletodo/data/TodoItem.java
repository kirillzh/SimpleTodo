package com.codepath.simpletodo.data;

import android.support.annotation.NonNull;

import java.util.UUID;

import javax.annotation.Nullable;

/**
 * Created by Kirill Zhukov on 8/15/17.
 */

public class TodoItem {
  @NonNull
  private final String mId;

  @NonNull
  private String mTitle;

  @Nullable
  private Long mDueDate;

  public TodoItem(@NonNull String title, @Nullable Long dueDate) {
    this(UUID.randomUUID().toString(), title, dueDate);
  }

  public TodoItem(@NonNull String id, @NonNull String title, @Nullable Long dueDate) {
    mId = id;
    mTitle = title;
    mDueDate = dueDate;
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

  @Nullable
  public Long getDueDate() {
    return mDueDate;
  }

  public void setDueDate(@Nullable Long dueDate) {
    mDueDate = dueDate;
  }

  @Override
  public String toString() {
    return getId() + ":" + getTitle();
  }
}
