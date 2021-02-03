package com.blueshadow.todolist.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blueshadow.todolist.R;

public class CheckButton extends androidx.appcompat.widget.AppCompatImageView {
    public CheckButton(@NonNull Context context){
        super(context);
        init(context);
    }
    public CheckButton(@NonNull Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                setImageResource(R.drawable.check_on);
                break;
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                setImageResource(R.drawable.check_off);
                break;
        }
        invalidate();
        return true;
    }

    private void init(Context context){
        setImageResource(R.drawable.check_off);
    }
}
