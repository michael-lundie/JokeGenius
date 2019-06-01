package io.lundie.gradle.jokegenius;

import javax.inject.Inject;

import io.lundie.gradle.jokegenius.backend.myApi.MyApi;

public class EndpointsAsyncTaskFactory {

    private MyApi myApiService;

    @Inject
    public EndpointsAsyncTaskFactory(MyApi apiService) {
        this.myApiService = apiService;
    }

    public void createEndpointsAsyncTask(AsyncCallback asyncCallback) {
        EndpointsAsyncTask asyncTask = new EndpointsAsyncTask(myApiService, asyncCallback);
        asyncTask.execute();
    }
}
