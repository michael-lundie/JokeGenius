package io.lundie.gradle.jokegenius.injection;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.lundie.gradle.jokegenius.ui.MainActivity;
import io.lundie.gradle.jokegenius.injection.modules.FragmentsModule;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {FragmentsModule.class})
    abstract MainActivity bindMainActivity();
}
