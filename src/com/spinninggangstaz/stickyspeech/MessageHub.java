package com.spinninggangstaz.stickyspeech;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MessageHub extends ListActivity {
	
	public ArrayAdapter<Message> adapter;
	ArrayList<Message> msgList;
	
	// Search EditText
    EditText inputSearch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_hub);
		
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
	
	 @Override
     protected void onListItemClick(ListView l, View v, int position, long id) {
		 super.onListItemClick(l, v, position, id);
		 
		 Intent editNoteActivity = new Intent(MessageHub.this, EditMessage.class);
		 editNoteActivity.putExtra("messageIndex", position);
			
		 startActivityForResult(editNoteActivity, 1);
		 
     }
	 
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			msgList = MessageDB.getList();
			adapter.notifyDataSetChanged();
		}
	}

}
