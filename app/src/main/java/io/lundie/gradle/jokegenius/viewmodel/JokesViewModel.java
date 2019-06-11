package io.lundie.gradle.jokegenius.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.lundie.gradle.jokegenius.apiutils.FetchStatus;
import io.lundie.gradle.jokegenius.apiutils.JokeLiveData;

public class JokesViewModel extends ViewModel {

    private static final String LOG_TAG = JokesViewModel.class.getSimpleName();

    private JokeLiveData jokeData;
    private MutableLiveData<FetchStatus> fetchStatusLiveData;

    public JokesViewModel() {}

    @Inject
    public JokesViewModel(JokeLiveData jokeData, MutableLiveData<FetchStatus> fetchStatusLiveData) {
        Log.v(LOG_TAG, "Creating JokesViewModel with constructor. Injected: " + jokeData);
        this.jokeData = jokeData;
        this.fetchStatusLiveData = fetchStatusLiveData;
    }

    public LiveData<String> getJokeData() {
        if(jokeData != null) {
            jokeData.loadData();
        }
        return jokeData;
    }

    public LiveData<FetchStatus> getFetchStatus() {
        return fetchStatusLiveData;
    }
}