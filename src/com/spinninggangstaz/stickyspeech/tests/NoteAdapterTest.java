package com.spinninggangstaz.stickyspeech.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.spinninggangstaz.stickyspeech.Note;
import com.spinninggangstaz.stickyspeech.NoteAdapter;

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

}
