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

public class FitnessAndSpaActivity extends AppCompatActivity implements View.OnFocusChangeListener {
    HtmlTextView spaText,fitnessText,laundry,salon;
    RecyclerView photoRecycler1,photoRecycler2,laundryRecycler,salonRecycler;
    ApiInterface apiInterface;
    RelativeLayout relSpaInfo,relFitnessInfo,relLaundry,relSalon;
    LinearLayout btnSpa,btnFitness,btnLaundry,btnSalon;
    private TextView txtTime,txtRoomNumber;
    private RoomNumDialog roomNumDialog;
    private StringBuilder roomSetMenuCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_and_spa);

        spaText = findViewById(R.id.spaInfoDescription);
        fitnessText = findViewById(R.id.fitnessInfoDescription);

        btnSpa = findViewById(R.id.btnSpa);
        btnFitness = findViewById(R.id.btnFitness);
        laundry = findViewById(R.id.laundryInfoDescription);
        salon = findViewById(R.id.salonInfoDescription);

        relLaundry = findViewById(R.id.relLaundryInfo);
        relSalon = findViewById(R.id.relSalonInfo);

        photoRecycler1 = findViewById(R.id.spaPhotoRecycler);
        photoRecycler2 = findViewById(R.id.fitnessPhotoRecycler);
        relFitnessInfo = findViewById(R.id.relFitnessInfo);
        relSpaInfo = findViewById(R.id.relSpaInfo);

        laundryRecycler = findViewById(R.id.laundryPhotoRecycler);
        salonRecycler = findViewById(R.id.salonPhotoRecycler);

        btnLaundry = findViewById(R.id.btnLaundry);
        btnSalon = findViewById(R.id.btnSalon);

        btnFitness.setOnFocusChangeListener(this);
        btnSpa.setOnFocusChangeListener(this);
        btnLaundry.setOnFocusChangeListener(this);
        btnSalon.setOnFocusChangeListener(this);

        txtRoomNumber = findViewById(R.id.txtRoomNumber);
        txtTime = findViewById(R.id.txtTime);
        setRoomNumber();
        showDateTime();

        photoRecycler1.setHasFixedSize(true);
        photoRecycler1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        photoRecycler2.setHasFixedSize(true);
        photoRecycler2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        laundryRecycler.setHasFixedSize(true);
        laundryRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        salonRecycler.setHasFixedSize(true);
        salonRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

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
                        spaText.setHtml(model.getSpaInfo().getInfo().getContent());
                        fitnessText.setHtml(model.getFitnessInfo().getInfo().getContent());
                        laundry.setHtml(model.getLaundryInfo().getInfo().getContent());
                        salon.setHtml(model.getSalonInfo().getInfo().getContent());

                        PhotoRecycler adapter1 = new PhotoRecycler(model.getSpaInfo().getGallery(),FitnessAndSpaActivity.this);
                        PhotoRecycler adapter2 = new PhotoRecycler(model.getFitnessInfo().getGallery(),FitnessAndSpaActivity.this);
                        PhotoRecycler adapter3 = new PhotoRecycler(model.getLaundryInfo().getGallery(),FitnessAndSpaActivity.this);
                        PhotoRecycler adapter4 = new PhotoRecycler(model.getSalonInfo().getGallery(),FitnessAndSpaActivity.this);

                        photoRecycler1.setAdapter(adapter1);
                        photoRecycler2.setAdapter(adapter2);

                        laundryRecycler.setAdapter(adapter3);
                        salonRecycler.setAdapter(adapter4);

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
            case R.id.btnSpa:
                relSpaInfo.setVisibility(View.VISIBLE);
                relFitnessInfo.setVisibility(View.GONE);
                relSalon.setVisibility(View.GONE);
                relLaundry.setVisibility(View.GONE);
                break;
            case R.id.btnFitness:
                relSpaInfo.setVisibility(View.GONE);
                relFitnessInfo.setVisibility(View.VISIBLE);
                relSalon.setVisibility(View.GONE);
                relLaundry.setVisibility(View.GONE);
                break;
            case R.id.btnLaundry:
                relSpaInfo.setVisibility(View.GONE);
                relFitnessInfo.setVisibility(View.GONE);
                relSalon.setVisibility(View.GONE);
                relLaundry.setVisibility(View.VISIBLE);
                break;
            case R.id.btnSalon:
                relSpaInfo.setVisibility(View.GONE);
                relFitnessInfo.setVisibility(View.GONE);
                relSalon.setVisibility(View.VISIBLE);
                relLaundry.setVisibility(View.GONE);
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




}