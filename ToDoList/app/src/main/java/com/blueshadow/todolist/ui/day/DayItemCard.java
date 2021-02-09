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
import com.blueshadow.todolist.ui.ToDoItem;

public class DayItemCard extends LinearLayout{
    public boolean itemDone = false;
    public ImageView card;
    public TextView textView;

    public ToDoItem item;

    public DayItemCard(@NonNull Context context){
        super(context);
        init(context);
    }
    public DayItemCard(@NonNull Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(itemDone == true){
                setItemDone(false);
            }
            else{
                setItemDone(true);
            }
        }
        return false;
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.day_item_card_base, this, true);

        card = findViewById(R.id.itemBox);
        textView = findViewById(R.id.itemTextView);

        textView.setText("");
        setItemDone(false);
    }

    private void setItemDone(boolean done){
        this.itemDone = done;
        if(this.itemDone == true){
            card.setImageResource(R.drawable.item_box_complete);
            textView.setPaintFlags(textView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else{
            card.setImageResource(R.drawable.item_box_incomplete);
            textView.setPaintFlags(textView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            textView.setPaintFlags(0);
        }
    }

    public void setText(String text){
        textView.setText(text);
    }
    public String getText(){ return textView.getText().toString(); }

    public String getDate(){ return item.getDate(); }

    public void createItem(ToDoItem item){
        this.item = item;
    }
    public ToDoItem getItem(){
        return item;
    }
}
