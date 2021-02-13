package com.blueshadow.todolist.ui.day;

import com.blueshadow.todolist.ToDoItem;

public class DayItemCard{
    public boolean isDone;
    public ToDoItem item;

    public DayItemCard(ToDoItem item) {
        this.item = item;
        this.isDone = item.getDone();
    }

    public boolean getItemDone(){
        return isDone;
    }

    public void setItemDone(boolean done) {
        this.isDone = done;
    }

    public void setItem(ToDoItem item){
        this.item = item;
    }
    public ToDoItem getItem(){
        return item;
    }
}
