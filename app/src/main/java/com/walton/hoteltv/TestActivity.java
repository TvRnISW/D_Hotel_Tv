package com.walton.hoteltv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import com.walton.hoteltv.sharedpref.SharedPrefClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TestActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";
    private TestViewModel mTestViewModel;
    private List<String> userList;
    private TextView txtTime,txtWelcomeNote, txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initUI();

        mTestViewModel = ViewModelProviders.of(this).get(TestViewModel.class);

        mTestViewModel.getUserData(SharedPrefClass.getRoomNumber(this).substring(12)).observe(TestActivity.this, list -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder str = new StringBuilder();
                    for (int i = 0; i < list.size(); i++){
                        Log.d(TAG, "run: "+String.valueOf(i)+" "+String.valueOf(list.size()));
                        if (i > 0 && i != list.size()){
                            if (i == list.size() - 1){
                                str.append(" and ");
                            }else {
                                str.append(",");
                            }
                        }
                        str.append(list.get(i));
                    }
                    txtWelcomeNote.setText("Welcome to\nRadisson Blu Hotel, Dubai Canal View\n"+str.toString());
                }
            });
        });

    }

    private void initUI() {
        txtTime = findViewById(R.id.txtTime);
        txtWelcomeNote = findViewById(R.id.txtWelcomeNote);
        txtDate = findViewById(R.id.txtDate);
        showDateTime();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown: "+keyCode);
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER){
            startActivity(new Intent(TestActivity.this, MenuActivity.class));
        }
        return super.onKeyDown(keyCode, event);
    }


    private void showDateTime() {
        txtTime.setText(Utils.getDate());

        final Handler someHandler = new Handler(getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String time = new SimpleDateFormat("HH:mm aa", Locale.US).format(new Date());
                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date());
                txtTime.setText(time);
                txtDate.setText(date);
                someHandler.postDelayed(this, 1000);
            }
        }, 10);
    }

}