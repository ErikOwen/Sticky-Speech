package com.spinninggangstaz.stickyspeech;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class TakeNote extends Activity {
	private RelativeLayout rootView;
	private RelativeLayout microphoneView;
	private Button saveButton;
	private EditText textField;
	private ToggleButton microphone;
	private static final int REQUEST_CODE = 1234;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		initLayout();
		initOnClickListeners();
	}
	
	private void initLayout() {
		setContentView(R.layout.take_note);
		
		this.rootView = (RelativeLayout)findViewById(R.id.takeNoteRootView);
		this.microphoneView = (RelativeLayout)findViewById(R.id.microphoneLayout);
		this.saveButton = (Button)findViewById(R.id.saveButton);
		this.textField = (EditText)findViewById(R.id.noteTextBox);
		this.microphone = (ToggleButton)findViewById(R.id.toggleMicrophone);
		
		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.saveButton.setTypeface(font);
	}
	
	private void initOnClickListeners() {
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
			    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice recognition Demo...");
			    startActivityForResult(intent, REQUEST_CODE);
			}
		});
		
		this.saveButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Message curMessage = new Message(textField.getText().toString());
				try
				{
					MessageDB.addMessage(curMessage);
					MessageDB.saveMessages();
				}
				catch(Exception e) {
					//Toast.makeText(getApplicationContext(), "Unable to load file due to IO exception", Toast.LENGTH_LONG).show();
				}
				
				Intent startMessageHubActivity = new Intent(TakeNote.this, MessageHub.class);
				startActivity(startMessageHubActivity);
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
            this.textField.setText(beforeCurs + voiceInput + afterCurs);
            this.textField.setSelection(cursorSpot);
        }
        
        super.onActivityResult(requestCode, resultCode, data);
    }

}
