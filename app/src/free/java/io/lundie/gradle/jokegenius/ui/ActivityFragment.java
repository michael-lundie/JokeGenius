package io.lundie.gradle.jokegenius.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import io.lundie.gradle.jokegenius.R;
import io.lundie.gradle.jokegenius.utilities.AppUtils;

public class ActivityFragment extends ExtendableActivityFragment {

    private static final String LOG_TAG = ActivityFragment.class.getName();

    @Inject AdRequest adRequest;
    @Inject InterstitialAd interstitialAd;
    @Inject AppUtils appUtils;

    @BindView(R.id.adView) AdView mAdView;
    @BindView(R.id.endpoints_progress_bar) protected ProgressBar mProgressBar;

    public ActivityFragment() { }

    @Override
    public void setBehaviors() {
        mAdView.loadAd(adRequest);
    }

    @Override
    public void setLaunchBehaviour() {
        if(appUtils.checkInternetAccess()) {
            Log.e(LOG_TAG, "INTERNET HAS ACCESS");
            interstitialAd.show();
        } else {
            launchJokePresenterActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureInterstitialAd();
    }

    @Override
    public void launchJokePresenterActivity() {
        super.launchJokePresenterActivity();
    }

    private void configureInterstitialAd() {
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                launchJokePresenterActivity();
            }
        });
    }

    private void configureDagger(){ AndroidSupportInjection.inject(this); }
}