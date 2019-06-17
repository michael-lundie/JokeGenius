package io.lundie.jokerpresenter;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class JokePresenterActivity extends AppCompatActivity {

    private static final String LOG_TAG = JokePresenterActivity.class.getName();

    @BindView(R2.id.jokeView) TextView jokeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_presenter);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent receivedIntent = this.getIntent();

        String joke ="No joke received via intent.";

        if(receivedIntent.hasExtra("joke")) {
            String extra = receivedIntent.getStringExtra("joke");
            if (extra != null) {
                joke = extra;
            }
            Log.e(LOG_TAG, "Joke Received: " + joke);
        }

        jokeTextView.setText(joke);
    }

}
