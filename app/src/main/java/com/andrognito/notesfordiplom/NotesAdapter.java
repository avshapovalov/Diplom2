package com.andrognito.notesfordiplom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private List<Note> notesList;
    private Note note;
    private Context mContext;

    public static class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView noteTitleForAdapter;
        private TextView noteDescriptionForAdapter;
        private TextView noteTimeForAdapter;
        private ImageButton priorityLevel;
        private List<Note> notesList;
        private Note note;
        private Context mContext;
        private int position;
        private NoteRepository noteRepository;

        public NotesViewHolder(View view) {
            super(view);
            noteTitleForAdapter = (TextView) view.findViewById(R.id.noteTitle);
            noteDescriptionForAdapter = (TextView) view.findViewById(R.id.noteDescription);
            noteTimeForAdapter = (TextView) view.findViewById(R.id.noteDeadlineView);
            priorityLevel = (ImageButton) view.findViewById(R.id.priorityLevel);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            mContext = itemView.getContext();
            noteRepository = new NoteRepository();
            notesList = noteRepository.fillList(mContext);
        }

        @Override
        public void onClick(View view) {
            position = getAdapterPosition();
            note = notesList.get(position);

            if (notesList.size() == 1 && position == 1) {
                note = notesList.get(0);
            } else {
                note = notesList.get(position);
            }
            Intent intent = new Intent(mContext, NewNote.class);
            intent.putExtra(NewNote.NOTE_ID, note);
            mContext.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View view) {
            position = getAdapterPosition();
            note = notesList.get(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder
                    .setIcon(R.drawable.ic_delete_sweep_white_24dp)
                    .setMessage(R.string.delete_note_question)
                    .setCancelable(false)
                    .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            note = notesList.get(position);
                            noteRepository.deleteNote(mContext, note, NotesAdapter.this, position);
                            notesList.remove(note);
                        }
                    })
                    .setNegativeButton(R.string.reject, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            return false;
        }
    }

    public NotesAdapter(List<Note> myDataset) {
        notesList = myDataset;
    }

    public NotesAdapter getNotesAdapter(){
        return NotesAdapter.this;
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_view, parent, false);
        return new NotesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, final int position) {
        mContext = holder.itemView.getContext();
        note = notesList.get(position);
        holder.noteTitleForAdapter.setText(String.valueOf(note.getNoteTitle()));
        holder.noteDescriptionForAdapter.setText(String.valueOf(note.getNoteDescription()));
        holder.noteTimeForAdapter.setText(String.valueOf(note.getNoteTime()));
        hideLabels(holder);
        setLevelColor(holder);
    }

    @Override
    public int getItemCount() {
        try {
            return notesList.size();
        } catch (NullPointerException e) {

        }
        return 0;
    }

    private void setLevelColor(NotesViewHolder holder) {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date deadlineDate = formatter.parse(note.getNoteTime());
            int difference =
                    ((int) ((deadlineDate.getTime() / (24 * 60 * 60 * 1000))
                            - (int) (Calendar.getInstance().getTime().getTime() / (24 * 60 * 60 * 1000))));

            if (difference <= 5 && difference >= 0) {
                holder.priorityLevel.setBackgroundColor(holder.itemView.getResources().getColor(R.color.colorOrange));
            } else if (difference < 0) {
                holder.priorityLevel.setBackgroundColor(holder.itemView.getResources().getColor(R.color.colorRed));
            } else {
                holder.priorityLevel.setBackgroundColor(holder.itemView.getResources().getColor(R.color.colorGreen));
            }

        } catch (ParseException e) {
            holder.priorityLevel.setBackgroundColor(holder.itemView.getResources().getColor(R.color.colorGreen));
            e.printStackTrace();
        } catch (NullPointerException e) {
            holder.priorityLevel.setBackgroundColor(holder.itemView.getResources().getColor(R.color.colorGreen));
            e.printStackTrace();
        }
    }

    private void hideLabels(NotesViewHolder holder) {
        if (String.valueOf(holder.noteTitleForAdapter.getText()).isEmpty()) {
            holder.noteTitleForAdapter.setVisibility(View.GONE);
        }

        if (String.valueOf(holder.noteDescriptionForAdapter.getText()).isEmpty()) {
            holder.noteDescriptionForAdapter.setVisibility(View.GONE);
        }

        if (String.valueOf(holder.noteTimeForAdapter.getText()).isEmpty()) {
            holder.noteTimeForAdapter.setVisibility(View.GONE);
        }
    }

}