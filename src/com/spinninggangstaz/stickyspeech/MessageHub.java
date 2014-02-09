package com.spinninggangstaz.stickyspeech;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MessageHub extends ListActivity {
	
	public ArrayAdapter<Message> adapter;
	private ListView list;
	ArrayList<Message> msgList;
	
	// Search EditText
    EditText inputSearch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_hub);
		
		initLayout();
		inputSearch = (EditText) findViewById(R.id.inputSearch);
		
		MessageDB.loadMessages();
		msgList = MessageDB.getList();
	    adapter = new ArrayAdapter<Message>(this, android.R.layout.simple_list_item_1, msgList);
	    setListAdapter(adapter);	
	    
	    inputSearch.addTextChangedListener(new TextWatcher() {
	        
	        @Override
	        public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
	            // When user changed the Text
	            MessageHub.this.adapter.getFilter().filter(cs);   
	        }
	         
	        @Override
	        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
	                int arg3) {
	            // TODO Auto-generated method stub
	             
	        }
	         
	        @Override
	        public void afterTextChanged(Editable arg0) {
	            // TODO Auto-generated method stub                          
	        }
	    });
		
	}
	
	private void initLayout() {
		//this.list = (ListView)findViewById(R.id.list);
		this.list = getListView();
		
		ActivitySwipeDetector activitySwipeDetector = new ActivitySwipeDetector(this);
		list.setOnTouchListener(activitySwipeDetector);
	}
	
	 @Override
     protected void onListItemClick(ListView l, View v, int position, long id) {
		 super.onListItemClick(l, v, position, id);
		 
		 Intent editNoteActivity = new Intent(MessageHub.this, EditMessage.class);
		 editNoteActivity.putExtra("messageIndex", position);
			
		 startActivityForResult(editNoteActivity, 1);
		 
     }
	 
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode == 1) {
			msgList = MessageDB.getList();
			adapter.notifyDataSetChanged();
		}
		else if(requestCode == 1 && resultCode == 55) {
			Log.w("StickySpeech", "Returned without saving anything");
		}
	}
	
	protected void goToNewNote() {
	    Intent startNewNoteActivity = new Intent(MessageHub.this, TakeNote.class);
		startActivity(startNewNoteActivity);
	 }

}
