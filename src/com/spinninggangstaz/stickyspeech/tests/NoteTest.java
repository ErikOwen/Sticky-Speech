package com.spinninggangstaz.stickyspeech.tests;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.spinninggangstaz.stickyspeech.Note;

public class NoteTest extends AndroidTestCase {

	@Test
	public void testNoteString() {
		String text = "note contents";
		Note note = new Note(text);
		assertTrue(note.getText() == text);
	}
	
	@Test
	public void testGetTitle() {
		String string1 = "a really really spectacularly long title that" +
				" will surely be truncated before being presented to the user";
		String string2 = "note text";
		Note note = new Note(string2);
		note.setTitle(string1);

		assertSame(note.getText(), string2);
		assertSame(note.getTitle(), string1);
	}

	@Test
	public void testNoteStringCalendar() {
		String text = "note contents";
		Calendar cal = new GregorianCalendar(100, 1, 1);
		Note note = new Note(text, cal);
		assertTrue(note.getDate().equals(cal));
		assertTrue(note.getText() == text);
	}

	@Test
	public void testToString() {
		Note note = new Note("a note");
		assertTrue(note.toString() != null);
		assertTrue(note.toString() != "");
	}

	@Test
	public void testTitlePassthrough() {
		String string1 = "note contents 1";
		Note note = new Note(string1);
		assertSame(note.getText(), string1);
		assertSame(note.getTitle(), string1);

		String string2 = "note contents 2";
		note.setText(string2);
		assertSame(note.getText(), string2);
		assertSame(note.getTitle(), string2);
		
		String string3 = "a new title";
		note.setTitle(string3);
		assertSame(note.getText(), string2);
		assertSame(note.getTitle(), string3);
	}
	
	@Test
	public void testEmptyTitle() {
		String string1 = "note contents";
		Note note = new Note(string1);

		note.setTitle("");
		assertSame(note.getTitle(), string1);
		
		note.setTitle(null);
		assertSame(note.getTitle(), string1);
	}

}
