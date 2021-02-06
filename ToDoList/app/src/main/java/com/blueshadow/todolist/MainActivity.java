package com.blueshadow.todolist;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;

import com.blueshadow.todolist.ui.day.Day;
import com.blueshadow.todolist.ui.day.DayFragment;
import com.blueshadow.todolist.ui.month.MonthFragment;
import com.blueshadow.todolist.ui.week.WeekFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnItemChangedListener {
    final static int REQUEST_CODE_PAY = 101;
    final static int REQUEST_CODE_SETTING = 102;
    final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy / MM / dd");

    NavigationView navigationView;

    DrawerLayout drawer;
    Toolbar toolbar;

    Fragment dayFragment;
    Fragment weekFragment;
    Fragment monthFragment;
    FragmentManager manager;

    BottomNavigationView tab;

    SQLiteDatabase db;
    ListDatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(dateFormat.format(new Date()));
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        manager = getSupportFragmentManager();

        helper = new ListDatabaseHelper(getApplicationContext());
        db = helper.getWritableDatabase();

        dayFragment = new DayFragment();
        weekFragment = new WeekFragment();
        monthFragment = new MonthFragment();

        tab = findViewById(R.id.tab_view);
        tab.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return onTabPressed(item.getItemId());
            }
        });

        manager.beginTransaction().add(R.id.container, dayFragment).commit();
    }

    private boolean onTabPressed(int res){
        switch(res){
            case R.id.tab_day:
                manager.beginTransaction().replace(R.id.container, dayFragment).commit();
                return true;
            case R.id.tab_week:
                manager.beginTransaction().replace(R.id.container, weekFragment).commit();
                return true;
            case R.id.tab_month:
                manager.beginTransaction().replace(R.id.container, monthFragment).commit();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_reset){

        }
        else if(id == R.id.nav_pay){
            Intent intent = new Intent(this, PayActivity.class);
            startActivityForResult(intent, REQUEST_CODE_PAY);
        }
        else if(id == R.id.nav_set){
            Intent intent = new Intent(this, SettingActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SETTING);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemInsert(Day day, String memo) {
        if(db == null){
            return;
        }
        String dateY = "" + day.getYear();
        String dateM = "" + day.getMonth();
        String dateD = "" + day.getDay();
        int date = 0;

        if(dateM.length() < 2){
            dateM = "0" + dateM;
        }
        if(dateD.length() < 2){
            dateD = "0" + dateD;
        }

        date = Integer.parseInt(dateY + dateM + dateD);

        db.execSQL("insert into "
                + helper.TABLE_NAME
                + " (" + helper.COLUMN_NAMES[0] + ", "
                + helper.COLUMN_NAMES[1] + ", "
                + helper.COLUMN_NAMES[0] + ")"
                + " values "
                + " (" + date + ", '" + memo + "', 0)"
        );


    }

    @Override
    public void onItemDelete(int id) {
        db.execSQL("delete from " + helper.TABLE_NAME
                + " where _id = " + id);
    }

    @Override
    public void onItemUpdate(int id, String memo, boolean done) {
        int isDone = 0;
        if(done == true){
            isDone = 1;
        }
        db.execSQL("update " + helper.TABLE_NAME
                + " set "
                + helper.COLUMN_NAMES[1] + " = " + memo + ", "
                + helper.COLUMN_NAMES[2] + " = " + isDone
                + " where _id = " + id
        );
    }
}