package io.lundie.gradle.jokegenius.injection;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.lundie.gradle.jokegenius.injection.modules.FragmentsModule;
import io.lundie.gradle.jokegenius.ui.MainActivity;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {FragmentsModule.class})
    abstract MainActivity bindMainActivity();
}
