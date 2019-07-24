package com.andrognito.notesfordiplom;

public enum Keys {
    SecretDirectory("secretDirectory"),
    Pin("pin"),
    Log("log"),
    NoteId("NOTE_ID"),
    NoteRepository("noteRepository"),
    PinLockView("PinLockView");
    private final String key;

    Keys(String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }
}
