package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditTodoItemActivity extends AppCompatActivity {
  public static final int ITEM_UPDATED_CODE_OK = 1;
  public static final String EXTRA_ITEM_TEXT = "item_text";
  public static final String EXTRA_ITEM_POSITION = "item_position";
  private int mItemPosition;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_todo_item);
    String itemText = getIntent().getStringExtra(EXTRA_ITEM_TEXT);
    mItemPosition = getIntent().getIntExtra(EXTRA_ITEM_POSITION, -1);
    EditText edEditItem = findViewById(R.id.etEditItem);
    edEditItem.setText(itemText);
    // make sure cursor in the text field is at the end of the current text value and
    // is in focus by default
    edEditItem.setSelection(itemText.length());
  }

  public void onSaveItem(View view) {
    String itemText = ((EditText) findViewById(R.id.etEditItem)).getText().toString();
    Intent intent = new Intent();
    intent.putExtra("item_text", itemText);
    intent.putExtra("item_position", mItemPosition);
    setResult(ITEM_UPDATED_CODE_OK, intent);
    finish();
  }
}
