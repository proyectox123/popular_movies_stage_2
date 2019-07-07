package com.example.android.popularmoviesstate1.data.local.database.converters;

import android.arch.persistence.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
    @TypeConverter
    public static Date toDate(String timestamp) {
        SimpleDateFormat inFormatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            return inFormatDate.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Date();
    }

    @TypeConverter
    public static String toTimestamp(Date date) {
        SimpleDateFormat outFormatDate = new SimpleDateFormat("yyyy", Locale.US);
        return outFormatDate.format(date);
    }
}
