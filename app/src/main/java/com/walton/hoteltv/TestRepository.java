package com.walton.hoteltv;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TestRepository {

    private static final String TAG = "TestRepository";
    private Context mContext;
    private MutableLiveData<List<String>> listMutableLiveData;
    ;

    public TestRepository(Context context) {
        this.mContext = context;
        listMutableLiveData = new MutableLiveData<>();
    }


    public MutableLiveData<List<String>> getUserNameRoomWise(String roomNumber){
        Log.d(TAG, "getUserNameRoomWise: "+roomNumber);
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("roomuser");
        db.child(roomNumber).child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    List<String> userList = new ArrayList<>();
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        String user = snapshot1.getValue(String.class);
                        userList.add(user);
                    }
                    listMutableLiveData.postValue(userList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return listMutableLiveData;
    }

}
