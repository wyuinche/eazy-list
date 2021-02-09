package com.blueshadow.todolist.ui.week;

import com.blueshadow.todolist.ui.ToDoItem;

public class WeekItemCard {
    public boolean isDone;
    ToDoItem item;

    public WeekItemCard(){
        isDone = false;
    }
    public WeekItemCard(ToDoItem item){
        this.item = item;
    }

    public ToDoItem getItem(){
        return item;
    }

    public boolean getItemDone(){
        return isDone;
    }
    public void setItemDone(boolean done){
        isDone = done;
    }
}
