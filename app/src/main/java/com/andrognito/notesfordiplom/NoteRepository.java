package com.andrognito.notesfordiplom;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class NoteRepository {

    public static final int ACTION_NEW_NOTE = 0;
    public static final int ACTION_UPDATE = 1;
    private static final String JSON_REPOSITORY_NAME = Keys.NoteRepository.getKey();
    private static final String JSON_REPOSITORY_KEY = Keys.NoteRepository.getKey();
    private List<Note> noteList;
    public static final String TAG = Keys.Log.getKey();
    private Gson gson;

    public NoteRepository() {
    }

    public void saveNote(Context context, Note note) {
        Log.d(TAG, R.string.note_repository_note_log_equal + note.toString());
        noteList = fillList(context);
        Log.d(TAG, R.string.note_repository_note_list_log_equal + noteList.toString());
        noteList.add(note);

        SharedPreferences sharedPreferences = context.getSharedPreferences(JSON_REPOSITORY_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        gson = new Gson();
        String json = gson.toJson(noteList);
        Log.d(TAG, R.string.note_repository_json_equal + json);
        editor.putString(JSON_REPOSITORY_KEY, json);
        editor.commit();
        Log.d(TAG, String.valueOf(R.string.note_repository_saved));
        Toast.makeText(context, R.string.note_repository_note_saved, Toast.LENGTH_LONG).show();
    }

    public List<Note> fillList(Context context) {
        gson = new Gson();
        SharedPreferences sharedPref = context.getSharedPreferences(JSON_REPOSITORY_NAME, Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(JSON_REPOSITORY_KEY, "");
        Log.d(TAG, R.string.note_repository_json_preferences + jsonPreferences);
        Type type = new TypeToken<List<Note>>() {
        }.getType();
        Log.d(TAG, String.valueOf(R.string.note_repository_type) + type);
        try {
            Log.d(TAG, String.valueOf(R.string.note_repository_log_start_filling_list));
            noteList = new ArrayList<>();
            noteList = gson.fromJson(jsonPreferences, type);
            Log.d(TAG, R.string.note_repository_log_noteList_equal + noteList.toString());
        } catch (NullPointerException e) {
            noteList = new ArrayList<>();
        }
        return noteList;
    }

    public void deleteNote(Context Context, Note note, NotesAdapter notesAdapter, int position) {
        noteList = fillList(Context);
        for (int i = 0; i < noteList.size(); i++) {
            if (noteList.get(i).getCreationDate().equals(note.getCreationDate())) {
                noteList.remove(i);
            }
        }

        SharedPreferences sharedPreferences = Context.getSharedPreferences(JSON_REPOSITORY_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        gson = new Gson();
        String json = gson.toJson(noteList);
        editor.putString(JSON_REPOSITORY_KEY, json);
        editor.commit();
        notesAdapter.notifyItemRemoved(position);
        Toast.makeText(Context, R.string.note_repository_note_deleted, Toast.LENGTH_SHORT).show();
    }

    public void updateNote(Context context, Note note) {
        noteList = fillList(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences(JSON_REPOSITORY_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < noteList.size(); i++) {
            if (noteList.get(i).getCreationDate().equals(note.getCreationDate())) {
                noteList.get(i).setNoteTitle(note.getNoteTitle());
                noteList.get(i).setNoteDescription(note.getNoteDescription());
                noteList.get(i).setNoteTime(note.getNoteTime());
                noteList.get(i).setChangeDate(note.getChangeDate());
                noteList.get(i).setDeadlineNeeded(note.getDeadlineNeeded());
            }
        }

        gson = new Gson();
        String json = gson.toJson(noteList);
        editor.putString(JSON_REPOSITORY_KEY, json);
        editor.commit();

        Toast.makeText(context, R.string.note_repository_note_updated, Toast.LENGTH_SHORT).show();
    }

}
