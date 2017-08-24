package com.codepath.simpletodo.ui.todoitems;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.data.TodoItem;
import com.codepath.simpletodo.data.TodoItemsDatabase;

import java.util.ArrayList;

public class TodoItemsActivity extends AppCompatActivity {
  private ArrayList<TodoItem> mItems;
  private ArrayAdapter<TodoItem> mItemsAdapter;
  private ListView mListViewItems;

  private static final int ITEM_UPDATED_CODE = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_todo_items);

    mListViewItems = findViewById(R.id.lvItems);
    mItems = (ArrayList<TodoItem>) TodoItemsDatabase.getInstance(this).getTodoItems();
    mItemsAdapter = new TodoItemsAdapter(this, mItems);
    mListViewItems.setAdapter(mItemsAdapter);
    setupListViewListener();
  }

  public void onAddTodo(View view) {
    EditText etNewItem = findViewById(R.id.etAddTodo);
    String title = etNewItem.getText().toString().trim();
    if (!title.isEmpty()) {
      TodoItem todoItem = new TodoItem(title, System.currentTimeMillis());
      mItemsAdapter.add(todoItem);
      etNewItem.setText("");
      TodoItemsDatabase.getInstance(this).addTodoItem(todoItem);
      scrollItemsListViewToBottom();
    }
  }

  private void setupListViewListener() {
    mListViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View item, int pos, long id) {
        launchEditItemActivity(mItems.get(pos), pos);
      }
    });

    mListViewItems.setOnItemLongClickListener(
        new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(AdapterView<?> adapterView, View item, int pos, long id) {
            TodoItem todoItem = mItems.remove(pos);
            mItemsAdapter.notifyDataSetChanged();
            TodoItemsDatabase.getInstance(TodoItemsActivity.this).deleteTodoItem(todoItem.getId());
            return true;
          }
        }
    );
  }

  private void updateItem(int position, TodoItem todoItem) {
    mItems.set(position, todoItem);
    mItemsAdapter.notifyDataSetChanged();
    TodoItemsDatabase.getInstance(this).updateTodoItem(todoItem);
  }

  private void launchEditItemActivity(TodoItem todoItem, int itemPosition) {
    Intent intent = new Intent(this, EditTodoItemActivity.class);
    intent.putExtra(EditTodoItemActivity.EXTRA_ITEM_TITLE, todoItem.getTitle());
    intent.putExtra(EditTodoItemActivity.EXTRA_ITEM_DUE_DATE, todoItem.getDueDate());
    intent.putExtra(EditTodoItemActivity.EXTRA_ITEM_POSITION, itemPosition);
    startActivityForResult(intent, ITEM_UPDATED_CODE);
  }

  /**
   * @param requestCode
   * @param resultCode
   * @param data
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == ITEM_UPDATED_CODE && resultCode == EditTodoItemActivity.ITEM_UPDATED_CODE_OK) {
      String title = data.getStringExtra(EditTodoItemActivity.EXTRA_ITEM_TITLE);
      long dueDate = data.getLongExtra(EditTodoItemActivity.EXTRA_ITEM_DUE_DATE, -1);
      int position = data.getIntExtra(EditTodoItemActivity.EXTRA_ITEM_POSITION, -1);
      TodoItem todoItem = mItemsAdapter.getItem(position);
      if (todoItem != null) {
        todoItem.setTitle(title);
        todoItem.setDueDate(dueDate);
        updateItem(position, todoItem);
      } else {
        // TODO: handle null case
      }
    }
  }

  private void scrollItemsListViewToBottom() {
    mListViewItems.post(new Runnable() {
      @Override
      public void run() {
        mListViewItems.setSelection(mListViewItems.getCount() - 1);
      }
    });
  }
}
