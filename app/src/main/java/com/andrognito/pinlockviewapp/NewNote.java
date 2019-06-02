package com.andrognito.pinlockviewapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class NewNote extends AppCompatActivity {

    private EditText newNoteTitle;
    private EditText newNoteDescription;
    private EditText newNoteDeadline;
    private CheckBox isDeadlineNeeded;
    private Note newNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        newNoteTitle = (EditText) findViewById(R.id.edit_note_title);
        newNoteDescription = (EditText) findViewById(R.id.edit_note_description);
        newNoteDeadline = (EditText) findViewById(R.id.edit_deadline_date);
        isDeadlineNeeded = findViewById(R.id.cbx_dedline_needed);

        Toolbar createNoteToolbar = (Toolbar) findViewById(R.id.createNoteToolbar);
        setSupportActionBar(createNoteToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        createNoteToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void saveNote(MenuItem item) {

        newNote = new Note(newNoteTitle.getText().toString(),
                newNoteDescription.getText().toString(),
                newNoteDeadline.getText().toString());
        try {
            NoteSaver noteSaver = new NoteSaver();
            noteSaver.saveNote(NewNote.this, newNote);
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_note_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_note: //Your task
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
