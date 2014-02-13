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
 * This class represents the screen used to edit messages
 *
 * @author Kevin
 * @date 2/8/14
 */
public class EditMessage extends Activity {
    private LinedEditText messageText;
    private Button backButton;
    private TextView title;
    private int noteIndex;
    private Message currentMessage;

    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

		Bundle bundle = getIntent().getExtras();
		this.noteIndex = bundle.getInt("messageIndex");
        
		MessageDB.loadMessages();
        this.currentMessage = MessageDB.getList().get(this.noteIndex);
		
        initLayout();
        initListeners();
    }

    /**
     * Lays out the gui.  Need to implement so it populates with an
     * actual message text.
     */
    private void initLayout() {
    	setContentView(R.layout.edit_message);
    	this.backButton = (Button)findViewById(R.id.backButton);
        this.messageText = (LinedEditText)findViewById(R.id.editText);
        this.title = (TextView)findViewById(R.id.editNoteTitle);
        this.messageText.setText(this.currentMessage.toString(), TextView.BufferType.EDITABLE);
        this.messageText.setSelection(this.currentMessage.toString().length());
        
		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.title.setTypeface(font);
		this.backButton.setTypeface(font);
        
    }
    
    private void initListeners() {
		ActivitySwipeDetector activitySwipeDetector = new ActivitySwipeDetector(this);
		messageText.setOnTouchListener(activitySwipeDetector);
		
    	this.backButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				MessageDB.editMessage(noteIndex, messageText.getText().toString());
				MessageDB.saveMessages();
				
				Intent returnToMessageHubActivity = new Intent(EditMessage.this, MessageHub.class);
				setResult(1, returnToMessageHubActivity);        
				finish();
			}
		});
    }
    
    protected void returnWithoutSaving() {
		Intent returnToMessageHubActivity = new Intent(EditMessage.this, MessageHub.class);
		setResult(55, returnToMessageHubActivity);        
		finish();
    }
}