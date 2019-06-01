package io.lundie.gradle.jokegenius.injection.modules;

import com.google.android.gms.ads.AdRequest;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.lundie.gradle.jokegenius.EndpointsAsyncTaskFactory;
import io.lundie.gradle.jokegenius.backend.myApi.MyApi;
import io.lundie.gradle.jokegenius.viewmodel.JokeLiveData;

@Module (includes = ViewModelModule.class)
public class AppModule {

    @Provides
    AdRequest provideAdRequest() {
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        return new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
    }

    @Provides
    @Singleton
    JokeLiveData provideJokeLiveData(EndpointsAsyncTaskFactory endpointsAsyncTaskFactory) {
        return new JokeLiveData(endpointsAsyncTaskFactory);
    }

    @Provides
    @Singleton
    EndpointsAsyncTaskFactory providesEndpointsAsyncTaskFactory(MyApi myApiService) {
        return new EndpointsAsyncTaskFactory(myApiService);
    }

    @Provides
    @Singleton
    MyApi providesMyApiService() {
        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                // options for running against local devappserver
                // - 10.0.2.2 is localhost's IP address in Android emulator
                // - turn off compression when running against local devappserver
                .setRootUrl("http://192.168.0.7:8888/_ah/api/")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
        return builder.build();
    }
}
