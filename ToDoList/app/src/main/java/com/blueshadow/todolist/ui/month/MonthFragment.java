package com.blueshadow.todolist.ui.month;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.blueshadow.todolist.DateController;
import com.blueshadow.todolist.OnItemAndDateChangedListener;
import com.blueshadow.todolist.R;

import java.util.Calendar;

public class MonthFragment extends Fragment implements DateController {
    final public static String MEMO_PREF_= "memo";

    private Calendar curDay;

    private TextView dateTextView;
    private ImageView leftButton;
    private ImageView rightButton;
    private ImageView addButton;
    private ImageView backButton;

    private LinearLayout memoLayout;
    private TextView memoTextView;

    private ListView[] listViews = new ListView[7];
    private MonthItemCardAdapter[] adapters = new MonthItemCardAdapter[7];

    private OnItemAndDateChangedListener listener;

    private TextWatcher watcher;

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
        memoTextView.setText(getMemo());

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

        memoLayout = view.findViewById(R.id.month_memo_layout);
        memoTextView = view.findViewById(R.id.month_memo_textView);
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

        memoLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                editMemo();
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
    public void setList(Calendar cal) {
        return;
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
        boolean isToday = false;
        boolean isMonth = false;
        Calendar today = listener.getTodayCalendar();
        int baseMonth = cal.get(Calendar.MONTH);
        int baseYear = cal.get(Calendar.YEAR);
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
                isToday = false;
                isMonth = false;
                if(baseYear == cal.get(Calendar.YEAR) && baseMonth == cal.get(Calendar.MONTH)){
                    isMonth = true;
                }
                if(today.get(Calendar.YEAR) == cal.get(Calendar.YEAR)
                        && today.get(Calendar.MONTH) == cal.get(Calendar.MONTH)
                        && today.get(Calendar.DATE) == cal.get(Calendar.DATE)){
                    isToday = true;
                }
                adapters[i].addItem(new MonthItemCard(cal, listener.onItemCountForDate(cal), isToday, isMonth));
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

    private void editMemo(){
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.month_edit_memo_dialog, null);

        EditText editText = v.findViewById(R.id.month_edit_memo_editText);
        TextView controlTextView = v.findViewById(R.id.month_edit_memo_control_textView);
        editText.setText(getMemo());
        controlTextView.setText(getNumOfLines(getMemo()) + " " + getString(R.string.memo_lines));
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String text = editText.getText().toString();
                controlTextView.setText(getNumOfLines(text) + " " + getString(R.string.memo_lines));
            }
        };
        editText.addTextChangedListener(watcher);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.memo_edit_title));
        builder.setView(v);
        builder.setPositiveButton(getString(R.string.memo_edit_done),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String memo = editText.getText().toString();
                        setMemo(memo);
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton(getString(R.string.memo_edit_cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private String getMemo(){
        return listener.getMemo();
    }

    private void setMemo(String memo){
        memoTextView.setText(memo);
        listener.setMemo(memo);
    }

    private int getNumOfLines(String text){
        int lines = 1;
        for(int i = 0; i < text.length(); i++){
            if(text.charAt(i) == '\n'){
                lines += 1;
            }
        }
        return lines;
    }
}