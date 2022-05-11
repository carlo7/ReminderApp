package com.example.reminderapp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    MutableLiveData<Data> mutableLiveData = new MutableLiveData<>();

    public void setText(Data data){
        mutableLiveData.setValue(data);
    }

    public MutableLiveData<Data> getText() {
        return mutableLiveData;
    }
}
