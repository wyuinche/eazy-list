package com.blueshadow.todolist;

import java.util.Calendar;

public interface OnItemAndDateChangedListener {
    final public static int DAY_FRAGMENT = 1;
    final public static int WEEK_FRAGMENT = 2;
    final public static int MONTH_FRAGMENT = 3;

    public int onItemInsert(Calendar day, String memo);
    public void onItemDelete(int id);
    public void onItemUpdate(int id, String memo, boolean done);
    public int onItemCountForDate(Calendar day);

    public Calendar getTodayCalendar();
    public void setCurrentCalendar(int fragm, Calendar calendar);
    public Calendar getCurrentCalendar(int curFragment);

    public int parseCalendarToIntDate(Calendar calendar);
    public String getWeekdayString(int wd);

    public void goToDailyList(int year, int month, int day);

    public void setMemo(String memo);
    public String getMemo();
}
