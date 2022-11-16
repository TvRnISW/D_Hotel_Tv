package com.walton.hoteltv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfoModel {

    @SerializedName("ComCode")
    @Expose
    private String comCode;
    @SerializedName("RoomNo")
    @Expose
    private Integer roomNo;
    @SerializedName("GuestName")
    @Expose
    private String guestName;
    @SerializedName("ArrivalDate")
    @Expose
    private ArrivalDate arrivalDate;
    @SerializedName("DepartureDate")
    @Expose
    private DepartureDate departureDate;

    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    public Integer getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(Integer roomNo) {
        this.roomNo = roomNo;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public ArrivalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(ArrivalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public DepartureDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(DepartureDate departureDate) {
        this.departureDate = departureDate;
    }

}




