package com.andrognito.pinlockviewapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private List<Note> notesList;
    NoteSaver noteSaver;
    private int noteID;
    private Note note;
    private static Context context;

    public static class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView noteTitleForAdapter;
        private TextView noteDescriptionForAdapter;
        private TextView noteTimeForAdapter;

        public NotesViewHolder(View view) {
            super(view);
            noteTitleForAdapter = view.findViewById(R.id.noteTitle);
            noteDescriptionForAdapter = view.findViewById(R.id.noteDescription);
            noteTimeForAdapter = view.findViewById(R.id.timeView);
            context = view.getContext();
        }
    }

    public NotesAdapter(List<Note> myDataset) {
        notesList = myDataset;
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list_view, parent, false);
        return new NotesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder notesViewHolder, int position) {
        try {
            noteID = notesList.get(position).getNoteTitle().hashCode();
            note = notesList.get(position);
            notesViewHolder.noteTitleForAdapter.setText(note.getNoteTitle());
            notesViewHolder.noteDescriptionForAdapter.setText(note.getNoteDescription());
            notesViewHolder.noteTimeForAdapter.setText(note.getNoteTime());
        } catch (NullPointerException e) {
            return;
        }

        notesViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(noteID), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(String.valueOf(noteID));
                editor.apply();
                notesList.remove(note);
                notifyDataSetChanged();
                return false;

            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

}