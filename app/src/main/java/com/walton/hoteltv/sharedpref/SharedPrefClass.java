package com.walton.hoteltv.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefClass {
    public static final String PREF_NAME = "room_pref";

    public static void saveRoomNum(Context mContext,String roomNum){
        SharedPreferences.Editor editor = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString("room_name", roomNum);
        editor.apply();
    }

    public static String getRoomNumber(Context mContext){
        SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String roomName = prefs.getString("room_name","103");
        return roomName;
    }

    public static void clearData(Context mContext){
        SharedPreferences settings = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }
}
