package io.lundie.gradle.jokegenius.apiutils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

public class JokeLiveData extends LiveData<String> implements AsyncCallback {

    private EndpointsAsyncTaskFactory taskFactory;
    private MutableLiveData<FetchStatus> fetchStatusLiveData;

    @Inject
    public JokeLiveData(EndpointsAsyncTaskFactory taskFactory,
                        MutableLiveData<FetchStatus> fetchStatusLiveData) {
        this.taskFactory = taskFactory;
        this.fetchStatusLiveData = fetchStatusLiveData;
    }

    public void loadData() {
        taskFactory.createEndpointsAsyncTask(this);
    }

    @Override
    public void processJokeData(String jokeDataOutput) {
        setValue(jokeDataOutput);
    }

    @Override
    public void setFetchStatus(FetchStatus fetchStatus) {
        // Using postValue method so that status can be updated from a background thread
        // (in AsyncTask).
        fetchStatusLiveData.postValue(fetchStatus);
    }
}