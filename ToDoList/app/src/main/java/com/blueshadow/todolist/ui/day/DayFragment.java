package com.blueshadow.todolist.ui.day;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blueshadow.todolist.R;
import com.blueshadow.todolist.ui.ToDoItem;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DayFragment extends Fragment implements OnChangeDayListener{
    private Day today;
    private Day curDay;
    final static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

    TextView dateTextView;
    ImageView leftButton;
    ImageView rightButton;
    ImageView checkButton;
    ImageView addButton;

    public DayFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);

        dateTextView = view.findViewById(R.id.day_dateTextView);
        leftButton = view.findViewById(R.id.day_leftButton);
        rightButton = view.findViewById(R.id.day_rightButton);
        checkButton = view.findViewById(R.id.day_checkButton);
        addButton = view.findViewById(R.id.day_addButton);

        today = new Day();
        curDay = new Day();

        setToday();
        setCurDay(today.getYear(), today.getMonth(), today.getDay());
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    @Override
    public void onDayChanged(Day day) {

    }

    private void setCurDay(int year, int month, int day){
        curDay.setYear(year);
        curDay.setMonth(month);
        curDay.setDay(day);

        setDate(curDay);
    }

    private void setToday(){
        String tmp = format.format(new Date());
        today.setYear(Integer.parseInt(tmp.substring(0, 4)));
        today.setMonth(Integer.parseInt(tmp.substring(4, 6)));
        today.setDay(Integer.parseInt(tmp.substring(6)));
    }

    private void setDate(Day day){
        dateTextView.setText(day.getYear() + " / "
                + day.getMonth() + " / " + day.getDay());
    }
}