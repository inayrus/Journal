package com.example.journal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EntryDatabase extends SQLiteOpenHelper {

    // turn EntryDatabase in a Singleton: only one instance is allowed to exist
    private static EntryDatabase instance;

    public static EntryDatabase getInstance(Context context) {
        if (EntryDatabase.instance == null) {
            EntryDatabase.instance = new EntryDatabase(context);
        }
        return EntryDatabase.instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Entries(_id INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT, Content TEXT, Mood TEXT, Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP);");

        // an example entry
        db.execSQL("INSERT INTO Entries(Title, Content, Mood) VALUES('Welcome to the journal app!'," +
                "'This is a journal where you can keep track of things like thoughts, moods, and daily activities!', '\uD83D\uDE0A✨\uD83D\uDE3B')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // deletes a table and creates a new one
        db.execSQL("DROP TABLE IF EXISTS " + "Entries");
        onCreate(db);
    }

    // a constructor
    private EntryDatabase(Context context) {
        super(context, "Entries", null, 1);
    }

    // select method
    public Cursor selectAll() {
        // open database
        SQLiteDatabase db = getWritableDatabase();

        // select all, return the cursor
        return db.rawQuery("SELECT * FROM Entries",null);
    }

    // insert method
    public void insert(JournalEntry entry) {
        SQLiteDatabase db = getWritableDatabase();

        // create object that binds values to SQLite columns
        ContentValues newRow = new ContentValues();

        // put entry values in the right columns
        newRow.put("Mood", entry.getMood());
        newRow.put("Title", entry.getTitle());
        newRow.put("Content", entry.getContent());

        // insert the column in database
        db.insert("Entries", null, newRow);
    }

    //delete method
    public void delete(long id) {
        // open database
        SQLiteDatabase db = getWritableDatabase();

        // delete the entry
        db.delete("Entries", "_id = ?", new String[] { String.valueOf(id) });
    }
}
