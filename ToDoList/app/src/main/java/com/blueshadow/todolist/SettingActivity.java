package com.blueshadow.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

public class SettingActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    int curTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        curTheme = getIntent().getIntExtra(MainActivity.THEME_PREF_NAME, -1);
        if(curTheme == MainActivity.THEME_BLACK){
            setTheme(R.style.DarkTheme);
            curTheme = R.id.setting_theme_dark;
        }
        else if(curTheme == MainActivity.THEME_BLUE){
            setTheme(R.style.BlueTheme);
            curTheme = R.id.setting_theme_blue;
        }
        else{
            setTheme(R.style.PurpleTheme);
            curTheme = R.id.setting_theme_purple;
        }

        setContentView(R.layout.activity_setting);

        radioGroup = findViewById(R.id.setting_theme_radio);
        radioGroup.check(curTheme);
    }

    @Override
    public void onBackPressed() {
        int radioId = radioGroup.getCheckedRadioButtonId();
        Intent intent = new Intent();
        switch(radioId){
            case R.id.setting_theme_purple:
                intent.putExtra(MainActivity.THEME_PREF_NAME, MainActivity.THEME_PURPLE);
                break;
            case R.id.setting_theme_blue:
                intent.putExtra(MainActivity.THEME_PREF_NAME, MainActivity.THEME_BLUE);
                break;
            case R.id.setting_theme_dark:
                intent.putExtra(MainActivity.THEME_PREF_NAME, MainActivity.THEME_BLACK);
                break;
            default:
                intent.putExtra(MainActivity.THEME_PREF_NAME, -1);
        }

        setResult(RESULT_OK, intent);
        finish();
    }
}