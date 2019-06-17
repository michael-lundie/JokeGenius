package io.lundie.gradle.jokegenius.injection.modules;

import android.app.Application;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import io.lundie.gradle.jokegenius.utilities.AppConstants;

@Module
public class AppModule extends ExtendableAppModule {

    @Provides
    @Reusable
    InterstitialAd provideInterstitialAd(Application application) {
        InterstitialAd interstitialAd = new InterstitialAd(application);
        interstitialAd.setAdUnitId(AppConstants.addId);
        return interstitialAd;
    }

    @Provides
    AdRequest provideAdRequest() {
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        return new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
    }
}
