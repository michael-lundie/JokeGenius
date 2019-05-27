package io.lundie.gradle.jokegenius;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import io.lundie.gradle.jokegenius.backend.myApi.MyApi;

public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {

    private static final String LOG_TAG = EndpointsAsyncTask.class.getName();

    private static MyApi myApiService = null;

    private AsyncCallback asyncCallback;

    public EndpointsAsyncTask(AsyncCallback asyncCallback) {
        this.asyncCallback = asyncCallback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Log.v(LOG_TAG, "QUERY result is: " + result);
        String jokeData = result;
        if(jokeData.isEmpty()) {
            jokeData = "Error";
        }
        if(asyncCallback != null) {
            asyncCallback.processJokeData(jokeData);
        } else {
            Log.e(LOG_TAG, "AsyncCallback reference was not set correctly.");
        }

    }
}
