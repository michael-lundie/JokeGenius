package io.lundie.gradle.jokegenius.injection.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.lundie.gradle.jokegenius.MainActivityFragment;

@Module
public abstract class FragmentsModule {

    @ContributesAndroidInjector
    abstract MainActivityFragment contributeMainActivityFragment();
}