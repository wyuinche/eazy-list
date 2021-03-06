package com.blueshadow.todolist;

import java.util.Calendar;

public class ToDoItem {
    int _id;
    String date;
    String memo;
    boolean done;

    public ToDoItem(int _id, Calendar cal, String memo){
        set_id(_id);
        setDate(MainActivity.format.format(cal.getTime()));
        setMemo(memo);
        setDone(false);
    }

    public ToDoItem(int _id, String date, String memo, int done){
        set_id(_id);
        setDate(date);
        setMemo(memo);
        if(done == 0){
            setDone(false);
        }
        else{
            setDone(true);
        }
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
