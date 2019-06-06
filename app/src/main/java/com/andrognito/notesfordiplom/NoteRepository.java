package com.andrognito.notesfordiplom;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class NoteRepository {
    public static final int ACTION_NEW_NOTE = 0;
    public static final int ACTION_UPDATE = 1;

    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_TITLE = "name";
    public static final String FIELD_DEADLINE = "deadline";
    public static final String FIELD_CREATIONDATE = "creationDate";
    public static final String FIELD_CHANGEDATE = "changeDate";
    public static final String FIELD_IS_DEADLINE_NEEDED = "isDeadlineNeeded";
    private ArrayList<Note> noteList = new ArrayList<Note>();

    public NoteRepository() {
    }

    public void saveNote(Context context, Note note) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(note.getCreationDate().getTime()), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NoteRepository.FIELD_TITLE, note.getNoteTitle());
        editor.putString(NoteRepository.FIELD_DESCRIPTION, note.getNoteDescription());
        editor.putString(NoteRepository.FIELD_DEADLINE, note.getNoteTime());
        editor.putLong(NoteRepository.FIELD_CREATIONDATE, note.getCreationDate().getTime());
        editor.putLong(NoteRepository.FIELD_CHANGEDATE, note.getChangeDate().getTime());
        editor.putBoolean(NoteRepository.FIELD_IS_DEADLINE_NEEDED, note.getDeadlineNeeded());
        editor.apply();
        Toast.makeText(context, "Заметка сохранена", Toast.LENGTH_SHORT).show();
    }

    public List<Note> fillList(Context context) {
        File prefsdir = new File(context.getApplicationInfo().dataDir, "shared_prefs");

        if (prefsdir.exists() && prefsdir.isDirectory()) {
            String[] list = prefsdir.list();
            for (int i = 0; i < list.length; i++) {
                SharedPreferences sharedPref = context.getSharedPreferences(list[i].replace(".xml", ""), Context.MODE_PRIVATE);
                Note note = new Note();
                note.setNoteTitle(sharedPref.getString(NoteRepository.FIELD_TITLE, "Unknown"));
                note.setNoteDescription(sharedPref.getString(NoteRepository.FIELD_DESCRIPTION, ""));
                note.setNoteTime(sharedPref.getString(NoteRepository.FIELD_DEADLINE, ""));
                note.setCreationDate(new Date(sharedPref.getLong(NoteRepository.FIELD_CREATIONDATE, 0)));
                note.setChangeDate(new Date(sharedPref.getLong(NoteRepository.FIELD_CHANGEDATE, 0)));
                note.setDeadlineNeeded(sharedPref.getBoolean(NoteRepository.FIELD_IS_DEADLINE_NEEDED, false));
                if (note.getNoteTitle().equals("Unknown") == false) {
                    noteList.add(note);
                }
            }
        }
        return noteList;
    }

    public void deleteNote(Context mContext, Note note) {
        File dir = new File(mContext.getFilesDir().getParent() + "/shared_prefs/");
        String[] children = dir.list();

        for (int i = 0; i < children.length; i++) {
            if (mContext.getSharedPreferences(children[i].replace(".xml", ""),
                    Context.MODE_PRIVATE).contains(String.valueOf(note.getCreationDate().getTime()))) {
                mContext.getSharedPreferences(children[i].replace(".xml", ""), Context.MODE_PRIVATE).edit().clear().commit();
            }
        }
        for (int i = 0; i < children.length; i++) {
            if (new File(dir, children[i]).getName().equals(note.getCreationDate().getTime() + ".xml")) {
                new File(dir, children[i]).delete();
            }
        }
    }

    public Note getNote(Context noteEditContext, String noteID) {
        File dir = new File(noteEditContext.getFilesDir().getParent() + "/shared_prefs/");
        String[] children = dir.list();
        Note note = new Note();
        for (int i = 0; i < children.length; i++) {
            if (noteEditContext.getSharedPreferences(children[i].replace(".xml", ""),
                    Context.MODE_PRIVATE).getLong(NoteRepository.FIELD_CREATIONDATE, 0) == Long.parseLong(noteID)) {
                SharedPreferences sharedPref = noteEditContext.getSharedPreferences(children[i].replace(".xml", ""), Context.MODE_PRIVATE);
                note.setNoteTitle(sharedPref.getString(NoteRepository.FIELD_TITLE, "Unknown"));
                note.setNoteDescription(sharedPref.getString(NoteRepository.FIELD_DESCRIPTION, ""));
                note.setNoteTime(sharedPref.getString(NoteRepository.FIELD_DEADLINE, ""));
                note.setCreationDate(new Date(sharedPref.getLong(NoteRepository.FIELD_CREATIONDATE, 0)));
                note.setChangeDate(new Date(sharedPref.getLong(NoteRepository.FIELD_CHANGEDATE, 0)));
                note.setDeadlineNeeded(sharedPref.getBoolean(NoteRepository.FIELD_IS_DEADLINE_NEEDED, false));
            }
        }
        return note;
    }

    public void updateNote(Context noteEditContext, Note note) {
        File dir = new File(noteEditContext.getFilesDir().getParent() + "/shared_prefs/");
        String[] children = dir.list();
        for (int i = 0; i < children.length; i++) {
            if (noteEditContext.getSharedPreferences(children[i].replace(".xml", ""),
                    Context.MODE_PRIVATE).getLong(NoteRepository.FIELD_CREATIONDATE, 0) == note.getCreationDate().getTime()) {
                SharedPreferences sharedPref = noteEditContext.getSharedPreferences(children[i].replace(".xml", ""), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.putString(NoteRepository.FIELD_TITLE, note.getNoteTitle());
                editor.putString(NoteRepository.FIELD_DESCRIPTION, note.getNoteDescription());
                editor.putString(NoteRepository.FIELD_DEADLINE, note.getNoteTime());
                editor.putLong(NoteRepository.FIELD_CREATIONDATE, note.getCreationDate().getTime());
                editor.putLong(NoteRepository.FIELD_CHANGEDATE, note.getChangeDate().getTime());
                editor.putBoolean(NoteRepository.FIELD_IS_DEADLINE_NEEDED, note.getDeadlineNeeded());
                editor.apply();
                Toast.makeText(noteEditContext, "Заметка обновлена", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
