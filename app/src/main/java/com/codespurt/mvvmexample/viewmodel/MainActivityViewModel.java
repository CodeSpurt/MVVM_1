package com.codespurt.mvvmexample.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codespurt.mvvmexample.model.NicePlace;
import com.codespurt.mvvmexample.repository.NicePlaceRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<NicePlace>> listOfPlaces;
    private MutableLiveData<Boolean> isUpdating = new MutableLiveData<>();

    private NicePlaceRepository repository;

    public void init() {
        if (listOfPlaces != null) {
            return;
        }
        repository = NicePlaceRepository.getInstance();
        listOfPlaces = repository.getNicePlaces();
    }

    public LiveData<List<NicePlace>> getNicePlaces() {
        return listOfPlaces;
    }

    public LiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }

    public void addNewNicePlace(final NicePlace item) {
        isUpdating.setValue(true);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                List<NicePlace> currentList = listOfPlaces.getValue();
                currentList.add(item);
                listOfPlaces.postValue(currentList);
                isUpdating.postValue(false);
            }
        }.execute();
    }
}
