package com.spinninggangstaz.stickyspeech;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class TakeNote extends Activity {
	private RelativeLayout rootView;
	private RelativeLayout microphoneView;
	private TextView title;
	private EditText textField;
	private ToggleButton microphone;
	private boolean isRecording;
	private static final int REQUEST_CODE = 1234;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		this.isRecording = false;
		
		initLayout();
	}
	
	private void initLayout() {
		setContentView(R.layout.take_note);
		
		this.rootView = (RelativeLayout)findViewById(R.id.takeNoteRootView);
		this.microphoneView = (RelativeLayout)findViewById(R.id.microphoneLayout);
		this.title = (TextView)findViewById(R.id.takeNoteTitle);
		this.textField = (EditText)findViewById(R.id.noteTextBox);
		this.microphone = (ToggleButton)findViewById(R.id.toggleMicrophone);
		
		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.title.setTypeface(font);
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
				if(!isRecording) {
			        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
			                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice recognition Demo...");
			        startActivityForResult(intent, REQUEST_CODE);
				}
			}
		});
	}

}
