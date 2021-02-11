package com.blueshadow.todolist.ui.month;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.blueshadow.todolist.DateController;
import com.blueshadow.todolist.OnItemAndDateChangedListener;
import com.blueshadow.todolist.R;
import com.blueshadow.todolist.ui.week.WeekItemCard;
import com.blueshadow.todolist.ui.week.WeekItemCardAdapter;

import java.time.Month;
import java.util.Calendar;

public class MonthFragment extends Fragment implements DateController {
    private Calendar curDay;

    private TextView dateTextView;
    private ImageView leftButton;
    private ImageView rightButton;
    private ImageView addButton;
    private ImageView backButton;

    private ListView[] listViews = new ListView[7];
    private MonthItemCardAdapter[] adapters = new MonthItemCardAdapter[7];

    private OnItemAndDateChangedListener listener;

    public MonthFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnItemAndDateChangedListener){
            listener = (OnItemAndDateChangedListener) context;
        }
        else{
            throw new RuntimeException(context.toString()
                    + " must implement OnItemChangedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        listener.setCurrentCalendar(listener.MONTH_FRAGMENT, curDay);
    }

    @Override
    public void onResume() {
        super.onResume();
        curDay = listener.getCurrentCalendar(listener.MONTH_FRAGMENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_month, container, false);
        findListViews(view);
        setListeners(view);

        curDay = listener.getCurrentCalendar(listener.MONTH_FRAGMENT);

        for(int i=0; i<7; i++){
            adapters[i] = new MonthItemCardAdapter(getContext());
            listViews[i].setAdapter(adapters[i]);
        }

        changeDate(Calendar.DATE, 0);

        // Inflate the layout for this fragment
        return view;
    }

    private void findListViews(View view){
        listViews[0] = view.findViewById(R.id.month_sunday_listView);
        listViews[1] = view.findViewById(R.id.month_monday_listView);
        listViews[2] = view.findViewById(R.id.month_tuesday_listView);
        listViews[3] = view.findViewById(R.id.month_wednesday_listView);
        listViews[4] = view.findViewById(R.id.month_thursday_listView);
        listViews[5] = view.findViewById(R.id.month_friday_listView);
        listViews[6] = view.findViewById(R.id.month_saturday_listView);
    }

    @Override
    public void findView(View view){
        dateTextView = view.findViewById(R.id.month_dateTextView);
        leftButton = view.findViewById(R.id.month_leftButton);
        rightButton = view.findViewById(R.id.month_rightButton);
        addButton = view.findViewById(R.id.month_addButton);
        backButton = view.findViewById(R.id.month_backButton);
    }

    @Override
    public void setListeners(View view){
        findView(view);

        addButton.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        addButton.setImageResource(R.drawable.ic_add_on);
                        return true;
                    case MotionEvent.ACTION_UP:
                        addButton.setImageResource(R.drawable.ic_add_off);
                        addTask();
                        return true;
                }
                return false;
            }
        });

        backButton.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        backButton.setImageResource(R.drawable.ic_back_today_on);
                        return true;
                    case MotionEvent.ACTION_UP:
                        backButton.setImageResource(R.drawable.ic_back_today_off);
                        backToday();
                        return true;
                }
                return false;
            }
        });

        leftButton.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        leftButton.setImageResource(R.drawable.ic_left_on);
                        return true;
                    case MotionEvent.ACTION_UP:
                        changeDate(Calendar.MONTH, -1);
                        leftButton.setImageResource(R.drawable.ic_left_off);
                        return true;
                }
                return false;
            }
        });

        rightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        rightButton.setImageResource(R.drawable.ic_right_on);
                        return true;
                    case MotionEvent.ACTION_UP:
                        changeDate(Calendar.MONTH, 1);
                        rightButton.setImageResource(R.drawable.ic_right_off);
                        return true;
                }
                return false;
            }
        });

        AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MonthItemCardAdapter adapter = (MonthItemCardAdapter) parent.getAdapter();
                MonthItemCard item = (MonthItemCard) adapter.getItem(position);

                listener.goToDailyList(item.getYear(), item.getMonth(), item.getDay());
            }
        };

        for(int i = 0; i < 7; i++){
            listViews[i].setOnItemClickListener(listViewItemClickListener);
        }
    }

    @Override
    public void setDateTitle(Calendar cal) {
        dateTextView.setText(cal.get(Calendar.YEAR) + " / "
                + (cal.get(Calendar.MONTH)+1));
    }

    @Override
    public void addTask(){

    }

    @Override
    public void changeDate(int field, int dd){
        curDay.add(field, dd);
        setDateTitle(curDay);

        Calendar tmp = Calendar.getInstance();
        tmp.set(Calendar.DATE, 1);
        tmp.set(Calendar.YEAR, curDay.get(Calendar.YEAR));
        tmp.set(Calendar.MONTH, curDay.get(Calendar.MONTH));
        drawCalendar(tmp);
    }

    @Override
    public void backToday(){
        curDay = Calendar.getInstance();
        changeDate(Calendar.DATE, 0);
    }

    private void drawCalendar(Calendar cal){
        int finMonth = (cal.get(Calendar.MONTH) + 1) % 12;
        cal.add(Calendar.DATE, -cal.get(Calendar.DAY_OF_WEEK));

        if(finMonth == 13){
            finMonth = 1;
        }

        cleanAdapters();
        while(true){
            if(cal.get(Calendar.MONTH) == finMonth){
                break;
            }
            for(int i = 0; i < 7; i++){
                cal.add(Calendar.DATE, 1);
                if(cal.get(Calendar.DAY_OF_WEEK) == 1 && cal.get(Calendar.DATE) == 1
                        && cal.get(Calendar.MONTH) == finMonth){
                    break;
                }
                adapters[i].addItem(new MonthItemCard(cal, listener.onItemCountForDate(cal)));
            }
        }

        for(int i=0; i<7; i++){
            adapters[i].notifyDataSetChanged();
        }
    }

    private void cleanAdapters(){
        for(int i=0; i<7; i++){
            adapters[i].cleanItems();
        }
    }
}