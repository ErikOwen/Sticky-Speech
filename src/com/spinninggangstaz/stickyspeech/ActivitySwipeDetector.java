package com.spinninggangstaz.stickyspeech;

import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ActivitySwipeDetector implements View.OnTouchListener {

static final String logTag = "ActivitySwipeDetector";
private Activity activity;
static final int MIN_DISTANCE = 100;
private float downX, downY, upX, upY;

public ActivitySwipeDetector(Activity activity){
    this.activity = activity;
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
}

public void onTopToBottomSwipe(){
    Log.i(logTag, "onTopToBottomSwipe!");
    //activity.doSomething();
}

public void onBottomToTopSwipe(){
    Log.i(logTag, "onBottomToTopSwipe!");
    //activity.doSomething();
}

public boolean onTouch(View v, MotionEvent event) {
    switch(event.getAction()){
        case MotionEvent.ACTION_DOWN: {
            downX = event.getX();
            downY = event.getY();
            return true;
        }
        case MotionEvent.ACTION_UP: {
            upX = event.getX();
            upY = event.getY();

            float deltaX = downX - upX;
            float deltaY = downY - upY;

            // swipe horizontal?
            if(Math.abs(deltaX) > MIN_DISTANCE){
                // left or right
                if(deltaX < 0) { this.onLeftToRightSwipe(); return true; }
                if(deltaX > 0) { this.onRightToLeftSwipe(); return true; }
            }
            else {
                    Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE);
                    return false; // We don't consume the event
            }

            // swipe vertical?
            if(Math.abs(deltaY) > MIN_DISTANCE){
                // top or down
                if(deltaY < 0) { this.onTopToBottomSwipe(); return true; }
                if(deltaY > 0) { this.onBottomToTopSwipe(); return true; }
            }
            else {
                    Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE);
                    return false; // We don't consume the event
            }

            return true;
        }
    }
    return false;
}

}
