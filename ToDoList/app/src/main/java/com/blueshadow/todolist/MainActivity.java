package com.blueshadow.todolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;

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
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnItemAndDateChangedListener {
    final public static int REQUEST_CODE_PAY = 101;
    final public static int REQUEST_CODE_SETTING = 102;

    final public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy / MM / dd");
    final public static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

    final public static String FILE_NAME = "BLUESHADOW_TODOLIST";
    final public static String CUR_ID_PREF_NAME = "CUR_ID";
    final public static String MEMO_PREF_NAME = "MEMO";

    NavigationView navigationView;
    DrawerLayout drawer;
    Toolbar toolbar;

    BottomNavigationView tab;

    Fragment dayFragment;
    Fragment weekFragment;
    Fragment monthFragment;
    FragmentManager manager;
    Calendar dayCal;
    Calendar weekCal;
    Calendar monthCal;

    SQLiteDatabase db;
    ListDatabaseHelper helper;

    SharedPreferences pref;
    SharedPreferences.Editor prefEditor;
    int curId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dayCal = Calendar.getInstance();
        weekCal = Calendar.getInstance();
        monthCal = Calendar.getInstance();

        pref = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        prefEditor = pref.edit();
        curId = getCurId();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(dateFormat.format(new Date()) + " ("
                + getWeekdayString(getTodayCalendar().get(Calendar.DAY_OF_WEEK)) + ")");
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

    private int getCurId(){
        int tmp = pref.getInt(CUR_ID_PREF_NAME, -1);
        if(tmp == -1){
            return 1;
        }
        else{
            return tmp;
        }
    }

    private void setCurId(){
        curId += 1;
        prefEditor.putInt(CUR_ID_PREF_NAME, curId);
        prefEditor.commit();
    }

    @Override
    public String getWeekdayString(int wd){
        switch(wd){
            case Calendar.SUNDAY:
                return getString(R.string.sunday);
            case Calendar.MONDAY:
                return getString(R.string.monday);
            case Calendar.TUESDAY:
                return getString(R.string.tuesday);
            case Calendar.WEDNESDAY:
                return getString(R.string.wednesday);
            case Calendar.THURSDAY:
                return getString(R.string.thursday);
            case Calendar.FRIDAY:
                return getString(R.string.friday);
            case Calendar.SATURDAY:
                return getString(R.string.saturday);
        }
        return getString(R.string.Null);
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
    public int onItemInsert(Calendar curCal, String memo) {
        if(db == null){
            return -1;
        }
        int date = parseCalendarToIntDate(curCal);

//        db.execSQL("insert into "
//                + helper.TABLE_NAME
//                + " (" + helper.COLUMN_NAMES[0] + ", "
//                + helper.COLUMN_NAMES[1] + ", "
//                + helper.COLUMN_NAMES[2] + ", "
//                + helper.COLUMN_NAMES[3] + ")"
//                + " values "
//                + " (" + curId + ", " + date + ", '" + memo + "', 0)"
//        );

//        setCurId();
//        return getCurId() - 1;
        return 0;
    }

    @Override
    public void onItemDelete(int id) {
//        db.execSQL("delete from " + helper.TABLE_NAME
//                + " where _id = " + id);
    }

    @Override
    public void onItemUpdate(int id, String memo, boolean done) {
        int isDone = 0;
        if(done == true){
            isDone = 1;
        }
        db.execSQL("update " + helper.TABLE_NAME
                + " set "
                + helper.COLUMN_NAMES[2] + " = " + memo + ", "
                + helper.COLUMN_NAMES[3] + " = " + isDone
                + " where _id = " + id
        );
    }

    @Override
    public int onItemCountForDate(Calendar day) {
        return 0;
    }

    @Override
    public Calendar getTodayCalendar() {
        return Calendar.getInstance();
    }

    @Override
    public int parseCalendarToIntDate(Calendar calendar) {
        String year = "" + calendar.get(Calendar.YEAR);
        String month = "" + calendar.get(Calendar.MONTH) + 1;
        String day = "" + calendar.get(Calendar.DATE);

        if(month.length() < 2){
            month = "0" + month;
        }
        if(day.length() < 2){
            day = "0" + day;
        }

        return Integer.parseInt(year+month+day);
    }

    @Override
    public void setCurrentCalendar(int fragm, Calendar calendar) {
        switch(fragm){
            case DAY_FRAGMENT:
                dayCal = calendar;
                break;
            case WEEK_FRAGMENT:
                weekCal = calendar;
                break;
            case MONTH_FRAGMENT:
                monthCal = calendar;
        }
    }

    @Override
    public Calendar getCurrentCalendar(int fragm) {
        switch(fragm){
            case DAY_FRAGMENT:
                return dayCal;
            case WEEK_FRAGMENT:
                return weekCal;
            case MONTH_FRAGMENT:
                return monthCal;
            default:
                return getTodayCalendar();
        }
    }

    @Override
    public void goToDailyList(int year, int month, int day) {
        dayCal.set(year, month, day);
        setCurrentCalendar(DAY_FRAGMENT, dayCal);
        manager.beginTransaction().replace(R.id.container, dayFragment).commit();
    }

    @Override
    public void setMemo(String memo) {
        prefEditor.putString(MEMO_PREF_NAME, memo);
        prefEditor.commit();
    }

    @Override
    public String getMemo() {
        String tmp = pref.getString(MEMO_PREF_NAME, "");
        return tmp;
    }
}