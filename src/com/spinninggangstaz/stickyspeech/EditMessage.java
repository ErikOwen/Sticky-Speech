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
    private TextView title;
    private Button backButton;

    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        initLayout();
    }

    /**
     * Lays out the gui.  Need to implement so it populates with an
     * actual message text.
     */
    private void initLayout() {
        this.messageText = (EditText)findViewById(R.id.editText);
        this.messageText.setText("hello", TextView.BufferType.EDITABLE);
        setContentView(R.layout.edit_message);
        Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
        this.title.setTypeface(font);
    }
}