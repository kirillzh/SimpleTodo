package com.codepath.simpletodo.ui.todoitems;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.data.TodoItem;

import java.util.ArrayList;

/**
 * Created by Kirill Zhukov on 8/15/17.
 */

public class TodoItemsAdapter extends ArrayAdapter<TodoItem> {
  public TodoItemsAdapter(Context context, ArrayList<TodoItem> todoItems) {
    super(context, 0, todoItems);
  }

  @NonNull
  @Override
  public View getView(int position, View convertView, @NonNull ViewGroup parent) {
    ViewHolder viewHolder;

    // Check if an existing view is being reused, otherwise inflate the view
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext())
          .inflate(R.layout.todo_item, parent, false);
      viewHolder = new ViewHolder(convertView);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }

    TodoItem todoItem = getItem(position);
    // Populate the data into the template view using the data object
    assert todoItem != null;
    viewHolder.mTextViewTitle.setText(todoItem.getTitle());
    // Return the completed view to render on screen
    return convertView;
  }

  private class ViewHolder {
    TextView mTextViewTitle;

    ViewHolder(View todoItemView) {
      mTextViewTitle = todoItemView.findViewById(R.id.tvTodoItemTitle);
    }
  }
}
