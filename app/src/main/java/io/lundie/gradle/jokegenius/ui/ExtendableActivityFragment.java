package io.lundie.gradle.jokegenius.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import io.lundie.gradle.jokegenius.R;
import io.lundie.gradle.jokegenius.apiutils.FetchStatus;
import io.lundie.gradle.jokegenius.viewmodel.JokesViewModel;
import io.lundie.jokerpresenter.JokePresenterActivity;

public abstract class ExtendableActivityFragment extends Fragment {

    private static final String LOG_TAG = ExtendableActivityFragment.class.getName();

    @Inject
    ViewModelProvider.Factory jokesViewModelFactory;

    @BindView(R.id.button) protected Button mJokeButton;

    @BindView(R.id.endpoints_progress_bar) protected ProgressBar mProgressBar;

    private JokesViewModel viewModel;

    private FetchStatus fetchStatus;

    public ExtendableActivityFragment() { }

    public abstract void setBehaviors();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        configureJokeButton(mJokeButton);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();
        setBehaviors();
    }

    private void launchJokePresenterActivity(String jokeData) {
        removeObservers();
        mProgressBar.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(getActivity(), JokePresenterActivity.class);
        intent.putExtra("joke", jokeData);
        startActivity(intent);
    }

    private void configureJokeButton(Button button) {
        button.setOnClickListener(view -> {
            mProgressBar.setVisibility(View.VISIBLE);
            configureJokeObserver();
        });
    }

    private void configureViewModel() {
        viewModel = ViewModelProviders.of(this, jokesViewModelFactory).get(JokesViewModel.class);
    }

    private void configureJokeObserver() {

        //Let's start by observing the fetching process of our API request
        viewModel.getFetchStatus().observe(this, fetchStatus -> {
            this.fetchStatus = fetchStatus;
            Log.e(LOG_TAG, "FETCH: " + fetchStatus);
        });


        viewModel.getJokeData().observe(this, jokeData -> {
            // Check that observer has changed and launch a new activity vie intent.
            switch (fetchStatus) {
                case FETCH_SUCCESS:
                    launchJokePresenterActivity(jokeData);
                    break;
                case RETURNED_EMPTY:
                case API__RERIEVE_ERROR:
                case IO_EXCEPTION_ERROR:
                    removeObservers();
            }
        });
    }

    private void removeObservers() {
        fetchStatus = null;
        viewModel.getJokeData().removeObservers(this);
        viewModel.getFetchStatus().removeObservers(this);
    }

    private void configureDagger(){ AndroidSupportInjection.inject(this); }
}
