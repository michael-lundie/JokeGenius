package io.lundie.jokerpresenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class JokePresenterActivity extends AppCompatActivity {
    //@BindView(R2.id.jokeView) TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_presenter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent receivedIntent = this.getIntent();

        String joke ="No joke received via intent.";

        if(receivedIntent.hasExtra("joke")) {
            joke = receivedIntent.getStringExtra("joke");
        }

        TextView textView = findViewById(R.id.jokeView);

        textView.setText(joke);
    }

}
