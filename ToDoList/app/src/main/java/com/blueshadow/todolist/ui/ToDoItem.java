package com.blueshadow.todolist.ui;

public class ToDoItem {
    int id;
    String date;
    String memo;
    boolean done;

    public ToDoItem(){}
    public ToDoItem(String date, String memo){
        this.date = date;
        this.memo = memo;
        this.done = false;
    }

    public void convertDone(){
        if(done == false){
            done = true;
        }
        else{
            done = false;
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
