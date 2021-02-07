package com.blueshadow.todolist.ui.day;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.blueshadow.todolist.OnItemAndDateChangedListener;
import com.blueshadow.todolist.R;
import com.blueshadow.todolist.ui.ToDoItem;

import java.util.Calendar;

public class DayFragment extends Fragment{
    private Calendar today;
    private Calendar curDay;

    TextView dateTextView;
    ImageView leftButton;
    ImageView rightButton;
    ImageView addButton;

    ListView listView;
    DayItemCardAdapter adapter;

    private OnItemAndDateChangedListener listener;

    public DayFragment() {
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
        listener.setDayCurrentCalendar(curDay);
    }

    @Override
    public void onResume() {
        super.onResume();
        curDay = listener.getDayCurrentCalendar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_day, container, false);
        setListeners(view);

        today = listener.getTodayCalendar();
        curDay = listener.getDayCurrentCalendar();
        setDateTitle(curDay);

        adapter = new DayItemCardAdapter(getContext());
        listView = view.findViewById(R.id.day_listView);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onItemDelete(((DayItemCard) adapter.getItem(position)).getItem().get_id());
                adapter.removeItem(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        return view;
    }

    private void setDateTitle(Calendar cal){
        dateTextView.setText(cal.get(Calendar.YEAR) + " / " + (cal.get(Calendar.MONTH)+1) + " / "
                + cal.get(Calendar.DATE) + " (" + listener.getWeekdayString(cal.get(Calendar.DAY_OF_WEEK)) + ")");
    }

    private void setListeners(View view){
        dateTextView = view.findViewById(R.id.day_dateTextView);
        leftButton = view.findViewById(R.id.day_leftButton);
        rightButton = view.findViewById(R.id.day_rightButton);
        addButton = view.findViewById(R.id.day_addButton);

        addButton.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        addButton.setImageResource(R.drawable.add_on);
                        return true;
                    case MotionEvent.ACTION_UP:
                        addButton.setImageResource(R.drawable.add_off);
                        addTask();
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
                        leftButton.setImageResource(R.drawable.left_on);
                        return true;
                    case MotionEvent.ACTION_UP:
                        changeDate(-1);
                        leftButton.setImageResource(R.drawable.left_off);
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
                        rightButton.setImageResource(R.drawable.right_on);
                        return true;
                    case MotionEvent.ACTION_UP:
                        changeDate(1);
                        rightButton.setImageResource(R.drawable.right_off);
                        return true;
                }
                return false;
            }
        });
    }

    private void addTask(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        EditText itemAddEditText = new EditText(getContext());
        itemAddEditText.setMaxEms(15);
        itemAddEditText.setMaxLines(1);
        itemAddEditText.setLines(1);
        builder.setTitle(getString(R.string.item_add_title));
        builder.setView(itemAddEditText);
        builder.setPositiveButton(getString(R.string.item_add_ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String memo = itemAddEditText.getText().toString();
                        DayItemCard item = new DayItemCard(getContext());
                        int itemId;
                        item.setText(memo);
                        itemId = listener.onItemInsert(curDay, memo);

                        if(itemId == -1){
                            return;
                        }
                        item.createItem(new ToDoItem(itemId, curDay, memo));

                        adapter.addItem(item);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton(getString(R.string.item_add_cancel),
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

    private void changeDate(int dd){
        curDay.add(Calendar.DATE, dd);
        setDateTitle(curDay);
    }
}