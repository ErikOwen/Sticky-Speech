package com.spinninggangstaz.stickyspeech;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.Window;
import android.widget.TextView;

public class TakeNote extends Activity {
	private TextView title;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		initLayout();
	}
	
	private void initLayout() {
		setContentView(R.layout.take_note);
		
		this.title = (TextView)findViewById(R.id.takeNoteTitle);
		
		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.title.setTypeface(font);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.take_note, menu);
		return true;
	}

}
