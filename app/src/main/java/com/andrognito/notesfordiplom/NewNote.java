package com.andrognito.notesfordiplom;

import android.app.DatePickerDialog;
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

public class NewNote extends AppCompatActivity {

    private EditText newNoteTitle;
    private EditText newNoteDescription;
    private EditText newNoteDeadline;
    private CheckBox isDeadlineNeeded;
    private ImageButton pickDeadlineButton;
    private Note newNote;
    private String strDeadlineDate;
    private Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        newNoteTitle = (EditText) findViewById(R.id.edit_note_title);
        newNoteDescription = (EditText) findViewById(R.id.edit_note_description);
        newNoteDeadline = (EditText) findViewById(R.id.edit_deadline_date);
        pickDeadlineButton = (ImageButton) findViewById(R.id.date_deadline_picker);
        isDeadlineNeeded = findViewById(R.id.cbx_dedline_needed);

        Toolbar createNoteToolbar = (Toolbar) findViewById(R.id.createNoteToolbar);
        setSupportActionBar(createNoteToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        createNoteToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

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

        pickDeadlineButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(NewNote.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabel() {
        String myFormat = "dd.MM.yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        newNoteDeadline.setText(sdf.format(myCalendar.getTime()));
    }

    public void saveNote(MenuItem item) {
        Date currentTime = Calendar.getInstance().getTime();
        newNote = new Note(newNoteTitle.getText().toString(),
                newNoteDescription.getText().toString(),
                newNoteDeadline.getText().toString(), currentTime);
        try {
            NoteSaver noteSaver = new NoteSaver();
            noteSaver.saveNote(NewNote.this, newNote);
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_note_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_note: //Your task
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
