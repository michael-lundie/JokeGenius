package io.lundie.gradle.jokegenius.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

public class JokesViewModel extends ViewModel {

    private static final String LOG_TAG = JokesViewModel.class.getSimpleName();

    private JokeLiveData jokeData;

    public JokesViewModel() {}

    @Inject
    public JokesViewModel(JokeLiveData jokeData) {
        Log.v(LOG_TAG, "Creating JokesViewModel with constructor. Injected: " + jokeData);
        this.jokeData = jokeData;
    }

    public LiveData<String> getJokeData() {
        if(jokeData != null) {
            jokeData.loadData();
        }
        return jokeData;
    }
}
