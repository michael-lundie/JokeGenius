package io.lundie.gradle.jokegenius.injection.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import io.lundie.gradle.jokegenius.injection.ViewModelKey;
import io.lundie.gradle.jokegenius.viewmodel.JokesViewModel;
import io.lundie.gradle.jokegenius.viewmodel.JokesViewModelFactory;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(JokesViewModel.class)
    abstract ViewModel bindMoviesViewModel(JokesViewModel viewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(JokesViewModelFactory factory);
}
