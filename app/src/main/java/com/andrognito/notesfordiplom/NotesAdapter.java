package com.andrognito.notesfordiplom;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private List<Note> notesList;
    private int noteID;
    private Note note;
    private Context mContext;

    public static class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView noteTitleForAdapter;
        private TextView noteDescriptionForAdapter;
        private TextView noteTimeForAdapter;

        public NotesViewHolder(@NonNull View view) {
            super(view);
            noteTitleForAdapter = (TextView) view.findViewById(R.id.noteTitle);
            noteDescriptionForAdapter = (TextView) view.findViewById(R.id.noteDescription);
            noteTimeForAdapter = (TextView) view.findViewById(R.id.noteDeadlineView);
        }
    }

    public NotesAdapter(List<Note> myDataset) {
        this.notesList = myDataset;
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_view, parent, false);
        return new NotesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder notesViewHolder, int position) {
        mContext = notesViewHolder.itemView.getContext();
        note = notesList.get(position);
        notesViewHolder.noteTitleForAdapter.setText(String.valueOf(note.getNoteTitle()));
        notesViewHolder.noteDescriptionForAdapter.setText(String.valueOf(note.getNoteDescription()));
        notesViewHolder.noteTimeForAdapter.setText(String.valueOf(note.getNoteTime()));

        notesViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                NoteSaver noteSaver = new NoteSaver();
                noteSaver.clearSharedPreferences(mContext, note);

                notesList.remove(note);
                notifyDataSetChanged();
                return false;
            }
        });

        notesViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

}