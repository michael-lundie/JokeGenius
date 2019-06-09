package io.lundie.gradle.injection.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import io.lundie.gradle.jokegenius.R;
import io.lundie.gradle.jokegenius.viewmodel.JokesViewModel;
import io.lundie.jokerpresenter.JokePresenterActivity;

public class MainActivityFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory jokesViewModelFactory;

    @Inject
    AdRequest adRequest;

    @BindView(R.id.button) Button mJokeButton;

    @BindView(R.id.adView) AdView mAdView;

    @BindView(R.id.endpoints_progress_bar) ProgressBar mProgressBar;

    private JokesViewModel viewModel;

    public MainActivityFragment() { }

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
        mAdView.loadAd(adRequest);
        this.configureViewModel();
    }

    private void launchJokePresenterActivity(String jokeData) {
        viewModel.getJokeData().removeObservers(this);
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
        viewModel.getJokeData().observe(this, jokeData -> {
            // Check that observer has changed and launch a new activity vie intent.
            if(!jokeData.isEmpty()) {
                launchJokePresenterActivity(jokeData);
            }
        });
    }

    private void configureDagger(){ AndroidSupportInjection.inject(this); }
}
