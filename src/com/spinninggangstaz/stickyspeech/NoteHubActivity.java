package com.spinninggangstaz.stickyspeech;

import java.util.List;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * The view of the app's list of notes 
 */
public class NoteHubActivity extends ListActivity {
	
	private ArrayAdapter<Note> adapter;
	private ListView list;
	private List<Note> noteList;
	private Button newNoteButton, searchButton;
    private EditText inputSearch;
    private TextView title;
    private boolean searchBarVisible;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_hub);
		
		initLayout();
		initOnClickListeners();
		
		NoteDB.loadNotes();
		noteList = NoteDB.getList();
	    adapter = new NoteAdapter(this, android.R.layout.simple_list_item_1, noteList);
	    
	    list.setAdapter(adapter);
		
	}
	
	private void initLayout() {
		this.list = getListView();
		this.searchButton = (Button)findViewById(R.id.searchButton);
		this.newNoteButton = (Button)findViewById(R.id.newNoteButton);
		this.inputSearch = (EditText) findViewById(R.id.inputSearch);
		this.title = (TextView)findViewById(R.id.NoteHubTitle);
		
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
	            adapter.getFilter().filter(cs.toString());   
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
	    this.newNoteButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				goToNewNote();
			}
		});
	    
	    this.getListView().setLongClickable(true);
	    this.getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
	         public boolean onItemLongClick(AdapterView<?> parent, View v, final int position, long id) {
	        	 AlertDialog.Builder aBuilder = new AlertDialog.Builder(NoteHubActivity.this);
	        	 aBuilder.setTitle("Delete Note Confirmation");
	        	 aBuilder.setMessage("Do you really want to delete \"" + adapter.getItem(position).getTitle() + "\"?");
	        	 aBuilder.setCancelable(false);
	        	 aBuilder.setIcon(R.drawable.delete_icon);
	        	 /*.setIcon(android.R.drawable.ic_dialog_alert)*/
	        	 aBuilder.setNegativeButton(getResources().getString(R.string.noOption), new DialogInterface.OnClickListener() {

		        	     public void onClick(DialogInterface dialog, int whichButton) {
		        	    	 
		         }});
	        	 
	        	 aBuilder.setPositiveButton(getResources().getString(R.string.yesOption), new DialogInterface.OnClickListener() {

	        	     public void onClick(DialogInterface dialog, int whichButton) {
	        	         //noteList.remove(position);
	        	         NoteDB.loadNotes();
	        	         NoteDB.deleteNote(position);
	        	         NoteDB.saveNotes();
	        	         noteList = NoteDB.getList();
	        	         
	        	         adapter.notifyDataSetChanged();
	        	 }});
	        	 
	        	 aBuilder.show();
	             return true;
	         }
	     });
	}
	
	 @Override
     protected void onListItemClick(ListView l, View v, int position, long id) {
		 super.onListItemClick(l, v, position, id);
		 
		 Intent editNoteActivity = new Intent(NoteHubActivity.this, EditNote.class);
		 editNoteActivity.putExtra("noteIndex", position);
			
		 startActivityForResult(editNoteActivity, 1);
		 overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		 
     }
	 
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode == 1) {
			noteList = NoteDB.getList();
			adapter.notifyDataSetChanged();
		}
		else if(requestCode == 1 && resultCode == 55) {
			Log.w("StickySpeech", "Returned without saving anything");
		}
	}
	
	protected void goToNewNote() {
	    Intent startNewNoteActivity = new Intent(NoteHubActivity.this, TakeNote.class);
		startActivity(startNewNoteActivity);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
	 }


}
