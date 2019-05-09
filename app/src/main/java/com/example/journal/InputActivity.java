package com.example.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class InputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
    }

//    https://stackoverflow.com/questions/43423050/android-studio-open-emoji-keyboard-from-default-keyboard

    // when the entry is submitted
    public void addEntry(View v) {

        // get the data from the view
        TextView titleView = findViewById(R.id.editTitle);
        String title = titleView.getText().toString();

        TextView contentView = findViewById(R.id.editContent);
        String content = contentView.getText().toString();

        TextView moodView = findViewById(R.id.editMood);
        String mood = moodView.getText().toString();

        // create new JournalEntry object with the data
        JournalEntry entry = new JournalEntry(title, content, mood);

        // insert it into the database
        EntryDatabase instance = EntryDatabase.getInstance(getApplicationContext());
        instance.insert(entry);

        // close activity
        finish();
    }
}
