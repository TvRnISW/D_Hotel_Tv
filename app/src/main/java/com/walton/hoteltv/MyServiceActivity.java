package com.walton.hoteltv;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mstar.android.tv.TvCommonManager;


public class MyServiceActivity extends AppCompatActivity implements View.OnFocusChangeListener, View.OnClickListener {

    private Button btnHDMIOne,btnHDMITwo, btnAyna,btnSetting,btnYoutube,btnMediaPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_service);

        initUI();
    }

    private void initUI() {
        btnHDMIOne = findViewById(R.id.btnHDMIOne);
        btnHDMITwo = findViewById(R.id.btnHdmiTwo);
        btnAyna = findViewById(R.id.ayna);
        btnSetting = findViewById(R.id.btnSetting);
        btnYoutube = findViewById(R.id.btnYoutube);
        btnMediaPlayer = findViewById(R.id.btnMediaPlayer);

        btnAyna.setOnClickListener(this);
        btnHDMIOne.setOnClickListener(this);
        btnHDMITwo.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        btnYoutube.setOnClickListener(this);
        btnMediaPlayer.setOnClickListener(this);

        btnHDMIOne.setOnFocusChangeListener(this);
        btnHDMITwo.setOnFocusChangeListener(this);
        btnAyna.setOnFocusChangeListener(this);
        btnSetting.setOnFocusChangeListener(this);
        btnYoutube.setOnFocusChangeListener(this);
        btnMediaPlayer.setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()){
            case R.id.btnHDMIOne:
                if (b) {
                    btnHDMIOne.setTextColor(Color.BLACK);
                    //handler.sendEmptyMessage(TestConstant.HDMI_ONE_FOCUS);
                } else {
                    btnHDMIOne.setTextColor(Color.WHITE);
                }
                break;
            case R.id.btnHdmiTwo:
                if (b) {
                    btnHDMITwo.setTextColor(Color.BLACK);
                    //handler.sendEmptyMessage(TestConstant.HDMI_TWO_FOCUS);
                } else {
                    btnHDMITwo.setTextColor(Color.WHITE);
                }
                break;
            case R.id.btnMediaPlayer:
                if (b) {
                    btnMediaPlayer.setTextColor(Color.BLACK);
                   // handler.sendEmptyMessage(TestConstant.HDMI_TWO_FOCUS);
                } else {
                    btnMediaPlayer.setTextColor(Color.WHITE);
                }
                break;
            case R.id.btnSetting:
                if (b) {
                    btnSetting.setTextColor(Color.BLACK);
                    //handler.sendEmptyMessage(TestConstant.BTN_SETTING_FOCUS);
                } else {
                    btnSetting.setTextColor(Color.WHITE);
                }
                break;
            case R.id.btnYoutube:
                if (b) {
                    btnYoutube.setTextColor(Color.BLACK);
                   // handler.sendEmptyMessage(TestConstant.BTN_YOUTUBE_FOCUS);
                } else {
                    btnYoutube.setTextColor(Color.WHITE);
                }
                break;
            case R.id.ayna:
                if (b) {
                    btnAyna.setTextColor(Color.BLACK);
                   // handler.sendEmptyMessage(TestConstant.BTN_YOUTUBE_FOCUS);
                } else {
                    btnAyna.setTextColor(Color.WHITE);
                }
                break;

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnHDMIOne:
                //Utils.startMTVPlayer(TvCommonManager.INPUT_SOURCE_HDMI,MyServiceActivity.this);
                setInputSourceWalton(TvCommonManager.INPUT_SOURCE_HDMI);
                break;
            case R.id.btnHdmiTwo:
                //Utils.startMTVPlayer(TvCommonManager.INPUT_SOURCE_HDMI2,MyServiceActivity.this);
                setInputSourceWalton(TvCommonManager.INPUT_SOURCE_HDMI2);
                break;
            case R.id.btnMediaPlayer:
                Utils.openApk("nextapp.fx",
                        "nextapp.fx.ui.ExplorerActivity",MyServiceActivity.this);
                break ;
            case R.id.btnSetting:
                Utils.openApk("com.bongo.waltonandroidtv",
                        "com.grameenphone.bioscope.ui.splash.view.SplashActivity",MyServiceActivity.this);
                break;
            case R.id.btnYoutube:
                Utils.openApk("com.google.android.youtube.tv",
                        "com.google.android.apps.youtube.tv.activity.TvGuideActivity",MyServiceActivity.this);
                break;
            case R.id.ayna:
                Utils.openApk("com.aynaott.apps","com.aynaott.apps.MainActivity",MyServiceActivity.this);
        }
    }

    private void setInputSourceWalton(int mCurrentTvPosition) {
        Log.d("jdk", "isLauncherJumpToMTvPlayer()===" + mCurrentTvPosition);
        ComponentName componentName = new ComponentName("com.mediatek.wwtv.tvcenter", "com.mediatek.wwtv.tvcenter.nav.TurnkeyUiMainActivity");
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(componentName);
        intent.putExtra("isPowerOn", false);
        intent.putExtra("task_tag", "input_source_changed");
        intent.putExtra("inputSrc", mCurrentTvPosition);
        intent.putExtra("mIsFromHomeTv", true);
        startActivity(intent);
    }
}