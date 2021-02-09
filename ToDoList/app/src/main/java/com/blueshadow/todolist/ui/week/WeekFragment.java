package com.blueshadow.todolist.ui.week;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.blueshadow.todolist.DateController;
import com.blueshadow.todolist.OnItemAndDateChangedListener;
import com.blueshadow.todolist.R;
import com.blueshadow.todolist.ui.ToDoItem;
import com.blueshadow.todolist.ui.day.DayItemCard;

import java.util.ArrayList;
import java.util.Calendar;

public class WeekFragment extends Fragment implements DateController {
    private Calendar curDay;
    private Calendar[] dayOnDisplay = new Calendar[7];

    private TextView dateTextView;
    private ImageView leftButton;
    private ImageView rightButton;
    private ImageView addButton;
    private ImageView backButton;

    private TextView[] dayTitleTextViews = new TextView[7];
    private TextView[] dayOfWeekTextViews = new TextView[7];

    private ListView[] listViews = new ListView[7];
    private WeekItemCardAdapter[] adapters = new WeekItemCardAdapter[7];

    private OnItemAndDateChangedListener listener;

    int selectedWd = -1;

    public WeekFragment() {

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
        listener.setCurrentCalendar(listener.WEEK_FRAGMENT, curDay);
    }

    @Override
    public void onResume() {
        super.onResume();
        curDay = listener.getCurrentCalendar(listener.WEEK_FRAGMENT);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);
        findListViews(view);
        setListeners(view);

        for(int i=0; i<7; i++){
            adapters[i] = new WeekItemCardAdapter(getContext());
            listViews[i].setAdapter(adapters[i]);
        }

        curDay = listener.getCurrentCalendar(listener.WEEK_FRAGMENT);
        changeDate(0);

