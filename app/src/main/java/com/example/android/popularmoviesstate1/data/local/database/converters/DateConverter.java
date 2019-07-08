package com.example.android.popularmoviesstate1.data.local.database.converters;

import android.arch.persistence.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.android.popularmoviesstate1.utils.Constants.SIMPLE_DATE_FORMAT;

public class DateConverter {
    @TypeConverter
    public static Date toDate(String timestamp) {
        SimpleDateFormat inFormatDate = new SimpleDateFormat(SIMPLE_DATE_FORMAT, Locale.US);
        try {
            return inFormatDate.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Date();
    }

    @TypeConverter
    public static String toTimestamp(Date date) {
        SimpleDateFormat outFormatDate = new SimpleDateFormat(SIMPLE_DATE_FORMAT, Locale.US);
        return outFormatDate.format(date);
    }
}
