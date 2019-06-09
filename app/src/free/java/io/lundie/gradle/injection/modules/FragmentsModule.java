package io.lundie.gradle.injection.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.lundie.gradle.jokegenius.ui.MainActivityFragment;

@Module
public abstract class FragmentsModule {

    @ContributesAndroidInjector
    abstract MainActivityFragment contributeMainActivityFragment();
}