        return view;
    }

    @Override
    public void findView(View view){
        dateTextView = view.findViewById(R.id.week_dateTextView);
        leftButton = view.findViewById(R.id.week_leftButton);
        rightButton = view.findViewById(R.id.week_rightButton);
        addButton = view.findViewById(R.id.week_addButton);
        backButton = view.findViewById(R.id.week_backButton);

        dayTitleTextViews[0] = view.findViewById(R.id.week_sunday_textView);
        dayTitleTextViews[1] = view.findViewById(R.id.week_monday_textView);
        dayTitleTextViews[2] = view.findViewById(R.id.week_tuesday_textView);
        dayTitleTextViews[3] = view.findViewById(R.id.week_wednesday_textView);
        dayTitleTextViews[4] = view.findViewById(R.id.week_thursday_textView);
        dayTitleTextViews[5] = view.findViewById(R.id.week_friday_textView);
        dayTitleTextViews[6] = view.findViewById(R.id.week_saturday_textView);

        dayOfWeekTextViews[0] = view.findViewById(R.id.week_sunday_date_textView);
        dayOfWeekTextViews[1] = view.findViewById(R.id.week_monday_date_textView);
        dayOfWeekTextViews[2] = view.findViewById(R.id.week_tuesday_date_textView);
        dayOfWeekTextViews[3] = view.findViewById(R.id.week_wednesday_date_textView);
        dayOfWeekTextViews[4] = view.findViewById(R.id.week_thursday_date_textView);
        dayOfWeekTextViews[5] = view.findViewById(R.id.week_friday_date_textView);
        dayOfWeekTextViews[6] = view.findViewById(R.id.week_saturday_date_textView);
    }

    public void findListViews(View view){
        listViews[0] = view.findViewById(R.id.week_sunday_listView);
        listViews[1] = view.findViewById(R.id.week_monday_listView);
        listViews[2] = view.findViewById(R.id.week_tuesday_listView);
        listViews[3] = view.findViewById(R.id.week_wednesday_listView);
        listViews[4] = view.findViewById(R.id.week_thursday_listView);
        listViews[5] = view.findViewById(R.id.week_friday_listView);
        listViews[6] = view.findViewById(R.id.week_saturday_listView);
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
                        changeDate(-7);
                        leftButton.setImageResource(R.drawable.ic_left_off);
                        return true;
                }
                return false;
            }
        });

        rightButton.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        rightButton.setImageResource(R.drawable.ic_right_on);
                        return true;
                    case MotionEvent.ACTION_UP:
                        changeDate(7);
                        rightButton.setImageResource(R.drawable.ic_right_off);
                        return true;
                }
                return false;
            }
        });

        AdapterView.OnItemLongClickListener listViewItemLongClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onItemDelete(((WeekItemCard)(((WeekItemCardAdapter) parent.getAdapter()).getItem(position)))
                        .getItem().get_id());
                ((WeekItemCardAdapter) parent.getAdapter()).removeItem(position);
                ((WeekItemCardAdapter) parent.getAdapter()).notifyDataSetChanged();
                return true;
            }
        };

        AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView)view.findViewById(R.id.week_item_textView);
                WeekItemCard item = (WeekItemCard)((WeekItemCardAdapter) parent.getAdapter()).getItem(position);
                if(item.getItemDone() == true){
                    textView.setPaintFlags(textView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                    textView.setPaintFlags(0);
                    item.setItemDone(false);
                }
                else{
                    textView.setPaintFlags(textView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                    item.setItemDone(true);
                }
            }
        };

        for(int i = 0; i < 7; i++){
            listViews[i].setOnItemLongClickListener(listViewItemLongClickListener);
            listViews[i].setOnItemClickListener(listViewItemClickListener);
        }
    }

    @Override
    public void setDateTitle(Calendar cal) {
        dateTextView.setText(cal.get(Calendar.YEAR) + " / "
                + (cal.get(Calendar.MONTH)+1));
    }

    @Override
    public void changeDate(int dd) {
        curDay.add(Calendar.DATE, dd);
        setDayOnDisplay(curDay);
        setDateTitle(curDay);

        clearDayOnDisplayColor();
        if(curDay.get(Calendar.YEAR) == listener.getTodayCalendar().get(Calendar.YEAR)
                &&curDay.get(Calendar.MONTH) == listener.getTodayCalendar().get(Calendar.MONTH)
                &&curDay.get(Calendar.DATE) == listener.getTodayCalendar().get(Calendar.DATE)){
            setDayOnDisplayColor(curDay);
        }

        setDayOfWeekTextViews();
    }

    @Override
    public void backToday() {
        curDay = Calendar.getInstance();
        changeDate(0);
    }

    @Override
    public void addTask() {
        selectedWd = -1;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.week_add_task_dialog, null);

        Spinner spinner = v.findViewById(R.id.week_add_task_spinner);
        EditText editText = v.findViewById(R.id.week_add_task_editText);
        ArrayList<String> spinnerItems = new ArrayList<String>();
        for (int i = 1; i < 8; i++) {
            spinnerItems.add(listener.getWeekdayString(i));
        }

        ArrayAdapter spinnerAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems);

        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(curDay.get(Calendar.DAY_OF_WEEK) - 1);

        builder.setTitle(getString(R.string.item_add_title));
        builder.setView(v);
        builder.setPositiveButton(getString(R.string.item_add_ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedWd = spinner.getSelectedItemPosition() + 1;
                        String memo = editText.getText().toString();
                        Calendar selectedCal = getSelectedCal(selectedWd);
                        int itemId;
                        itemId = listener.onItemInsert(selectedCal, memo);
                        if (itemId == -1) {
                            return;
                        }
                        WeekItemCard item = new WeekItemCard(new ToDoItem(itemId, selectedCal, memo));
                        adapters[selectedWd - 1].addItem(item);
                        adapters[selectedWd - 1].notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton(getString(R.string.item_add_cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedWd = -1;
                        dialog.dismiss();
                    }
                });
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private Calendar getSelectedCal(int wd){
        Calendar tmpCal = Calendar.getInstance();
        tmpCal.add(Calendar.DATE, wd - curDay.get(Calendar.DAY_OF_WEEK));
        return tmpCal;
    }

    private void setDayOnDisplay(Calendar cal){
        int wd = cal.get(Calendar.DAY_OF_WEEK);
        Calendar tmp = Calendar.getInstance();
        tmp.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

        dayOnDisplay[wd-1] = Calendar.getInstance();
        dayOnDisplay[wd-1].set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

        for(int i = wd; i < 7; i++){
            tmp.add(Calendar.DATE, 1);
            dayOnDisplay[i] = Calendar.getInstance();
            dayOnDisplay[i].set(tmp.get(Calendar.YEAR), tmp.get(Calendar.MONTH), tmp.get(Calendar.DATE));
        }
        tmp.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        for(int i = wd-2; i >= 0; i--){
            tmp.add(Calendar.DATE, -1);
            dayOnDisplay[i] = Calendar.getInstance();
            dayOnDisplay[i].set(tmp.get(Calendar.YEAR), tmp.get(Calendar.MONTH), tmp.get(Calendar.DATE));
        }
    }

    private void setDayOnDisplayColor(Calendar cal){
        int wd = cal.get(Calendar.DAY_OF_WEEK);

        dayTitleTextViews[wd-1].setBackgroundResource(R.drawable.week_day_title_box_selected);
        listViews[wd-1].setBackgroundResource(R.drawable.week_listview_box_selected);
    }

    private void clearDayOnDisplayColor(){
        for(int i=0; i < 7; i++){
            dayTitleTextViews[i].setBackgroundResource(R.drawable.week_day_title_box);
            listViews[i].setBackgroundResource(R.drawable.week_listview_box);
        }
    }

    private void setDayOfWeekTextViews(){
        for(int i = 0; i < 7; i++){
            dayOfWeekTextViews[i].setText(""+dayOnDisplay[i].get(Calendar.DATE));
        }
    }
}