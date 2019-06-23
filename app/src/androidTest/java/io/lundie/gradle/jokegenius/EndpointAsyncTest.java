package io.lundie.gradle.jokegenius;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import io.lundie.gradle.jokegenius.apiutils.AsyncCallback;
import io.lundie.gradle.jokegenius.apiutils.AsyncFetchStatus;
import io.lundie.gradle.jokegenius.apiutils.EndpointsAsyncTask;
import io.lundie.gradle.jokegenius.apiutils.FetchStatus;
import io.lundie.gradle.jokegenius.injection.modules.ApiModule;
import io.lundie.gradle.jokegenius.ui.MainActivity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyString;

@RunWith(AndroidJUnit4.class)
public class EndpointAsyncTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    /**
     * A simple test which creates a new AsyncTask within MainActivity and checks for a non-empty
     * return value.
     */
    @Test
    public void endpointAsyncStandAloneTest() throws ExecutionException, InterruptedException {
        CompletableFuture<String> futureData = new CompletableFuture<>();
        CompletableFuture<FetchStatus> futureStatus = new CompletableFuture<>();

        AsyncFetchStatus asyncFetchStatus = new AsyncFetchStatus() {
            @Override
            public void post(FetchStatus fetchStatus) {
                futureStatus.complete(fetchStatus);
            }

            @Override
            public void set(FetchStatus fetchStatus) {
                futureStatus.complete(fetchStatus);
            }
        };

        AsyncCallback asyncCallback = futureData::complete;

        new EndpointsAsyncTask(
                new ApiModule().providesMyApiService(), asyncCallback, asyncFetchStatus).execute();

        assertThat(futureData.get(), not(isEmptyString()) );
        assertThat(futureStatus.get(), is(FetchStatus.FETCH_SUCCESS));

        Log.e(this.getClass().getSimpleName(), "STRING: " + futureData.get() );
    }
}
