package io.lundie.gradle.jokegenius.apiutils;

public interface AsyncFetchStatus {
    void post(FetchStatus fetchStatus);
    void set(FetchStatus fetchStatus);
}
