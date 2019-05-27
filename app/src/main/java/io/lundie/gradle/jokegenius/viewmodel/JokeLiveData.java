package io.lundie.gradle.jokegenius.viewmodel;

import androidx.lifecycle.LiveData;


import io.lundie.gradle.jokegenius.AsyncCallback;
import io.lundie.gradle.jokegenius.EndpointsAsyncTask;

public class JokeLiveData extends LiveData<String> implements AsyncCallback {

    public void loadData() {
        new EndpointsAsyncTask(this::processJokeData).execute();
    }

    @Override
    public void processJokeData(String jokeDataOutput) {
        setValue(jokeDataOutput);
    }
}
