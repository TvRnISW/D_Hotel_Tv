package com.walton.hoteltv.retrofit;

import com.walton.hoteltv.model.HotelBillModel;
import com.walton.hoteltv.model.HotelInfoModel;
import com.walton.hoteltv.model.UserInfoModel;
import com.walton.hoteltv.model.WelcomeNoteModel;
import com.walton.hoteltv.model.hotel.HotelAllDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("welcomenote")
    Call<WelcomeNoteModel> getWelcomeNote();

    @GET("tv_no/welcome.php")
    Call<List<UserInfoModel>> getUserInformation(
            @Query("room") String room,
            @Query("companny") String company
    );

    @GET("hotel-info")
    Call<HotelInfoModel> getHotelInfo();

    @GET("get-all")
    Call<HotelAllDataModel> getAllInfo();

    @GET("TV_NO/guestbill.php?")
    Call<List<HotelBillModel>> getHotelBoll(
            @Query("room") String room,
            @Query("companny") String company
    );

}
