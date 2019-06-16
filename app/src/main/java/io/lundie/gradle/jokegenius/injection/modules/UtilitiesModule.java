package io.lundie.gradle.jokegenius.injection.modules;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import io.lundie.gradle.jokegenius.utilities.AppUtils;

@Module
public class UtilitiesModule {
    // Utils Injection
    @Provides
    @Reusable
    AppUtils provideUtils(Application application) {
        return new AppUtils(application);
    }
}