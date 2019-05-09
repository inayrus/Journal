package com.example.journal;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class EntryAdapter extends ResourceCursorAdapter {

    public EntryAdapter(Context context, Cursor c) {
        // constructor, new version needs flags
        super(context, R.layout.entry_row, c, FLAG_REGISTER_CONTENT_OBSERVER);
    }

    // method that puts the data in your recycled view,
    // where the cursor points to a row in the db
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // get the layout elements
        TextView moodView = view.findViewById(R.id.mainMood);
        TextView titleView = view.findViewById(R.id.mainTitle);
        TextView dateView = view.findViewById(R.id.mainDate);

        // a list of the views and the database column names
        TextView[] allViews = {moodView, titleView, dateView};
        String[] columnNames = {"Mood", "Title", "Timestamp"};

        for (int i = 0, len = allViews.length; i < len; i ++) {

            // get the value in every column
            int columnIndex = cursor.getColumnIndex(columnNames[i]);
            String value = cursor.getString(columnIndex);

            // set the value to the right View
            allViews[i].setText(value);
        }
    }
}
