package com.walton.hoteltv.model.hotel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class HotelAllDataModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("welcome")
    @Expose
    private Welcome welcome;
    @SerializedName("hotel-info")
    @Expose
    private HotelInfo hotelInfo;
    @SerializedName("villa-info")
    @Expose
    private VillaInfo villaInfo;
    @SerializedName("tower-info")
    @Expose
    private TowerInfo towerInfo;
    @SerializedName("banquet-info")
    @Expose
    private BanquetInfo banquetInfo;
    @SerializedName("dining-info")
    @Expose
    private DiningInfo diningInfo;
    @SerializedName("bbq-info")
    @Expose
    private BbqInfo bbqInfo;
    @SerializedName("coffee-info")
    @Expose
    private CoffeeInfo coffeeInfo;
    @SerializedName("spa-info")
    @Expose
    private SpaInfo spaInfo;
    @SerializedName("fitness-info")
    @Expose
    private FitnessInfo fitnessInfo;
    @SerializedName("swim-info")
    @Expose
    private SwimInfo swimInfo;
    @SerializedName("open-info")
    @Expose
    private OpenInfo openInfo;
    @SerializedName("ex-meet-info")
    @Expose
    private ExMeetInfo exMeetInfo;
    @SerializedName("board-meet-info")
    @Expose
    private BoardMeetInfo boardMeetInfo;
    @SerializedName("kids-info")
    @Expose
    private KidsInfo kidsInfo;
    @SerializedName("laundry-info")
    @Expose
    private LaundryInfo laundryInfo;
    @SerializedName("salon-info")
    @Expose
    private SalonInfo salonInfo;
    @SerializedName("beach-info")
    @Expose
    private BeachInfo beachInfo;
    @SerializedName("music-info")
    @Expose
    private MusicInfo musicInfo;
    @SerializedName("surfing-info")
    @Expose
    private SurfingInfo surfingInfo;
    @SerializedName("manik")
    @Expose
    private Manik manik;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Welcome getWelcome() {
        return welcome;
    }

    public void setWelcome(Welcome welcome) {
        this.welcome = welcome;
    }

    public HotelInfo getHotelInfo() {
        return hotelInfo;
    }

    public void setHotelInfo(HotelInfo hotelInfo) {
        this.hotelInfo = hotelInfo;
    }

    public VillaInfo getVillaInfo() {
        return villaInfo;
    }

    public void setVillaInfo(VillaInfo villaInfo) {
        this.villaInfo = villaInfo;
    }

    public TowerInfo getTowerInfo() {
        return towerInfo;
    }

    public void setTowerInfo(TowerInfo towerInfo) {
        this.towerInfo = towerInfo;
    }

    public BanquetInfo getBanquetInfo() {
        return banquetInfo;
    }

    public void setBanquetInfo(BanquetInfo banquetInfo) {
        this.banquetInfo = banquetInfo;
    }

    public DiningInfo getDiningInfo() {
        return diningInfo;
    }

    public void setDiningInfo(DiningInfo diningInfo) {
        this.diningInfo = diningInfo;
    }

    public BbqInfo getBbqInfo() {
        return bbqInfo;
    }

    public void setBbqInfo(BbqInfo bbqInfo) {
        this.bbqInfo = bbqInfo;
    }

    public CoffeeInfo getCoffeeInfo() {
        return coffeeInfo;
    }

    public void setCoffeeInfo(CoffeeInfo coffeeInfo) {
        this.coffeeInfo = coffeeInfo;
    }

    public SpaInfo getSpaInfo() {
        return spaInfo;
    }

    public void setSpaInfo(SpaInfo spaInfo) {
        this.spaInfo = spaInfo;
    }

    public FitnessInfo getFitnessInfo() {
        return fitnessInfo;
    }

    public void setFitnessInfo(FitnessInfo fitnessInfo) {
        this.fitnessInfo = fitnessInfo;
    }

    public SwimInfo getSwimInfo() {
        return swimInfo;
    }

    public void setSwimInfo(SwimInfo swimInfo) {
        this.swimInfo = swimInfo;
    }

    public OpenInfo getOpenInfo() {
        return openInfo;
    }

    public void setOpenInfo(OpenInfo openInfo) {
        this.openInfo = openInfo;
    }

    public ExMeetInfo getExMeetInfo() {
        return exMeetInfo;
    }

    public void setExMeetInfo(ExMeetInfo exMeetInfo) {
        this.exMeetInfo = exMeetInfo;
    }

    public BoardMeetInfo getBoardMeetInfo() {
        return boardMeetInfo;
    }

    public void setBoardMeetInfo(BoardMeetInfo boardMeetInfo) {
        this.boardMeetInfo = boardMeetInfo;
    }

    public KidsInfo getKidsInfo() {
        return kidsInfo;
    }

    public void setKidsInfo(KidsInfo kidsInfo) {
        this.kidsInfo = kidsInfo;
    }

    public LaundryInfo getLaundryInfo() {
        return laundryInfo;
    }

    public void setLaundryInfo(LaundryInfo laundryInfo) {
        this.laundryInfo = laundryInfo;
    }

    public SalonInfo getSalonInfo() {
        return salonInfo;
    }

    public void setSalonInfo(SalonInfo salonInfo) {
        this.salonInfo = salonInfo;
    }

    public BeachInfo getBeachInfo() {
        return beachInfo;
    }

    public void setBeachInfo(BeachInfo beachInfo) {
        this.beachInfo = beachInfo;
    }

    public MusicInfo getMusicInfo() {
        return musicInfo;
    }

    public void setMusicInfo(MusicInfo musicInfo) {
        this.musicInfo = musicInfo;
    }

    public SurfingInfo getSurfingInfo() {
        return surfingInfo;
    }

    public void setSurfingInfo(SurfingInfo surfingInfo) {
        this.surfingInfo = surfingInfo;
    }

    public Manik getManik() {
        return manik;
    }

    public void setManik(Manik manik) {
        this.manik = manik;
    }

}
