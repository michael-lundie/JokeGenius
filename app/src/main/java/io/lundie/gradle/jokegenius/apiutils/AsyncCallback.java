package io.lundie.gradle.jokegenius.apiutils;

public interface AsyncCallback {
    void processJokeData(String jokeDataOutput);
    void setFetchStatus(FetchStatus fetchStatus);
}
