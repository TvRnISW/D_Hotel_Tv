package com.walton.hoteltv;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mstar.android.tv.TvCommonManager;
import com.walton.hoteltv.dialog.RoomNumDialog;
import com.walton.hoteltv.model.ArrivalDate;
import com.walton.hoteltv.model.DepartureDate;
import com.walton.hoteltv.model.HotelBillModel;
import com.walton.hoteltv.model.UserInfoModel;
import com.walton.hoteltv.model.hotel.HotelAllDataModel;
import com.walton.hoteltv.retrofit.ApiClient;
import com.walton.hoteltv.retrofit.ApiClientThirdParty;
import com.walton.hoteltv.retrofit.ApiInterface;
import com.walton.hoteltv.sharedpref.SharedPrefClass;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MenuActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private static final String TAG = "MenuActivity";
    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == TestConstant.HOTEL_INFO) {
                Log.d(TAG, "handleMessage: hotel info");
                relInfo.setVisibility(View.VISIBLE);

            }else if (msg.what == TestConstant.SHOW_HOME) {
                Log.d(TAG, "handleMessage: show home");
                relInfo.setVisibility(View.GONE);

            }

            else if (msg.what == TestConstant.CAFE) {

            } else if (msg.what == TestConstant.RESTAURANT) {

            } else if (msg.what == TestConstant.BAR) {

            } else if (msg.what == TestConstant.FITNESS_SPA) {
                relInfo.setVisibility(View.GONE);

                // showFitnessSpaInfo();
            } else if (msg.what == TestConstant.LOCAL_ATTRACTION) {
                // showLocalAttrctionInfo();
                relInfo.setVisibility(View.GONE);

            } else if (msg.what == TestConstant.SHOP) {
                showShopInfo();
            }
            /*else if (msg.what == TestConstant.HIDE_LOCAL_ATTRACTION){
                lnlocalattr.setVisibility(View.GONE);
                Log.d(TAG, "handleMessage: ");
            }else if (msg.what == TestConstant.HIDE_FITNESS_SPA){
                lnfitnessspa.setVisibility(View.GONE);
            }*/else if (msg.what == TestConstant.SHOW_FOOD_MENU){

            }else if (msg.what == TestConstant.HIDE_FOOD_MENU){

            }
        }
    };
    private LinearLayout  btnHome,btnHotelInfo, btnVilla, btnDining,btnBill,btnFitnessSpa,btnSwim,btnMeet,btnExclusive,btnTV,btnManikganj,btnMyService;
    private LinearLayout lnfitnessspa,lnVilla,lnDining,lnSwim,lnExclusive,lnMeet,lnTV,lnService,lnTelevision,lnMyService;
    RelativeLayout relInfo,relHome,relBillInfo,relMalikInfo;
    private TextView txtTime, /*txtDate,*/txtRoomNumber,txtNames,txtBillRoomNumber,txtGuestNames,txtStayDate;

    RecyclerView billRecycler,photoRecycler;
    HtmlTextView txtWelcomeNote,txtHotelInfoTitle,manikInfoDescription;
    private StringBuilder roomSetMenuCode;
    private RoomNumDialog roomNumDialog;
    private ApiInterface apiInterface;

    private void showShopInfo() {
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main_layout);

        initUI();
        //txtWelcomeNote.setHtml("<p>This is a demo application developed by Walton.</p>");

        apiInterface = ApiClientThirdParty.getApiClient().create(ApiInterface.class);
        Call<List<UserInfoModel>> userInfoModelCall = apiInterface.getUserInformation(SharedPrefClass.getRoomNumber(this),"NC001");
        userInfoModelCall.enqueue(new Callback<List<UserInfoModel>>() {
            @Override
            public void onResponse(Call<List<UserInfoModel>> call, Response<List<UserInfoModel>> response) {
                if(response.isSuccessful()){
                    StringBuilder data= new StringBuilder();
                    List<UserInfoModel> modelList = response.body();
                    if (modelList != null) {
                        for (int i=0;i<modelList.size();i++){
                            if(modelList.size() - 1 != i)
                                data.append(modelList.get(i).getGuestName()).append(", ");
                            else
                                data.append(modelList.get(i).getGuestName());
                        }
                        txtNames.setText(data.toString());
                        txtGuestNames.setText(data.toString());
                        txtBillRoomNumber.setText(new StringBuilder().append("Room : ").append(SharedPrefClass.getRoomNumber(MenuActivity.this)).toString());
                        try {
                            ArrivalDate arrivalDate = modelList.get(0).getArrivalDate();
                            DepartureDate departureDate = modelList.get(0).getDepartureDate();
                            txtStayDate.setText(String.format("%s - %s", arrivalDate.getDate().substring(0, arrivalDate.getDate().indexOf(" ")), departureDate.getDate().substring(0, departureDate.getDate().indexOf(" ")))
                            );
                        }catch (Exception e){
                            Log.d("error",e.getMessage());
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserInfoModel>> call, Throwable t) {

            }
        });

        Call<List<HotelBillModel>> hotelBillCall = apiInterface.getHotelBoll(SharedPrefClass.getRoomNumber(this),"NC001");
        hotelBillCall.enqueue(new Callback<List<HotelBillModel>>() {
            @Override
            public void onResponse(Call<List<HotelBillModel>> call, Response<List<HotelBillModel>> response) {
                if (response.body() != null) {
                    Log.d("hotel_bill_info: ",String.valueOf(response.isSuccessful()));
                    Log.d("hotel_bill_info: ",response.message());
                    List<HotelBillModel> hotelBillModelList = new ArrayList<>();
                    hotelBillModelList.addAll(response.body());
                    BillRecyclerAdapter adapter = new BillRecyclerAdapter(MenuActivity.this,hotelBillModelList);
                    billRecycler.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<List<HotelBillModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);

        Call<HotelAllDataModel> getAllCall= apiInterface.getAllInfo();

        getAllCall.enqueue(new Callback<HotelAllDataModel>() {
            @Override
            public void onResponse(Call<HotelAllDataModel> call, Response<HotelAllDataModel> response) {
                if (response.isSuccessful()){
                    HotelAllDataModel model = response.body();
                    if (model != null) {
                        txtWelcomeNote.setHtml(model.getWelcome().getDescription());
                       PhotoRecycler adapter = new PhotoRecycler(model.getHotelInfo().getGallery(),MenuActivity.this);
                       photoRecycler.setAdapter(adapter);
                       txtHotelInfoTitle.setHtml(model.getHotelInfo().getInfo().getContent());
                        manikInfoDescription.setHtml(model.getManik().getInfo().getContent());

                    }

                }
            }

            @Override
            public void onFailure(Call<HotelAllDataModel> call, Throwable t) {

            }
        });

    }

    private void initUI() {
        btnHome = findViewById(R.id.btnHome);
        btnHotelInfo = findViewById(R.id.btnHotelInfo);
        btnVilla= findViewById(R.id.btnVilla);
        btnDining= findViewById(R.id.btnDining);
        btnFitnessSpa = findViewById(R.id.btnFitnessSpa);
        btnSwim= findViewById(R.id.btnSwim);
        btnMeet= findViewById(R.id.btnMeet);
        btnExclusive= findViewById(R.id.btnExclusive);
        btnTV= findViewById(R.id.btnTV);
        btnMyService= findViewById(R.id.btnMyService);
        btnManikganj= findViewById(R.id.btnManikganj);

        btnBill = findViewById(R.id.btnBill);
        lnfitnessspa = findViewById(R.id.lnfitnessspa);
        lnVilla = findViewById(R.id.lnVilla);

        lnDining = findViewById(R.id.lnDining);
        lnExclusive = findViewById(R.id.lnExclusive);
        lnSwim = findViewById(R.id.lnSwimming);
        lnTV = findViewById(R.id.lnTV);
        lnService = findViewById(R.id.lnService);
        lnMeet = findViewById(R.id.lnMeeting);
        lnTelevision = findViewById(R.id.lnTelevision);
        lnMyService = findViewById(R.id.lnMyService);


        txtRoomNumber = findViewById(R.id.txtRoomNumber);
        txtTime = findViewById(R.id.txtTime);
        txtWelcomeNote = findViewById(R.id.txtWelcomeNote);
        txtHotelInfoTitle = findViewById(R.id.hotelInfoDescription);
        manikInfoDescription = findViewById(R.id.manikInfoDescription);
        txtNames = findViewById(R.id.users);


        txtBillRoomNumber = findViewById(R.id.txtBillRoomNumber);
        txtGuestNames = findViewById(R.id.txtGuestNames);
        txtStayDate = findViewById(R.id.txtStayDate);
        billRecycler = findViewById(R.id.billRecycler);
        photoRecycler = findViewById(R.id.photoRecycler);


        btnBill.setOnClickListener(this);
        btnHotelInfo.setOnClickListener(this);
        btnVilla.setOnClickListener(this);
        btnFitnessSpa.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnDining.setOnClickListener(this);
        btnExclusive.setOnClickListener(this);
        btnSwim.setOnClickListener(this);
        btnMeet.setOnClickListener(this);
        btnTV.setOnClickListener(this);
        btnMyService.setOnClickListener(this);
        btnManikganj.setOnClickListener(this);
        lnMyService.setOnClickListener(this);
        lnTelevision.setOnClickListener(this);

        btnBill.setOnFocusChangeListener(this);
        btnHotelInfo.setOnFocusChangeListener(this);
        btnFitnessSpa.setOnFocusChangeListener(this);
        btnHome.setOnFocusChangeListener(this);
        btnVilla.setOnFocusChangeListener(this);
        btnDining.setOnFocusChangeListener(this);
        btnExclusive.setOnFocusChangeListener(this);
        btnSwim.setOnFocusChangeListener(this);
        btnMeet.setOnFocusChangeListener(this);
        btnTV.setOnFocusChangeListener(this);
        btnMyService.setOnFocusChangeListener(this);
        btnManikganj.setOnFocusChangeListener(this);

        relInfo = findViewById(R.id.relInfo);
        relHome = findViewById(R.id.relHome);
        relBillInfo = findViewById(R.id.relBill);
        relMalikInfo = findViewById(R.id.relMalikInfo);

        roomSetMenuCode = new StringBuilder();


        setRoomNumber();

        showDateTime();

        billRecycler.setHasFixedSize(true);
        billRecycler.setLayoutManager(new LinearLayoutManager(this));

        photoRecycler.setHasFixedSize(true);
        photoRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

    }

    private void setRoomNumber() {
//        SharedPrefClass.clearData(this);
        txtRoomNumber.setText("Room #"+SharedPrefClass.getRoomNumber(this));
    }


    private void showRoomNumSet() {
        roomNumDialog = new RoomNumDialog(this, new RoomNumDialog.RoomData() {
            @Override
            public void getRoomData(String data) {
                Log.d(TAG, "getRoomData: "+data);
                if (data.equalsIgnoreCase("cancel")) {
                    roomNumDialog.dismiss();
                } else {
                    DatabaseReference rf = FirebaseDatabase.getInstance().getReference().child("rooms");
                    rf.child(data).setValue(data).addOnSuccessListener(aVoid -> {
                        SharedPrefClass.saveRoomNum(getApplicationContext(), data);
                        setRoomNumber();
                    }).addOnFailureListener(runnable -> {
                        Log.d(TAG, "getRoomData: "+runnable.getMessage());
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                    });
                }
                roomNumDialog.dismiss();
            }
        });
        roomNumDialog.show();
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
                //txtDate.setText(date);
                someHandler.postDelayed(this, 1000);
            }
        }, 10);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown: "+keyCode);





//        if (keyCode == KeyEvent.KEYCODE_PROG_RED){
//            //Utils.startMTVPlayer(TvCommonManager.INPUT_SOURCE_ATV, MenuActivity.this);
//            Utils.openApk("com.mediatek.wwtv.tvcenter","com.mediatek.wwtv.tvcenter.nav.TurnkeyUiMainActivity",MenuActivity.this);
//            //setInputSourceWalton(TvCommonManager.INPUT_SOURCE_ATV);
//
//        }else if (keyCode== KeyEvent.KEYCODE_PROG_GREEN){
//            startActivity(new Intent(MenuActivity.this, MyServiceActivity.class));
//
//        }
         if (keyCode == KeyEvent.KEYCODE_INFO) {
            roomSetMenuCode = new StringBuilder(5);
        } else {
            try {
                roomSetMenuCode.append(keyCode);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        if (keyCode == 7) {
            Log.d(TAG, "onKeyDown: " + roomSetMenuCode.toString());
            if (roomSetMenuCode.toString().equalsIgnoreCase("912157")) {

                showRoomNumSet();
            }
        }



        return super.onKeyDown(keyCode, event);
    }

    private static boolean isFireKey(int keyCode) {
        // Here we treat Button_A and DPAD_CENTER as the primary action
        // keys for the game.
        return keyCode == KeyEvent.KEYCODE_DPAD_CENTER
                || keyCode == KeyEvent.KEYCODE_BUTTON_A;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnHotelInfo:
                break;
            case R.id.btnVilla:
                startActivity(new Intent(MenuActivity.this,VillaActivity.class));
                break;
            case R.id.btnDining:
                startActivity(new Intent(MenuActivity.this, DiningActivity.class));
                break;
            case R.id.btnTV:
            case R.id.lnTelevision:
//                Utils.openApk("com.aynaott.apps","com.aynaott.apps.MainActivity",MenuActivity.this);
              //  Utils.startMTVPlayer(TvCommonManager.INPUT_SOURCE_ATV, MenuActivity.this);
                Utils.openApk("com.mediatek.wwtv.tvcenter","com.mediatek.wwtv.tvcenter.nav.TurnkeyUiMainActivity",MenuActivity.this);
                //setInputSourceWalton(TvCommonManager.INPUT_SOURCE_ATV);
                break;
            case R.id.btnMyService:
            case R.id.lnMyService:
                startActivity(new Intent(MenuActivity.this,MyServiceActivity.class));
                break;
            case R.id.btnFitnessSpa:
                startActivity(new Intent(MenuActivity.this,FitnessAndSpaActivity.class));
                break;
            case R.id.btnSwim:
                startActivity(new Intent(MenuActivity.this,PoolAndShowerActivity.class));
                break;
            case R.id.btnMeet:
                startActivity(new Intent(MenuActivity.this, MeetingActivity.class));
                break;
            case R.id.btnExclusive:
                startActivity(new Intent(MenuActivity.this,ExclusiveActivity.class));
                break;
            case R.id.btnMenu:
                startActivity(new Intent(MenuActivity.this, FoodMenuActivity.class));
                break;
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {

        switch (view.getId()) {
            case R.id.btnHome:
                if (b) {
                    Log.d(TAG, "Home");
                    relHome.setVisibility(View.VISIBLE);
                    relInfo.setVisibility(View.GONE);
                    relBillInfo.setVisibility(View.GONE);
                    relMalikInfo.setVisibility(View.GONE);
                    lnfitnessspa.setVisibility(View.GONE);
                    lnTV.setVisibility(View.GONE);
                    lnService.setVisibility(View.GONE);
                    lnVilla.setVisibility(View.GONE);
                    lnDining.setVisibility(View.GONE);
                    lnExclusive.setVisibility(View.GONE);
                    lnMeet.setVisibility(View.GONE);
                    lnSwim.setVisibility(View.GONE);
                }
                break;
            case R.id.btnHotelInfo:
                if (b) {
                    relInfo.setVisibility(View.VISIBLE);
                    relBillInfo.setVisibility(View.GONE);
                    relMalikInfo.setVisibility(View.GONE);
                    relHome.setVisibility(View.GONE);
                    lnfitnessspa.setVisibility(View.GONE);
                    lnVilla.setVisibility(View.GONE);
                    lnDining.setVisibility(View.GONE);
                    lnTV.setVisibility(View.GONE);
                    lnService.setVisibility(View.GONE);
                    lnExclusive.setVisibility(View.GONE);
                    lnMeet.setVisibility(View.GONE);
                    lnSwim.setVisibility(View.GONE);
                }
                break;
            case R.id.btnVilla:
                if (b) {
                    //startActivity(new Intent(MenuActivity.this,VillaActivity.class));
                    relInfo.setVisibility(View.GONE);
                    relHome.setVisibility(View.GONE);
                    lnfitnessspa.setVisibility(View.GONE);
                    lnVilla.setVisibility(View.VISIBLE);
                    lnDining.setVisibility(View.GONE);
                    relBillInfo.setVisibility(View.GONE);
                    relMalikInfo.setVisibility(View.GONE);
                    lnExclusive.setVisibility(View.GONE);
                    lnMeet.setVisibility(View.GONE);
                    lnSwim.setVisibility(View.GONE);
                    lnTV.setVisibility(View.GONE);
                    lnService.setVisibility(View.GONE);
                }
                break;
            case R.id.btnTV:
                if (b){
                    relInfo.setVisibility(View.GONE);
                    relHome.setVisibility(View.GONE);
                    lnfitnessspa.setVisibility(View.GONE);
                    lnTV.setVisibility(View.VISIBLE);
                    lnService.setVisibility(View.GONE);
                    lnVilla.setVisibility(View.GONE);
                    lnDining.setVisibility(View.GONE);
                    relBillInfo.setVisibility(View.GONE);
                    lnExclusive.setVisibility(View.GONE);
                    lnMeet.setVisibility(View.GONE);
                    lnSwim.setVisibility(View.GONE);
                    relMalikInfo.setVisibility(View.GONE);
                }
                break;
            case R.id.btnMyService:
                relInfo.setVisibility(View.GONE);
                relHome.setVisibility(View.GONE);
                lnfitnessspa.setVisibility(View.GONE);
                lnTV.setVisibility(View.GONE);
                lnService.setVisibility(View.VISIBLE);
                lnVilla.setVisibility(View.GONE);
                lnDining.setVisibility(View.GONE);
                relBillInfo.setVisibility(View.GONE);
                lnExclusive.setVisibility(View.GONE);
                lnMeet.setVisibility(View.GONE);
                lnSwim.setVisibility(View.GONE);
                relMalikInfo.setVisibility(View.GONE);
                break;
            case R.id.btnManikganj:
                relInfo.setVisibility(View.GONE);
                relHome.setVisibility(View.GONE);
                lnfitnessspa.setVisibility(View.GONE);
                lnVilla.setVisibility(View.GONE);
                lnDining.setVisibility(View.GONE);
                relBillInfo.setVisibility(View.GONE);
                relMalikInfo.setVisibility(View.VISIBLE);
                lnExclusive.setVisibility(View.GONE);
                lnMeet.setVisibility(View.GONE);
                lnSwim.setVisibility(View.GONE);
                lnTV.setVisibility(View.GONE);
                lnService.setVisibility(View.GONE);
                break;
            case R.id.btnDining:
                if (b) {
                    //startActivity(new Intent(MenuActivity.this, DiningActivity.class));
                    relInfo.setVisibility(View.GONE);
                    relHome.setVisibility(View.GONE);
                    lnfitnessspa.setVisibility(View.GONE);
                    lnVilla.setVisibility(View.GONE);
                    lnDining.setVisibility(View.VISIBLE);
                    relBillInfo.setVisibility(View.GONE);
                    lnExclusive.setVisibility(View.GONE);
                    lnMeet.setVisibility(View.GONE);
                    lnSwim.setVisibility(View.GONE);
                    lnTV.setVisibility(View.GONE);
                    lnService.setVisibility(View.GONE);
                    relMalikInfo.setVisibility(View.GONE);
                }
                break;
            case R.id.btnBill:
                if (b) {
                    relInfo.setVisibility(View.GONE);
                    relHome.setVisibility(View.GONE);
                    lnfitnessspa.setVisibility(View.GONE);
                    lnVilla.setVisibility(View.GONE);
                    lnDining.setVisibility(View.GONE);
                    relBillInfo.setVisibility(View.VISIBLE);
                    lnExclusive.setVisibility(View.GONE);
                    lnMeet.setVisibility(View.GONE);
                    lnSwim.setVisibility(View.GONE);
                    lnTV.setVisibility(View.GONE);
                    lnService.setVisibility(View.GONE);
                    relMalikInfo.setVisibility(View.GONE);
                }
                break;
            case R.id.btnFitnessSpa:
                if (b) {
                    relInfo.setVisibility(View.GONE);
                    relBillInfo.setVisibility(View.GONE);
                    relHome.setVisibility(View.GONE);
                    lnVilla.setVisibility(View.GONE);
                    lnDining.setVisibility(View.GONE);
                    lnfitnessspa.setVisibility(View.VISIBLE);
                    lnExclusive.setVisibility(View.GONE);
                    lnMeet.setVisibility(View.GONE);
                    lnSwim.setVisibility(View.GONE);
                    lnTV.setVisibility(View.GONE);
                    lnService.setVisibility(View.GONE);
                    relMalikInfo.setVisibility(View.GONE);
                }
                break;
            case R.id.btnExclusive:
                if(b){
                    relInfo.setVisibility(View.GONE);
                    relBillInfo.setVisibility(View.GONE);
                    relHome.setVisibility(View.GONE);
                    lnVilla.setVisibility(View.GONE);
                    lnDining.setVisibility(View.GONE);
                    lnfitnessspa.setVisibility(View.GONE);
                    lnExclusive.setVisibility(View.VISIBLE);
                    lnMeet.setVisibility(View.GONE);
                    lnSwim.setVisibility(View.GONE);
                    lnTV.setVisibility(View.GONE);
                    lnService.setVisibility(View.GONE);
                    relMalikInfo.setVisibility(View.GONE);
                }

                break;
            case R.id.btnMeet:
                if (b){
                    relInfo.setVisibility(View.GONE);
                    relBillInfo.setVisibility(View.GONE);
                    relHome.setVisibility(View.GONE);
                    lnVilla.setVisibility(View.GONE);
                    lnfitnessspa.setVisibility(View.GONE);
                    lnExclusive.setVisibility(View.GONE);
                    lnMeet.setVisibility(View.VISIBLE);
                    lnSwim.setVisibility(View.GONE);
                    lnTV.setVisibility(View.GONE);
                    lnService.setVisibility(View.GONE);
                    relMalikInfo.setVisibility(View.GONE);
                }

                break;
            case R.id.btnSwim:
                if(b){
                    relInfo.setVisibility(View.GONE);
                    relBillInfo.setVisibility(View.GONE);
                    relHome.setVisibility(View.GONE);
                    lnVilla.setVisibility(View.GONE);
                    lnfitnessspa.setVisibility(View.GONE);
                    lnExclusive.setVisibility(View.GONE);
                    lnMeet.setVisibility(View.GONE);
                    lnSwim.setVisibility(View.VISIBLE);
                    lnTV.setVisibility(View.GONE);
                    lnService.setVisibility(View.GONE);
                    relMalikInfo.setVisibility(View.GONE);
                }

                break;
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

    private void setInputSourceWalton(int mCurrentTvPosition) {
        Log.d("jdk", "isLauncherJumpToMTvPlayer()===" + mCurrentTvPosition);
        ComponentName componentName = new ComponentName("com.cvte.tv.androidsetting", "com.cvte.tv.androidsetting.TvSettingActivity");
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
