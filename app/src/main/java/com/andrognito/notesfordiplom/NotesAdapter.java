package com.andrognito.notesfordiplom;

import android.content.Context;
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
    private Context context;

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
        notesList = myDataset;
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_view, parent, false);
        context = parent.getContext();
        return new NotesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder notesViewHolder, int position) {
        note = notesList.get(position);
        notesViewHolder.noteTitleForAdapter.setText(String.valueOf(note.getNoteTitle()));
        notesViewHolder.noteDescriptionForAdapter.setText(String.valueOf(note.getNoteDescription()));
        notesViewHolder.noteTimeForAdapter.setText(String.valueOf(note.getNoteTime()));

        notesViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

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