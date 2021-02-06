package com.blueshadow.todolist.ui.day;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.blueshadow.todolist.MainActivity;
import com.blueshadow.todolist.OnItemChangedListener;
import com.blueshadow.todolist.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DayFragment extends Fragment implements OnChangeDayListener{
    final static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

    private Day today;
    private Day curDay;

    TextView dateTextView;
    ImageView leftButton;
    ImageView rightButton;
    ImageView addButton;

    ListView listView;
    DayItemCardAdapter adapter;

    private OnItemChangedListener listener;

    public DayFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnItemChangedListener){
            listener = (OnItemChangedListener) context;
        }
        else{
            throw new RuntimeException(context.toString()
                    + "must implement OnItemChangedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_day, container, false);

        dateTextView = view.findViewById(R.id.day_dateTextView);
        leftButton = view.findViewById(R.id.day_leftButton);
        rightButton = view.findViewById(R.id.day_rightButton);

        listView = view.findViewById(R.id.day_listView);
        adapter = new DayItemCardAdapter(getContext());

        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.removeItem(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        today = new Day();
        curDay = new Day();

        setToday();
        setCurDay(today.getYear(), today.getMonth(), today.getDay());

        addButton = view.findViewById(R.id.day_addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
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

    private void addTask(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        EditText itemAddEditText = new EditText(getContext());
        builder.setTitle(getString(R.string.item_add_title));
        builder.setView(itemAddEditText);
        builder.setPositiveButton(getString(R.string.item_add_ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String memo = itemAddEditText.getText().toString();
                        DayItemCard item = new DayItemCard(getContext());
                        item.setText(memo);

                        //   listener.onItemInsert(curDay, memo);

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
}