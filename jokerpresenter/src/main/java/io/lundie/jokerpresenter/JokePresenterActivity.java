package io.lundie.jokerpresenter;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class JokePresenterActivity extends AppCompatActivity {

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
            joke = receivedIntent.getStringExtra("joke");
        }

        jokeTextView.setText(joke);
    }

}
