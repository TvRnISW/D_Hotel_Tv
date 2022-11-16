package com.walton.hoteltv;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mstar.android.tv.TvCommonManager;
import com.walton.hoteltv.dialog.RoomNumDialog;
import com.walton.hoteltv.sharedpref.SharedPrefClass;
import com.walton.hoteltv.viewmodel.TestMainViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TestMainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private static final String TAG = "TestMainActivity";
    private TestMainViewModel mTestMainViewModel;
    private TextView txtRoomNumber;
    private int[] imageArray = {R.drawable.fitnessspa, R.drawable.travelguide, R.drawable.testimage};
    private ImageView imgTvLeft, imgTvRight, imgHIleft, imgHIright, imgWeatherLeft, imgWeatherRight, imgServiceLeft, imgServiceRight, imgShow;

    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == TestConstant.TELEVISION_FOCUS) {
                imgTvLeft.setVisibility(View.VISIBLE);
                imgTvRight.setVisibility(View.VISIBLE);
            } else if (msg.what == TestConstant.TELEVISION_UN_FOCUS) {
                imgTvLeft.setVisibility(View.GONE);
                imgTvRight.setVisibility(View.GONE);
            } else if (msg.what == TestConstant.HI_FOCUS) {
                imgHIleft.setVisibility(View.VISIBLE);
                imgHIright.setVisibility(View.VISIBLE);
            } else if (msg.what == TestConstant.HI_UN_FOCUS) {
                imgHIleft.setVisibility(View.GONE);
                imgHIright.setVisibility(View.GONE);
            } else if (msg.what == TestConstant.WEATHER_FOCUS) {
                imgWeatherLeft.setVisibility(View.VISIBLE);
                imgWeatherRight.setVisibility(View.VISIBLE);
            } else if (msg.what == TestConstant.WEATHER_UN_FOCUS) {
                imgWeatherLeft.setVisibility(View.GONE);
                imgWeatherRight.setVisibility(View.GONE);
            } else if (msg.what == TestConstant.SERVICE_FOCUS) {
                imgServiceLeft.setVisibility(View.VISIBLE);
                imgServiceRight.setVisibility(View.VISIBLE);
            } else if (msg.what == TestConstant.SERVICE_UN_FOCUS) {
                imgServiceLeft.setVisibility(View.GONE);
                imgServiceRight.setVisibility(View.GONE);
            }
        }
    };
    private LinearLayout lnTelevision, lnHI, lnWeather, lnMyService;
    private TextView txtTime, txtDate;
    private StringBuilder roomSetMenuCode;
    private RoomNumDialog roomNumDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);
        initUI();
    }

    private void initUI() {
        txtRoomNumber = findViewById(R.id.txtRoomNumber);
        txtTime = findViewById(R.id.txtTime);
        txtDate = findViewById(R.id.txtDate);
        imgShow = findViewById(R.id.imgShow);
        lnTelevision = findViewById(R.id.lnTelevision);
        lnHI = findViewById(R.id.lnHI);
        lnWeather = findViewById(R.id.lnWeather);
        lnMyService = findViewById(R.id.lnMyService);

        lnTelevision.setOnClickListener(this);
        lnHI.setOnClickListener(this);
        lnWeather.setOnClickListener(this);
        lnMyService.setOnClickListener(this);

        lnTelevision.setOnFocusChangeListener(this);
        lnHI.setOnFocusChangeListener(this);
        lnWeather.setOnFocusChangeListener(this);
        lnMyService.setOnFocusChangeListener(this);

        imgTvLeft = findViewById(R.id.imgTvLeft);
        imgTvRight = findViewById(R.id.imgTvRight);
        imgHIleft = findViewById(R.id.imgHIleft);
        imgHIright = findViewById(R.id.imgHIright);
        imgWeatherLeft = findViewById(R.id.imgWeatherLeft);
        imgWeatherRight = findViewById(R.id.imgWeatherRight);
        imgServiceLeft = findViewById(R.id.imgServiceLeft);
        imgServiceRight = findViewById(R.id.imgServiceRight);

        showDateTime();

        showImageShow();

        setRoomNumber();
    }

    private void setRoomNumber() {
//        SharedPrefClass.clearData(this);
        txtRoomNumber.setText(SharedPrefClass.getRoomNumber(this));
    }

    private void showImageShow() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i = 0;

            @Override
            public void run() {
                imgShow.setImageResource(imageArray[i]);
                ObjectAnimator.ofFloat(imgShow, View.ALPHA, 0.2f, 1.0f).setDuration(1500).start();
                i++;
                if (i > imageArray.length - 1) {
                    i = 0;
                }
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(runnable, 5000);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnTelevision:
                //Utils.startMTVPlayer(TvCommonManager.INPUT_SOURCE_ATV, TestMainActivity.this);
                Utils.openApk("com.mediatek.wwtv.tvcenter","com.mediatek.wwtv.tvcenter.nav.TurnkeyUiMainActivity",TestMainActivity.this);
                break;
            case R.id.lnHI:
                startActivity(new Intent(TestMainActivity.this, TestActivity.class));
                break;
            case R.id.lnWeather:
                Utils.openApk("com.cvte.tv.appstore","com.cvte.tv.appstore.AppStoreActivity",TestMainActivity.this);
                break;
            case R.id.lnMyService:
                startActivity(new Intent(TestMainActivity.this, MyServiceActivity.class));
                break;

        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.lnTelevision:
                if (b) {
                    handler.sendEmptyMessage(TestConstant.TELEVISION_FOCUS);
                } else {
                    handler.sendEmptyMessage(TestConstant.TELEVISION_UN_FOCUS);
                }
                break;
            case R.id.lnHI:
                if (b) {
                    handler.sendEmptyMessage(TestConstant.HI_FOCUS);
                } else {
                    handler.sendEmptyMessage(TestConstant.HI_UN_FOCUS);
                }
                break;
            case R.id.lnWeather:
                if (b) {
                    handler.sendEmptyMessage(TestConstant.WEATHER_FOCUS);
                } else {
                    handler.sendEmptyMessage(TestConstant.WEATHER_UN_FOCUS);
                }
                break;
            case R.id.lnMyService:
                if (b) {
                    handler.sendEmptyMessage(TestConstant.SERVICE_FOCUS);
                } else {
                    handler.sendEmptyMessage(TestConstant.SERVICE_UN_FOCUS);
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown: " + keyCode);
        showRoomNumSetMenu();
//        if (keyCode == 82) {
        if (keyCode == 165) {
            Log.d(TAG, "showing dialog");

        }

           // roomSetMenuCode = new StringBuilder(5);
//        } else {
//            try {
//                roomSetMenuCode.append(keyCode);
//            } catch (NullPointerException e) {
//                e.printStackTrace();
//            }
//        }

//        if (keyCode == 7) {
//            Log.d(TAG, "onKeyDown: " + roomSetMenuCode.toString());
//            if (roomSetMenuCode.toString().equalsIgnoreCase("1657")) {
//                Log.d(TAG, "onKeyDown: Show Menu");
//                showRoomNumSetMenu();
//            }
//        }

        return super.onKeyDown(keyCode, event);
    }

    private void showRoomNumSetMenu() {
        Log.d(TAG, "showRoomNumSetMenu: ");
        roomNumDialog = new RoomNumDialog(this, new RoomNumDialog.RoomData() {
            @Override
            public void getRoomData(String data) {
                if (data.equalsIgnoreCase("cancel")) {
                    roomNumDialog.dismiss();
                } else {
                    DatabaseReference rf = FirebaseDatabase.getInstance().getReference().child("rooms");
                    rf.child(data).setValue(data).addOnSuccessListener(aVoid -> {
                        SharedPrefClass.saveRoomNum(getApplicationContext(), "Room Number "+data);
                        setRoomNumber();
                    }).addOnFailureListener(runnable -> {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                    });
                }
                roomNumDialog.dismiss();
            }
        });
        roomNumDialog.show();
    }
}