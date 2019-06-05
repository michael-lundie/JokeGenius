package io.lundie.gradle.jokegenius.viewmodel;

import androidx.lifecycle.LiveData;

import javax.inject.Inject;

import io.lundie.gradle.jokegenius.apiutils.AsyncCallback;
import io.lundie.gradle.jokegenius.apiutils.EndpointsAsyncTaskFactory;

public class JokeLiveData extends LiveData<String> implements AsyncCallback {

    EndpointsAsyncTaskFactory taskFactory;

    @Inject
    public JokeLiveData(EndpointsAsyncTaskFactory taskFactory) {
        this.taskFactory = taskFactory;
    }

    public void loadData() {
        taskFactory.createEndpointsAsyncTask(this::processJokeData);
    }

    @Override
    public void processJokeData(String jokeDataOutput) {
        setValue(jokeDataOutput);
    }
}