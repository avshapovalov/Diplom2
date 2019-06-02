package com.andrognito.pinlockviewapp;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private List<Note> notesList;
    NoteSaver noteSaver;
    public NotesAdapter(List<Note> myDataset) {
        notesList = myDataset;
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView noteTitleForAdapter;
        private TextView noteDescriptionForAdapter;
        private TextView noteTimeForAdapter;

        public NotesViewHolder(View view) {
            super(view);
            noteTitleForAdapter = view.findViewById(R.id.noteTitle);
            noteDescriptionForAdapter = view.findViewById(R.id.noteDescription);
            noteTimeForAdapter = view.findViewById(R.id.timeView);
        }
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list_view, parent, false);
        return new NotesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final NotesViewHolder notesViewHolder, final int position) {
        notesViewHolder.noteTitleForAdapter.setText(notesList.get(position).getNoteTitle());
        notesViewHolder.noteDescriptionForAdapter.setText(notesList.get(position).getNoteDescription());
        //notesViewHolder.noteTimeForAdapter.setText(notesList.get(position).getNoteTime());
        notesViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SharedPreferences sharedPreferences = v.getContext().getSharedPreferences(String.valueOf(notesList.get(position).getNoteTitle().hashCode()), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(String.valueOf(notesList.get(position).getNoteTitle().hashCode()));
                editor.apply();
                notesList.remove(notesList.get(position));
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