package com.codepath.simpletodo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
    TodoItem todoItem = getItem(position);
    // Check if an existing view is being reused, otherwise inflate the view
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
    }
    // Lookup view for data population
    TextView tvTodoItemTitle = convertView.findViewById(R.id.tvTodoItemTitle);
    // Populate the data into the template view using the data object
    assert todoItem != null;
    tvTodoItemTitle.setText(todoItem.title);
    // Return the completed view to render on screen
    return convertView;
  }
}
