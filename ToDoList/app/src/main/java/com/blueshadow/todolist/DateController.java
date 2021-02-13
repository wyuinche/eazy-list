package com.blueshadow.todolist;

import android.view.View;
import java.util.Calendar;

public interface DateController {
    public void init(View view);
    public void findViews(View view);
    public void setListeners(View view);
    public void setList(Calendar cal);

    public void setDateTitle(Calendar cal);
    public void changeDate(int field, int dd);
    public void backToday();

    public void addTask();
}
