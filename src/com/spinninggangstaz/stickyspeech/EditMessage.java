package com.spinninggangstaz.stickyspeech;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This class represents the screen used to edit messages
 *
 * @author Kevin
 * @date 2/8/14
 */
public class EditMessage extends Activity {
    private EditText messageText;
    private Button backButton;
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
    }

    /**
     * Lays out the gui.  Need to implement so it populates with an
     * actual message text.
     */
    private void initLayout() {
    	setContentView(R.layout.edit_message);
        this.messageText = (EditText)findViewById(R.id.editText);
        this.messageText.setText(this.currentMessage.toString(), TextView.BufferType.EDITABLE);
        
    }
}