package io.lundie.gradle.jokegenius;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;

import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.lundie.gradle.jokegenius.viewmodel.JokesViewModel;
import io.lundie.jokerlib.Joker;
import io.lundie.jokerpresenter.JokePresenterActivity;


public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    ViewModelProvider.Factory jokesViewModelFactory;

    private JokesViewModel viewModel;

    Joker joker = new Joker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidInjection.inject(this);
        configureViewModel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void launchJokePresenterActivity(String jokeData) {
        Intent intent = new Intent(this, JokePresenterActivity.class);
        intent.putExtra("joke", joker.getJoke());
        startActivity(intent);

    }
    public void tellJoke(View view) {
        configureJokeObserver();
    }

    private void configureViewModel() {
        viewModel = ViewModelProviders.of(this, jokesViewModelFactory).get(JokesViewModel.class);
    }
    private void configureJokeObserver() {
        viewModel.getJokeData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String jokeData) {
                // Check that observer has changed and launch a new activity vie intent.
                // TODO: Be careful not to load multiple activities.
                //TOOD: Unregister observer on activity open, register on resume?
                if(!jokeData.isEmpty()) {
                    launchJokePresenterActivity(jokeData);
                }
            }
        });
    }

    //--! Dagger Injection --! //

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}