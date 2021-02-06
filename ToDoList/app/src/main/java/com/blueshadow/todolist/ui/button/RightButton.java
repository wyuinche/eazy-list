package com.blueshadow.todolist.ui.button;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blueshadow.todolist.R;

public class RightButton extends androidx.appcompat.widget.AppCompatImageView {
    public RightButton(@NonNull Context context) {
        super(context);
        init(context);
    }

    public RightButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        setImageResource(R.drawable.right_off);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                setImageResource(R.drawable.right_on);
                break;
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_UP:
                setImageResource(R.drawable.right_off);
                break;
        }
        return true;
    }
}
