package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.codepath.simpletodo.EditItemActivity.ITEM_UPDATED_CODE_OK;

public class MainActivity extends AppCompatActivity {
  private ArrayList<TodoItem> items;
  private ArrayAdapter<TodoItem> itemsAdapter;
  private ListView lvItems;

  private static final int ITEM_UPDATED_CODE = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    lvItems = findViewById(R.id.lvItems);
    readItems();
    itemsAdapter = new TodoItemsAdapter(this, items);
    lvItems.setAdapter(itemsAdapter);
    setupListViewListener();
  }

  public void onAddItem(View view) {
    EditText etNewItem = findViewById(R.id.etNewItem);
    String itemText = etNewItem.getText().toString().trim();
    if (!itemText.isEmpty()) {
      itemsAdapter.add(new TodoItem(itemText));
      etNewItem.setText("");
      writeItems();
      scrollItemsListViewToBottom();
    }
  }

  private void setupListViewListener() {
    lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View item, int pos, long id) {
        launchEditItemActivity(items.get(pos).title, pos);
      }
    });

    lvItems.setOnItemLongClickListener(
        new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(AdapterView<?> adapterView, View item, int pos, long id) {
            items.remove(pos);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
            return true;
          }
        }
    );
  }

  private void updateItem(int position, String newItemText) {
    items.set(position, new TodoItem(newItemText));
    itemsAdapter.notifyDataSetChanged();
    writeItems();
  }

  private File getTodoFile() {
    return new File(getFilesDir(), "todo.txt");
  }

  /**
   * Opens a file and reads a newline-delimited list of items
   */
  private void readItems() {
    items = new ArrayList<>();
    try {
      ArrayList lines = new ArrayList<>(FileUtils.readLines(getTodoFile()));
      for (Object line : lines) {
        items.add(new TodoItem(line.toString()));
      }
    } catch (IOException ignored) {
    }
  }

  /**
   * Saves a file and writes a newline-delimited list of items
   */
  private void writeItems() {
    try {
      FileUtils.writeLines(getTodoFile(), items);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void launchEditItemActivity(String itemText, int itemPosition) {
    Intent intent = new Intent(this, EditItemActivity.class);
    intent.putExtra("item_text", itemText);
    intent.putExtra("item_position", itemPosition);
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
    if (requestCode == ITEM_UPDATED_CODE && resultCode == ITEM_UPDATED_CODE_OK) {
      int itemPosition = data.getIntExtra("item_position", -1);
      String itemText = data.getStringExtra("item_text");
      updateItem(itemPosition, itemText);
    }
  }

  private void scrollItemsListViewToBottom() {
    lvItems.post(new Runnable() {
      @Override
      public void run() {
        lvItems.setSelection(lvItems.getCount() - 1);
      }
    });
  }
}
