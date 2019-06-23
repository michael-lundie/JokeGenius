package io.lundie.gradle.jokegenius.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import io.lundie.gradle.jokegenius.R;
import io.lundie.gradle.jokegenius.apiutils.FetchStatus;
import io.lundie.gradle.jokegenius.utilities.AppUtils;
import io.lundie.gradle.jokegenius.viewmodel.JokesViewModel;
import io.lundie.jokerpresenter.JokePresenterActivity;

/**
 * Extendable base activity fragment class. Responsible for UI display, observing ViewModel data and
 * user interactions, and reacting appropriately to observable changes.
 * Fragment extended by {@link ActivityFragment}
 */
public abstract class ExtendableActivityFragment extends Fragment {

    private static final String LOG_TAG = ExtendableActivityFragment.class.getName();

    @Inject ViewModelProvider.Factory jokesViewModelFactory;
    @Inject AppUtils appUtils;

    @BindView(R.id.button) protected Button mJokeButton;
    @BindView(R.id.endpoints_progress_bar) protected ProgressBar mProgressBar;
    @BindView(R.id.rootLayout) protected ConstraintLayout rootLayout;

    @BindString(R.string.error_network) String networkErrorString;
    @BindString(R.string.error_api) String apiErrorString;
    @BindString(R.string.error_general) String generalErrorString;

    private JokesViewModel viewModel;
    private FetchStatus fetchStatus;
    private String jokeString;
    private long previousClickTimeStamp;

    /** Required Empty constructor **/
    public ExtendableActivityFragment() { }

    /**
     * Allows the insertion of additional behaviours in onActivityCreated method by an extended
     * class without the need to override super.
     */
    public abstract void setBehaviors();

    public abstract void setLaunchBehaviour();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        mJokeButton.setOnClickListener(view -> configureJokeButton());
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();
        setBehaviors();
    }

    @Override
    public void onResume() {
        super.onResume();
        mJokeButton.setEnabled(true); // Re-enables the joke button.
    }

    /**
     * Launches the {@link JokePresenterActivity} setting retrieved data from API as intent extra.
     */
    public void launchJokePresenterActivity() {
        removeObservers();
        mProgressBar.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(getActivity(), JokePresenterActivity.class);
        intent.putExtra("joke", jokeString);
        startActivity(intent);
        jokeString = null; // Reset the jokeString in preparation for another request
    }

    /**
     * Method which manages the configuration of the 'fetch joke' button.
     */
    private void configureJokeButton() {
        // Since we are using LiveData, we want to be really careful not to set off multiple
        // observers with multiple clicks.
        // Using a method from https://stackoverflow.com/a/16514644 .
        if (timeSinceLastClicked() < 1000) { return; }
        previousClickTimeStamp = SystemClock.elapsedRealtime();
        mProgressBar.setVisibility(View.VISIBLE);
        mJokeButton.setEnabled(false);
        configureJokeObserver();
    }

    private long timeSinceLastClicked() {
        return SystemClock.elapsedRealtime() - previousClickTimeStamp;
    }

    /**
     * Sets-up ViewModel, using injected factory class.
     */
    private void configureViewModel() {
        viewModel = ViewModelProviders.of(this, jokesViewModelFactory).get(JokesViewModel.class);
    }

    /**+
     * Configures LiveData observables, active for the duration of an API Request.
     * Important: Observers should be removed after a request is complete to prevent the creation
     * of multiple observers (and in turn, API Requests).
     */
    private void configureJokeObserver() {
        // Let's start by observing the fetching process of our API request, this allows us
        // to act appropriately upon any returned errors.
        viewModel.getFetchStatus().observe(this, fetchStatus -> {
            this.fetchStatus = fetchStatus;
            Log.e(LOG_TAG, "FETCH: " + fetchStatus);
        });

        //Set-up data observable, to begin our API request and retrieve data.
        viewModel.getJokeData().observe(this, jokeData -> processRequestReturn(jokeData));
    }

    /**
     * Performs action on any returned data from API and deals with any returned errors.
     * This method is exposed and overridden by the FREE version of {@link ActivityFragment}.
     * @param jokeData returned data from API (or Mock).
     */
    private void processRequestReturn(String jokeData) {
        // Check that observer has changed, launch a new activity via intent or process errors.
        switch (fetchStatus) {
            case FETCH_SUCCESS:
                jokeString = jokeData;
                setLaunchBehaviour(); // Launch behavior which can be set by extended class
                break;
            case RETURNED_EMPTY:
                if(!appUtils.checkNetworkAccess()) {
                    displayErrorSnackbar(networkErrorString);
                    break;
                }
            case IO_EXCEPTION_ERROR:
                displayErrorSnackbar(generalErrorString);
                break;
            case API_RETRIEVE_ERROR:
                displayErrorSnackbar(apiErrorString);
                break;
        }
        mProgressBar.setVisibility(View.INVISIBLE);
        removeObservers();
    }

    /**
     * Creates a {@link Snackbar} for displaying any errors.
     * @param errorMessage error message to be displayed
     */
    private void displayErrorSnackbar(String errorMessage){
        Snackbar.make(rootLayout, errorMessage, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Removes all data observers and resets associated variables
     */
    private void removeObservers() {
        fetchStatus = null;
        viewModel.getJokeData().removeObservers(this);
        viewModel.getFetchStatus().removeObservers(this);
    }

    /**
     * Performs injection of dependencies.
     */
    private void configureDagger(){ AndroidSupportInjection.inject(this); }
}
