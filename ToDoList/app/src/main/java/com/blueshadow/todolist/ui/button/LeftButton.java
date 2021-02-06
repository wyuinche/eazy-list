package com.blueshadow.todolist.ui.button;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blueshadow.todolist.R;

public class LeftButton extends androidx.appcompat.widget.AppCompatImageView{
    public LeftButton(@NonNull Context context) {
        super(context);
        init(context);
    }

    public LeftButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        setImageResource(R.drawable.left_off);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                setImageResource(R.drawable.left_on);
                break;
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_UP:
                setImageResource(R.drawable.left_off);
                break;
        }
        return true;
    }
}
