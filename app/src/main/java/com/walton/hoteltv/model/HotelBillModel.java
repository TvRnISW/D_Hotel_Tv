package com.walton.hoteltv.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HotelBillModel {

    @SerializedName("itemid")
    @Expose
    private String itemid;
    @SerializedName("roomno")
    @Expose
    private String roomno;
    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("trbillno")
    @Expose
    private String trbillno;
    @SerializedName("Sname")
    @Expose
    private String sname;
    @SerializedName("ammount")
    @Expose
    private String ammount;
    @SerializedName("service")
    @Expose
    private String service;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("credit")
    @Expose
    private String credit;
    @SerializedName("crService")
    @Expose
    private String crService;
    @SerializedName("crTax")
    @Expose
    private String crTax;
    @SerializedName("balance")
    @Expose
    private String balance;

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTrbillno() {
        return trbillno;
    }

    public void setTrbillno(String trbillno) {
        this.trbillno = trbillno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getAmmount() {
        return ammount;
    }

    public void setAmmount(String ammount) {
        this.ammount = ammount;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getCrService() {
        return crService;
    }

    public void setCrService(String crService) {
        this.crService = crService;
    }

    public String getCrTax() {
        return crTax;
    }

    public void setCrTax(String crTax) {
        this.crTax = crTax;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}

