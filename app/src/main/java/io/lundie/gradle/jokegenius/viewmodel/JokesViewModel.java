package io.lundie.gradle.jokegenius.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.lundie.gradle.jokegenius.apiutils.EndpointsAsyncTask;
import io.lundie.gradle.jokegenius.apiutils.FetchStatus;
import io.lundie.gradle.jokegenius.apiutils.JokeLiveData;

/**
 * Simple ViewModel class used for the access LiveData.
 */
public class JokesViewModel extends ViewModel {

    private static final String LOG_TAG = JokesViewModel.class.getSimpleName();

    private JokeLiveData jokeData;
    private MutableLiveData<FetchStatus> fetchStatusLiveData;

    public JokesViewModel() {}

    /**
     * ViewModel constructor injecting singleton LiveData via Dagger.
     * @param jokeData extended LiveData instance, which fetches and stores fetched data on request.
     * @param fetchStatusLiveData simple LiveData instance, holding simple enum values
     */
    @Inject
    public JokesViewModel(JokeLiveData jokeData, MutableLiveData<FetchStatus> fetchStatusLiveData) {
        this.jokeData = jokeData;
        this.fetchStatusLiveData = fetchStatusLiveData;
    }

    /**
     * Getter method retrieves LiveData instance and 'self-loads' data from API.
     * @return {@link JokeLiveData} instance.
     */
    public LiveData<String> getJokeData() {
        if(jokeData != null) {
            jokeData.loadData();
        }
        return jokeData;
    }

    /**
     * Getter method retrieves status live data, returned from {@link EndpointsAsyncTask}
     * @return {@link MutableLiveData}
     */
    public LiveData<FetchStatus> getFetchStatus() {
        return fetchStatusLiveData;
    }
}