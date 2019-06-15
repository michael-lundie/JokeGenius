package io.lundie.gradle.jokegenius.apiutils;

/**
 * Simple interface to be implemented by {@link JokeLiveData}.
 */
public interface AsyncCallback {
    void processJokeData(String jokeDataOutput);
    void setFetchStatus(FetchStatus fetchStatus);
}
