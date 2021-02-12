package com.blueshadow.todolist;

import java.util.ArrayList;
import java.util.Calendar;

public interface OnItemAndDateChangedListener {
    final public static int DAY_FRAGMENT = 1;
    final public static int WEEK_FRAGMENT = 2;
    final public static int MONTH_FRAGMENT = 3;

    public int onItemInsert(Calendar calendar, String memo);
    public void onItemDelete(int id);
    public void onItemUpdate(int id, String memo, boolean done);
    public ArrayList<ToDoItem> onItemSelect(Calendar calendar);
    public int onItemCountForDate(Calendar calendar);

    public Calendar getTodayCalendar();
    public void setCurrentCalendar(int fragm, Calendar calendar);
    public Calendar getCurrentCalendar(int curFragment);

    public int parseCalendarToIntDate(Calendar calendar);
    public String getWeekdayString(int wd);

    public void goToDailyList(int year, int month, int day);

    public void setMemo(String memo);
    public String getMemo();
}
