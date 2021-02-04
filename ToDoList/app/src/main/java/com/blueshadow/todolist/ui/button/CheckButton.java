package com.blueshadow.todolist.ui.button;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blueshadow.todolist.R;

public class CheckButton extends androidx.appcompat.widget.AppCompatImageView {
    public CheckButton(@NonNull Context context) {
        super(context);
        init(context);

    }

    public CheckButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        setImageResource(R.drawable.check_off);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                setImageResource(R.drawable.check_on);
                return true;
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_UP:
                setImageResource(R.drawable.check_off);
                return true;
        }
        return false;
    }
}
