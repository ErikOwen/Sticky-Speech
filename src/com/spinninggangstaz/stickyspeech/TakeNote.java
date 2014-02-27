package com.spinninggangstaz.stickyspeech;

import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class TakeNote extends Activity {
	private RelativeLayout rootView;
	private RelativeLayout microphoneView;
	private Button saveButton;
	private LinedEditText textField;
	private ToggleButton microphone;
	private static final int REQUEST_CODE = 1234;
	private TextView title;
	private boolean hasNewNote;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		this.hasNewNote = false;

		initLayout();
		initOnClickListeners();
	}

	private void initLayout() {
		setContentView(R.layout.take_note);

		this.rootView = (RelativeLayout)findViewById(R.id.takeNoteRootView);
		this.title = (TextView)findViewById(R.id.takeNoteTitle);
		this.microphoneView = (RelativeLayout)findViewById(R.id.microphoneLayout);
		this.saveButton = (Button)findViewById(R.id.saveButton);
		this.textField = (LinedEditText)findViewById(R.id.newNoteTextBox);
		this.microphone = (ToggleButton)findViewById(R.id.toggleMicrophone);

		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.title.setTypeface(font);
	}

	private void initOnClickListeners() {
		ActivitySwipeDetector activitySwipeDetector = new ActivitySwipeDetector(this);
		textField.setOnTouchListener(activitySwipeDetector);

		this.rootView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
		    @Override
		    public void onGlobalLayout() {
		        int heightDiff = rootView.getRootView().getHeight() - rootView.getHeight();
		        if (heightDiff > 100) { // if more than 100 pixels, its probably a keyboard...
		            microphoneView.setVisibility(View.GONE);
		        }
		        else {
		        	microphoneView.setVisibility(View.VISIBLE);
		        }
		     }
		});

		this.microphone.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
			    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Record Your Note...");
			    startActivityForResult(intent, REQUEST_CODE);
			}
		});

	    this.textField.addTextChangedListener(new TextWatcher() {

	        @Override
	        public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
	            if(cs.length() > 0) {
	            	//saveButton.setText("Save Note");
	            	saveButton.setBackgroundResource(R.drawable.save_icon_selector);
	            	hasNewNote = true;
	            }
	            else {
	            	//saveButton.setText("My Notes");
	            	saveButton.setBackgroundResource(R.drawable.next_arrow_selector);
	            	hasNewNote = false;
	            }
	        }

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {


			}
	    });

		this.saveButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if(hasNewNote) {
					Calendar date = Calendar.getInstance();
					Log.w("StickySpeech", "Current date: " + date.toString());
					Note curNote = new Note(textField.getText().toString(), date);
					NoteDB.loadNotes();
					NoteDB.addNote(curNote);
					NoteDB.saveNotes();
				}

				Intent startNoteHubActivity = new Intent(TakeNote.this, NoteHubActivity.class);
				startActivity(startNoteHubActivity);
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
			}
		});
	}

    /**
     * Handle the results from the voice recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {	
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            // Populate the wordsList with the String values the recognition engine thought it heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String voiceInput = matches.get(0);
            
            int cursorSpot = textField.getSelectionStart();
            String beforeCurs = this.textField.getText().toString().substring(0, cursorSpot);
            String afterCurs = this.textField.getText().toString().substring(cursorSpot);
            this.textField.getText().clear();
            this.textField.setText(beforeCurs + voiceInput + afterCurs);
            this.textField.setSelection(cursorSpot + voiceInput.length());
            Log.w("stickyspeech", "Text field text: " + textField.getText().toString());
        }
        
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    protected void gotoNoteHub() {
    	Intent startNoteHubActivity = new Intent(TakeNote.this, NoteHubActivity.class);
		startActivity(startNoteHubActivity);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

}