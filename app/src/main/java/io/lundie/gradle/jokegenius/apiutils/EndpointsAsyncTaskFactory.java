package io.lundie.gradle.jokegenius.apiutils;

import javax.inject.Inject;

import io.lundie.gradle.jokegenius.backend.myApi.MyApi;

public class EndpointsAsyncTaskFactory {

    private MyApi myApiService;

    @Inject
    public EndpointsAsyncTaskFactory(MyApi apiService) {
        this.myApiService = apiService;
    }

    public void createEndpointsAsyncTask(AsyncCallback asyncCallback) {
        //Since an AsyncTask can only be used once, we're creating a new task every time
        // we need to connect to our API. Using factory in order to facilitate injection
        // of dependencies required by AsyncTask.
        // Trying to find a better way of doing this.
        EndpointsAsyncTask asyncTask = new EndpointsAsyncTask(myApiService, asyncCallback);
        asyncTask.execute();
    }
}