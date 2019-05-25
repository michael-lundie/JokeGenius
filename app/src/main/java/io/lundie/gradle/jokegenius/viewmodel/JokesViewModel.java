package io.lundie.gradle.jokegenius.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class JokesViewModel extends ViewModel {

    private LiveData<String> jokeString;

    public JokesViewModel() {}

}
