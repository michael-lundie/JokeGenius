package io.lundie.gradle.jokegenius.apiutils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import io.lundie.gradle.jokegenius.backend.myApi.MyApi;

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
                Log.e(LOG_TAG, e.getMessage());
                return "";
            }
        }
        Log.e(LOG_TAG, "Error retrieving API service.");
        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        String jokeData = result;
        if(jokeData.isEmpty()) {
            jokeData = "";
        }
        if(asyncCallback != null) {
            asyncCallback.processJokeData(jokeData);
        } else {
            Log.e(LOG_TAG, "AsyncCallback reference was not set correctly.");
        }
    }
}