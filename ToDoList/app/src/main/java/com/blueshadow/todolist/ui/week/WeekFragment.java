package com.blueshadow.todolist.ui.week;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blueshadow.todolist.DateController;
import com.blueshadow.todolist.OnItemAndDateChangedListener;
import com.blueshadow.todolist.R;
import com.blueshadow.todolist.ToDoItem;
import com.blueshadow.todolist.ui.day.DayItemCard;

import java.util.ArrayList;
import java.util.Calendar;

public class WeekFragment extends Fragment implements DateController {
    private Calendar curDay;
    private Calendar[] dayOnDisplay = new Calendar[7];

    private TextView dateTextView;
    private ImageView leftButton;
    private ImageView rightButton;
    private ImageView backButton;

    private TextView[] dayDateTextViews = new TextView[7];
    private TextView[] dayTextViews = new TextView[7];
    private LinearLayout[] dayAddLayouts = new LinearLayout[7];

    private ListView[] listViews = new ListView[7];
    private WeekItemCardAdapter[] adapters = new WeekItemCardAdapter[7];

    private OnItemAndDateChangedListener listener;

    int selectedWd = -1;

    public WeekFragment() { }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);
        findViews(view);

        for(int i=0; i<7; i++){
            adapters[i] = new WeekItemCardAdapter(getContext());
            listViews[i].setAdapter(adapters[i]);
        }
        setListeners(view);

        init(view);

        return view;
    }

    @Override
    public void init(View view) {
        curDay = listener.getCurrentCalendar(listener.WEEK_FRAGMENT);
        changeDate(Calendar.DATE, 0);
    }

    @Override
    public void findViews(View view){
        findListViews(view);

        dateTextView = view.findViewById(R.id.week_dateTextView);
        leftButton = view.findViewById(R.id.week_leftButton);
        rightButton = view.findViewById(R.id.week_rightButton);
        backButton = view.findViewById(R.id.week_backButton);

        dayTextViews[0] = view.findViewById(R.id.week_sunday_textView);
        dayTextViews[1] = view.findViewById(R.id.week_monday_textView);
        dayTextViews[2] = view.findViewById(R.id.week_tuesday_textView);
        dayTextViews[3] = view.findViewById(R.id.week_wednesday_textView);
        dayTextViews[4] = view.findViewById(R.id.week_thursday_textView);
        dayTextViews[5] = view.findViewById(R.id.week_friday_textView);
        dayTextViews[6] = view.findViewById(R.id.week_saturday_textView);

        dayDateTextViews[0] = view.findViewById(R.id.week_sunday_date_textView);
        dayDateTextViews[1] = view.findViewById(R.id.week_monday_date_textView);
        dayDateTextViews[2] = view.findViewById(R.id.week_tuesday_date_textView);
        dayDateTextViews[3] = view.findViewById(R.id.week_wednesday_date_textView);
        dayDateTextViews[4] = view.findViewById(R.id.week_thursday_date_textView);
        dayDateTextViews[5] = view.findViewById(R.id.week_friday_date_textView);
        dayDateTextViews[6] = view.findViewById(R.id.week_saturday_date_textView);

        dayAddLayouts[0] = view.findViewById(R.id.week_sunday_addLayout);
        dayAddLayouts[1] = view.findViewById(R.id.week_monday_addLayout);
        dayAddLayouts[2] = view.findViewById(R.id.week_tuesday_addLayout);
        dayAddLayouts[3] = view.findViewById(R.id.week_wednesday_addLayout);
        dayAddLayouts[4] = view.findViewById(R.id.week_thursday_addLayout);
        dayAddLayouts[5] = view.findViewById(R.id.week_friday_addLayout);
        dayAddLayouts[6] = view.findViewById(R.id.week_saturday_addLayout);
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
                        changeDate(Calendar.DATE, -7);
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
                        changeDate(Calendar.DATE, 7);
                        rightButton.setImageResource(R.drawable.ic_right_off);
                        return true;
                }
                return false;
            }
        });

        AdapterView.OnItemLongClickListener listViewItemLongClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                startTaskForItem((WeekItemCardAdapter) parent.getAdapter(), position);
                return true;
            }
        };

        AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView)view.findViewById(R.id.week_item_textView);

                WeekItemCard item = (WeekItemCard)((WeekItemCardAdapter) parent.getAdapter()).getItem(position);
                if(item.getItemDone() == true){
                    textView.setBackgroundResource(R.drawable.week_day_item_box);
                    textView.setPaintFlags(textView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                    textView.setPaintFlags(0);
                    ((WeekItemCardAdapter) parent.getAdapter()).convertItemDone(position, false);
                }
                else{
                    textView.setBackgroundResource(R.drawable.week_day_item_box_selected);
                    textView.setPaintFlags(textView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                    ((WeekItemCardAdapter) parent.getAdapter()).convertItemDone(position, true);
                }
                listener.onItemUpdate(item.getItem().get_id(), item.getItem().getMemo(), item.getItem().getDone());
            }
        };

        View.OnClickListener addTaskClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.week_sunday_addLayout:
                        selectedWd = 0;
                        break;
                    case R.id.week_monday_addLayout:
                        selectedWd = 1;
                        break;
                    case R.id.week_tuesday_addLayout:
                        selectedWd = 2;
                        break;
                    case R.id.week_wednesday_addLayout:
                        selectedWd = 3;
                        break;
                    case R.id.week_thursday_addLayout:
                        selectedWd = 4;
                        break;
                    case R.id.week_friday_addLayout:
                        selectedWd = 5;
                        break;
                    case R.id.week_saturday_addLayout:
                        selectedWd = 6;
                        break;
                    default:
                        selectedWd = -1;
                }
                addTask();
            }
        };

        for(int i = 0; i < 7; i++){
            listViews[i].setOnItemLongClickListener(listViewItemLongClickListener);
            listViews[i].setOnItemClickListener(listViewItemClickListener);
            dayAddLayouts[i].setOnClickListener(addTaskClickListener);
        }
    }

    private void startTaskForItem(WeekItemCardAdapter targetAdapter, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(getString(R.string.item_task_title));
        builder.setPositiveButton(getString(R.string.item_task_copy),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String memo = ((WeekItemCard) targetAdapter.getItem(position)).getItem().getMemo();

                        ClipboardManager clipManager =
                                (ClipboardManager) getContext().getSystemService(getContext().CLIPBOARD_SERVICE);
                        ClipData data = ClipData.newPlainText("label", memo);

                        clipManager.setPrimaryClip(data);
                        Toast.makeText(getContext(),  getString(R.string.item_task_copy_done), Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton(getString(R.string.item_task_modify),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        modifyItem(targetAdapter, position);
                        dialog.dismiss();
                    }
                });
        builder.setNeutralButton(getString(R.string.item_task_delete),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onItemDelete(((WeekItemCard)targetAdapter.getItem(position)).getItem().get_id());
                        targetAdapter.removeItem(position);
                        targetAdapter.notifyDataSetChanged();

                        dialog.dismiss();
                    }
                });

        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void modifyItem(WeekItemCardAdapter targetAdapter, int position){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        LinearLayout dialogLayout = (LinearLayout) inflater.inflate(R.layout.month_add_task_dialog, null);

        DatePicker datePicker = dialogLayout.findViewById(R.id.month_add_task_datePicker);
        EditText editText = dialogLayout.findViewById(R.id.month_add_task_editText);
        editText.setText(((WeekItemCard) targetAdapter.getItem(position)).getItem().getMemo());

        datePicker.setSpinnersShown(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogLayout);
        builder.setTitle(getString(R.string.item_task_modify));
        builder.setPositiveButton(getString(R.string.item_done),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String memo = editText.getText().toString();
                        Calendar selectedCal =  Calendar.getInstance();
                        selectedCal.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                        int itemId;
                        itemId = listener.onItemInsert(selectedCal, memo);
                        if (itemId == -1) {
                            return;
                        }
                        listener.onItemDelete(((WeekItemCard) targetAdapter.getItem(position)).getItem().get_id());
                        targetAdapter.notifyDataSetChanged();
                        changeDate(Calendar.DATE, 0);
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton(getString(R.string.item_cancel),
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

    @Override
    public void setDateTitle(Calendar cal) {
        dateTextView.setText(cal.get(Calendar.YEAR) + " / "
                + (cal.get(Calendar.MONTH)+1));
    }

    @Override
    public void changeDate(int field, int dd) {
        curDay.add(field, dd);
        setDayOnDisplay(curDay);
        setDateTitle(curDay);

        clearDayOnDisplayColor();
        if(curDay.get(Calendar.YEAR) == listener.getTodayCalendar().get(Calendar.YEAR)
                &&curDay.get(Calendar.MONTH) == listener.getTodayCalendar().get(Calendar.MONTH)
                &&curDay.get(Calendar.DATE) == listener.getTodayCalendar().get(Calendar.DATE)){
            setDayOnDisplayColor(curDay);
        }

        setDayOfWeekTextViews();
        setList(curDay);
    }

    @Override
    public void setList(Calendar cal) {
        Calendar baseCal = Calendar.getInstance();
        baseCal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        baseCal.add(Calendar.DATE, -cal.get(Calendar.DAY_OF_WEEK) + 1);

        Log.d("WeekFragment", "" + listener.parseCalendarToIntDate(baseCal));
        for(int i = 0; i < 7; i++){
            ArrayList<ToDoItem> items = listener.onItemSelect(baseCal, listener.SELECT_MODE_ALL);
            adapters[i].cleanItems();
            for(int j = 0; j < items.size(); j++){
                adapters[i].addItem(new WeekItemCard(items.get(j)));
            }
            adapters[i].notifyDataSetChanged();
            baseCal.add(Calendar.DATE, 1);
        }
    }

    @Override
    public void backToday() {
        curDay = Calendar.getInstance();
        changeDate(Calendar.DATE, 0);
    }

    @Override
    public void addTask() {
        if(selectedWd == -1){
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        EditText itemAddEditText = new EditText(getContext());
        itemAddEditText.setMaxEms(15);
        itemAddEditText.setMaxLines(1);
        itemAddEditText.setLines(1);
        itemAddEditText.setHint(R.string.item_add_hint);
        builder.setTitle(getString(R.string.item_add_title));
        builder.setView(itemAddEditText);
        builder.setPositiveButton(getString(R.string.item_add_ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String memo = itemAddEditText.getText().toString();
                        Calendar selectedCal = getSelectedCal(selectedWd);
                        int itemId;
                        itemId = listener.onItemInsert(selectedCal, memo);
                        if (itemId == -1) {
                            return;
                        }
                        WeekItemCard item = new WeekItemCard(new ToDoItem(itemId, selectedCal, memo));
                        adapters[selectedWd].addItem(item);
                        adapters[selectedWd].notifyDataSetChanged();
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
        tmpCal.add(Calendar.DATE, wd - curDay.get(Calendar.DAY_OF_WEEK) + 1);
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

        dayTextViews[wd-1].setBackgroundResource(R.drawable.week_day_title_box_selected);
        listViews[wd-1].setBackgroundResource(R.drawable.week_listview_background_selected);
    }

    private void clearDayOnDisplayColor(){
        for(int i=0; i < 7; i++){
            dayTextViews[i].setBackgroundResource(R.drawable.week_day_title_box);
            listViews[i].setBackgroundResource(R.drawable.week_listview_background);
        }
    }

    private void setDayOfWeekTextViews(){
        for(int i = 0; i < 7; i++){
            dayDateTextViews[i].setText(""+dayOnDisplay[i].get(Calendar.DATE));
        }
    }
}