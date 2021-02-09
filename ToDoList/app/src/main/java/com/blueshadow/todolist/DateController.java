package com.blueshadow.todolist;

import android.view.View;

import java.util.Calendar;

public interface DateController {
    public void findView(View view);
    public void setListeners(View view);

    public void setDateTitle(Calendar cal);
    public void changeDate(int dd);
    public void backToday();

    public void addTask();
}
