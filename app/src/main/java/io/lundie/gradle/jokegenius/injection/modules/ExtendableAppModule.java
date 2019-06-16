package io.lundie.gradle.jokegenius.injection.modules;

import androidx.lifecycle.MutableLiveData;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.lundie.gradle.jokegenius.apiutils.EndpointsAsyncTaskFactory;
import io.lundie.gradle.jokegenius.apiutils.FetchStatus;
import io.lundie.gradle.jokegenius.apiutils.JokeLiveData;
import io.lundie.gradle.jokegenius.backend.myApi.MyApi;

@Module (includes = {ViewModelModule.class, ApiModule.class, UtilitiesModule.class})
public abstract class ExtendableAppModule {

    @Provides
    @Singleton
    MutableLiveData<FetchStatus> provideFetchStatusLiveData() {
        return new MutableLiveData<>();
    }

    @Provides
    @Singleton
    JokeLiveData provideJokeLiveData(EndpointsAsyncTaskFactory endpointsAsyncTaskFactory,
                                     MutableLiveData<FetchStatus> fetchStatusLiveData) {
        return new JokeLiveData(endpointsAsyncTaskFactory, fetchStatusLiveData);
    }

    @Provides
    @Singleton
    EndpointsAsyncTaskFactory providesEndpointsAsyncTaskFactory(MyApi myApiService) {
        return new EndpointsAsyncTaskFactory(myApiService);
    }
}
