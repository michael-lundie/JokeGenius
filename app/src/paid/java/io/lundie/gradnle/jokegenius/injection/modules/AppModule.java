package io.lundie.gradnle.jokegenius.injection.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.lundie.gradle.jokegenius.apiutils.EndpointsAsyncTaskFactory;
import io.lundie.gradle.jokegenius.backend.myApi.MyApi;
import io.lundie.gradle.jokegenius.viewmodel.JokeLiveData;

@Module (includes = {ViewModelModule.class, ApiModule.class})
public class AppModule {

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
}
