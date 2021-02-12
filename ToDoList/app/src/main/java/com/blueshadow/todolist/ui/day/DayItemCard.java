package com.blueshadow.todolist.ui.day;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blueshadow.todolist.R;
import com.blueshadow.todolist.ToDoItem;

public class DayItemCard{
    public boolean isDone = false;
    public ToDoItem item;

    public DayItemCard(){}

    public DayItemCard(ToDoItem item) {
        this.item = item;
        this.isDone = item.isDone();
    }

    private void setItemDone(boolean done){
        this.isDone = done;

    }

    public void setItem(ToDoItem item){
        this.item = item;
    }
    public ToDoItem getItem(){
        return item;
    }
}
