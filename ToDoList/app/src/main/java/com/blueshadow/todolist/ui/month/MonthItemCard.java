package com.blueshadow.todolist.ui.month;

import java.util.Calendar;

public class MonthItemCard {
    int year;
    int month;
    int day;
    int itemCount;

    public MonthItemCard() {}
    public MonthItemCard(Calendar calendar, int count) {
        parseCalendar(calendar);
        this.itemCount = count;
    }

    private void parseCalendar(Calendar calendar){
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DATE);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
}
