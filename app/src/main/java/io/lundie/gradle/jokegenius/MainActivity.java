package io.lundie.gradle.jokegenius;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import io.lundie.jokerlib.Joker;
import io.lundie.jokerpresenter.JokePresenterActivity;


public class MainActivity extends AppCompatActivity {
    Joker joker = new Joker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void launchJokePresenterActivity() {
        Intent intent = new Intent(this, JokePresenterActivity.class);
        intent.putExtra("joke", joker.getJoke());
        startActivity(intent);

    }
    public void tellJoke(View view) {
        launchJokePresenterActivity();
        //Toast.makeText(this, joker.getJoke(), Toast.LENGTH_SHORT).show();
    }
}