package com.example.journal;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    // class attributes
    private EntryDatabase db;
    private EntryAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create/get the Entries database
        this.db = EntryDatabase.getInstance(getApplicationContext());

        // get the elements in the database
        Cursor cursor = db.selectAll();

        // create new adapter and link to ListView
        ListView listView = findViewById(R.id.itemsList);
        this.adapter = new EntryAdapter(getApplicationContext(), cursor);
        listView.setAdapter(adapter);

        // connect itemListeners to the listview
        listView.setOnItemClickListener(new ListItemClickListener());
        listView.setOnItemLongClickListener(new ListItemLongClickListener());
    }

    // method for when the user returns to this activity (ex. after adding an entry)
    @Override
    protected void onResume() {
        super.onResume();
        updateData();
    }

    // when the floating action button is clicked
    public void actionButtonClicked(View v) {

        // use intent to direct user to input activity
        Intent intent = new Intent(MainActivity.this, InputActivity.class);
        startActivity(intent);
    }

    // listener class for when an item (child) is clicked in the view produced by adapter (parent)
    private class ListItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // send the user to the detail activity
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);

            // add the JournalEntry with as extra
            // cursor can't be put as extra, so unpack the values and send those
            Cursor cursor = (Cursor) parent.getItemAtPosition(position);

            String[] columnNames = {"Timestamp", "Title", "Mood", "Content"};

            for (int i = 0, len = columnNames.length; i < len; i ++) {

                // get the value in every column
                int columnIndex = cursor.getColumnIndex(columnNames[i]);
                String value = cursor.getString(columnIndex);
                intent.putExtra(columnNames[i], value);
            }

            startActivity(intent);
        }
    }

    private class ListItemLongClickListener implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            // get the clicked entry (parent contains all shown Entry objects from adapter)
            Cursor cursor = (Cursor) parent.getItemAtPosition(position);

            // unpack the cursor
            int columnIndex = cursor.getColumnIndex("_id");
            int entryId = cursor.getInt(columnIndex);

            // get the connection to the database and delete entry
            EntryDatabase instance = EntryDatabase.getInstance(getApplicationContext());
            instance.delete(entryId);

            // update the adapter
            updateData();

            return true;
        }
    }

    private void updateData() {
        // get all the updated data from the database
        Cursor newCursor = db.selectAll();

        // replace the cursor in the adapter
        adapter.swapCursor(newCursor);
    }


}