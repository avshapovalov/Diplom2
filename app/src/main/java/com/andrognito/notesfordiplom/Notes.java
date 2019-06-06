package com.andrognito.notesfordiplom;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Notes extends AppCompatActivity {
    private RecyclerView notesView;
    private RecyclerView.Adapter notesAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Toolbar notesToolbar;
    private List<Note> noteList;
    private NoteRepository noteRepository;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        noteList = new ArrayList<Note>();
        noteRepository = new NoteRepository();
        noteList = noteRepository.fillList(Notes.this);
        notesView = (RecyclerView) findViewById(R.id.notes_recycler_view);
        notesView.setHasFixedSize(true);
        Collections.sort(noteList, new NotesComparator());
        Collections.reverse(noteList);
        notesAdapter = new NotesAdapter(noteList);
        notesView.setAdapter(notesAdapter);
        layoutManager = new LinearLayoutManager(Notes.this);
        notesView.setLayoutManager(layoutManager);
        notesToolbar = (Toolbar) findViewById(R.id.notesTooldbar);
        setSupportActionBar(notesToolbar);
        FloatingActionButton floatingAddNoteButton = findViewById(R.id.fab_add_note);
        floatingAddNoteButton.setOnClickListener(onfloatingAddNoteButtonClickListener);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        noteList = new ArrayList<Note>();
        noteRepository = new NoteRepository();
        noteList = noteRepository.fillList(Notes.this);
        notesView = (RecyclerView) findViewById(R.id.notes_recycler_view);
        notesView.setHasFixedSize(true);
        Collections.sort(noteList, new NotesComparator());
        Collections.reverse(noteList);
        notesAdapter = new NotesAdapter(noteList);
        notesView.setAdapter(notesAdapter);
        layoutManager = new LinearLayoutManager(Notes.this);
        notesView.setLayoutManager(layoutManager);
        notesToolbar = (Toolbar) findViewById(R.id.notesTooldbar);
        setSupportActionBar(notesToolbar);
        FloatingActionButton floatingAddNoteButton = findViewById(R.id.fab_add_note);
        floatingAddNoteButton.setOnClickListener(onfloatingAddNoteButtonClickListener);
    }

    View.OnClickListener onfloatingAddNoteButtonClickListener = v -> {
        switch (v.getId()) {
            case R.id.fab_add_note:
                Intent notesIntent = new Intent(Notes.this, NewNote.class);
                startActivity(notesIntent);
                break;
        }
    };

}
