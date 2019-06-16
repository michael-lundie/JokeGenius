package io.lundie.gradle.jokegenius.injection.modules;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import io.lundie.gradle.jokegenius.backend.myApi.MyApi;
import io.lundie.gradle.jokegenius.utilities.AppConstants;

/**
 * Dagger module responsible for creation of ApiService.
 * Note: Endpoints root URL is configured in AppConstants.
 */
@Module
public class ApiModule {

    @Provides
    @Reusable
    public MyApi providesMyApiService() {
        return new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                // options for running against local devappserver
                // - 10.0.2.2 is localhost's IP address in Android emulator
                // - turn off compression when running against local devappserver
                .setRootUrl(AppConstants.endpointsRootURL)
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                }).build();
    }
}
