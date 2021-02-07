package com.blueshadow.todolist;

import java.util.Calendar;

public interface OnItemAndDateChangedListener {
    public int onItemInsert(Calendar day, String memo);
    public void onItemDelete(int id);
    public void onItemUpdate(int id, String memo, boolean done);

    public Calendar getTodayCalendar();
    public void setDayCurrentCalendar(Calendar calendar);
    public Calendar getDayCurrentCalendar();

//    public void setWeekCurrentCalendar(Calendar calendar);
//    public Calendar getWeekCurrentCalendar();
//    public void setMonthCurrentCalendar(Calendar calendar);
//    public Calendar getMonthCurrentCalendar();

    public int parseCalendarToIntDate(Calendar calendar);
    public String getWeekdayString(int wd);
}
