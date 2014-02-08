package com.spinninggangstaz.stickyspeech;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.ListActivity;
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
						
		Message m1 = new Message("This is message 1");
		Message m2 = new Message("This is message 2");
		
		MessageDB.addMessage(m1);
		MessageDB.addMessage(m2);		
	    
	    adapter = new ArrayAdapter<Message>(this, android.R.layout.simple_list_item_1, msgList);
	    setListAdapter(adapter);		
		
	}
	
	 @Override
     protected void onListItemClick(ListView l, View v, int position, long id) {
		 super.onListItemClick(l, v, position, id);
     }
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.message_hub, menu);
		return true;
	}

}
