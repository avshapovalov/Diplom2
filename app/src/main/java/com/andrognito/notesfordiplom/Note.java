package com.andrognito.notesfordiplom;

import java.util.Date;

public class Note {
    private String noteTitle;
    private String noteDescription;
    private String noteTime;
    private Date creationDate;
    private Date changeDate;
    private Boolean isDeadlineNeeded;

    public Note() {
    }

    public Note(String noteTitle, String noteDescription, String noteTime, Date creationDate, Date changeDate, Boolean isDeadlineNeeded) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteTime = noteTime;
        this.creationDate = creationDate;
        this.changeDate = changeDate;
        this.isDeadlineNeeded = isDeadlineNeeded;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public String getNoteTime() {
        return noteTime;
    }

    public void setNoteTime(String noteTime) {
        this.noteTime = noteTime;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public Boolean getDeadlineNeeded() {
        return isDeadlineNeeded;
    }

    public void setDeadlineNeeded(Boolean deadlineNeeded) {
        isDeadlineNeeded = deadlineNeeded;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteTitle='" + noteTitle + '\'' +
                ", noteDescription='" + noteDescription + '\'' +
                ", noteTime='" + noteTime + '\'' +
                ", creationDate=" + creationDate +
                ", changeDate=" + changeDate +
                ", isDeadlineNeeded=" + isDeadlineNeeded +
                '}';
    }
}
