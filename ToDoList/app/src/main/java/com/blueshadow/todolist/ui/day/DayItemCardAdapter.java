package com.blueshadow.todolist.ui.day;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blueshadow.todolist.R;

import java.util.ArrayList;

public class DayItemCardAdapter extends BaseAdapter {
    Context context = null;
    private ArrayList<DayItemCard> items = new ArrayList<>();

    public DayItemCardAdapter(Context context) {
        this.context = context;
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
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = (LinearLayout) inflater.inflate(R.layout.day_item_card, null);

        TextView textView = v.findViewById(R.id.day_item_textView);
        textView.setText(items.get(position).getItem().getMemo());

        if(items.get(position).getItemDone() == true){
            textView.setBackgroundResource(R.drawable.day_item_box_complete);
            textView.setPaintFlags(textView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else{
            textView.setBackgroundResource(R.drawable.day_item_box_incomplete);
            textView.setPaintFlags(textView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            textView.setPaintFlags(0);
        }

        return v;
    }

    public void addItem(DayItemCard item) {
        items.add(item);
    }
    public void removeItem(int position){
        items.remove(position);
    }

    public void convertItemDone(int position, boolean done){
        items.get(position).setItemDone(done);
        items.get(position).getItem().setDone(done);
    }

    public void cleanItems(){
        items.clear();
    }
}