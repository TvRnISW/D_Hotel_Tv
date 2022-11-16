package com.walton.hoteltv;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.walton.hoteltv.model.hotel.HotelAllDataModel;
import com.walton.hoteltv.retrofit.ApiClient;
import com.walton.hoteltv.retrofit.ApiInterface;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VillaActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    HtmlTextView villaText,towerText;
    RecyclerView photoRecycler,photoRecycler2;
    ApiInterface apiInterface;
    RelativeLayout relTowerInfo,relVillaInfo;
    LinearLayout btnVilla,btnTower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_villa);

        villaText = findViewById(R.id.villaText);
        towerText = findViewById(R.id.towerText);

        btnVilla = findViewById(R.id.btnVilla);
        btnTower = findViewById(R.id.btnTower);

        photoRecycler = findViewById(R.id.villaPhotoRecycler);
        photoRecycler2 = findViewById(R.id.towerPhotoRecycler);
        relTowerInfo = findViewById(R.id.relTowerInfo);
        relVillaInfo = findViewById(R.id.relVillaInfo);

        btnVilla.setOnFocusChangeListener(this);
        btnTower.setOnFocusChangeListener(this);


        photoRecycler.setHasFixedSize(true);
        photoRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

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
                        villaText.setHtml(model.getVillaInfo().getInfo().getContent());
                        towerText.setHtml(model.getTowerInfo().getInfo().getContent());
                        PhotoRecycler adapter = new PhotoRecycler(model.getVillaInfo().getGallery(),VillaActivity.this);
                        PhotoRecycler adapter2 = new PhotoRecycler(model.getTowerInfo().getGallery(),VillaActivity.this);
                        photoRecycler.setAdapter(adapter);
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
            case R.id.btnVilla:
                relVillaInfo.setVisibility(View.VISIBLE);
                relTowerInfo.setVisibility(View.GONE);
                break;
            case R.id.btnTower:
                relVillaInfo.setVisibility(View.GONE);
                relTowerInfo.setVisibility(View.VISIBLE);
                break;
        }
    }
}