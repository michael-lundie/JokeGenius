package io.lundie.gradle.jokegenius.injection.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.lundie.gradle.jokegenius.viewmodel.JokeLiveData;

@Module (includes = ViewModelModule.class)
public class AppModule {

    @Provides
    @Singleton
    JokeLiveData provideJokeLiveData() {
        return new JokeLiveData(); }

}
