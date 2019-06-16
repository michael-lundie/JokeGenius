package io.lundie.gradle.jokegenius.apiutils;

import javax.inject.Inject;

import io.lundie.gradle.jokegenius.backend.myApi.MyApi;

/**
 * Simple Factory Class for creating {@link EndpointsAsyncTask}. This factory class facilitates
 * the ability to inject dependencies, while still creating a new AsyncTask each time an Api
 * Request is made.
 * TODO: Find if there is a better way to do this.
 */
public class EndpointsAsyncTaskFactory {

    private MyApi myApiService;

    @Inject
    public EndpointsAsyncTaskFactory(MyApi apiService) {
        this.myApiService = apiService;
    }

    /**
     * Constructor for factory creation of {@link EndpointsAsyncTask}.
     * @param asyncCallback Reference to an implementation of {@link AsyncCallback}
     */
    public void createEndpointsAsyncTask(AsyncCallback asyncCallback,
                                         AsyncFetchStatus asyncFetchStatus) {
        EndpointsAsyncTask asyncTask = new EndpointsAsyncTask(myApiService, asyncCallback, asyncFetchStatus);
        asyncTask.execute();
    }
}