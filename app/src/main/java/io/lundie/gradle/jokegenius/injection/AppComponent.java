package io.lundie.gradle.jokegenius.injection;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import io.lundie.gradle.jokegenius.injection.modules.FragmentsModule;
import io.lundie.gradle.jokegenius.App;
import io.lundie.gradle.jokegenius.injection.modules.AppModule;

@Singleton
@Component(modules = {  AndroidSupportInjectionModule.class,
                        FragmentsModule.class,
                        AppModule.class,
                        ActivityBuilder.class})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        AppComponent build();

        @BindsInstance
        Builder application(Application application);
    }

    void inject(App app);

}
