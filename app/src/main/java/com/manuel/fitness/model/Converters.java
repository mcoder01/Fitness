package com.manuel.fitness.model;

import androidx.room.TypeConverter;

import com.manuel.fitness.model.entity.set.RepetitionSet;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Converters {
    @TypeConverter
    public static String dateToString(LocalDate date) {
        return date.toString();
    }

    @TypeConverter
    public static LocalDate dateFromString(String s) {
        return LocalDate.parse(s);
    }

    @TypeConverter
    public static String timeToString(LocalTime time) {
        if (time == null) return null;
        return format(time.getHour()) + ":"
             + format(time.getMinute()) + ":"
             + format(time.getSecond()) + ":";
    }

    public static String format(int val) {
        if (val < 10)
            return "0" + val;
        return "" + val;
    }

    @TypeConverter
    public static LocalTime timeFromString(String s) {
        if (s == null) return null;
        String[] info = s.split(":");
        int ore = Integer.parseInt(info[0]);
        int min = Integer.parseInt(info[1]);
        int sec = Integer.parseInt(info[2]);
        return LocalTime.of(ore, min, sec);
    }

    @TypeConverter
    public static String dowToString(DayOfWeek dow) {
        return dow.toString();
    }

    @TypeConverter
    public static DayOfWeek dowFromString(String s) {
        return DayOfWeek.valueOf(s);
    }

    public static String timeToText(LocalTime time) {
        String s = "", append = "";
        if (time.getHour() > 0) {
            s += time.getHour() + "ore";
            append = " e ";
        }
        if (time.getMinute() > 0) {
            s += append + time.getMinute() + "min";
            append = " e ";
        }
        if (time.getSecond() > 0)
            s += append + time.getSecond() + "sec";

        return s;
    }

    public static String dateToText(LocalDate date) {
        return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
    }

    @TypeConverter
    public static String setArrayToString(int[] set) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < set.length-1; i++)
            builder.append(set[i]).append(".");
        builder.append(set[set.length-1]);
        return builder.toString();
    }

    @TypeConverter
    public static int[] setArrayFromString(String s) {
        String[] info = s.split("\\.");
        int[] set = new int[info.length];
        for (int i = 0; i < set.length; i++)
            set[i] = Integer.parseInt(info[i]);
        return set;
    }
}
