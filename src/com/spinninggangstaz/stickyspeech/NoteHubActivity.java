package com.spinninggangstaz.stickyspeech;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nhaarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.contextualundo.ContextualUndoAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.contextualundo.ContextualUndoAdapter.DeleteItemCallback;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingRightInAnimationAdapter;

/**
 * The view of the app's list of notes 
 */
public class NoteHubActivity extends ListActivity implements DeleteItemCallback
{
	private NoteAdapter adapter;
	private ListView list;
	private List<Note> noteList;
	private Button newNoteButton, searchButton;
	private EditText inputSearch;
	private TextView title;
	private boolean searchBarVisible;
	private LinearLayout rootView;

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
		
	    ContextualUndoAdapter contextualUndoAdapter = new ContextualUndoAdapter(adapter,
	    		R.layout.undo_row, R.id.undo_row_undobutton, this);
	    SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(contextualUndoAdapter);
	    
	    swingBottomInAnimationAdapter.setAbsListView(getListView());
	    contextualUndoAdapter.setAbsListView(getListView());
	    list.setAdapter(swingBottomInAnimationAdapter);
	}

	private void initLayout() {
		this.rootView = (LinearLayout)findViewById(R.id.noteHubRoot);
		this.list = getListView();
		this.searchButton = (Button)findViewById(R.id.searchButton);
		this.newNoteButton = (Button)findViewById(R.id.newNoteButton);
		this.inputSearch = (EditText) findViewById(R.id.inputSearch);
		this.title = (TextView)findViewById(R.id.NoteHubTitle);

		this.searchBarVisible = false;
		this.inputSearch.setVisibility(View.GONE);

		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.title.setTypeface(font);
	}

	private void initOnClickListeners() {
		this.inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
				if(!noteList.isEmpty()) {
					adapter.getFilter().filter(cs.toString());
				}
			}

			@Override
			public void afterTextChanged(Editable s) { }

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
		});

		this.searchButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				if(!searchBarVisible) {
					inputSearch.setVisibility(View.VISIBLE);
					inputSearch.getText().clear();
					inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
					inputSearch.requestFocus();
					searchBarVisible = true;
				}
				else {
					inputSearch.setVisibility(View.GONE);
					inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
					searchBarVisible = false;
				}
			}
		});
		this.newNoteButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
				goToNewNote();
			}
		});

		this.getListView().setLongClickable(true);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Intent editNoteActivity = new Intent(NoteHubActivity.this, EditNote.class);
		editNoteActivity.putExtra("noteIndex", adapter.getPosition(adapter.getItem(position)));

		InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		inputSearch.setVisibility(View.GONE);
		inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
		
		startActivityForResult(editNoteActivity, 1);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		findViewById(R.id.noteHubRoot).requestFocus();
		this.inputSearch.clearFocus();
		if (requestCode == 1 && resultCode == 1) {
			noteList = NoteDB.getList();
			adapter.resetDataSet(noteList);
			adapter.notifyDataSetChanged();
		}
		else if(requestCode == 1 && resultCode == 55) {
			Log.w("StickySpeech", "Returned without saving anything");
		}
	}

	protected void goToNewNote() {
		Intent startNewNoteActivity = new Intent(NoteHubActivity.this, TakeNote.class);
		
		InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		inputSearch.setVisibility(View.GONE);
		inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
		
		startActivity(startNewNoteActivity);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
	}

	@Override
	public void deleteItem(int position) {
		deleteNoteAndUpdate(position);
	}

	private void deleteNoteAndUpdate(int position) {
		NoteDB.loadNotes();
		NoteDB.deleteNote(position);
		NoteDB.saveNotes();
		noteList = NoteDB.getList();

		adapter.resetDataSet(noteList);
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onBackPressed()
	{
		this.inputSearch.setVisibility(View.GONE);
		super.onBackPressed();
	}
}