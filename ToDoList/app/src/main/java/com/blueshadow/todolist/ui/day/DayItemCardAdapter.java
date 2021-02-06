package com.blueshadow.todolist.ui.day;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blueshadow.todolist.R;

import java.util.ArrayList;

public class DayItemCardAdapter extends BaseAdapter {
    Context context = null;
    private TextView textView;
    private ImageView cancelButton;
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
        View v = (FrameLayout) inflater.inflate(R.layout.item_card, null);

        DayItemCard item = v.findViewById(R.id.itemCard);
        item.setText(items.get(position).getText());

        return v;
    }

    public void addItem(DayItemCard item) {
        items.add(item);
    }
    public void removeItem(int position){
        items.remove(position);
    }
}
