package io.lundie.gradle.jokegenius.injection.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.lundie.gradle.jokegenius.ui.ExtendableActivityFragment;
import io.lundie.gradle.jokegenius.ui.ActivityFragment;

@Module
public abstract class FragmentsModule {

    @ContributesAndroidInjector
    abstract ActivityFragment contributeMainActivityFragment();

    @ContributesAndroidInjector
    abstract ExtendableActivityFragment contributeExtendableActivityFragment();
}