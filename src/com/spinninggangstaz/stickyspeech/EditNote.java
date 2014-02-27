package com.spinninggangstaz.stickyspeech;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This class represents the screen used to edit notes
 *
 * @author Kevin
 * @date 2/8/14
 */
public class EditNote extends Activity {
    private LinedEditText noteText;
    private Button saveButton, newTitleButton;
    private TextView title;
    private int noteIndex;
    private Note currentNote;
    private String desiredTitle;

    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

		Bundle bundle = getIntent().getExtras();
		this.noteIndex = bundle.getInt("noteIndex");
        
		NoteDB.loadNotes();
        this.currentNote = NoteDB.getList().get(this.noteIndex);
		
        initLayout();
        initListeners();
    }

    /**
     * Lays out the gui.  Need to implement so it populates with an
     * actual note text.
     */
    private void initLayout() {
    	setContentView(R.layout.edit_note);
    	this.saveButton = (Button)findViewById(R.id.backButton);
    	this.newTitleButton = (Button)findViewById(R.id.titleButton);
        this.noteText = (LinedEditText)findViewById(R.id.editText);
        this.title = (TextView)findViewById(R.id.editNote);
        this.noteText.setText(this.currentNote.getText(), TextView.BufferType.EDITABLE);
        this.noteText.setSelection(this.currentNote.getText().length());
        
		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.title.setTypeface(font);
		this.saveButton.setTypeface(font);
        
    }
    
    private void initListeners() {
		ActivitySwipeDetector activitySwipeDetector = new ActivitySwipeDetector(this);
		noteText.setOnTouchListener(activitySwipeDetector);
		
    	this.saveButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				NoteDB.editNoteText(noteIndex, noteText.getText().toString());
				if(desiredTitle != null) {
					NoteDB.editNoteTitle(noteIndex, desiredTitle);
				}
				NoteDB.saveNotes();
				
				Intent returnToNoteHubActivity = new Intent(EditNote.this, NoteHubActivity.class);
				setResult(1, returnToNoteHubActivity);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
				finish();
			}
		});
    	
    	this.newTitleButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				AlertDialog.Builder builder = new AlertDialog.Builder(EditNote.this);
				builder.setTitle("Give this note a title: ");

				// Set up the input
				final EditText input = new EditText(EditNote.this);
				input.setHint(currentNote.getTitle());
				// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
				input.setInputType(InputType.TYPE_CLASS_TEXT);
				builder.setView(input);

				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				    	if(input.getText().toString().length() > 0) {
				    		desiredTitle = input.getText().toString();
				    	}
				    }
				});
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				        dialog.cancel();
				    }
				});

				builder.show();
			}
		});
    }
    
    protected void returnWithoutSaving() {
		Intent returnToNoteHubActivity = new Intent(EditNote.this, NoteHubActivity.class);
		setResult(55, returnToNoteHubActivity);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
		finish();
    }
}