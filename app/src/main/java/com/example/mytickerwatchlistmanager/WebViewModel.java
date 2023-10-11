package com.example.mytickerwatchlistmanager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WebViewModel extends ViewModel {
    private MutableLiveData<String> currentUrlLiveData;

    public LiveData<String> getCurrentUrl() {
        if (currentUrlLiveData == null) {
            currentUrlLiveData = new MutableLiveData<>();
        }
        return currentUrlLiveData;
    }

    public void setCurrentUrl(String url) {
        if (currentUrlLiveData == null) {
            currentUrlLiveData = new MutableLiveData<>();
        }
        currentUrlLiveData.setValue(url);
    }
}
