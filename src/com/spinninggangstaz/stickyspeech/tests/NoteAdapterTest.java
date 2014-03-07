package com.spinninggangstaz.stickyspeech.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import android.test.AndroidTestCase;
import android.view.View;
import android.widget.TextView;

import com.spinninggangstaz.stickyspeech.Note;
import com.spinninggangstaz.stickyspeech.NoteAdapter;
import com.spinninggangstaz.stickyspeech.R;

public class NoteAdapterTest extends AndroidTestCase {

	private Note firstnote = new Note("this note is first in the list");

	private List<Note> createTestNoteList() {
		List<Note> list = new ArrayList<Note>();
		
		list.add(firstnote );
		list.add(new Note("This is a note"));
		list.add(new Note("This note has an interesting word in it"));
		Note titledNote = new Note("This note has a custom title");
		titledNote.setTitle("This is a title");
		list.add(titledNote);
		
		return list;
	}
	
	@Test
	public void testNoteAdapter() {
		NoteAdapter adapter = new NoteAdapter(getContext(), 0, createTestNoteList());
		assertEquals(adapter.getPosition(firstnote), 0);
	}

	@Test
	public void testGetFilter() {
		NoteAdapter adapter = new NoteAdapter(getContext(), 0, createTestNoteList());
		assertNotNull(adapter.getFilter());
	}
	
	@Test
	public void testTitleTruncation() {
		List<Note> list = new ArrayList<Note>();
		Note note = new Note("note");
		note.setTitle("really really really really stupendously long title that will surely be truncated");
		NoteAdapter adapter = new NoteAdapter(getContext(), 0, list);
		
		View view = adapter.getView(0, null, null);
		TextView toptextview = (TextView)view.findViewById(R.id.toptext);
		String toptext = toptextview.toString();
		
		assertTrue(toptext.contains("..."));
	}

}
