package com.codepath.simpletodo.ui.todoitems;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.utils.DateUtils;

import java.util.Calendar;

public class EditTodoItemActivity extends AppCompatActivity {
  public static final int ITEM_UPDATED_CODE_OK = 1;
  public static final String EXTRA_ITEM_TITLE = "item_title";
  public static final String EXTRA_ITEM_DUE_DATE = "item_due_date";
  public static final String EXTRA_ITEM_POSITION = "item_position";
  private int mItemPosition;
  private Calendar mCalendar = Calendar.getInstance();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_todo_item);
    String itemTitle = getIntent().getStringExtra(EXTRA_ITEM_TITLE);
    long dueDate = getIntent().getLongExtra(EXTRA_ITEM_DUE_DATE, -1);
    mItemPosition = getIntent().getIntExtra(EXTRA_ITEM_POSITION, -1);
    EditText edEditItem = findViewById(R.id.etEditItem);
    edEditItem.setText(itemTitle);
    // make sure cursor in the text field is at the end of the current text value and
    // is in focus by default
    edEditItem.setSelection(itemTitle.length());

    TextView tvTodoItemDueDate = findViewById(R.id.tvDueDate);
    if (dueDate != -1) {
      mCalendar.setTimeInMillis(dueDate);
      tvTodoItemDueDate.setText(DateUtils.toReadableDate(dueDate));
    }
  }

  public void onSaveItem(View view) {
    String itemTitle = ((EditText) findViewById(R.id.etEditItem)).getText().toString();
    Intent intent = new Intent();
    intent.putExtra(EXTRA_ITEM_POSITION, mItemPosition);
    intent.putExtra(EXTRA_ITEM_TITLE, itemTitle);
    intent.putExtra(EXTRA_ITEM_DUE_DATE, mCalendar.getTimeInMillis());
    setResult(ITEM_UPDATED_CODE_OK, intent);
    finish();
  }

  DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
      mCalendar.set(Calendar.YEAR, year);
      mCalendar.set(Calendar.MONTH, month);
      mCalendar.set(Calendar.DAY_OF_MONTH, day);
      TextView tvDueDate = findViewById(R.id.tvDueDate);
      tvDueDate.setText(DateUtils.toReadableDate(mCalendar.getTimeInMillis()));
    }
  };

  public void chooseDueDate(View view) {
    new DatePickerDialog(this,
        onDateSetListener,
        mCalendar.get(Calendar.YEAR),
        mCalendar.get(Calendar.MONTH),
        mCalendar.get(Calendar.DAY_OF_MONTH)
    ).show();
  }
}
