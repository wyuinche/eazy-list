package com.blueshadow.todolist.ui.month;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blueshadow.todolist.R;

import java.util.ArrayList;

public class MonthItemCardAdapter extends BaseAdapter {
    ArrayList<MonthItemCard> items = new ArrayList<>();
    Context context;

    public MonthItemCardAdapter(Context context){
        this.context = context;
    }

    public void addItem(MonthItemCard item){
        items.add(item);
    }

    public void cleanItems(){
        items.clear();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MonthItemCard item = items.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.month_day_card, parent, false);
        }

        TextView dateTextView = convertView.findViewById(R.id.month_day_date_textView);
        TextView countTextView = convertView.findViewById(R.id.month_day_item_count_textView);

        int date = item.getDay();
        int count = item.getItemCount();

        dateTextView.setText(""+date);
        if(count == 0){
            countTextView.setText("");
        }
        else{
            countTextView.setText(""+count);
        }

        return convertView;
    }
}
