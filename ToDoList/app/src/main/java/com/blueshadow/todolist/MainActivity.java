package com.blueshadow.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

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
        implements NavigationView.OnNavigationItemSelectedListener{
    final static int REQUEST_CODE_PAY = 101;
    final static int REQUEST_CODE_SETTING = 102;
    final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy / MM / dd");

    NavigationView navigationView;

    DrawerLayout drawer;
    Toolbar toolbar;
    ImageView checkButton;

    Fragment dayFragment;
    Fragment weekFragment;
    Fragment monthFragment;
    FragmentManager manager;

    BottomNavigationView tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(dateFormat.format(new Date()));
        setSupportActionBar(toolbar);

        checkButton = findViewById(R.id.checkButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tab = findViewById(R.id.tab_view);
        tab.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return onTabPressed(item.getItemId());
            }
        });

        manager = getSupportFragmentManager();

        dayFragment = new DayFragment();
        weekFragment = new WeekFragment();
        monthFragment = new MonthFragment();

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

}