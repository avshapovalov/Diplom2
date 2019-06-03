package com.andrognito.notesfordiplom;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class Notes extends AppCompatActivity {

    RecyclerView notesView;
    RecyclerView.Adapter notesAdapter;
    RecyclerView.LayoutManager layoutManager;
    private Toolbar notesToolbar;
    private List<Note> noteList;
    NoteSaver noteSaver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        noteList = new ArrayList<>();
        noteSaver = new NoteSaver();
        noteList = noteSaver.fillList(Notes.this);
        notesView = (RecyclerView) findViewById(R.id.notes_recycler_view);

        notesView.setHasFixedSize(true);
        notesAdapter = new NotesAdapter(getApplicationContext(), noteList);
        notesView.setAdapter(notesAdapter);
        layoutManager = new LinearLayoutManager(this);
        notesView.setLayoutManager(layoutManager);

        notesToolbar = (Toolbar) findViewById(R.id.notesTooldbar);
        setSupportActionBar(notesToolbar);

        FloatingActionButton floatingAddNoteButton = findViewById(R.id.fab_add_note);
        floatingAddNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent notesIntent = new Intent(Notes.this, NewNote.class);
                startActivity(notesIntent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        noteList.clear();
        noteList = noteSaver.fillList(Notes.this);
        notesAdapter.notifyDataSetChanged();
    }


}
