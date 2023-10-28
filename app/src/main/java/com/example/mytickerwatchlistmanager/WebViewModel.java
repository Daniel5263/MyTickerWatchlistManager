package com.example.mytickerwatchlistmanager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class WebViewModel extends ViewModel {
    private MutableLiveData<String> currentUrlLiveData;
    private MutableLiveData<List<String>> tickerListLiveData;

    public LiveData<String> getCurrentUrl() {
        if (currentUrlLiveData == null) {
            currentUrlLiveData = new MutableLiveData<>();
        }
        return currentUrlLiveData;
    }

    public LiveData<List<String>> getTickerList() {
            if (tickerListLiveData == null) {
                tickerListLiveData = new MutableLiveData<>();
            }
            return tickerListLiveData;
        }

    public void addTicker(String ticker) {
        List<String> currentList = tickerListLiveData.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(ticker);
        tickerListLiveData.setValue(currentList);

        currentUrlLiveData.setValue("https://seekingalpha.com/symbol/" + ticker);
    }

    public void setCurrentUrl(String url) {
        if (currentUrlLiveData == null) {
            currentUrlLiveData = new MutableLiveData<>();
        }
        currentUrlLiveData.setValue(url);
    }
}
