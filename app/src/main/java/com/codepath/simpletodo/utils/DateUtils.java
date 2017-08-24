package com.codepath.simpletodo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Kirill Zhukov on 8/23/17.
 */

public class DateUtils {
  public static String toReadableDate(long date) {
    return new SimpleDateFormat("MMMM d", Locale.ENGLISH).format(new Date(date));
  }
}
