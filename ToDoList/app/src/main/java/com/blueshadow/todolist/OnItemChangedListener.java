package com.blueshadow.todolist;

import com.blueshadow.todolist.ui.day.Day;

public interface OnItemChangedListener {
    public void onItemInsert(Day day, String memo);
    public void onItemDelete(int id);
    public void onItemUpdate(int id, String memo, boolean done);
}
