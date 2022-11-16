package com.walton.hoteltv.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.walton.hoteltv.TestMainActivity;
import com.walton.hoteltv.repository.TestMainRepository;

public class TestMainViewModel extends AndroidViewModel {

    private TestMainRepository testMainRepository;
    private MutableLiveData<String> stringMutableLiveData;

    public TestMainViewModel(@NonNull Application application) {
        super(application);
        this.testMainRepository = new TestMainRepository(application);
    }

    public MutableLiveData<String> setRoomNumber(String roomNumber){
        Log.d("viewModel","set name called");
        this.stringMutableLiveData = this.testMainRepository.getMutableStringRoomNumUpdate(roomNumber);
        return stringMutableLiveData;
    }
}
