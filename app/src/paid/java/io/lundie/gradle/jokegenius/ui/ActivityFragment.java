package io.lundie.gradle.jokegenius.ui;

/**
 * ## Paid AppFlavour version
 * Fragment extending {@link ExtendableActivityFragment}. Used to allow custom behavior
 * implementations depending the use of free or paid app flavours.
 */
public class ActivityFragment extends ExtendableActivityFragment {

    /**
     * Custom launch behaviour which must be implemented by the extended class.
     */
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