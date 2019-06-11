package io.lundie.gradle.jokegenius.apiutils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import io.lundie.gradle.jokegenius.backend.myApi.MyApi;

import static io.lundie.gradle.jokegenius.apiutils.FetchStatus.API__RERIEVE_ERROR;
import static io.lundie.gradle.jokegenius.apiutils.FetchStatus.FETCH_SUCCESS;
import static io.lundie.gradle.jokegenius.apiutils.FetchStatus.IO_EXCEPTION_ERROR;
import static io.lundie.gradle.jokegenius.apiutils.FetchStatus.RETURNED_EMPTY;

public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {

    private static final String LOG_TAG = EndpointsAsyncTask.class.getName();

    private MyApi myApiService;

    private AsyncCallback asyncCallback;

    public EndpointsAsyncTask(MyApi apiService, AsyncCallback asyncCallback) {
        this.myApiService = apiService;
        this.asyncCallback = asyncCallback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        if(myApiService !=null) {
            try {
                return myApiService.getJoke().execute().getData();
            } catch (IOException e) {
                asyncCallback.setFetchStatus(IO_EXCEPTION_ERROR);
                Log.e(LOG_TAG, e.getMessage());
                return "";
            }
        }
        asyncCallback.setFetchStatus(API__RERIEVE_ERROR);
        Log.e(LOG_TAG, "Error retrieving API service.");
        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        String jokeData = result;
        if(asyncCallback != null) {
            if(jokeData.isEmpty()) {
                asyncCallback.setFetchStatus(RETURNED_EMPTY);
                jokeData = "";
            } else {
                asyncCallback.setFetchStatus(FETCH_SUCCESS);
            } asyncCallback.processJokeData(jokeData);
        }else{
            Log.e(LOG_TAG, "AsyncCallback reference was not set correctly.");
        }
    }
}