package com.blueshadow.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {
    int curPage;
    int lastPage = 10;

    ImageView nextButton;
    TextView contentsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        curPage = 0;

        contentsView = findViewById(R.id.help_contents);
        contentsView.setText(R.string.help_content_1);

        nextButton = findViewById(R.id.help_nextButton);
        nextButton.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundResource(R.drawable.ic_next_on);
                        break;
                    case MotionEvent.ACTION_UP:
                        goNextPage();
                        v.setBackgroundResource(R.drawable.ic_next_off);
                        break;
                }
                return true;
            }
        });
    }

    private void goNextPage(){
        if(curPage >= lastPage){
            return;
        }
        curPage++;
    }
}