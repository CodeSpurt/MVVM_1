package com.codespurt.mvvmexample.repository;

import androidx.lifecycle.MutableLiveData;

import com.codespurt.mvvmexample.model.NicePlace;

import java.util.ArrayList;
import java.util.List;

public class NicePlaceRepository {

    private static NicePlaceRepository instance;
    private ArrayList<NicePlace> list = new ArrayList<>();

    public static NicePlaceRepository getInstance() {
        if (instance == null) {
            instance = new NicePlaceRepository();
        }
        return instance;
    }

    public MutableLiveData<List<NicePlace>> getNicePlaces() {
        setNicePlaces();

        MutableLiveData<List<NicePlace>> data = new MutableLiveData<>();
        data.setValue(list);
        return data;
    }

    private void setNicePlaces() {
        int n = 5;
        for (int i = 0; i < n; i++) {
            NicePlace item = new NicePlace(String.valueOf(i + 1), "");
            list.add(item);
        }
    }
}
