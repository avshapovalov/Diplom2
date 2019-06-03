package com.andrognito.notesfordiplom;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class NoteSaver {
    public static final int ACTION_NEW_TASK = 0;
    public static final int ACTION_UPDATE = 1;

    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_TITLE = "name";
    public static final String FIELD_DEADLINE = "deadline";
    public static final String FIELD_CREATIONDATE = "creationDate";

    private ArrayList<Note> noteList = new ArrayList<Note>();

    public NoteSaver() {
    }

    public void saveNote(Context context, Note note) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(note.getCreateDate().getTime()), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NoteSaver.FIELD_TITLE, note.getNoteTitle());
        editor.putString(NoteSaver.FIELD_DESCRIPTION, note.getNoteDescription());
        editor.putString(NoteSaver.FIELD_DEADLINE, note.getNoteTime());
        editor.putLong(NoteSaver.FIELD_CREATIONDATE, note.getCreateDate().getTime());
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
                note.setNoteTitle(sharedPref.getString(NoteSaver.FIELD_TITLE, "Unknown"));
                note.setNoteDescription(sharedPref.getString(NoteSaver.FIELD_DESCRIPTION, ""));
                note.setNoteTime(sharedPref.getString(NoteSaver.FIELD_DEADLINE, ""));
                note.setCreateDate(new Date(sharedPref.getLong(NoteSaver.FIELD_CREATIONDATE, 0)));
                if (note.getNoteTitle().equals("Unknown") == false) {
                    noteList.add(note);
                }
            }
        }
        return noteList;
    }
}
