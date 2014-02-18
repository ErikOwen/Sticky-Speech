package com.spinninggangstaz.stickyspeech;

import android.util.Log;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDB {
	static List<Note> noteList = new ArrayList<Note>();
	static final String dbDirectory = "memoData";
	static final String dbFile = "memoData/memoData.txt";
	
	public static void addNote(Note msg) {
		noteList.add(msg);
	}
	
	public static void deleteNote(int position) {
		noteList.remove(position);
	}
	
	public static void editNoteText(int index, String str)
	{
		noteList.get(index).editText(str);
		//noteList.get(index).setTitle(str);
	}
	
	public static List<Note> getList() {
		return noteList;
	}

    public static void saveNotes()
    {
        try {
            writeFile();
        }
        catch (IOException ioe) {
            Log.w("StickySpeech", "Unable to write to file " + dbFile + ": " + ioe.getMessage());
        }
    }

	private static void writeFile() throws IOException, FileNotFoundException {
		FileOutputStream fout = null;
		ObjectOutputStream out = null;
		File contextDir = StickySpeechApplication.getAppContext().getFilesDir();
		File dir = new File(contextDir, dbDirectory);
		File file = new File(contextDir, dbFile);
		
		if(!dir.isDirectory()) {
			dir.mkdir();
		}
		if(!file.isFile()) {
			file.createNewFile();
		}
		fout = new FileOutputStream(file);
		        
		out = new ObjectOutputStream(fout);
		out.writeObject(noteList);
		out.flush();
		out.close();
		fout.close();
	}

    public static void loadNotes()
    {
        try {
        	loadFromFile();
        }
        catch (Exception ex) {
            Log.w("stickyspeech", "exception in NoteDB load : " + ex.getMessage());
        }
    }

	@SuppressWarnings("unchecked")
	private static void loadFromFile() throws FileNotFoundException,
			StreamCorruptedException, IOException, OptionalDataException,
			ClassNotFoundException {
		File file = new File(StickySpeechApplication.getAppContext().getFilesDir(), dbFile);
		noteList.clear();
		if (file.exists() && file.length() > 0) {
			InputStream reader = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(reader);
		    List<Note> loadedList = (ArrayList<Note>)in.readObject();
		    in.close();

			noteList.addAll(loadedList);
		}
	}


}
