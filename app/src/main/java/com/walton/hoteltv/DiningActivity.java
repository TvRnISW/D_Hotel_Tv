package com.walton.hoteltv;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.walton.hoteltv.dialog.RoomNumDialog;
import com.walton.hoteltv.model.hotel.HotelAllDataModel;
import com.walton.hoteltv.retrofit.ApiClient;
import com.walton.hoteltv.retrofit.ApiInterface;
import com.walton.hoteltv.sharedpref.SharedPrefClass;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiningActivity extends AppCompatActivity implements View.OnFocusChangeListener {
    LinearLayout btnBanquet,btnDining,btnRoofTop,btnCoffee;
    RelativeLayout relBbq,relBanquet,relDining,relCoffee;
    RecyclerView bbqRecycler,banquetRecycler,diningRecycler,coffeeRecycler;
    HtmlTextView bbq,banquet,dining,coffee;
    ApiInterface apiInterface;
    private TextView txtTime,txtRoomNumber;
    private RoomNumDialog roomNumDialog;
    private StringBuilder roomSetMenuCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining);

        relBbq = findViewById(R.id.relBbqInfo);
        relBanquet = findViewById(R.id.relBanquetInfo);
        relDining = findViewById(R.id.relDiningInfo);
        relCoffee = findViewById(R.id.relCoffeeInfo);

        //btnBanquet = findViewById(R.id.btnBanquet);
        btnDining = findViewById(R.id.btnDining);
        btnRoofTop = findViewById(R.id.btnRoofTop);
        btnCoffee = findViewById(R.id.btnCoffee);

        bbq = findViewById(R.id.bbqInfoDescription);
        banquet = findViewById(R.id.banquetInfoDescription);
        dining = findViewById(R.id.diningInfoDescription);
        coffee = findViewById(R.id.coffeeInfoDescription);

        bbqRecycler = findViewById(R.id.bbqPhotoRecycler);
        diningRecycler = findViewById(R.id.diningPhotoRecycler);
        coffeeRecycler = findViewById(R.id.coffeePhotoRecycler);
        banquetRecycler = findViewById(R.id.banquetPhotoRecycler);

        bbqRecycler.setHasFixedSize(true);
        bbqRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        diningRecycler.setHasFixedSize(true);
        diningRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        coffeeRecycler.setHasFixedSize(true);
        coffeeRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        banquetRecycler.setHasFixedSize(true);
        banquetRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));


       // btnBanquet.setOnFocusChangeListener(this);
        btnDining.setOnFocusChangeListener(this);
        btnRoofTop.setOnFocusChangeListener(this);
        btnCoffee.setOnFocusChangeListener(this);

        txtRoomNumber = findViewById(R.id.txtRoomNumber);
        txtTime = findViewById(R.id.txtTime);
        setRoomNumber();
        showDateTime();

        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);

        Call<HotelAllDataModel> getAllCall= apiInterface.getAllInfo();

        getAllCall.enqueue(new Callback<HotelAllDataModel>() {
            @Override
            public void onResponse(Call<HotelAllDataModel> call, Response<HotelAllDataModel> response) {
                if (response.isSuccessful()){
                    HotelAllDataModel model = response.body();
                    if (model != null) {
                        bbq.setHtml(model.getBbqInfo().getInfo().getContent());
                        coffee.setHtml(model.getCoffeeInfo().getInfo().getContent());
                        banquet.setHtml(model.getBanquetInfo().getInfo().getContent());
                        dining.setHtml(model.getDiningInfo().getInfo().getContent());

                        PhotoRecycler adapter1 = new PhotoRecycler(model.getDiningInfo().getGallery(),DiningActivity.this);
                        PhotoRecycler adapter2 = new PhotoRecycler(model.getBanquetInfo().getGallery(),DiningActivity.this);
                        PhotoRecycler adapter3 = new PhotoRecycler(model.getCoffeeInfo().getGallery(),DiningActivity.this);
                        PhotoRecycler adapter4 = new PhotoRecycler(model.getBbqInfo().getGallery(),DiningActivity.this);
                        diningRecycler.setAdapter(adapter1);
                        banquetRecycler.setAdapter(adapter2);
                        coffeeRecycler.setAdapter(adapter3);
                        bbqRecycler.setAdapter(adapter4);

                    }

                }
            }

            @Override
            public void onFailure(Call<HotelAllDataModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()){
           /* case R.id.btnBanquet:
                relCoffee.setVisibility(View.GONE);
                relDining.setVisibility(View.GONE);
                relBbq.setVisibility(View.GONE);
                relBanquet.setVisibility(View.VISIBLE);
                break;*/
            case R.id.btnDining:
                relCoffee.setVisibility(View.GONE);
                relDining.setVisibility(View.VISIBLE);
                relBbq.setVisibility(View.GONE);
                relBanquet.setVisibility(View.GONE);
                break;
            case R.id.btnCoffee:
                relCoffee.setVisibility(View.VISIBLE);
                relDining.setVisibility(View.GONE);
                relBbq.setVisibility(View.GONE);
                relBanquet.setVisibility(View.GONE);
                break;
            case R.id.btnRoofTop:
                relCoffee.setVisibility(View.GONE);
                relDining.setVisibility(View.GONE);
                relBbq.setVisibility(View.VISIBLE);
                relBanquet.setVisibility(View.GONE);
                break;
        }
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

    private void showRoomNumSetMenu() {
        roomNumDialog = new RoomNumDialog(this, new RoomNumDialog.RoomData() {
            @Override
            public void getRoomData(String data) {
                //Log.d(TAG, "getRoomData: "+data);
                if (data.equalsIgnoreCase("cancel")) {
                    roomNumDialog.dismiss();
                } else {
                    DatabaseReference rf = FirebaseDatabase.getInstance().getReference().child("rooms");
                    rf.child(data).setValue(data).addOnSuccessListener(aVoid -> {
                        SharedPrefClass.saveRoomNum(getApplicationContext(), data);
                        setRoomNumber();
                    }).addOnFailureListener(runnable -> {
                       // Log.d(TAG, "getRoomData: "+runnable.getMessage());
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                    });
                }
                roomNumDialog.dismiss();
            }
        });
        roomNumDialog.show();
    }

    private void setRoomNumber() {
//        SharedPrefClass.clearData(this);
        txtRoomNumber.setText("Room #"+SharedPrefClass.getRoomNumber(this));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Log.d(TAG, "onKeyDown: "+keyCode);
        if (keyCode == KeyEvent.KEYCODE_PROG_RED){
            //Utils.startMTVPlayer(TvCommonManager.INPUT_SOURCE_ATV, MenuActivity.this);
            Utils.openApk("com.mediatek.wwtv.tvcenter","com.mediatek.wwtv.tvcenter.nav.TurnkeyUiMainActivity",DiningActivity.this);
            //setInputSourceWalton(TvCommonManager.INPUT_SOURCE_ATV);

        }else if (keyCode== KeyEvent.KEYCODE_PROG_GREEN){
            startActivity(new Intent(DiningActivity.this, MyServiceActivity.class));

        }
        else if (keyCode == 165) {
            roomSetMenuCode = new StringBuilder(5);
        } else {
            try {
                roomSetMenuCode.append(keyCode);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        if (keyCode == 7) {
           // Log.d(TAG, "onKeyDown: " + roomSetMenuCode.toString());
            if (roomSetMenuCode.toString().equalsIgnoreCase("912157")) {
              //  Log.d(TAG, "onKeyDown: Show Menu");
                showRoomNumSetMenu();
            }
        }



        return super.onKeyDown(keyCode, event);
    }

}