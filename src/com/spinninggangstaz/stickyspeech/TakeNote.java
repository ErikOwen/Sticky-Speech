package com.spinninggangstaz.stickyspeech;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TakeNote extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.take_note);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.take_note, menu);
		return true;
	}

}
