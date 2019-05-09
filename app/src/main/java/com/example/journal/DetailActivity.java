package com.example.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // retrieve the send intent
        Intent intent = getIntent();

        // show the elements in the appropriate Views
        TextView[] allViews = { findViewById(R.id.detailDate),
                                findViewById(R.id.detailTitle),
                                findViewById(R.id.detailMood),
                                findViewById(R.id.detailContent)};
        String[] entryElems = {"Timestamp", "Title", "Mood", "Content"};

        for (int i = 0, len = allViews.length; i < len; i++) {

            // unpack the extras
            String value = intent.getStringExtra(entryElems[i]);

            //set the extra to the view
            allViews[i].setText(value);
        }
    }


}
