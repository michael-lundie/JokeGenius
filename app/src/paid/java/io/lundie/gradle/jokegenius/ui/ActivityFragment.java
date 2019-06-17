package io.lundie.gradle.jokegenius.ui;

public class ActivityFragment extends ExtendableActivityFragment {

    @Override
    public void setLaunchBehaviour() {
        launchJokePresenterActivity();
    }

    public ActivityFragment() { }

    @Override
    public void setBehaviors() {
    }

    @Override
    public void launchJokePresenterActivity() {
        super.launchJokePresenterActivity();
    }
}
