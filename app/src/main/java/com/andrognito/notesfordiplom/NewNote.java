package com.andrognito.notesfordiplom;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.andrognito.notesfordiplom.NoteRepository.ACTION_NEW_NOTE;
import static com.andrognito.notesfordiplom.NoteRepository.ACTION_UPDATE;

public class NewNote extends AppCompatActivity {

    private EditText newNoteTitle;
    private EditText newNoteDescription;
    private EditText newNoteDeadline;
    private CheckBox isDeadlineNeeded;
    private ImageButton pickDeadlineButton;
    private Toolbar createNoteToolbar;
    private Note newNote = new Note();
    private Calendar myCalendar;
    private int DIALOG_DATE = 1;
    private int year;
    private int monthOfYear;
    private int dayOfMonth;
    public static final String NOTE_ID = "NOTE_ID";
    private int actionType = ACTION_NEW_NOTE;
    private NoteRepository noteRepository = new NoteRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        newNoteTitle = (EditText) findViewById(R.id.edit_note_title);
        newNoteDescription = (EditText) findViewById(R.id.edit_note_description);
        newNoteDeadline = (EditText) findViewById(R.id.edit_deadline_date);
        pickDeadlineButton = (ImageButton) findViewById(R.id.date_deadline_picker);
        isDeadlineNeeded = (CheckBox) findViewById(R.id.cbx_dedline_needed);

        if (getIntent().hasExtra(NOTE_ID)) {
            actionType = 1;
            newNote = getIntent().getParcelableExtra(NOTE_ID);
            newNoteTitle.setText(newNote.getNoteTitle());
            newNoteDescription.setText(newNote.getNoteDescription());
            newNoteDeadline.setText(String.valueOf(newNote.getNoteTime()));
            isDeadlineNeeded.setChecked(newNote.getDeadlineNeeded().booleanValue());
        }

        createNoteToolbar = (Toolbar) findViewById(R.id.createNoteToolbar);
        setSupportActionBar(createNoteToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        createNoteToolbar.setNavigationOnClickListener(v -> onBackPressed());

        myCalendar = Calendar.getInstance();
        year = myCalendar.get(Calendar.YEAR);
        monthOfYear = myCalendar.get(Calendar.MONTH);
        dayOfMonth = myCalendar.get(Calendar.DATE);
        pickDeadlineButton.setOnClickListener(onNewNoteClickListener);
        isDeadlineNeeded.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked == false) {
                newNoteDeadline.setText("");
            }
        });
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(this, onDateSetListener, year, monthOfYear, dayOfMonth);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    View.OnClickListener onNewNoteClickListener = v -> {
        switch (v.getId()) {
            case R.id.date_deadline_picker:
                showDialog(DIALOG_DATE);
                break;
        }
    };

    private void updateLabel() {
        String myFormat = "dd.MM.yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        newNoteDeadline.setText(sdf.format(myCalendar.getTime()));
    }

    public void saveNote(MenuItem item) {
        Date currentTime = Calendar.getInstance().getTime();
        switch (actionType) {
            case ACTION_NEW_NOTE:
                newNote = new Note();
                newNote.setNoteTitle(newNoteTitle.getText().toString());
                newNote.setNoteDescription(newNoteDescription.getText().toString());
                newNote.setNoteTime(newNoteDeadline.getText().toString());
                newNote.setCreationDate(currentTime.getTime());
                newNote.setChangeDate(currentTime.getTime());
                newNote.setDeadlineNeeded(isDeadlineNeeded.isChecked());
                try {
                    noteRepository.saveNote(NewNote.this, newNote);
                } catch (NullPointerException e) {

                }
                break;
            case ACTION_UPDATE:
                newNote.setNoteTitle(newNoteTitle.getText().toString());
                newNote.setNoteDescription(newNoteDescription.getText().toString());
                newNote.setNoteTime(newNoteDeadline.getText().toString());
                newNote.setChangeDate(currentTime.getTime());
                newNote.setChangeDate(currentTime.getTime());
                newNote.setDeadlineNeeded(isDeadlineNeeded.isChecked());
                try {
                    noteRepository.saveNote(NewNote.this, newNote);
                } catch (NullPointerException e) {

                }
                break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_note_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_note:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
