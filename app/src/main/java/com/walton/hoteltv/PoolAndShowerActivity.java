package com.walton.hoteltv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class PoolAndShowerActivity extends AppCompatActivity implements View.OnFocusChangeListener{
    HtmlTextView swimText,openText;
    RecyclerView photoRecycler1,photoRecycler2;
    ApiInterface apiInterface;
    RelativeLayout relSwimInfo,relOpenInfo;
    LinearLayout btnSwim,btnOpen;
    private TextView txtTime,txtRoomNumber;
    private RoomNumDialog roomNumDialog;
    private StringBuilder roomSetMenuCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pool_and_shower);
        swimText = findViewById(R.id.swimInfoDescription);
        openText = findViewById(R.id.openInfoDescription);

        btnSwim = findViewById(R.id.btnSwim);
        btnOpen = findViewById(R.id.btnOpen);

        photoRecycler1 = findViewById(R.id.swimPhotoRecycler);
        photoRecycler2 = findViewById(R.id.openPhotoRecycler);
        relSwimInfo = findViewById(R.id.relSwimInfo);
        relOpenInfo = findViewById(R.id.relOpenInfo);

        btnSwim.setOnFocusChangeListener(this);
        btnOpen.setOnFocusChangeListener(this);

        txtRoomNumber = findViewById(R.id.txtRoomNumber);
        txtTime = findViewById(R.id.txtTime);
        setRoomNumber();
        showDateTime();

        photoRecycler1.setHasFixedSize(true);
        photoRecycler1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        photoRecycler2.setHasFixedSize(true);
        photoRecycler2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));


        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);

        Call<HotelAllDataModel> getAllCall= apiInterface.getAllInfo();

        getAllCall.enqueue(new Callback<HotelAllDataModel>() {
            @Override
            public void onResponse(Call<HotelAllDataModel> call, Response<HotelAllDataModel> response) {
                if (response.isSuccessful()){
                    HotelAllDataModel model = response.body();
                    if (model != null) {
                        swimText.setHtml(model.getSwimInfo().getInfo().getContent());
                        openText.setHtml(model.getOpenInfo().getInfo().getContent());
                        PhotoRecycler adapter1 = new PhotoRecycler(model.getSwimInfo().getGallery(),PoolAndShowerActivity.this);
                        PhotoRecycler adapter2 = new PhotoRecycler(model.getOpenInfo().getGallery(),PoolAndShowerActivity.this);
                        photoRecycler1.setAdapter(adapter1);
                        photoRecycler2.setAdapter(adapter2);

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
            case R.id.btnSwim:
                relSwimInfo.setVisibility(View.VISIBLE);
                relOpenInfo.setVisibility(View.GONE);
                break;
            case R.id.btnOpen:
                relSwimInfo.setVisibility(View.GONE);
                relOpenInfo.setVisibility(View.VISIBLE);
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

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        //Log.d(TAG, "onKeyDown: "+keyCode);
//        if (keyCode == KeyEvent.KEYCODE_PROG_RED){
//            //Utils.startMTVPlayer(TvCommonManager.INPUT_SOURCE_ATV, MenuActivity.this);
//            Utils.openApk("com.mediatek.wwtv.tvcenter","com.mediatek.wwtv.tvcenter.nav.TurnkeyUiMainActivity",PoolAndShowerActivity.this);
//            //setInputSourceWalton(TvCommonManager.INPUT_SOURCE_ATV);
//
//        }else if (keyCode== KeyEvent.KEYCODE_PROG_GREEN){
//            startActivity(new Intent(PoolAndShowerActivity.this, MyServiceActivity.class));
//
//        }
//        else if (keyCode == 165) {
//            roomSetMenuCode = new StringBuilder(5);
//        } else {
//            try {
//                roomSetMenuCode.append(keyCode);
//            } catch (NullPointerException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (keyCode == 7) {
//            // Log.d(TAG, "onKeyDown: " + roomSetMenuCode.toString());
//            if (roomSetMenuCode.toString().equalsIgnoreCase("912157")) {
//                //  Log.d(TAG, "onKeyDown: Show Menu");
//                showRoomNumSetMenu();
//            }
//        }
//
//
//
//        return super.onKeyDown(keyCode, event);
//    }

}