package com.spinninggangstaz.stickyspeech;

import android.app.Activity;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class ActivitySwipeDetector implements View.OnTouchListener {

	static final String logTag = "ActivitySwipeDetector";
	private Activity activity;
	private GestureDetectorCompat detector;
	
	public ActivitySwipeDetector(Activity activity){
	    this.activity = activity;
	    detector = new GestureDetectorCompat(activity, new FlingListener());
	}
	
	public void onRightToLeftSwipe(){
	    Log.i(logTag, "RightToLeftSwipe!");
	    if(activity instanceof TakeNote) {
	    	((TakeNote)activity).goToMessageHub();
	    }
	
	}
	
	public void onLeftToRightSwipe(){
	    Log.i(logTag, "LeftToRightSwipe!");
	    if(activity instanceof MessageHub) {
	    	((MessageHub)activity).goToNewNote();
	    }
	    else if(activity instanceof EditMessage) {
	    	((EditMessage)activity).returnWithoutSaving();
	    }
	}
	
	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		// TODO Auto-generated method stub
		return this.detector.onTouchEvent(event);
	}

	class FlingListener extends GestureDetector.SimpleOnGestureListener {
		private static final float MIN_VELOCITY = 500;

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			Log.i(logTag, "Fling detected. velocityX was " + velocityX);
			if (velocityX > MIN_VELOCITY) {
				onLeftToRightSwipe();
				return true;
			}
			if (velocityX < -MIN_VELOCITY) {
				onRightToLeftSwipe();
				return true;
			}
			return false;
		}
	}
}
