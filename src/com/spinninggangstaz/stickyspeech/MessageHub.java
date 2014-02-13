package com.spinninggangstaz.stickyspeech;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MessageHub extends ListActivity {
	
	public ArrayAdapter<Message> adapter;
	ArrayList<Message> msgList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_hub);
		
		MessageDB.loadMessages();
		msgList = MessageDB.getList();
	    adapter = new ArrayAdapter<Message>(this, android.R.layout.simple_list_item_1, MessageDB.getList());
	    setListAdapter(adapter);	
		
	}
	
	 @Override
     protected void onListItemClick(ListView l, View v, int position, long id) {
		 super.onListItemClick(l, v, position, id);
		 
		 Intent editNoteActivity = new Intent(MessageHub.this, EditMessage.class);
		 editNoteActivity.putExtra("messageIndex", position);
			
		 startActivity(editNoteActivity);
		 
     }

}
