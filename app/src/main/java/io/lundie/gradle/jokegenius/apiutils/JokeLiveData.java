package io.lundie.gradle.jokegenius.apiutils;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

/**
 * Subclass of {@link LiveData}, implementing {@link AsyncCallback}. This implementation allows our
 * LiveData object to fetch data through an API request. Any active observers will in turn be able
 * to report when data has successfully been loaded.
 * Improved error reporting implemented via the {@link AsyncCallback} interface.
 */
public class JokeLiveData extends LiveData<String> implements AsyncCallback, AsyncFetchStatus {

    private static final String LOG_TAG = EndpointsAsyncTask.class.getName();

    private EndpointsAsyncTaskFactory taskFactory;
    private MutableLiveData<FetchStatus> fetchStatusLiveData;

    @Inject
    public JokeLiveData(EndpointsAsyncTaskFactory taskFactory,
                        MutableLiveData<FetchStatus> fetchStatusLiveData) {
        this.taskFactory = taskFactory;
        this.fetchStatusLiveData = fetchStatusLiveData;
    }

    public void loadData() {
        taskFactory.createEndpointsAsyncTask(this, this);
    }

    @Override
    public void processJokeData(String jokeDataOutput) {
        setValue(jokeDataOutput);
    }

    @Override
    public void post(FetchStatus fetchStatus) {
        // Using postValue method so that status can be updated from a background thread (AsyncTask)
        fetchStatusLiveData.postValue(fetchStatus);
        Log.e(LOG_TAG, "JOKE DATA: SETTING STATUS --> " + fetchStatus);
    }

    @Override
    public void set(FetchStatus fetchStatus) {
        fetchStatusLiveData.setValue(fetchStatus);
    }
}