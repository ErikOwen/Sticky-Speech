package com.spinninggangstaz.stickyspeech;

import java.util.List;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MessageHub extends ListActivity {
	
	private ArrayAdapter<Message> adapter;
	private ListView list;
	private List<Message> msgList;
	private Button searchButton;
    private EditText inputSearch;
    private TextView title;
    private boolean searchBarVisible;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_hub);
		
		initLayout();
		initOnClickListeners();
		
		MessageDB.loadMessages();
		msgList = MessageDB.getList();
	    adapter = new ArrayAdapter<Message>(this, android.R.layout.simple_list_item_1, msgList);
	    
	    list.setAdapter(adapter);
		
	}
	
	private void initLayout() {
		//this.list = (ListView)findViewById(R.id.list);
		this.list = getListView();
		this.searchButton = (Button)findViewById(R.id.searchButton);
		this.inputSearch = (EditText) findViewById(R.id.inputSearch);
		this.title = (TextView)findViewById(R.id.MessageHubTitle);
		
		this.searchBarVisible = false;
		this.inputSearch.setVisibility(View.GONE);
		
		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.title.setTypeface(font);
		
		ActivitySwipeDetector activitySwipeDetector = new ActivitySwipeDetector(this);
		this.list.setOnTouchListener(activitySwipeDetector);
	}
	
	private void initOnClickListeners() {
	    this.inputSearch.addTextChangedListener(new TextWatcher() {
	        
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
	    
	    this.searchButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				if(searchBarVisible) {
					inputSearch.setVisibility(View.GONE);
					inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
					searchBarVisible = false;
				}
				else {
					inputSearch.setVisibility(View.VISIBLE);
				    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
				    inputSearch.requestFocus();
					searchBarVisible = true;
				}
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
