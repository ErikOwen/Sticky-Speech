package com.spinninggangstaz.stickyspeech;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * This class represents the screen used to edit notes
 *
 * @author Kevin
 * @date 2/8/14
 */
public class EditNote extends Activity {
    private LinedEditText noteText;
    private Button backButton;
    private Button cancelButton;
    private TextView title;
    private int noteIndex;
    private Note currentNote;

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
    	this.backButton = (Button)findViewById(R.id.backButton);
    	this.cancelButton = (Button)findViewById(R.id.cancelButton);
        this.noteText = (LinedEditText)findViewById(R.id.editText);
        this.title = (TextView)findViewById(R.id.editNoteTitle);
        this.noteText.setText(this.currentNote.toString(), TextView.BufferType.EDITABLE);
        this.noteText.setSelection(this.currentNote.toString().length());
        
		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.title.setTypeface(font);
		this.backButton.setTypeface(font);
        
    }
    
    private void initListeners() {
		ActivitySwipeDetector activitySwipeDetector = new ActivitySwipeDetector(this);
		noteText.setOnTouchListener(activitySwipeDetector);
		
    	this.backButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				NoteDB.editNoteText(noteIndex, noteText.getText().toString());
				NoteDB.saveNotes();
				
				Intent returnToNoteHubActivity = new Intent(EditNote.this, NoteHubActivity.class);
				setResult(1, returnToNoteHubActivity);        
				finish();
			}
		});
    	
    	this.cancelButton.setOnClickListener(new OnClickListener() {
    		public void onClick(View view) {
				EditNote.this.returnWithoutSaving();
    		}
    	});
    }
    
    protected void returnWithoutSaving() {
		Intent returnToNoteHubActivity = new Intent(EditNote.this, NoteHubActivity.class);
		setResult(55, returnToNoteHubActivity);        
		finish();
    }
}