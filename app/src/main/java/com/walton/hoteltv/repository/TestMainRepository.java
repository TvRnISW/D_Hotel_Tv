package com.walton.hoteltv.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TestMainRepository {

    private Context mContext;
    private MutableLiveData<String> mutableStringRoomNumUpdate;

    public TestMainRepository(Context context){
        this.mContext = context;
    }


    public MutableLiveData<String> getMutableStringRoomNumUpdate(String roomNum){
        Log.d("repoository","set name called");
        DatabaseReference rf = FirebaseDatabase.getInstance().getReference().child("rooms");
        rf.child(roomNum).setValue(roomNum).addOnSuccessListener(aVoid -> {
            mutableStringRoomNumUpdate.postValue("Success");
        }).addOnFailureListener(runnable -> {
            mutableStringRoomNumUpdate.postValue("Failure");
        });

        return mutableStringRoomNumUpdate;
    }
}
