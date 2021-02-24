package com.blueshadow.todolist.ui.week;

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

public class WeekItemCardAdapter extends BaseAdapter {
    Context context = null;
    private ArrayList<WeekItemCard> items = new ArrayList<WeekItemCard>();

    public WeekItemCardAdapter(Context context) {
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
        View v = (LinearLayout) inflater.inflate(R.layout.week_item_card, null);

        TextView textView = v.findViewById(R.id.week_item_textView);
        textView.setText(items.get(position).getItem().getMemo());

        if(items.get(position).getItemDone() == true){
            textView.setBackgroundResource(R.drawable.week_day_item_box_selected);
            textView.setPaintFlags(textView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else{
            textView.setBackgroundResource(R.drawable.week_day_item_box);
            textView.setPaintFlags(textView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            textView.setPaintFlags(0);
        }

        return v;
    }

    public void addItem(WeekItemCard item) {
        items.add(item);
    }
    public void removeItem(int position){
        items.remove(position);
    }

    public void cleanItems(){
        items.clear();
    }

    public void convertItemDone(int position, boolean done){
        items.get(position).getItem().setDone(done);
        items.get(position).setItemDone(done);
    }
}
