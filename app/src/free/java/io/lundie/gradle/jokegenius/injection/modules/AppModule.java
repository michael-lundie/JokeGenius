package io.lundie.gradle.jokegenius.injection.modules;

import com.google.android.gms.ads.AdRequest;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule extends ExtendableAppModule {
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
