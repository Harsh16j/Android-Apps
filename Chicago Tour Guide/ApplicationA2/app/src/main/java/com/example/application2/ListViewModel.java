package com.example.application2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListViewModel extends ViewModel {
    private final MutableLiveData<Integer> selectedItem= new MutableLiveData<>();

    // returns the MutableLiveData object
    public LiveData<Integer> getSelectedItem() {
        return selectedItem;
    }

    // sets the MutableLiveData object value to the provided value
    public void selectItem(Integer item){
        selectedItem.setValue(item);
    }
}
