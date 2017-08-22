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

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TodoItemsActivity extends AppCompatActivity {
  private ArrayList<TodoItem> mItems;
  private ArrayAdapter<TodoItem> mItemsAdapter;
  private ListView mLvItems;

  private static final int ITEM_UPDATED_CODE = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_todo_items);

    mLvItems = findViewById(R.id.lvItems);
    readItems();
    mItemsAdapter = new TodoItemsAdapter(this, mItems);
    mLvItems.setAdapter(mItemsAdapter);
    setupListViewListener();
  }

  public void onAddItem(View view) {
    EditText etNewItem = findViewById(R.id.etNewItem);
    String itemText = etNewItem.getText().toString().trim();
    if (!itemText.isEmpty()) {
      mItemsAdapter.add(new TodoItem(itemText));
      etNewItem.setText("");
      writeItems();
      scrollItemsListViewToBottom();
    }
  }

  private void setupListViewListener() {
    mLvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View item, int pos, long id) {
        launchEditItemActivity(mItems.get(pos).getTitle(), pos);
      }
    });

    mLvItems.setOnItemLongClickListener(
        new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(AdapterView<?> adapterView, View item, int pos, long id) {
            mItems.remove(pos);
            mItemsAdapter.notifyDataSetChanged();
            writeItems();
            return true;
          }
        }
    );
  }

  private void updateItem(int position, String newItemText) {
    mItems.set(position, new TodoItem(newItemText));
    mItemsAdapter.notifyDataSetChanged();
    writeItems();
  }

  private File getTodoFile() {
    return new File(getFilesDir(), "todo.txt");
  }

  /**
   * Opens a file and reads a newline-delimited list of items
   */
  private void readItems() {
    mItems = new ArrayList<>();
    try {
      ArrayList lines = new ArrayList<>(FileUtils.readLines(getTodoFile()));
      for (Object line : lines) {
        mItems.add(new TodoItem(line.toString()));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Saves a file and writes a newline-delimited list of items
   */
  private void writeItems() {
    try {
      FileUtils.writeLines(getTodoFile(), mItems);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void launchEditItemActivity(String itemText, int itemPosition) {
    Intent intent = new Intent(this, EditTodoItemActivity.class);
    intent.putExtra(EditTodoItemActivity.EXTRA_ITEM_TEXT, itemText);
    intent.putExtra(EditTodoItemActivity.EXTRA_ITEM_POSITION, itemPosition);
    startActivityForResult(intent, ITEM_UPDATED_CODE);
  }

  /**
   * TODO: add snackbar
   *
   * @param requestCode
   * @param resultCode
   * @param data
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == ITEM_UPDATED_CODE && resultCode == EditTodoItemActivity.ITEM_UPDATED_CODE_OK) {
      String itemText = data.getStringExtra(EditTodoItemActivity.EXTRA_ITEM_TEXT);
      int itemPosition = data.getIntExtra(EditTodoItemActivity.EXTRA_ITEM_POSITION, -1);
      updateItem(itemPosition, itemText);
    }
  }

  private void scrollItemsListViewToBottom() {
    mLvItems.post(new Runnable() {
      @Override
      public void run() {
        mLvItems.setSelection(mLvItems.getCount() - 1);
      }
    });
  }
}
