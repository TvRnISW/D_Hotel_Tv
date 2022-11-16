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

public class ExclusiveActivity extends AppCompatActivity implements View.OnFocusChangeListener{

    LinearLayout btnKids,btnLaundry,btnSalon,btnChair,btnMusic,btnSurfing;
    RelativeLayout relKids,relChair,relMusic,relSurf;
    RecyclerView kidsRecycler,chairRecycler,musicRecycler,surfingRecycler;
    HtmlTextView kids,chair,music,surf;
    ApiInterface apiInterface;
    private TextView txtTime,txtRoomNumber;
    private RoomNumDialog roomNumDialog;
    private StringBuilder roomSetMenuCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exclusive);

        relKids = findViewById(R.id.relKidsInfo);
        relChair = findViewById(R.id.relChairInfo);
        relMusic = findViewById(R.id.relMusicInfo);
        relSurf = findViewById(R.id.relSurfingInfo);

        btnKids = findViewById(R.id.btnKids);
        btnChair = findViewById(R.id.btnChair);
        btnSurfing = findViewById(R.id.btnSurfing);
        btnMusic = findViewById(R.id.btnMusic);

        kids = findViewById(R.id.kidsInfoDescription);
        surf = findViewById(R.id.surfingInfoDescription);
        chair = findViewById(R.id.chairInfoDescription);
        music = findViewById(R.id.musicInfoDescription);

        kidsRecycler = findViewById(R.id.kidsPhotoRecycler);
        chairRecycler = findViewById(R.id.chairPhotoRecycler);
        musicRecycler = findViewById(R.id.musicPhotoRecycler);
        surfingRecycler = findViewById(R.id.surfingPhotoRecycler);

        txtRoomNumber = findViewById(R.id.txtRoomNumber);
        txtTime = findViewById(R.id.txtTime);
        setRoomNumber();
        showDateTime();

        kidsRecycler.setHasFixedSize(true);
        kidsRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        chairRecycler.setHasFixedSize(true);
        chairRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        musicRecycler.setHasFixedSize(true);
        musicRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        surfingRecycler.setHasFixedSize(true);
        surfingRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));


        btnKids.setOnFocusChangeListener(this);
        btnChair.setOnFocusChangeListener(this);
        btnMusic.setOnFocusChangeListener(this);
        btnSurfing.setOnFocusChangeListener(this);

        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);

        Call<HotelAllDataModel> getAllCall= apiInterface.getAllInfo();

        getAllCall.enqueue(new Callback<HotelAllDataModel>() {
            @Override
            public void onResponse(Call<HotelAllDataModel> call, Response<HotelAllDataModel> response) {
                if (response.isSuccessful()){
                    HotelAllDataModel model = response.body();
                    if (model != null) {
                        kids.setHtml(model.getKidsInfo().getInfo().getContent());
                        chair.setHtml(model.getBeachInfo().getInfo().getContent());
                        music.setHtml(model.getMusicInfo().getInfo().getContent());
                        surf.setHtml(model.getSurfingInfo().getInfo().getContent());

                        PhotoRecycler adapter1 = new PhotoRecycler(model.getKidsInfo().getGallery(),ExclusiveActivity.this);
                        PhotoRecycler adapter4 = new PhotoRecycler(model.getBeachInfo().getGallery(),ExclusiveActivity.this);
                        PhotoRecycler adapter5 = new PhotoRecycler(model.getMusicInfo().getGallery(),ExclusiveActivity.this);
                        PhotoRecycler adapter6 = new PhotoRecycler(model.getSurfingInfo().getGallery(),ExclusiveActivity.this);
                        kidsRecycler.setAdapter(adapter1);
                        chairRecycler.setAdapter(adapter4);
                        musicRecycler.setAdapter(adapter5);
                        surfingRecycler.setAdapter(adapter6);

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
            case R.id.btnKids:
                relKids.setVisibility(View.VISIBLE);
                relChair.setVisibility(View.GONE);
                relMusic.setVisibility(View.GONE);
                relSurf.setVisibility(View.GONE);
                break;
            case R.id.btnChair:
                relKids.setVisibility(View.GONE);
                relChair.setVisibility(View.VISIBLE);
                relMusic.setVisibility(View.GONE);
                relSurf.setVisibility(View.GONE);
                break;
            case R.id.btnMusic:
                relKids.setVisibility(View.GONE);
                relChair.setVisibility(View.GONE);
                relMusic.setVisibility(View.VISIBLE);
                relSurf.setVisibility(View.GONE);
                break;
            case R.id.btnSurfing:
                relKids.setVisibility(View.GONE);
                relChair.setVisibility(View.GONE);
                relMusic.setVisibility(View.GONE);
                relSurf.setVisibility(View.VISIBLE);
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
//            Utils.openApk("com.mediatek.wwtv.tvcenter","com.mediatek.wwtv.tvcenter.nav.TurnkeyUiMainActivity",ExclusiveActivity.this);
//            //setInputSourceWalton(TvCommonManager.INPUT_SOURCE_ATV);
//
//        }else if (keyCode== KeyEvent.KEYCODE_PROG_GREEN){
//            startActivity(new Intent(ExclusiveActivity.this, MyServiceActivity.class));
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