package com.andrognito.notesfordiplom;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;

public class Note {
    private String noteTitle;
    private String noteDescription;
    private String noteTime;
    private Date createDate;
    private Boolean isDeadlineNeeded;

    public Note() {
    }

    public Note(String noteTitle, String noteDescription, String noteTime, Date createDate, Boolean isDeadlineNeeded) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteTime = noteTime;
        this.createDate = createDate;
        this.isDeadlineNeeded = isDeadlineNeeded;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    @NonNull
    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    @Nullable
    public String getNoteDescription() {
        return noteDescription;
    }

    @Nullable
    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    @Nullable
    public String getNoteTime() {
        return noteTime;
    }

    @Nullable
    public void setNoteTime(String noteTime) {
        this.noteTime = noteTime;
    }

    @NonNull
    public Date getCreateDate() {
        return createDate;
    }

    @NonNull
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @NonNull
    public Boolean getDeadlineNeeded() {
        return isDeadlineNeeded;
    }

    @NonNull
    public void setDeadlineNeeded(Boolean deadlineNeeded) {
        isDeadlineNeeded = deadlineNeeded;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteTitle='" + noteTitle + '\'' +
                ", noteDescription='" + noteDescription + '\'' +
                ", noteTime='" + noteTime + '\'' +
                ", createDate=" + createDate +
                ", isDeadlineNeeded=" + isDeadlineNeeded +
                '}';
    }
}
