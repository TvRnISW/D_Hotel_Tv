package com.walton.hoteltv;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import java.util.List;

public class TestViewModel extends AndroidViewModel {

    private TestRepository testRepository;
    private MutableLiveData<List<String>> mutableLiveData;


    public TestViewModel(@NonNull Application application) {
        super(application);
        this.testRepository = new TestRepository(application);
    }

    public MutableLiveData<List<String>> getUserData(String roomNum){
        this.mutableLiveData = this.testRepository.getUserNameRoomWise(roomNum);
        return mutableLiveData;
    }
}
